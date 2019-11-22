package uk.ac.ed.bikerental;

import java.util.HashSet;
import java.util.Set;

public class Provider {
	private Set<Provider> partners;
	private Location location;

	
	public Provider(Location location){
		this.partners = new HashSet<Provider>();
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

}
