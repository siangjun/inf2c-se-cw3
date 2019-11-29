package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.*;

public class Provider {
	private ArrayList<Bike> bikes;
	private Set<Provider> partners;
	private final Location location;
	private BigDecimal depositRate;
	private PricingPolicy pricingPolicy;

	
	public Provider(Location location, double depositRate) {
		this(location, BigDecimal.valueOf(depositRate), new MockPricingPolicy());
	}
	public Provider(Location location, double depositRate, PricingPolicy policy) {
		this(location, BigDecimal.valueOf(depositRate), policy);
	}
	public Provider(Location location, BigDecimal depositRate, PricingPolicy pricingPolicy){
		this.partners = new HashSet<Provider>();
		this.bikes = new ArrayList<Bike>(); 
		this.depositRate = depositRate;
		this.location = location;
		this.pricingPolicy = pricingPolicy;
	}
	
	public void addPartner(Provider provider) {
		this.partners.add(provider);
	}

	public void removePartner(Provider provider) {
		this.partners.remove(provider); // Nothing will happen if there is no provider in providers
	}
	
	public BigDecimal getPriceForBike(Bike bike, DateRange duration) {
		return pricingPolicy.calculatePrice(Collections.singletonList(bike), duration);
	}
	
	public Location getLocation() {
		return this.location;
	}
	public ArrayList<Bike> getBikes(){
		return this.bikes;
	}
	public BigDecimal getDepositRate() {
		return this.depositRate;
	}
	public void addBike(Bike bike) {
		this.bikes.add(bike);
	}
	public PricingPolicy getPricingPolicy(){
		return this.pricingPolicy;
	}

	public boolean isPartnerWith(Provider provider) {
		return partners.stream().anyMatch((prov) -> {
			return provider.equals(prov);
		});
	}

}
