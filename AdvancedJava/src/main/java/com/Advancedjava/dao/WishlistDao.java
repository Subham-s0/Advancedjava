package com.Advancedjava.dao;

import java.util.List;

import com.Advancedjava.exception.DataAccessException;

public interface WishlistDao {
	    void addToWishlist(String userId, int productId) throws DataAccessException;
	    void removeFromWishlist(String userId, int productId) throws DataAccessException;
	    boolean isInWishlist(String userId, int productId) throws DataAccessException;
	    List<Integer> getWishlistByUserId(String userId) throws DataAccessException;
	}
