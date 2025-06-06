package com.Advancedjava.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;

public class BookingModel {
	private int bookingId;
	private int propertyId;
	private int userId;
	private BookingStatus bookingStatus;
	private Date checkInDate;
	private Date checkOutDate;
	private int discountPercent;
	private int numberOfGuests;
	private Timestamp bookingCreatedAt;
	private BigDecimal basePrice;
	private BigDecimal totalPrice;

	public BookingModel(int propertyId, int userId, Date checkInDate, Date checkOutDate, BigDecimal basePrice,
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

	public BookingModel(int bookingId, int propertyId, int userId, BookingStatus bookingStatus, Date checkInDate,
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

	public int getUserId() {
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
		cancelled, pending, confirmed
	}

	public void display() {
		System.out.println("Booking Details:");
		System.out.println("Booking ID: " + this.bookingId);
		System.out.println("Property ID: " + this.propertyId);
		System.out.println("User ID: " + this.userId);
		System.out.println("Booking Status: " + (this.bookingStatus != null ? this.bookingStatus : "N/A"));
		System.out.println("Check-In Date: " + this.checkInDate);
		System.out.println("Check-Out Date: " + this.checkOutDate);
		System.out.println("Base Price (per night): " + this.basePrice);
		System.out.println("Total Price: " + this.totalPrice);
		System.out.println("Discount Percent: " + this.discountPercent + "%");
		System.out.println("Number of Guests: " + this.numberOfGuests);
		System.out
				.println("Booking Created At: " + (this.bookingCreatedAt != null ? this.bookingCreatedAt : "Not Set"));
	}

}
