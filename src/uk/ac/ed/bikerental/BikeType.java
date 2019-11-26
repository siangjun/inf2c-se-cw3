package uk.ac.ed.bikerental;

import java.math.BigDecimal;

public class BikeType { 
	enum SubType{
		Mountain,
		Street,
		Hybrid,
		BMX
	} 
	private final SubType type;
	private final BigDecimal replacementValue;
	
	public BikeType(double replacementValue, SubType type) {
		this(new BigDecimal(replacementValue), type);
	}
	public BikeType(BigDecimal replacementValue, SubType type) {
		this.replacementValue = replacementValue;
		this.type = type;
	}

    public BigDecimal getReplacementValue() {
    	return this.replacementValue;
    }
    public SubType getSubType() {
    	return this.type;
    }

    @Override
	public String toString() {
		return type.name();
	}
    
}