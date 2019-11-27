package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MockValuationPolicy implements ValuationPolicy{
	@Override
	public BigDecimal calculateValue(Bike bike, LocalDate date) {
		return bike.getType().getReplacementValue();
	}
}
