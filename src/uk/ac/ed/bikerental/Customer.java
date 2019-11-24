package uk.ac.ed.bikerental;

import java.util.ArrayList;

public class Customer {
	private Location location;
	private ArrayList<Booking> bookings;
	
	public Customer() {
		this.location = null;
		this.bookings = new ArrayList<Booking>();
	}

	public Customer(Location location) {
		this.location = location;
		this.bookings = new ArrayList<>();
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	public Location getLocation() {
		return this.location;
	}
	public void addBooking(Booking booking) {
		this.bookings.add(booking);
	}
}
