package uk.ac.ed.bikerental;

public class Customer {
	private Location location;
	
	public Customer() {
		this.location = null;
	}

	public Customer(Location location) {
		this.location = location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	public Location getLocation() {
		return this.location;
	}
}
