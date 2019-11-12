package uk.ac.ed.bikerental;

public class Query {
	private final Location location;
	private final DateRange dateRange;
	
	public Query(Location location, DateRange dRange) {
		this.location = location;
		this.dateRange = dRange;
	}
	
	/**
	 * Creates a new query that is extended by the amount of days given as an argument
	 * @param days the amount of days by which the query should be extended by 
	 * @return a new object of the query that is extended
	 */
	public Query createExtendedQuery(int days) {
		return new Query(location, dateRange.createExtendedRange(days));
	}
	
	public Location getLocation() {
		return this.location;
	}
	public DateRange getDateRange() {
		return this.dateRange;
	}
	
}
