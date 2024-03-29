package uk.ac.ed.bikerental;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MockServerData implements ServerDataInterface{
	private ArrayList<Provider> providers;
	private Map<Integer, Booking> bookings;
	static Integer num = 0;
	
	public MockServerData(ArrayList<Provider> providers) {
		this.providers = providers;
		bookings = new HashMap<Integer, Booking>();
	}

	public Integer addBooking(Booking booking) {
		num++;
		bookings.put(num, booking);
		return num;
	}
	
	public Booking getBooking(Integer num) {
		return bookings.get(num);
	}
	
	public void removeBooking(Integer num) {
		bookings.remove(num);
	}

	@Override
	public ArrayList<Provider> getProviders() {
		return this.providers;
	}

}
