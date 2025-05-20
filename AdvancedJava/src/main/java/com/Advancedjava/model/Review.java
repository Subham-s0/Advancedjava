package com.Advancedjava.model;

import java.sql.Date;

public class Review {
	 private int reviewId;
	    private int rating;
	    private String comment;
	    private Date reviewDate;
	    private int bookingId;
		public int getReviewId() {
			return reviewId;
		}
		public void setReviewId(int reviewId) {
			this.reviewId = reviewId;
		}
		public int getRating() {
			return rating;
		}
		public void setRating(int rating) {
			this.rating = rating;
		}
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
		public Date getReviewDate() {
			return reviewDate;
		}
		public void setReviewDate(Date reviewDate) {
			this.reviewDate = reviewDate;
		}
		public int getBookingId() {
			return bookingId;
		}
		public void setBookingId(int bookingId) {
			this.bookingId = bookingId;
		}
		public Review(int reviewId, int rating, String comment, Date reviewDate, int bookingId) {
			super();
			this.reviewId = reviewId;
			this.rating = rating;
			this.comment = comment;
			this.reviewDate = reviewDate;
			this.bookingId = bookingId;
		}
		public Review(int rating, String comment, int bookingId) {
			super();
			this.rating = rating;
			this.comment = comment;
			this.bookingId = bookingId;
		}

}
