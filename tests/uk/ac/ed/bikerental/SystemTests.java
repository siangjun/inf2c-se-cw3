package uk.ac.ed.bikerental;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Array;
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
    ArrayList<Provider> testProviders;
    ArrayList<Quote> testQuotes;
    Customer c1;
    Bike b1;
    @BeforeEach
    void setUp() throws Exception {
        // Setup mock delivery service before each tests
        DeliveryServiceFactory.setupMockDeliveryService();
        PaymentServiceFactory.setupMockPaymentService();
        testProviders = new ArrayList<>();
        Provider p1 = new Provider(new Location("EH3 9QG", "123 Fountainbridge"), 10.0);
        Provider p2 = new Provider(new Location("EH8 9LE", "1 Potterrow"), 20.0);
        p1.addPartner(p2);
        p2.addPartner(p1);
        testProviders.add(p1);
        testProviders.add(p2);
        c1 = new Customer(new Location("EH9 1SE", "16 East Mayfield"));
        b1 = new Bike()
        testServer = new Server(new MockServerData(testProviders));

        // Don't care how query fetching to the server works so we make a test one
        testQuery = new Query(new Location("EH8 9LE", ""), new DateRange(
                LocalDate.of(2019,11,1), LocalDate.of(2019,11,4)),
                new BikeType((5.0), BikeType.SubType.Mountain));
    }
    
    // TODO: Write system tests covering the three main use cases
    // TODO: Use MockPaymentData from MockPaymentService (if constructed with empty string it will reject payment and accept it otherwise)

    @Test
    void testMatchQuery() {
        testQuotes = new ArrayList<>();
        Quote q1 = new Quote()
        testQuotes.add()
        assertEquals(testServer.getQuotes(testQuery);
    }
}
