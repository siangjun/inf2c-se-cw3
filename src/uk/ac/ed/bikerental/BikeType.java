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
}