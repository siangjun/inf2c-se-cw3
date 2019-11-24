package uk.ac.ed.bikerental;

import java.math.BigDecimal;

import java.util.Objects;

public class BikeType { // TODO: is this an abstract class?
	private final BigDecimal replacementValue;
	
	public BikeType(double replacementValue) {
		this(new BigDecimal(replacementValue));
	}
	public BikeType(BigDecimal replacementValue) {
		this.replacementValue = replacementValue;
	}

    public BigDecimal getReplacementValue() {
    	return this.replacementValue;
    }
    
    public boolean equals(Object obj) {
        // equals method for testing equality in tests
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        assert(false); // TODO implement BikeType equals 
        // As a note it is already used in the Server matchesQuery function
        return false;
    }
}