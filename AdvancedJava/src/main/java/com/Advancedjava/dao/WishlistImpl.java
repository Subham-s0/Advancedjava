package com.Advancedjava.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Advancedjava.connection.DBconnection;
import com.Advancedjava.exception.DataAccessException;

public class WishlistImpl implements WishlistDao {

	@Override
	public void addToWishlist(String userId, int productId) throws DataAccessException {
		String query = "INSERT INTO User_Wishlist (user_id, wished_product_id) VALUES (?, ?)";

		try (Connection conn = DBconnection.getDbConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, userId); // Set userId as a String
			pstmt.setInt(2, productId);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new DataAccessException("Error adding to wishlist", e);
		}
	}

	@Override
	public void removeFromWishlist(String userId, int productId) throws DataAccessException {
		String query = "DELETE FROM User_Wishlist WHERE user_id = ? AND wished_product_id = ?";

		try (Connection conn = DBconnection.getDbConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, userId); // Set userId as a String
			pstmt.setInt(2, productId);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new DataAccessException("Error removing from wishlist", e);
		}
	}

	@Override
	public boolean isInWishlist(String userId, int productId) throws DataAccessException {
		String query = "SELECT COUNT(*) FROM User_Wishlist WHERE user_id = ? AND wished_product_id = ?";

		try (Connection conn = DBconnection.getDbConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, userId); // Set userId as a String
			pstmt.setInt(2, productId);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next() && rs.getInt(1) > 0;
			}

		} catch (SQLException e) {
			throw new DataAccessException("Error checking wishlist status", e);
		}
	}

	@Override
	public List<Integer> getWishlistByUserId(String userId) throws DataAccessException {
		String query = "SELECT wished_product_id FROM User_Wishlist WHERE user_id = ?";
		List<Integer> wishlist = new ArrayList<>();

		try (Connection conn = DBconnection.getDbConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, userId); // Set userId as a String
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					wishlist.add(rs.getInt("wished_product_id"));
				}
			}
		} catch (SQLException e) {
			throw new DataAccessException("Error fetching wishlist items for user", e);
		}

		return wishlist;
	}
}
