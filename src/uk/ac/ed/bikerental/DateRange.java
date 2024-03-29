package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.function.BooleanSupplier;

/**
 * DateRange is the class that represents a specific time period.
 * It is represented by:
 * <ul>
 *     <li>start</li>
 *     <li>end</li>
 * </ul>
 */
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
		assert(!end.isBefore(start));
    }
    
    /**
     * Gets the start date
     * @return start date
     */
    public LocalDate getStart() {
        return this.start;
    }
    
    /**
     * Gets the end date
     * @return end date
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
     */
    public Boolean overlaps(DateRange other) {
		if (start.isBefore(other.getEnd()) &&
				other.getStart().isBefore(end)){
			// overlaps
			return Boolean.TRUE;
		}else{
			// does not overlap
			return Boolean.FALSE;
			
		}
    }


    /**
     * Overrides hashCode so the DateRange can be used in collections
     * @return hash of the object
     */
    @Override
    public int hashCode() {
        // hashCode method allowing use in collections
        return Objects.hash(end, start);
    }


    /**
     * Overrides object equals method so it is possible to check whether one {@link DateRange} equals another
     * @param obj especially {@link DateRange} object
     * @return <code>boolean</code> whether the objects are equal
     */
    @Override
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

    /**
     * Extends the range by the same number of days to the left, and to the right.
     * @param days The amount of days to change.
     * @return A new DateRange with the appropriate range.
     */
	public DateRange createExtendedRange(int days) {
		return new DateRange(start.minusDays(days), end.plusDays(days));
	}

    /**
     * @return The date range in {@link String}. For example for 10/01/2000 - 14/01/2000, it will be
     *         (2000-01-10 - 2000-01-14)
     */
	@Override
    public String toString() {
        return "(" + start.toString() + " - " + end.toString() + ")";
    }
}
