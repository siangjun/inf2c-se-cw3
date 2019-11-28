package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TestLocation {
    private Location location1;
    private Location location2;
    private Location location3;
    @BeforeEach
    void setUp() throws Exception {
        // Locations that are next to each other
        location1 = new Location("EH8 9LE", "11 Crichton Street");
        location2 = new Location("EH8 9LE", "10 Crichton Street");

        // A location that isn't next to the other 2 locations
        location3 = new Location("NW1 6XE", "221B Baker Street");
    }
    
    @Test
    void testAssertPostcode() {
        assertThrows(AssertionError.class, () -> {
            Location failLoc = new Location("abc", "1 Potterrow");
        });
    }

    @Test
    void testIsNearToNear() {
        assertTrue(location1.isNearTo(location2));
        assertTrue(location2.isNearTo(location1));
    }

    @Test
    void testIsNearToFar() {
        assertFalse(location1.isNearTo(location3));
        assertFalse(location3.isNearTo(location1));
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

    @Test
    void testToString() {
        assertEquals("11 Crichton Street, EH8 9LE", location1.toString());
    }
}
