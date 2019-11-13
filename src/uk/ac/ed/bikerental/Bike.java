package uk.ac.ed.bikerental;

import java.time.LocalDate;

/**
 * Class of each bike in the system.
 * @author Michal Glinski
 * @author Siang Jun Teo
 */
public class Bike {
    private BikeType type;
    private boolean taken;
    private LocalDate dateAcquired;

    /**
     * Creates an instance of {@link Bike} class. Sets the date acquired to now.
     * @param type of class {@link BikeType}.
     */
    public Bike(BikeType type) {
        this.type = type;
        this.taken = false;
        this.dateAcquired = LocalDate.now();
    }

    /**
     * Creates an instance of {@link Bike} class.
     * @param type of class {@link BikeType}.
     * @param dateAcquired of class {@link LocalDate}.
     */
    public Bike(BikeType type, LocalDate dateAcquired) {
        this.type = type;
        this.taken = false;
        this.dateAcquired = dateAcquired;
    }
    public BikeType getType() {
        return this.type;
    }
    public boolean isTaken(Query query) {
        return this.taken;
    }
}