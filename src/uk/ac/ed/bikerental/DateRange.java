package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.function.BooleanSupplier;

public class DateRange {
    private LocalDate start, end;
    
    public DateRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }
    
    public LocalDate getStart() {
        return this.start;
    }
    
    public LocalDate getEnd() {
        return this.end;
    }

    public long toYears() {
        return ChronoUnit.YEARS.between(this.getStart(), this.getEnd());
    }

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
		if (end.isBefore(start) || 
				other.getEnd().isBefore(other.getStart())){
			throw new IllegalArgumentException();
		}
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
    // TODO: Add JavaDoc
    public int hashCode() {
        // hashCode method allowing use in collections
        return Objects.hash(end, start);
    }

    @Override
    // TODO: Add JavaDoc
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
}
