package uk.ac.ed.bikerental;

public class Booking {
	private final Customer customer;
	private final Quote quote;

	
	public Booking(Customer customer, Quote quote) {
		this.customer = customer;
		this.quote = quote;
	}
	
	public Customer getCustomer() {
		return this.customer;
	}
	public Quote getQuote() {
		return this.quote;
	}

}