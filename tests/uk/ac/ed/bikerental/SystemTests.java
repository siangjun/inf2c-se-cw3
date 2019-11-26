package uk.ac.ed.bikerental;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Unit tests for the entire system.
 *
 * @author Siang Jun Teo
 */
class SystemTests {
    // You can add attributes here
    private Server testServer;
    private Query testQuery1;
    private Query testQuery2;
    private Query testQuery3;
    private ArrayList<Provider> testProviders;
    private ArrayList<Quote> testQuotes;
    private Customer c1;
    private Bike b1;
    private Bike b2;
    private Quote q1;
    private Quote q2;
    private Quote quotes[];
    private Quote lockedQuotes[];

    @BeforeEach
    void setUp() throws Exception {
        // Setup mock delivery service before each tests
        DeliveryServiceFactory.setupMockDeliveryService();
        PaymentServiceFactory.setupMockPaymentService();
        testProviders = new ArrayList<>();
        Provider p1 = new Provider(new Location("EH3 9QG", "123 Fountainbridge"), 0.5);

        Provider p2 = new Provider(new Location("EH8 9LE", "11 Crichton Street"), 0.5);
        p1.addPartner(p2);
        p2.addPartner(p1);
        testProviders.add(p1);
        testProviders.add(p2);

        c1 = new Customer(new Location("EH9 1SE", "16 East Mayfield"));

        b1 = new Bike(new BikeType((5.0), BikeType.SubType.Mountain),
                new LinearDepreciationValuationPolicy(0.5)); //TODO: change so that we're not using extended submodules
        b2 = new Bike(new BikeType((5.0), BikeType.SubType.BMX),
                new LinearDepreciationValuationPolicy(0.5));

        p1.addBike(b1);

        q1 = new Quote(b1, p1, new DateRange(LocalDate.of(2019, 11, 1),
                LocalDate.of(2019,11,4)), b1.getValue(),
                b1.getValue().multiply(p1.getDepositRate()));

        q2 = new Quote(b2, p1, new DateRange(LocalDate.of(2019, 11, 1),
                LocalDate.of(2019,11,4)), b2.getValue(),
                b2.getValue().multiply(p1.getDepositRate()));

        testServer = new Server(new MockServerData(testProviders));

        // Don't care how query fetching to the server works so we make a test one
        testQuery1 = new Query(new Location("EH8 9LE", ""), new DateRange(
                LocalDate.of(2019,11,1), LocalDate.of(2019,11,4)),
                new BikeType((0.0), BikeType.SubType.Mountain));

        testQuery2 = new Query(new Location("SW1A 1AA", ""), new DateRange(
                LocalDate.of(2019,11,1), LocalDate.of(2019,11,4)),
                new BikeType((0.0), BikeType.SubType.Mountain));

        testQuery3 = new Query(new Location("EH8 9LE", ""), new DateRange(
                LocalDate.of(2019,11,1), LocalDate.of(2019,11,4)),
                new BikeType((0.0), BikeType.SubType.BMX));

        b2.lock(q2);

        testQuotes = new ArrayList<>();
        testQuotes.add(q1);
        quotes = new Quote[1];
        quotes[0] = q1;
        lockedQuotes = new Quote[1];
        lockedQuotes[0] = q2;

    }
    
    // TODO: Write system tests covering the three main use cases
    // TODO: Use MockPaymentData from MockPaymentService (if constructed with empty string it will reject payment and accept it otherwise)

    @Test
    void testGetQuote() {
        assertEquals(testQuotes, testServer.getQuotes(testQuery1));
    }

    @Test
    void testGetQuoteProviderFar() {  // TODO: Fails because function doesn't check if provider is far
        assertEquals(new ArrayList<Quote>(), testServer.getQuotes(testQuery2));
    }

    @Test
    void testGetQuoteBikeUnavailable() {
        assertEquals(new ArrayList<Quote>(), testServer.getQuotes(testQuery3));
    }

    @Test
    void testBookQuote() throws Exception {
        assertEquals(/*TODO: what object?*/ 0 ,testServer.bookQuote(c1, quotes,
                new MockPaymentService.MockPaymentData("test"), false, null));

    }

    @Test
    void testBookQuoteDeliveryTrue() throws Exception {
        assertEquals(/*TODO: what object?*/ 0,testServer.bookQuote(c1, quotes,
                new MockPaymentService.MockPaymentData("test"), true,
                new Location("EH9 1SE", "")));
    }

    @Test
    void testBookQuotePaymentFailed() {
        assertThrows(Server.PaymentRefusedException.class, () -> {
            testServer.bookQuote(c1, quotes, new MockPaymentService.MockPaymentData(""),
                    false, null);
        });
    }

    @Test
    void testBookQuoteBikeUnavailable() {
        assertThrows(Server.BikesUnavailableException.class, () -> {
            testServer.bookQuote(c1, lockedQuotes, new MockPaymentService.MockPaymentData("test"),
                    false, null);
        });
    }

    @Test
    void testReturnBike() {
        testServer.getServerData().getBooking(1);
    }

    @Test
    void testIntegration() {
        // TODO: Run a full simulation test of a successful run
    }
}
