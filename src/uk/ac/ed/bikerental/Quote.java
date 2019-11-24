package uk.ac.ed.bikerental;

import java.math.BigDecimal;

public class Quote {
	private final Bike bike;
	private final Provider provider;
	private final BigDecimal price;
	private final BigDecimal deposit;
	private final DateRange dateRange;

	public Quote(Bike bike, Provider provider, DateRange dateRange,  BigDecimal price,
			BigDecimal deposit){
		this.bike = bike;
		this.provider = provider;
		this.price = price;
		this.deposit = deposit;
		this.dateRange = dateRange;
	}
	
	public Bike getBike() {
		return this.bike;
	}
	public DateRange getDateRange() {
		return this.dateRange;
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
