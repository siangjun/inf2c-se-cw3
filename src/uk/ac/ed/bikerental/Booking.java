package uk.ac.ed.bikerental;

import java.util.ArrayList;

public class Booking implements Deliverable{
	private final Customer customer;
	private ArrayList<Quote> quotes;

	
	public Booking(Customer customer) {
		this.customer = customer;
		this.quotes = new ArrayList<Quote>();
	}
	
	public void setFinalised() {
		//TODO
	}
	public void addQuote(Quote quote) {
		// TODO
	}
	public void freeBikes() {
		// Should unlock all the bikes
		// TODO
	}
	public Customer getCustomer() {
		return this.customer;
	}

	@Override
	public void onPickup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDropoff() {
		// TODO Auto-generated method stub
		
	}

}
