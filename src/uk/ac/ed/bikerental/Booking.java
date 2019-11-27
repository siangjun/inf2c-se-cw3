package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.ArrayList;

enum DeliveryState{
	None,
	AwaitingDelivery,
	AwaitingReturn,
	BeingTransported
}

enum BookingState{
	Uncompleted,
	AwaitingCustomer,
	BeingDelivered,
	WithCustomer,
	BeingReturned,
	Resolved
}

public class Booking implements Deliverable{
	private final Customer customer;
	private ArrayList<Quote> quotes;
	private Provider provider;
	private DeliveryState deliveryState;
	private BookingState state;
	private BigDecimal deposit;

	
	public Booking(Customer customer, Provider provider) {
		this.customer = customer;
		this.provider = provider;
		this.quotes = new ArrayList<Quote>();
		this.deliveryState = DeliveryState.None;
		this.state = BookingState.AwaitingCustomer;
		this.deposit = null;
	}
	
	/**
	 * Call after the booking was paid
	 * @param deposit the deposit amount that will be returned to the client
	 */
	public void setFinalised(BigDecimal deposit) {
		this.state = BookingState.AwaitingCustomer;
		this.deposit = deposit;
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
	
	public void resolveDeposit() {
		if (deposit == null) {
			
		} else {
			
		}
	}
	
	public void setDeliveryState(DeliveryState state) {
		this.deliveryState = state;
	}

	public Customer getCustomer() {
		return this.customer;
	}
	public Provider getProvider() {
		return this.provider;
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
			this.deliveryState = DeliveryState.None;
			this.resolveDeposit();
			this.freeBikes();
		}
	}

	public DeliveryState getDeliveryState() {
		return this.deliveryState;
	}

	public BookingState getState() {
		return this.state;
	}
}
