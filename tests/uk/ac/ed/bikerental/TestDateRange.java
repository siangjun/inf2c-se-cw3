package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestDateRange {
    private DateRange dateRange1, dateRange2, dateRange3;

    @BeforeEach
    void setUp() throws Exception {
        // Setup resources before each test
        // is using "this" necessary? -- SJ
        this.dateRange1 = new DateRange(LocalDate.of(2019, 1, 7),
                LocalDate.of(2019, 1, 10));
        this.dateRange2 = new DateRange(LocalDate.of(2019, 1, 5),
                LocalDate.of(2019, 1, 23));
        this.dateRange3 = new DateRange(LocalDate.of(2015, 1, 7),
                LocalDate.of(2018, 1, 10));
    }

    // Sample JUnit tests checking toYears works
    @Test
    void testToYears1() {
        assertEquals(0, this.dateRange1.toYears());
    }

    @Test
    void testToYears3() {
        assertEquals(3, this.dateRange3.toYears());
    }

    @Test
    void testOverlapsTrue() {
        assertTrue(dateRange1.overlaps(dateRange2));
        assertTrue(dateRange2.overlaps(dateRange1));
    }

    @Test
    void testOverlapsFalse() {
        assertFalse(dateRange1.overlaps(dateRange3));
        assertFalse(dateRange3.overlaps(dateRange1));
    }

	@Test
	void testInvalidData() {
        //checks for day difference
        assertThrows(IllegalArgumentException.class, () -> {
            DateRange invalid = new DateRange(LocalDate.of(2019,12,5),
                    LocalDate.of(2019,12,4));
        });
        //checks for month difference
        assertThrows(IllegalArgumentException.class, () -> {
            DateRange invalid = new DateRange(LocalDate.of(2019,12,5),
                    LocalDate.of(2019,11,5));
        });
        //checks for year difference
        assertThrows(IllegalArgumentException.class, () -> {
            DateRange invalid = new DateRange(LocalDate.of(2019,12,5),
                    LocalDate.of(2018,11,5));
        });

	}
}
