package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Objects;

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

	@Override
	public String toString() {
		return "Bike: " + bike.toString() + "\nProvider: " + provider.toString() + "\nPrice: " +
				price.toString() + "\nDeposit: " + deposit.toString() + "\nDate range:" +
				dateRange.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (Objects.isNull(obj)) return false;
		if (this.getClass() != obj.getClass()) return false;
		Quote other = (Quote) obj;
		return this.bike.equals(other.bike) && this.provider.equals(other.provider) &&
				this.price.equals(other.price) && this.deposit.equals(other.deposit) &&
				this.dateRange.equals(other.dateRange);
	}

}
