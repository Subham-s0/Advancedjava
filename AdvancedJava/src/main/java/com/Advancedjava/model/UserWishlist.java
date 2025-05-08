package com.Advancedjava.model;

public class UserWishlist {
	 private int userId;
	 private int wishedProductId;
	 public UserWishlist(int userId, int wishedProductId) {
	        this.userId = userId;
	        this.wishedProductId = wishedProductId;
	    }

	    public int getUserId() {
	        return userId;
	    }

	    public int getWishedProductId() {
	        return wishedProductId;
	    }

	    public void setUserId(int userId) {
	        this.userId = userId;
	    }

	    public void setWishedProductId(int wishedProductId) {
	        this.wishedProductId = wishedProductId;
	    }

}
