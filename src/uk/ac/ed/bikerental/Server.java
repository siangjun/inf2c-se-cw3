package uk.ac.ed.bikerental;

import java.util.ArrayList;

public class Server {
	private ServerDataInterface serverData;
	private PaymentService paymentService;
	
	public Server(ServerDataInterface sdata, PaymentService paymentService) {
		this.serverData = sdata;
		this.paymentService = paymentService;
	}
	
	/**
	 * Returns <code>boolean</code> whether the bike matches give query
	 * @param query query to be checked against
	 * @param bike bike to check against query
	 * @return <code>true</code> or <code>false</code> depending whether bike matches the query
	 */
	private boolean matchesQuery(Query query, Bike bike) {  // could be BikeType??
		// TODO implement this
		assert false;
		return false;
	}

	public ArrayList<Quote> getQuotes(Customer customer, Query query) {
		ArrayList<Quote> availableQuotes = new ArrayList<Quote>();
		
		ArrayList<Bike> bikes = serverData.getBikes();
		ArrayList<Bike> availableBikes = new ArrayList<Bike>();
		bikes.forEach((bike) -> {
			if (!bike.isTaken(query)) 
				if (matchesQuery(query, bike))
					availableBikes.add(bike);
		});
		
		availableBikes.forEach((bike) -> {
			availableQuotes.add(new Quote(bike, query));
		});
		
		// TODO: what if availableBikes/Quotes is empty then have to rerun the process with bigger query
		//		 Maybe add a new function that will check all the bikes give query in this(Server) class
		
		return availableQuotes;
	}
	
	public Integer bookQuote(Customer customer, Quote[] quotes, PaymentService.PaymentData paymentData, boolean wantsDelivery) {
		Booking booking = new Booking(customer);
		
		boolean succ = true;
		for (Quote q: quotes) {
			boolean s = q.getBike().lock(customer, q);
			if (!s) {
				succ = false;
				break;
			} else {
				booking.addQuote(q);
			}
		}
		if (succ) {
			booking.setFinalised();
		} else {
			booking.freeBikes(); // will unlock all the bikes
		}
		
		// TODO add delivery (booking is deliverable btw)
		
		return this.serverData.addBooking(booking);
	}
	
	public void returnBike(Provider provider, int bookingNumber) {

	}

}
