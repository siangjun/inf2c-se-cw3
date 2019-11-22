package uk.ac.ed.bikerental;

public class Quote {
	private final Bike bike;
	private final Provider provider;
	private final Query query;
	public Quote(Bike bike, Query query, Provider provider){
		this.bike = bike;
		this.query = query;
		this.provider = provider;
	}
	
	public Bike getBike() {
		return this.bike;
	}
	public Query getQuery() {
		return this.query;
	}
	public Provider getProvider() {
		return this.provider;
	}

}
