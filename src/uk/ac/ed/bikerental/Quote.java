package uk.ac.ed.bikerental;

import java.math.BigDecimal;

public class Quote {
	private final Bike bike;
	private final Provider provider;
	private final Query query;
	private final BigDecimal price;
	private final BigDecimal deposit;
	public Quote(Bike bike, Query query, Provider provider, BigDecimal price,
			BigDecimal deposit){
		this.bike = bike;
		this.query = query;
		this.provider = provider;
		this.price = price;
		this.deposit = deposit;
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
	public BigDecimal getPrice() {
		return this.price;
	}
	public BigDecimal getDeposit() {
		return this.deposit;
	}

}
