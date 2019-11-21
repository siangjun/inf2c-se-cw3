package uk.ac.ed.bikerental;

import java.util.HashSet;
import java.util.Set;

public class Provider {
	Set<Provider> partners;
	
	public Provider(){
		this.partners = new HashSet<Provider>();
	}
	
	public void addPartner(Provider provider) {
		this.partners.add(provider);
	}

}
