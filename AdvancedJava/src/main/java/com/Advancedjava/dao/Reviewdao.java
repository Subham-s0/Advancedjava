package com.Advancedjava.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.Advancedjava.connection.DBconnection;
import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.Review;

public class Reviewdao {
	public int saveReview(Review review) throws DataAccessException {
	    String sql = "INSERT INTO review (rating, comment, review_date, booking_id) VALUES (?, ?, ?, ?)";

	    try (Connection conn = DBconnection.getDbConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

	        stmt.setInt(1, review.getRating());
	        stmt.setString(2, review.getComment());
	        stmt.setDate(3, review.getReviewDate());
	        stmt.setInt(4, review.getBookingId());

	        int rows = stmt.executeUpdate();

	        if (rows == 0) {
	            throw new DataAccessException("Failed to insert review");
	        }

	        try (ResultSet rs = stmt.getGeneratedKeys()) {
	            if (rs.next()) {
	                return rs.getInt(1);  // return generated review_id
	            } else {
	                throw new DataAccessException("Failed to get generated review ID");
	            }
	        }

	    } catch (SQLException e) {
	        throw new DataAccessException("Database error while saving review", e);
	    }
	}
	public List<Review> getReviewsByPropertyId(int propertyId) throws SQLException {
	    List<Review> reviews = new ArrayList<>();

	    String sql = "SELECT r.review_id, r.rating, r.comment, r.review_date, r.booking_id " +
	                 "FROM review r JOIN booking b ON r.booking_id = b.booking_id " +
	                 "WHERE b.property_id = ?";

	    try (Connection connection = DBconnection.getDbConnection();
	         PreparedStatement ps = connection.prepareStatement(sql)) {

	        ps.setInt(1, propertyId);

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Review review = new Review(rs.getInt("review_id"),
	                                        rs.getInt("rating"),
	                                        rs.getString("comment"),
	                                        rs.getDate("review_date"),
	                                        rs.getInt("booking_id"));
	           
	            reviews.add(review);
	        }
	    }
	    return reviews;
	}

	public boolean reviewExistsForBooking(int bookingId) throws DataAccessException {
	    String sql = "SELECT COUNT(*) FROM review WHERE booking_id = ?";
	    
	    try (Connection conn = DBconnection.getDbConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setInt(1, bookingId);
	        
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }
	            return false;
	        }
	        
	    } catch (SQLException e) {
	        throw new DataAccessException("Database error while checking existing review", e);
	    }
	}
}
