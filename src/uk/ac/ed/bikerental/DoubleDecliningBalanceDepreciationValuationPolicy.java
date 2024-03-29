package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DoubleDecliningBalanceDepreciationValuationPolicy implements ValuationPolicy {
	private BigDecimal depreciationRate;
	
	public DoubleDecliningBalanceDepreciationValuationPolicy(double depRate) {
		this(BigDecimal.valueOf(depRate));
	}
	public DoubleDecliningBalanceDepreciationValuationPolicy(BigDecimal depreciationRate) {
		this.depreciationRate = depreciationRate;
	}

	@Override
	public BigDecimal calculateValue(Bike bike, LocalDate date) {
		long years = ChronoUnit.YEARS.between(bike.getAcquirementDate(), date);
		BigDecimal orgPrice = bike.getType().getReplacementValue();
		BigDecimal depreciation = BigDecimal.valueOf(1);
		depreciation = depreciation.subtract(this.depreciationRate.multiply(BigDecimal.valueOf(2)));
		depreciation = depreciation.pow((int) (years)); 
		return orgPrice.multiply(depreciation);

	}

}
