package uk.ac.ed.bikerental;

public class Query {
	private final Location location;
	private final DateRange dateRange;
	
	public Query(Location location, DateRange dRange) {
		this.location = location;
		this.dateRange = dRange;
	}
	
	public Location getLocation() {
		return this.location;
	}
	public DateRange getDateRange() {
		return this.dateRange;
	}
	
}
