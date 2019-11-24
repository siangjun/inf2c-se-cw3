package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.function.BooleanSupplier;

public class DateRange {
	// No reason for this to not be final
    private final LocalDate start, end;
    
    /**
     * Constructor of the class.
     * Invariant start < end.
     * @param start  the start of the date range
     * @param end    the end of the date range
     * @throws IllegalArgumentException if end comes before start
     */
    public DateRange(LocalDate start, LocalDate end) {
        // see unit test
        this.start = start;
        this.end = end;
        if (end.isBefore(start)) {
        	throw new IllegalArgumentException();
        }
    }
    
    /**
     * Gets the start date
     * @returns start date
     */
    public LocalDate getStart() {
        return this.start;
    }
    
    /**
     * Gets the end date
     * @returns end date
     */
    public LocalDate getEnd() {
        return this.end;
    }

    /**
     * Calculates the amount of years between start date and end date
     * @return <code>long</code> of years between start end end
     */
    public long toYears() {
        return ChronoUnit.YEARS.between(this.getStart(), this.getEnd());
    }

    /**
     * Calculates the amount of days between start and end
     * @return <code>long</code> of days between start and end
     */
    public long toDays() {
        return ChronoUnit.DAYS.between(this.getStart(), this.getEnd());
    }


    /**
     * Checks whether the current object overlaps with another {@link DateRange} passed
     * as a parameter.
     *
     * @param other an object {@link DateRange}
     * @return      <code>true</code> if overlap found, <code>false</code> otherwise.
     * @throws IllegalArgumentException If other is null
     */
    public Boolean overlaps(DateRange other) {
    	if (other == null) throw new IllegalArgumentException();
		if (start.isBefore(other.getEnd()) &&
				other.getStart().isBefore(end)){
			// overlaps
			return Boolean.TRUE;
		}else{
			// does not overlap
			return Boolean.FALSE;
			
		}
    }

    @Override
    /**
     * Overrides hashCode so the DateRange can be used in collections
     * @returns hash of the object
     */
    public int hashCode() {
        // hashCode method allowing use in collections
        return Objects.hash(end, start);
    }

    @Override
    /**
     * Overrides object equals method so it is possible to check whether one {@link DateRange} equals another
     * @param any object, especially {@link DateRange} object
     * @returns <code>boolean</code> whether the objects are equal
     */
    public boolean equals(Object obj) {
        // equals method for testing equality in tests
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DateRange other = (DateRange) obj;
        return Objects.equals(end, other.end) && Objects.equals(start, other.start);
    }

    // You can add your own methods here

	public DateRange createExtendedRange(int days) {
		return new DateRange(start.minusDays(days), end.plusDays(days));
	}
    
}
