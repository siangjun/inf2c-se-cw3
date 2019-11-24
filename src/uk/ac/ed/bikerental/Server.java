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

	public ArrayList<Quote> getQuotes(Query query) {  // TODO: why is there customer?
		ArrayList<Quote> availableQuotes = new ArrayList<Quote>();
		
		serverData.getProviders().forEach((provider) -> {
			provider.getBikes().forEach((bike) -> {
				if (!bike.isTaken(query))
					if (matchesQuery(query, bike)) {
						BigDecimal bikePrice = bike.getPrice();
						BigDecimal deposit = bikePrice.multiply(provider.getDepositRate());

						availableQuotes.add(
								new Quote(bike, provider, query.getDateRange(),
										bikePrice, 
										deposit));
					}
			});
		});
		
		return availableQuotes;
	}

	/**
	 * All of the quotes should be from the same provider as delivery
	 * and return to a partner should be possible (and not to partners)
	 * @param customer
	 * @param quotes
	 * @param paymentData
	 * @param wantsDelivery
	 * @param location
	 * @return
	 * @throws BikesUnavaliableException
	 * @throws PaymentRefusedException
	 */
	public Integer bookQuote(Customer customer,
			Quote[] quotes,
			PaymentService.PaymentData paymentData,
			boolean wantsDelivery,
			Location location) // Can be null if the wantsDelivery is false
					throws BikesUnavaliableException, PaymentRefusedException {
		/* TODO
		 * check whether the provider is unique
		 * check if the date is the same for all bikes
		 * check if there is at least one quote
		 * NULL? idk if we should do it
		 */
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
			throw new BikesUnavaliableException();
		}
		if (!PaymentServiceFactory.getPaymentService().
				confirmPayment(paymentData, price.add(price.multiply(depositRate)))) {
			booking.freeBikes();
			throw new PaymentRefusedException();
		}
		
		booking.setFinalised(price.multiply(depositRate));
		
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
		if (provider.equals(provider)) { 
			// Delivered to the original provider
			booking.resolveDeposit();
			booking.freeBikes();
		} else if (booking.getProvider().isPartnerWith(provider)) {
			DeliveryServiceFactory.getDeliveryService().scheduleDelivery(
					booking, 
					provider.getLocation(), 
					booking.getProvider().getLocation(), 
					LocalDate.now());
			// The Booking will resolve itself when it is delivered
		} else {
			throw new ProviderIsNotAPartnerException();
		}

	}
	
	@SuppressWarnings("serial")
	public class BikesUnavaliableException extends Exception {
	}
	@SuppressWarnings("serial")
	public class PaymentRefusedException extends Exception {
	}
	@SuppressWarnings("serial")
	public class ProviderIsNotAPartnerException extends Exception {
	}
			

}
