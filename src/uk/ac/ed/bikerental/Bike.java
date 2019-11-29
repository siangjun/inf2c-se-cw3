package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;

/**
 * Class of each bike in the system.
 * @author Michal Glinski
 * @author Siang Jun Teo
 */
public class Bike {
    private BikeType type;
    private LocalDate dateAcquired;
    private HashSet<DateRange> bookings; 
	private ValuationPolicy valuationPolicy;

    /**
     * Creates an instance of {@link Bike} class. Sets the date acquired to now.
     * @param type of class {@link BikeType}.
     * @param valuationPolicy {@link ValuationPolicy}.
     */
    public Bike(BikeType type, ValuationPolicy valuationPolicy) {
        this.type = type;
        this.dateAcquired = LocalDate.now();
        this.bookings = new HashSet<DateRange>();
        this.valuationPolicy = valuationPolicy;
    }

    /**
     * Creates an instance of {@link Bike} class, with chance to set dateAcquired.
     * @param type of class {@link BikeType}.
     * @param valuationPolicy {@link ValuationPolicy}.
     * @param dateAcquired of class {@link LocalDate}.
     */
    public Bike(BikeType type, ValuationPolicy valuationPolicy, LocalDate dateAcquired) {
        this.type = type;
        this.dateAcquired = dateAcquired;
        this.valuationPolicy = valuationPolicy;
        this.bookings = new HashSet<DateRange>();
    }
    public BikeType getType() {
        return this.type;
    }


	/**
	 * Calcualtes the value of the bike at a given time
	 * Used as calculation for the deposit
	 * @param atTime time at which the deposit should be calculated
	 * @return BigDecimal that represents the value of a bike at give time
	 */
	public BigDecimal getValue(LocalDate atTime) {
		return this.valuationPolicy.calculateValue(this, atTime);
	}
    /**
     * Checks whether bike is available for a give DateRange in a query
     * @param dateRange The range that is given.
     * @return <code>boolean</code> whether is taken
     */
	public boolean isTaken(DateRange dateRange) {
    	return bookings.stream().anyMatch((booking) -> 
    		booking.overlaps(dateRange) );
	}
    
    /**
     * Checks whether the bike is still available for the quote and if it is it creates a new
     * {@link Booking} and make the bike unavailable for the time of the booking
     * @param quote {@link Quote} that the customer has chosen to book
     * @return <code>false</code> if the bike is no longer available, <code>true</code> otherwise
     */
    public boolean lock(Quote quote) {
		if (this.isTaken(quote.getDateRange())) {
			return false;
		} else {
			bookings.add(quote.getDateRange());
			return true;
		}
    }

    /**
     * Removed the booking from the set so not litter code
     * @param quote to be removed
     */
    public void unlock(Quote quote) {
		bookings.remove(quote.getDateRange());
    }

	public LocalDate getAcquirementDate() {
		return this.dateAcquired;
	}

	@Override
    public String toString() {
        return "(" + type.toString() + ", " + dateAcquired.toString() + ")";
    }
}
