package com.Advancedjava.dao;

import java.sql.*;
import java.sql.PreparedStatement;
import java.util.List;

import com.Advancedjava.connection.DBconnection;
import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.BookingModel;


public class BookingDao {
	    public int saveBooking(BookingModel booking) throws DataAccessException {
	        String sql = "INSERT INTO booking (property_id, user_id, booking_status, check_in_date, check_out_date, base_price, total_price, discount_percent, number_of_guests, booking_created_at) " +
	                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	        try (Connection conn = DBconnection.getDbConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {

	            stmt.setInt(1, booking.getPropertyId());
	            stmt.setInt(2, booking.getUserId());
	            stmt.setString(3, booking.getBookingStatus().toString());
	            stmt.setDate(4, booking.getCheckInDate());
	            stmt.setDate(5, booking.getCheckOutDate());
	            stmt.setBigDecimal(6, booking.getBasePrice());
	            stmt.setBigDecimal(7, booking.getTotalPrice());
	            stmt.setInt(8, booking.getDiscountPercent());
	            stmt.setInt(9, booking.getNumberOfGuests());
	            stmt.setTimestamp(10, booking.getBookingCreatedAt());

	            int rows = stmt.executeUpdate();

	            if (rows == 0) {
	                throw new DataAccessException("Failed to insert booking");
	            }

	            try (ResultSet rs = stmt.getGeneratedKeys()) {
	                if (rs.next()) {
	                    return rs.getInt(1);
	                } else {
	                    throw new DataAccessException("Failed to get generated booking ID");
	                }
	            }

	        } catch (SQLException e) {
	            throw new DataAccessException("Database error while saving booking", e);
	        }
	    }

	 
	    public boolean isPropertyAvailable(int propertyId,Date checkIn, Date checkOut) throws DataAccessException {
	        String sql = "SELECT COUNT(*) FROM booking WHERE property_id = ? AND booking_status != 'CANCELLED' " +
	                     "AND (check_in_date < ? AND check_out_date > ?)";

	        try (Connection conn = DBconnection.getDbConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {

	            stmt.setInt(1, propertyId);
	            stmt.setDate(2, checkOut);  // new booking's end
	            stmt.setDate(3, checkIn);   // new booking's start

	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getInt(1) == 0;
	                }
	                return false;
	            }

	        } catch (SQLException e) {
	            throw new DataAccessException("Error checking availability", e);
	        }
	    }

	   
	    public List<BookingModel> getBookingsByUserId(int userId) throws DataAccessException {
	        // Implement if needed
	        return null;
	    }
	}



