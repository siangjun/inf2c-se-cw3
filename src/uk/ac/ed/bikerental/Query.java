package uk.ac.ed.bikerental;

public class Query {
	private final Location location;
	private final DateRange dateRange;
	private final BikeType requestedBikeType;
	
	public Query(Location location, DateRange dRange, BikeType requestedBikeType) {
		this.location = location;
		this.dateRange = dRange;
		this.requestedBikeType = requestedBikeType;
	}
	
	/**
	 * Creates a new query that is extended by the amount of days given as an argument
	 * @param days the amount of days by which the query should be extended by 
	 * @return a new object of the query that is extended
	 */
	public Query createExtendedQuery(int days) {
		return new Query(location, dateRange.createExtendedRange(days), 
				this.requestedBikeType);
	}
	
	public Location getLocation() {
		return this.location;
	}
	public DateRange getDateRange() {
		return this.dateRange;
	}

	public BikeType getRequestedType() {
		return this.requestedBikeType;
	}
	
}
