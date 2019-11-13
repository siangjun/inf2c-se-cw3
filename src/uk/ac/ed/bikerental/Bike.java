package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.concurrent.Semaphore;

/**
 * Class of each bike in the system.
 * @author Michal Glinski
 * @author Siang Jun Teo
 */
public class Bike {
    private Semaphore mutex;
    private BikeType type;
    private LocalDate dateAcquired;
    private HashSet<Booking> bookings;

    /**
     * Creates an instance of {@link Bike} class. Sets the date acquired to now.
     * @param type of class {@link BikeType}.
     */
    public Bike(BikeType type) {
        this.type = type;
        this.dateAcquired = LocalDate.now();
        this.bookings = new HashSet<Booking>();
        this.mutex = new Semaphore(1);
    }

    /**
     * Creates an instance of {@link Bike} class.
     * @param type of class {@link BikeType}.
     * @param dateAcquired of class {@link LocalDate}.
     */
    public Bike(BikeType type, LocalDate dateAcquired) {
        this.type = type;
        this.dateAcquired = dateAcquired;
        this.bookings = new HashSet<Booking>();
        this.mutex = new Semaphore(1);
    }
    public BikeType getType() {
        return this.type;
    }
    
    /**
     * Checks whether bike is available for a give DateRange in a query
     * @param query
     * @return <code>boolean</code> whether is taken
     */
    public boolean isTaken(Query query) {
    	return bookings.stream().anyMatch((booking) -> 
    		booking.getQuote().getQuery().getDateRange().overlaps(query.getDateRange()) );
    }
    
    /**
     * Checks whether the bike is still available for the quote and if it is it creates a new Booking and makes 
     * the bike unavailable for the time of the booking
     * @param customer Customer is needed to create a booking
     * @param quote Quote that the customer has chosen to book
     * @return null if the bike is no longer available, a new object of Booking that represents booking that was made
     */
    public Booking lock(Customer customer, Quote quote) {
    	// Make only one thread at a time to be possible to write to the set 
    	// Defensive as we don't know how the system may be designed when being implemented
    	Booking booking = null;
    	this.mutex.acquireUninterruptibly();

    	try {
			if (this.isTaken(quote.getQuery())) {
				// The booking time was taken in between somebody got a quote and booked it
				// The booking will return null by default
				booking = null;
			} else {
				// The booking is not taken
				booking = new Booking(customer, quote);
				bookings.add(booking);
			}
    	} finally {
    		this.mutex.release();
    	}
    	return booking;
    }

    /**
     * Removed the booking from the set so not litter code
     * @param booking to be removed
     */
    public void unlock(Booking booking) {
    	this.mutex.acquireUninterruptibly();
    	try {
    		bookings.remove(booking);
    	} finally {
    		this.mutex.release();
    	}
    }
}