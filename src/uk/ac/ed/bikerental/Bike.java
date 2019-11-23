package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.concurrent.Semaphore;

/**
 * Class of each bike in the system.
 * @author Michal Glinski
 * @author Siang Jun Teo
 */
public class Bike {
    private BikeType type;
    private LocalDate dateAcquired;
    private HashSet<DateRange> bookings; 

    /**
     * Creates an instance of {@link Bike} class. Sets the date acquired to now.
     * @param type of class {@link BikeType}.
     */
    public Bike(BikeType type) {
        this.type = type;
        this.dateAcquired = LocalDate.now();
        this.bookings = new HashSet<DateRange>();
    }

    /**
     * Creates an instance of {@link Bike} class.
     * @param type of class {@link BikeType}.
     * @param dateAcquired of class {@link LocalDate}.
     */
    public Bike(BikeType type, LocalDate dateAcquired) {
        this.type = type;
        this.dateAcquired = dateAcquired;
        this.bookings = new HashSet<DateRange>();
    }
    public BikeType getType() {
        return this.type;
    }
    
	public BigDecimal getPrice() {
		return this.type.getReplacementValue();
	}
    /**
     * Checks whether bike is available for a give DateRange in a query
     * @param query
     * @return <code>boolean</code> whether is taken
     */
    public boolean isTaken(Query query) {
    	return bookings.stream().anyMatch((booking) -> 
    		booking.overlaps(query.getDateRange()) );
    }
    
    /**
     * Checks whether the bike is still available for the quote and if it is it creates a new Booking and makes 
     * the bike unavailable for the time of the booking
     * @param customer Customer is needed to create a booking
     * @param quote Quote that the customer has chosen to book
     * @return null if the bike is no longer available, a new object of Booking that represents booking that was made
     */
    public boolean lock(Quote quote) {
    	boolean ret = false;

		if (this.isTaken(quote.getQuery())) {
			ret = false;
		} else {
			bookings.add(quote.getQuery().getDateRange());
			ret = true;
		}
    	return ret;
    }

    /**
     * Removed the booking from the set so not litter code
     * @param booking to be removed
     */
    public void unlock(Quote quote) {
		bookings.remove(quote.getQuery().getDateRange());
    }

	public LocalDate getAcquirementDate() {
		return this.dateAcquired;
	}
}