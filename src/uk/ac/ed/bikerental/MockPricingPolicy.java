package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MockPricingPolicy implements PricingPolicy{
	Map<BikeType.SubType, BigDecimal> dailyPrices;
	public MockPricingPolicy(){
		this.dailyPrices = new HashMap<BikeType.SubType, BigDecimal>();
	}

	@Override
	public void setDailyRentalPrice(BikeType bikeType, BigDecimal dailyPrice) {
		dailyPrices.put(bikeType.getSubType(), dailyPrice);
	}

	@Override
	public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange duration) {
		BigDecimal price = new BigDecimal(0);
		BigDecimal days = new BigDecimal(ChronoUnit.DAYS.between(
				duration.getStart(), duration.getEnd()));
		
		for (Bike b: bikes){
			price = price
				.add(dailyPrices.get(b.getType().getSubType()).multiply(days));
		}
		return price;
	}
}

