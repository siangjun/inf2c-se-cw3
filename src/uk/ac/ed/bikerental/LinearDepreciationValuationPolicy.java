package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LinearDepreciationValuationPolicy implements ValuationPolicy {
	private BigDecimal depreciationRate;

	public LinearDepreciationValuationPolicy(BigDecimal depreciationRate) {
		this.depreciationRate = depreciationRate;
	}

	@Override
	public BigDecimal calculateValue(Bike bike, LocalDate date) {
		BigDecimal years = new BigDecimal(ChronoUnit.YEARS.between(bike.getAcquirementDate(), date));
		BigDecimal orgPrice = bike.getType().getReplacementValue();
		return orgPrice.multiply(new BigDecimal(1).subtract(years.multiply(this.depreciationRate)));
	}

}
