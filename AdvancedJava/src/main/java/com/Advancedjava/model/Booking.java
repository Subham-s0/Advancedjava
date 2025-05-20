package com.Advancedjava.model;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.sql.Date;

public class Booking {
	    private int bookingId;
	    private int propertyId;
	    private Integer userId; 
	    private BookingStatus bookingStatus;
	    private Date checkInDate;
	    private Date checkOutDate;
	    private BigDecimal basePrice;
	    private BigDecimal totalPrice;
	    public Booking(int propertyId, Integer userId, Date checkInDate, Date checkOutDate, BigDecimal basePrice,
				BigDecimal totalPrice, int discountPercent, int numberOfGuests) {
			
			this.propertyId = propertyId;
			this.userId = userId;
			this.checkInDate = checkInDate;
			this.checkOutDate = checkOutDate;
			this.basePrice = basePrice;
			this.totalPrice = totalPrice;
			this.discountPercent = discountPercent;
			this.numberOfGuests = numberOfGuests;
		}
	    
		public Booking(int bookingId, int propertyId, Integer userId, BookingStatus bookingStatus, Date checkInDate,
				Date checkOutDate, BigDecimal basePrice, BigDecimal totalPrice, int discountPercent, int numberOfGuests,
				Timestamp bookingCreatedAt) {
		
			this.bookingId = bookingId;
			this.propertyId = propertyId;
			this.userId = userId;
			this.bookingStatus = bookingStatus;
			this.checkInDate = checkInDate;
			this.checkOutDate = checkOutDate;
			this.basePrice = basePrice;
			this.totalPrice = totalPrice;
			this.discountPercent = discountPercent;
			this.numberOfGuests = numberOfGuests;
			this.bookingCreatedAt = bookingCreatedAt;
		}

		private int discountPercent;
	    private int numberOfGuests;
	    private Timestamp bookingCreatedAt;
		public int getBookingId() {
			return this.bookingId;
		}
		public void setBookingId(int bookingId) {
			this.bookingId = bookingId;
		}
		public int getPropertyId() {
			return propertyId;
		}
		public void setPropertyId(int propertyId) {
			this.propertyId = propertyId;
		}
		public Integer getUserId() {
			return this.userId;
		}
		public void setUserId(Integer userId) {
			this.userId = userId;
		}
		public BookingStatus getBookingStatus() {
			return this.bookingStatus;
		}
		public void setBookingStatus(BookingStatus bookingStatus) {
			this.bookingStatus = bookingStatus;
		}
		public Date getCheckInDate() {
			return checkInDate;
		}
		public void setCheckInDate(Date checkInDate) {
			this.checkInDate = checkInDate;
		}
		public Date getCheckOutDate() {
			return checkOutDate;
		}
		public void setCheckOutDate(Date checkOutDate) {
			this.checkOutDate = checkOutDate;
		}
		public BigDecimal getBasePrice() {
			return basePrice;
		}
		public void setBasePrice(BigDecimal basePrice) {
			this.basePrice = basePrice;
		}
		public BigDecimal getTotalPrice() {
			return totalPrice;
		}
		public void setTotalPrice(BigDecimal totalPrice) {
			this.totalPrice = totalPrice;
		}
		public int getDiscountPercent() {
			return discountPercent;
		}
		public void setDiscountPercent(int discountPercent) {
			this.discountPercent = discountPercent;
		}
		public int getNumberOfGuests() {
			return numberOfGuests;
		}
		public void setNumberOfGuests(int numberOfGuests) {
			this.numberOfGuests = numberOfGuests;
		}
		public Timestamp getBookingCreatedAt() {
			return bookingCreatedAt;
		}
		public void setBookingCreatedAt(Timestamp bookingCreatedAt) {
			this.bookingCreatedAt = bookingCreatedAt;
		}
		public enum BookingStatus {
		    CANCELLED,
		    PENDING,
		    CONFIRMED
		}
		
		
}
