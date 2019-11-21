package uk.ac.ed.bikerental;

public interface PaymentService {
	public abstract class PaymentData{
		public abstract String getData();
	};
	public boolean confirmPayment(PaymentData paymentData);

}
