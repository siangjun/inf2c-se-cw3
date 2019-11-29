package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * The Server class is the class that processes the queries and bookings that are passed to the system.
 */
public class Server {
	private ServerDataInterface serverData;
	
	public Server(ServerDataInterface sdata) {
		this.serverData = sdata;
	}
	
	/**
	 * Returns <code>boolean</code> whether the bike matches give query
	 * @param query query to be checked against
	 * @param bike bike to check against query
	 * @return <code>true</code> or <code>false</code> depending whether bike matches the query
	 */
	private boolean matchesQuery(Query query, Bike bike) {
		return bike.getType().getSubType().equals(query.getRequestedType().getSubType());
	}

	/**
	 * Searches the server data for providers which are near the query location,
	 * then searches each provider for bikes that match the criteria in the query.
	 * @param query Query that is passed by the system to the server.
	 * @return an {@link ArrayList} of {@link Quote} that matches the query.
	 */
	public ArrayList<Quote> getQuotes(Query query) {
		ArrayList<Quote> availableQuotes = new ArrayList<Quote>();
		
		serverData.getProviders().forEach((provider) -> {
			if (provider.getLocation().isNearTo(query.getLocation())){
				provider.getBikes().forEach((bike) -> {
					if (!bike.isTaken(query.getDateRange()) &&
							matchesQuery(query, bike)) {
						BigDecimal bikePrice = provider.
							getPriceForBike(bike, query.getDateRange()); 
						BigDecimal deposit = bike.
							getValue(query.getDateRange().getStart());

						availableQuotes.add(
								new Quote(bike, provider, query.getDateRange(),
										bikePrice, 
										deposit));
					}
				});
			}
		});
		
		return availableQuotes;
	}

	/**
	 * Checks whether the quotes are valid.
	 * @param quotes Quotes that are passed to the server by the system
	 * @return <code>true</code> if the quotes are valid, <code>false</code> otherwise.
	 */
	private static boolean checkQuotes(Quote[] quotes){
		Provider prov = quotes[0].getProvider();
		DateRange dateRange = quotes[0].getDateRange();
		for (Quote q : quotes){
			if (!q.getProvider().equals(prov)){
				assert(false);
				return false;
			}
			if (!q.getDateRange().equals(dateRange)){
				assert(false);
				return false;
			}
			if (!q.getProvider().getBikes().contains(q.getBike())){
				assert(false);
				return false;
			}
		}
		return true;
	}

	/**
	 * All of the quotes should be from the same provider as delivery
	 * and return to a partner should be possible (and not to partners)
	 * @param customer the person booking the quote
	 * @param quotes an array of quotes to book
	 * @param paymentData details of payment
	 * @param wantsDelivery boolean on whether the customer wants delivery
	 * @param location location to deliver to if null and customer wants delivery it will be taken from the customer data
	 * @return the unique booking number
	 * @throws BikesUnavailableException if the bike in the quote is unavailable.
	 * @throws PaymentRefusedException if the payment fails.
	 */
	public Integer bookQuote(Customer customer,
			Quote[] quotes,
			PaymentService.PaymentData paymentData,
			boolean wantsDelivery,
			Location location) 
					throws BikesUnavailableException, PaymentRefusedException {
		// Assuming that the bookQuote is called from inside the system
		// If it were otherwise those asserts should be changed exceptions
		assert(customer != null);
		assert(quotes != null);
		assert(paymentData != null);
		assert(quotes.length > 0); // cannot book 0 quotes

		assert(checkQuotes(quotes));
		assert(this.serverData.getProviders().contains(quotes[0].getProvider())); // check whether the provider is in the system, so there are no mistakes in the tests


		if (location == null) {
			location = customer.getLocation();
		}
		DateRange dateRange = quotes[0].getDateRange();
		Provider provider = quotes[0].getProvider();
		Booking booking = new Booking(provider);

		boolean succ = true;
		BigDecimal price = new BigDecimal(0);
		BigDecimal deposit = new BigDecimal(0);
		for (Quote q: quotes) {
			boolean s = q.getBike().lock(q);
			if (!s) {
				succ = false;
				break;
			} else {
				booking.addQuote(q);
				price = price.add(q.getPrice());
				deposit = deposit.add(q.getDeposit());
			}
		}

		if (!succ) {
			booking.freeBikes();
			throw new BikesUnavailableException(); // throws for things we have no control over
		}
		if (!PaymentServiceFactory.getPaymentService().
				confirmPayment(paymentData, price.add(deposit))) {
			booking.freeBikes();
			throw new PaymentRefusedException();
		}
		
		booking.setFinalised(deposit, paymentData);
		
		if (wantsDelivery) {
			DeliveryServiceFactory.getDeliveryService().scheduleDelivery(
					booking,
					provider.getLocation(),
					location,
					dateRange.getStart());
			booking.setDeliveryState(DeliveryState.AwaitingDelivery);
		}

		return this.serverData.addBooking(booking);
	}

	/**
	 * Function to be called when the bike is returned to the {@link Provider} or it's partner.
	 * Arranges appropriate delivery actions if returned to partner.
	 * @param provider The {@link Provider} that's being returned to.
	 * @param bookingNumber The unique number associated with the booking.
	 * @throws ProviderIsNotAPartnerException When the {@link Provider} given is not a partner nor
	 *         the original owner.
	 */
	public void returnBike(Provider provider, int bookingNumber)
			throws ProviderIsNotAPartnerException {
		assert(this.serverData.getProviders().contains(provider)); // check whether the provider is in the system, so there are no mistakes in the tests
		Booking booking = this.serverData.getBooking(bookingNumber);
		assert(booking != null);
		if (booking.getProvider().equals(provider)) { 
			// Delivered to the original provider
			booking.resolveDeposit();
			booking.freeBikes();
		} else if (booking.getProvider().isPartnerWith(provider)) { 
			booking.setDeliveryState(DeliveryState.AwaitingReturn);
			booking.wasGivenToPartner();
			DeliveryServiceFactory.getDeliveryService().scheduleDelivery(
					booking, 
					provider.getLocation(), 
					booking.getProvider().getLocation(),
					booking.getDateRange().getEnd()); 
			// The Booking will resolve itself when it is delivered
		} else {
			throw new ProviderIsNotAPartnerException();
		}

	}

	/**
	 * @return the server data.
	 */
	ServerDataInterface getServerData() {
		return this.serverData;
	}
	
	@SuppressWarnings("serial")
	public class BikesUnavailableException extends Exception {
	}
	@SuppressWarnings("serial")
	public class PaymentRefusedException extends Exception {
	}
	@SuppressWarnings("serial")
	public class ProviderIsNotAPartnerException extends Exception {
	}
			

}
