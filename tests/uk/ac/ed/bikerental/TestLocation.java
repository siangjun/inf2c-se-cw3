package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TestLocation {
    Location location1;
    Location location2;
    Location location3;
    @BeforeEach
    void setUp() throws Exception {
        // Locations that are next to each other
        location1 = new Location("EH8 9LE", "11 Crichton Street");
        location2 = new Location("EH8 9LE", "10 Crichton Street");

        // A location that isn't next to the other 2 locations
        location3 = new Location("NW1 6XE", "221B Baker Street");
    }
    
    // TODO: put some tests here
    @Test
    void testAssertPostcode() {
        assertThrows(AssertionError.class, () -> {
            Location failLoc = new Location("abc", "1 Potterrow");
        });
    }

    @Test
    void testIsNearToNear() {
        assertEquals(true, location1.isNearTo(location2));
    }

    @Test
    void testIsNearToFar() {
        assertEquals(false, location1.isNearTo(location3));
    }

    @Test
    void testIsNearToNull() {
        assertThrows(NullPointerException.class, () -> {
            location1.isNearTo(null);
        });
    }
    @Test
    void testGetPostcode() {
        assertEquals("EH8 9LE", location1.getPostcode());
    }

    @Test
    void testGetAddress() {
        assertEquals("11 Crichton Street", location1.getAddress());
    }
}
