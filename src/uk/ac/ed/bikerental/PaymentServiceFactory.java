package uk.ac.ed.bikerental;

public class PaymentServiceFactory {
    private static PaymentService paymentServiceInstance;

    public static PaymentService getPaymentService() {
        if (paymentServiceInstance == null) {
            // Not implemented -- we are only interested in testing using the Mock.
            assert false;
        }
        return paymentServiceInstance;
    }

    public static void setupMockPaymentService() {
        // Should only be called in unit tests, not production code.
        paymentServiceInstance= new MockPaymentService();
    }


}
