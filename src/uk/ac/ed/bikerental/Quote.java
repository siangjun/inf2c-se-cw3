package uk.ac.ed.bikerental;

public class Quote {
	private final Bike bike;
	private final Query query;
	public Quote(Bike bike, Query query){
		this.bike = bike;
		this.query = query;
	}
	
	public Bike getBike() {
		return this.bike;
	}
	public Query getQuery() {
		return this.query;
	}

}
