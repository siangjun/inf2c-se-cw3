package uk.ac.ed.bikerental;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class SystemTests {
    // You can add attributes here
    Server testServer;
    Query testQuery;
    @BeforeEach
    void setUp() throws Exception {
        // Setup mock delivery service before each tests
        DeliveryServiceFactory.setupMockDeliveryService();
        testServer = new Server(new MockServerData());
        // Don't care how query fetching to the server works so we make a test one
        testQuery = new Query(new Location("EH8 9LE", ""), new DateRange(
                new LocalDate.of(2019,11,1), new LocalDate.of(2019,11,4)));
        // there should be a way to test a specific bike we want returned that somehow comes from the mock data
    }
    
    // TODO: Write system tests covering the three main use cases

    @Test
    void testMatchQuery() {

    }
}
