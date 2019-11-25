package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Provider {
	private ArrayList<Bike> bikes;
	private Set<Provider> partners;
	private final Location location;
	private BigDecimal depositRate;

	
	public Provider(Location location, double depositRate) {
		this(location, new BigDecimal(depositRate));
	}
	public Provider(Location location, BigDecimal depositRate){
		this.partners = new HashSet<Provider>();
		this.bikes = new ArrayList<Bike>(); 
		this.depositRate = depositRate;
		this.location = location;
	}
	
	public void addPartner(Provider provider) {
		this.partners.add(provider);
	}
	
	public void removePartner(Provider provider) {
		this.partners.remove(provider);
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

	public boolean isPartnerWith(Provider provider) {
		return partners.stream().anyMatch((prov) -> {
			return provider.equals(prov);
		});
	}

}
