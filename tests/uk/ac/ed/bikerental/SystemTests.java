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
    Query testQuery1;
    Query testQuery2;
    ArrayList<Provider> testProviders;
    ArrayList<Quote> testQuotes;
    Customer c1;
    Bike b1;
    Quote q1;

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

        b1 = new Bike(new BikeType((5.0), BikeType.SubType.Mountain),
                new LinearDepreciationValuationPolicy(0.5));

        q1 = new Quote(b1, p1, new DateRange(LocalDate.of(2019, 11, 1),
                LocalDate.of(2019,11,4)), b1.getPrice(),
                b1.getPrice().multiply(p1.getDepositRate()));

        testServer = new Server(new MockServerData(testProviders));

        // Don't care how query fetching to the server works so we make a test one
        testQuery1 = new Query(new Location("EH8 9LE", ""), new DateRange(
                LocalDate.of(2019,11,1), LocalDate.of(2019,11,4)),
                new BikeType((0.0), BikeType.SubType.Mountain));

        testQuery2 = new Query(new Location("SW1A 1AA", ""), new DateRange(
                LocalDate.of(2019,11,1), LocalDate.of(2019,11,4)),
                new BikeType((0.0), BikeType.SubType.Mountain));

        testQuotes = new ArrayList<>();
        testQuotes.add(q1);
    }
    
    // TODO: Write system tests covering the three main use cases
    // TODO: Use MockPaymentData from MockPaymentService (if constructed with empty string it will reject payment and accept it otherwise)

    @Test
    void testMatchQuery() {
        assertEquals(testQuotes, testServer.getQuotes(testQuery1));
    }

    @Test
    void testProviderFar() {  //TODO: Currently false positive because testMatchQuery fails.
        assertEquals(new ArrayList<Quote>(), testServer.getQuotes(testQuery2));
    }
}
