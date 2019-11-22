package uk.ac.ed.bikerental;

import java.util.ArrayList;

enum DeliveryState{
	None,
	AwaitingDelivery,
	AwaitingReturn,
	BeingTransported
}

enum BookingState{
	AwaitingCustomer,
	BeingDelivered,
	WithCustomer,
	BeingReturned,
	Resolved
}

public class Booking implements Deliverable{
	private final Customer customer;
	private ArrayList<Quote> quotes;
	private boolean finalized;
	private Provider provider;
	private DeliveryState deliveryState;
	private BookingState state;

	
	public Booking(Customer customer, Provider provider) {
		this.customer = customer;
		this.provider = provider;
		this.quotes = new ArrayList<Quote>();
		this.finalized = false;
		this.deliveryState = DeliveryState.None;
		this.state = BookingState.AwaitingCustomer;
	}
	
	/**
	 * Call after all the bikes are locked and 
	 * TODO
	 */
	public void setFinalised() {
		finalized = true;
	}
	public void addQuote(Quote quote) {
		quotes.add(quote);
	}
	/**
	 * Will unlock all the bikes that were used by the booking
	 */
	public void freeBikes() {
		quotes.forEach( (quote) -> {
			quote.getBike().unlock(quote);
		});
	}
	
	public void setDeliveryState(DeliveryState state) {
		this.deliveryState = state;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	@Override
	public void onPickup() {
		if (this.deliveryState == DeliveryState.AwaitingDelivery) {
			this.deliveryState = DeliveryState.BeingTransported;
			this.state = BookingState.BeingDelivered;
		} else if (this.deliveryState == DeliveryState.AwaitingReturn) {
			this.deliveryState = DeliveryState.BeingTransported;
			this.state = BookingState.BeingReturned;
		}
	}

	@Override
	public void onDropoff() {
		if (this.state == BookingState.BeingDelivered) {
			this.state = BookingState.WithCustomer;
			this.deliveryState = DeliveryState.None;
		} else if (this.state == BookingState.BeingReturned ){
			this.state = BookingState.Resolved;
			// TODO resolve
			this.deliveryState = DeliveryState.None;
		}
		
	}

}
