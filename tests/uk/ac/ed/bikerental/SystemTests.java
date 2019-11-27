package uk.ac.ed.bikerental;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

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
    private Query testQuery4;
    private ArrayList<Provider> testProviders;
    private ArrayList<Quote> testQuotes;
    private Customer c1;
    private Provider p1;
    private Provider p2;
    private Provider p3;
    private Bike b1;
    private Bike b2;
    private Bike b3;
    private Quote q1;
    private Quote q2;
    private Quote quotes[];
    private Quote lockedQuotes[];
    private Booking book1;
    private int ticket;
				
	private static void setUpMockPricingPolicyFor(Provider prov){
		PricingPolicy policy = prov.getPricingPolicy();
		for (BikeType.SubType type: BikeType.SubType.values()){
			policy.setDailyRentalPrice(new BikeType(0.0, type), new BigDecimal(100));
		}
	}
			

    @BeforeEach
    void setUp() throws Exception {
        // Setup mock delivery service before each tests
        DeliveryServiceFactory.setupMockDeliveryService();
        PaymentServiceFactory.setupMockPaymentService();
        testProviders = new ArrayList<>();
        p1 = new Provider(new Location("EH3 9QG", "123 Fountainbridge"), 0.5);

        p2 = new Provider(new Location("EH8 9LE", "11 Crichton Street"), 0.5);
        p3 = new Provider(new Location("EH8 9LE", "10 Crichton Street"), 0.5);
        p1.addPartner(p2);
        p2.addPartner(p1);
        testProviders.add(p1);
        testProviders.add(p2);
        testProviders.add(p3);
        setUpMockPricingPolicyFor(p1);
        setUpMockPricingPolicyFor(p2);
        setUpMockPricingPolicyFor(p3);

        c1 = new Customer(new Location("EH9 1SE", "16 East Mayfield"));

        b1 = new Bike(new BikeType((500), BikeType.SubType.Mountain),
                new LinearDepreciationValuationPolicy(0.5),
                LocalDate.of(2015,1,1)); //TODO: change so that we're not using extended submodules
        b2 = new Bike(new BikeType((500), BikeType.SubType.BMX),
                new LinearDepreciationValuationPolicy(0.5),
                LocalDate.of(2018,1,1));

        b3 = new Bike(new BikeType((500), BikeType.SubType.Hybrid),
                new LinearDepreciationValuationPolicy(0.5),
                LocalDate.of(2018,1,1));

        p1.addBike(b1);
        p3.addBike(b3);

        q1 = new Quote(b1, p1, new DateRange(LocalDate.of(2019, 11, 1),
                LocalDate.of(2019,11,4)), p1.getPriceForBike(b1,
                new DateRange(LocalDate.of(2019, 11, 1),
                        LocalDate.of(2019,11,4))),
                b1.getValue());

        q2 = new Quote(b2, p1, new DateRange(LocalDate.of(2019, 11, 1),
                LocalDate.of(2019,11,4)), p1.getPriceForBike(b2,
                new DateRange(LocalDate.of(2019, 11, 1),
                        LocalDate.of(2019,11,4))),
                b2.getValue());

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

        testQuery4 = new Query(new Location("EH8 9LE", ""), new DateRange(
                LocalDate.of(2019, 11, 1), LocalDate.of(2019, 11, 4)),
                new BikeType((0.0), BikeType.SubType.Hybrid));

        b2.lock(q2);

        testQuotes = new ArrayList<>();
        testQuotes.add(q1);
        quotes = new Quote[1];
        quotes[0] = q1;
        lockedQuotes = new Quote[1];
        lockedQuotes[0] = q2;

        book1 = new Booking(c1,p1);
        book1.addQuote(q1);
        ticket = testServer.getServerData().addBooking(book1);



    }

    @Test
    void testGetQuote() {
        assertEquals(testQuotes, testServer.getQuotes(testQuery1));
    }

    @Test
    void testGetQuoteProviderFar() {
        assertEquals(new ArrayList<Quote>(), testServer.getQuotes(testQuery2));
    }

    @Test
    void testGetQuoteBikeUnavailable() {
        assertEquals(new ArrayList<Quote>(), testServer.getQuotes(testQuery3));
    }

    @Test
    void testBookQuote() throws Exception {
        int t = testServer.bookQuote(c1, quotes,
                new MockPaymentService.MockPaymentData("test"), false, null);
        Booking booking = testServer.getServerData().getBooking(t);
        assertEquals(DeliveryState.None, booking.getDeliveryState());
        assertEquals(BookingState.AwaitingCustomer, booking.getState());

    }

    @Test
    void testBookQuoteDeliveryTrue() throws Exception {
        int t = testServer.bookQuote(c1, quotes,
                new MockPaymentService.MockPaymentData("test"), true,
                new Location("EH9 1SE", "16 East Mayfield"));
        Booking booking = testServer.getServerData().getBooking(t);
        assertEquals(DeliveryState.AwaitingDelivery, booking.getDeliveryState());
        assertEquals(BookingState.AwaitingCustomer, booking.getState());
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
    void testReturnBike() throws Exception {
        Booking booking = testServer.getServerData().getBooking(ticket);
        booking.bikesPickedUp();         // to simulate that bike is with customer
        testServer.returnBike(p1, ticket);
        assertEquals(DeliveryState.None, booking.getDeliveryState());
        assertEquals(BookingState.Resolved, booking.getState());
    }

    @Test
    void testReturnBikeToPartner() throws Exception {
        Booking booking = testServer.getServerData().getBooking(ticket);
        booking.bikesPickedUp();         // to simulate that bike is with customer
        testServer.returnBike(p2, ticket);
        assertEquals(DeliveryState.AwaitingReturn, booking.getDeliveryState());
        assertEquals(BookingState.WithPartner, booking.getState());
    }

    @Test
    void testReturnBikeNotPartner() {
        assertThrows(Server.ProviderIsNotAPartnerException.class, () -> {
            testServer.returnBike(p3, ticket);
        });
    }

    @Test
    void testIntegration() throws Exception {
        MockDeliveryService deliveryService = (MockDeliveryService) DeliveryServiceFactory.getDeliveryService();
        HashSet<Booking> delBookings = new HashSet<>(); // bookings to be delivered.
        // we have a few customers each with a different criteria.
        Location deliveryAdd1 = new Location("EH9 1SE", "16 East Mayfield");
        Location deliveryAdd2 = new Location("EH3 9QG", "123 Fountainbridge");
        Customer testCustomer1 = new Customer(deliveryAdd1);
        Customer testCustomer2 = new Customer(deliveryAdd2);
        Customer testCustomer3 = new Customer(deliveryAdd1);

        // each customer queries what they want.
        ArrayList<Quote> quotes1 = testServer.getQuotes(testQuery1);
        ArrayList<Quote> quotes2 = testServer.getQuotes(testQuery4);

        Quote[] arrayQ1 = new Quote[quotes1.size()];
        Quote[] arrayQ2 = new Quote[quotes2.size()];

        // each customer then proceeds to book.
        int bookingNo1 = testServer.bookQuote(testCustomer1, quotes1.toArray(arrayQ1),
                new MockPaymentService.MockPaymentData("test"), true, deliveryAdd2);
        int bookingNo2 = testServer.bookQuote(testCustomer2, quotes2.toArray(arrayQ2),
                new MockPaymentService.MockPaymentData("test"), false, null);
        Booking testBooking1 = testServer.getServerData().getBooking(bookingNo1);
        Booking testBooking2 = testServer.getServerData().getBooking(bookingNo2);

        // test the statuses of the bookings
        assertEquals(DeliveryState.AwaitingDelivery, testBooking1.getDeliveryState());
        assertEquals(BookingState.AwaitingCustomer, testBooking1.getState());
        assertEquals(DeliveryState.None, testBooking2.getDeliveryState());
        assertEquals(BookingState.AwaitingCustomer, testBooking2.getState());

        // delivery of the bike
        delBookings.add(testBooking1);
        assertEquals(delBookings, deliveryService.getPickupsOn(LocalDate.of(2019,11,1)));
        deliveryService.carryOutPickups(LocalDate.of(2019,11,1));
        assertEquals(DeliveryState.BeingTransported, testBooking1.getDeliveryState());
        deliveryService.carryOutDropoffs();

        // bikes with customers
        assertEquals(DeliveryState.None, testBooking1.getDeliveryState());
        assertEquals(BookingState.WithCustomer, testBooking1.getState());
        testBooking2.bikesPickedUp();
        assertEquals(DeliveryState.None, testBooking2.getDeliveryState());
        assertEquals(BookingState.WithCustomer, testBooking2.getState());

        // bikes being returned
        testServer.returnBike(p1, bookingNo1);
        testServer.returnBike(p3, bookingNo2);
        assertEquals(DeliveryState.None, testBooking1.getDeliveryState());
        assertEquals(BookingState.Resolved, testBooking1.getState());
        assertEquals(DeliveryState.None, testBooking2.getDeliveryState());
        assertEquals(BookingState.Resolved, testBooking2.getState());
    }
}
