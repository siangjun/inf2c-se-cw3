package uk.ac.ed.bikerental;

import java.util.ArrayList;

/**
 * Interface for server data so that it can be easily mocked for testing
 * @author Michal Glinski
 */
public interface ServerDataInterface {
	public ArrayList<Provider> getProviders();
	public Integer addBooking(Booking booking);
	public Booking getBooking(Integer num);
	public void removeBooking(Integer num);
}
