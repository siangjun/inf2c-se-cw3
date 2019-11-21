package uk.ac.ed.bikerental;

import java.util.ArrayList;

/**
 * Interface for server data so that it can be easily mocked for testing
 * @author Michal Glinski
 */
public interface ServerDataInterface {
	public ArrayList<Bike> getBikes();
	// TODO: Add more things
}
