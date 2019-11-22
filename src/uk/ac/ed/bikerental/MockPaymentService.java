package uk.ac.ed.bikerental;

import java.math.BigDecimal;

/**
 * This is a mock payment service
 * For convenience of testing it will confirm all 
 * payments as long as the data string in PaymentData is not empty
 * @author Michal Glinski
 *
 */
public class MockPaymentService implements PaymentService {
	public class MockPaymentData extends PaymentData{
		private String data;

		@Override
		public String getData() {
			return this.data;
		}
		
		public MockPaymentData(String data) {
			this.data = data;
		}
	}

	@Override
	/**
	 * Mock confirm payment function
	 * @return true if data in paymentData is not empty, otherwise false
	 */
	public boolean confirmPayment(PaymentData paymentData, BigDecimal price) {
		if (paymentData.getData().length() > 0) {
			return true;
		}
		return false;
	}

}
