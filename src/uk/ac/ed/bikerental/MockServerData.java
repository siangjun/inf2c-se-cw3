package uk.ac.ed.bikerental;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MockServerData implements ServerDataInterface{
	private ArrayList<Bike> bikes;
	private Map<Integer, Booking> bookings;
	static Integer num = 0;
	
	public MockServerData() {
		bikes = new ArrayList<Bike>();
		bookings = new HashMap<Integer, Booking>();
		// TODO: Implement this method
	}

	@Override
	public ArrayList<Bike> getBikes() {
		return this.bikes;
	}
	
	public Integer addBooking(Booking booking) {
		num++;
		bookings.put(num, booking);
		return num;
	}
	
	public void resolveBooking(Integer num) {
		// TODO
		
	}

}
