package uk.ac.ed.bikerental;

import java.util.ArrayList;

public class Server {
	private ServerDataInterface serverData;
	
	public Server(ServerDataInterface sdata) {
		this.serverData = sdata;
	}
	
	/**
	 * Returns <code>boolean</code> whether the bike matches give query
	 * @param query query to be checked against
	 * @param bike bike to check against query
	 * @return <code>true</code> or <code>false</code> depending whether bike matches the query
	 */
	private boolean matchesQuery(Query query, Bike bike) {
		// TODO implement this
		assert false;
		return false;
	}

	public ArrayList<Quote> getQuotes(Customer customer, Query query) {
		ArrayList<Quote> avaliableQuotes = new ArrayList<Quote>();
		
		ArrayList<Bike> bikes = serverData.getBikes();
		ArrayList<Bike> avaliableBikes = new ArrayList<Bike>();
		bikes.forEach((bike) -> {
			if (!bike.isTaken(query)) 
				if (matchesQuery(query, bike))
					avaliableBikes.add(bike);
		});
		
		avaliableBikes.forEach((bike) -> {
			avaliableQuotes.add(new Quote(bike, query));
		});
		
		// TODO: what if avaliableBikes/Quotes is empty then have to rerun the process with bigger querry
		//		 Maybe add a new function that will check all the bikes give query in this(Server) class
		
		return avaliableQuotes;
	}

}
