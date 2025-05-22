package com.Advancedjava.model;

public class Categorymodel {
	private int categoryId;
	private String categoryName;
	private String categoryIcon;

	public Categorymodel(int categoryId, String categoryName, String categoryIcon) {

		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.categoryIcon = categoryIcon;
	}

	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryIcon() {
		return this.categoryIcon;
	}

	public void setCategoryIcon(String categoryIcon) {
		this.categoryIcon = categoryIcon;

	}

	public Categorymodel(String categoryName, String categoryIcon) {
		this.categoryName = categoryName;
		this.categoryIcon = categoryIcon;
	}

}
