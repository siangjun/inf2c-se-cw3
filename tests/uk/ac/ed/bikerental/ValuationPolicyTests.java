package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.LocalDate;

class ValuationPolicyTests {
    // You can add attributes here
    private Bike b1;
    private Bike b2;
    private Bike b3;
    private Bike b4;

    @BeforeEach
    void setUp() throws Exception {
        // Put setup here
        b1 = new Bike(new BikeType(BigDecimal.valueOf(500.0), BikeType.SubType.Mountain),
                new LinearDepreciationValuationPolicy(BigDecimal.valueOf(0.1)),
                LocalDate.of(2015, 9, 15));
        b2 = new Bike(new BikeType(500.0, BikeType.SubType.Mountain),
                new LinearDepreciationValuationPolicy(0.1),
                LocalDate.of(2015, 9, 15));
        b3 = new Bike(new BikeType(BigDecimal.valueOf(500.0), BikeType.SubType.BMX),
                new DoubleDecliningBalanceDepreciationValuationPolicy(BigDecimal.valueOf(0.1)),
                LocalDate.of(2015,9,15));
        b4 = new Bike(new BikeType(500.0, BikeType.SubType.BMX),
                new DoubleDecliningBalanceDepreciationValuationPolicy(0.1),
                LocalDate.of(2015,9,15));
    }
    
    @Test
    void testLinearDepreciationValuation() {
        assertEquals(BigDecimal.valueOf(300.00).stripTrailingZeros(), b1.getValue().stripTrailingZeros());
    }

    @Test
    void testLinearDepreciationValuationDoubleConv() {  // TODO: Something wrong with converting double to BigDecimal
        assertEquals(BigDecimal.valueOf(300.00).stripTrailingZeros(), b2.getValue().stripTrailingZeros());
    }

    @Test
    void testDoubleDecliningBalanceDepreciationValuation() { // TODO: Calculation is wrong
        assertEquals(BigDecimal.valueOf(204.80).stripTrailingZeros(), b3.getValue().stripTrailingZeros());
    }

    @Test
    void testDoubleDecliningBalanceDepreciationValuationDoubleConv() {
        assertEquals(BigDecimal.valueOf(204.80).stripTrailingZeros(), b4.getValue().stripTrailingZeros());
    }
}
