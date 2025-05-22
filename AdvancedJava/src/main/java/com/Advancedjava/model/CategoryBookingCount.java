package com.Advancedjava.model;

public class CategoryBookingCount {
	private String categoryName;
	private int totalBookings;

	public CategoryBookingCount(String categoryName, int totalBookings) {
		this.categoryName = categoryName;
		this.totalBookings = totalBookings;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public int getTotalBookings() {
		return totalBookings;
	}
}
