package uk.ac.ed.bikerental;

import java.util.ArrayList;

public class MockServerData implements ServerDataInterface{
	private ArrayList<Bike> bikes;
	
	public MockServerData() {
		// TODO: Implement this method
	}

	@Override
	public ArrayList<Bike> getBikes() {
		return this.bikes;
	}

}
