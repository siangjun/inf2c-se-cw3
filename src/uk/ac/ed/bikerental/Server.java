package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

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

	public ArrayList<Quote> getQuotes(Query query) {
		ArrayList<Quote> availableQuotes = new ArrayList<Quote>();
		
		serverData.getProviders().forEach((provider) -> {
			if (provider.getLocation().isNearTo(query.getLocation())){
				provider.getBikes().forEach((bike) -> {
					if (!bike.isTaken(query.getDateRange())){
						if (matchesQuery(query, bike)) {
							BigDecimal bikePrice = provider.getPriceForBike(bike, query.getDateRange()); 
							BigDecimal deposit = bike.getValue();

							availableQuotes.add(
									new Quote(bike, provider, query.getDateRange(),
											bikePrice, 
											deposit));
						}
					}
				});
			}
		});
		
		return availableQuotes;
	}

	private static boolean checkQuotes(Quote[] quotes){
		Provider prov = quotes[0].getProvider();
		DateRange dateRange = quotes[0].getDateRange();
		for (Quote q : quotes){
			if (!q.getProvider().equals(prov)){
				return false;
			}
			if (!q.getDateRange().equals(dateRange)){
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
		// If it were otherwise those asserts should be exceptions
		assert(customer != null);
		assert(quotes != null);
		assert(paymentData != null);
		assert(quotes.length > 0); // cannot book 0 quotes

		assert(checkQuotes(quotes));


		if (location == null) {
			location = customer.getLocation();
		}
		DateRange dateRange = quotes[0].getDateRange();
		Provider provider = quotes[0].getProvider();
		Booking booking = new Booking(customer, provider);

		boolean succ = true;
		BigDecimal price = new BigDecimal(0);
		BigDecimal depositRate = provider.getDepositRate();
		for (Quote q: quotes) {
			boolean s = q.getBike().lock(q);
			if (!s) {
				succ = false;
				break;
			} else {
				booking.addQuote(q);
				price = price.add(q.getPrice());
			}
		}

		if (!succ) {
			booking.freeBikes();
			throw new BikesUnavailableException(); // throws for things we have no control over
		}
		if (!PaymentServiceFactory.getPaymentService().
				confirmPayment(paymentData, price.add(price.multiply(depositRate)))) {
			booking.freeBikes();
			throw new PaymentRefusedException();
		}
		
		booking.setFinalised(price.multiply(depositRate), paymentData);
		
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
	
	public void returnBike(Provider provider, int bookingNumber) 
			throws ProviderIsNotAPartnerException {
		Booking booking = this.serverData.getBooking(bookingNumber);
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
					LocalDate.now());   // TODO: is it possible to change it such that it gets the return date?
			// The Booking will resolve itself when it is delivered
		} else {
			throw new ProviderIsNotAPartnerException();
		}

	}

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
