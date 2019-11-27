package uk.ac.ed.bikerental;

import java.math.BigDecimal;

public interface PaymentService {
	public abstract class PaymentData{
		public abstract String getData();
	};
	public boolean confirmPayment(PaymentData paymentData, BigDecimal price);
	public boolean resolveDeposit(PaymentData paymentData, BigDecimal deposit);

}
