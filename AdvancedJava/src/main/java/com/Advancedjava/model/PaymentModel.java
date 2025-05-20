package com.Advancedjava.model;

import java.math.BigDecimal;
import java.sql.Date;

public class PaymentModel {
	  private int transactionId;
	    private BigDecimal paymentAmount;
	    private PaymentMethod paymentMethod;
	    private PaymentStatus paymentStatus;
	    private int bookingId;
	    private Date paymentDate;
	    public PaymentModel(BigDecimal paymentAmount, PaymentMethod paymentMethod, PaymentStatus paymentStatus,
				Date paymentDate, int bookingId) {
		
			this.paymentAmount = paymentAmount;
			this.paymentMethod = paymentMethod;
			this.paymentStatus = paymentStatus;
			this.paymentDate = paymentDate;
			this.bookingId = bookingId;
		}
		
	    public PaymentModel(int transactionId, BigDecimal paymentAmount, PaymentMethod paymentMethod,
				PaymentStatus paymentStatus, Date paymentDate, int bookingId) {
		
			this.transactionId = transactionId;
			this.paymentAmount = paymentAmount;
			this.paymentMethod = paymentMethod;
			this.paymentStatus = paymentStatus;
			this.paymentDate = paymentDate;
			this.bookingId = bookingId;
		}
		
	    public int getTransactionId() {
			return transactionId;
		}
		public void setTransactionId(int transactionId) {
			this.transactionId = transactionId;
		}
		public BigDecimal getPaymentAmount() {
			return paymentAmount;
		}
		public void setPaymentAmount(BigDecimal paymentAmount) {
			this.paymentAmount = paymentAmount;
		}
		public PaymentMethod getPaymentMethod() {
			return paymentMethod;
		}
		public void setPaymentMethod(PaymentMethod paymentMethod) {
			this.paymentMethod = paymentMethod;
		}
		public PaymentStatus getPaymentStatus() {
			return paymentStatus;
		}
		public void setPaymentStatus(PaymentStatus paymentStatus) {
			this.paymentStatus = paymentStatus;
		}
		public Date getPaymentDate() {
			return paymentDate;
		}
		public void setPaymentDate(Date paymentDate) {
			this.paymentDate = paymentDate;
		}
		public int getBookingId() {
			return bookingId;
		}
		public void setBookingId(int bookingId) {
			this.bookingId = bookingId;
		}
		public enum PaymentMethod {
	        bank,
	        card,
	        digital_wallet,
	        none
	    }
	    public enum PaymentStatus {
paid,unpaid
	    }
	    
	    
}
