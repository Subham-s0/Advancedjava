package com.Advancedjava.dao;

import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.Advancedjava.connection.DBconnection;
import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.BookingModel;
import com.Advancedjava.model.BookingModel.BookingStatus;


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

	    public List<BookingModel> getBookingsByUserId(String id) throws DataAccessException {
	        String sql = "SELECT * FROM booking WHERE user_id = ?";
	        List<BookingModel> bookings = new ArrayList<>();
	        
	        try (Connection conn = DBconnection.getDbConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	             
	            ps.setString(1, id);
	            
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    BookingModel booking = new BookingModel(
	                        rs.getInt("booking_id"),
	                        rs.getInt("property_id"),
	                        rs.getInt("user_id"),
	                        BookingStatus.valueOf(rs.getString("booking_status")),
	                        rs.getDate("check_in_date"),
	                        rs.getDate("check_out_date"),
	                        rs.getBigDecimal("base_price"),
	                        rs.getBigDecimal("total_price"),
	                        rs.getInt("discount_percent"),
	                        rs.getInt("number_of_guests"),
	                        rs.getTimestamp("booking_created_at")
	                    );
	                    
	                    bookings.add(booking);
	                }
	            }
	            
	            return bookings;
	            
	        } catch (SQLException e) {
	            throw new DataAccessException("Error fetching bookings by user ID", e);
	        }
	    }
	    public BookingModel findBookingById(int bookingId) throws DataAccessException {
	        String sql = "SELECT * FROM booking WHERE booking_id = ?";

	        try (Connection conn = DBconnection.getDbConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	        	ps.setInt(1, bookingId);

	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    BookingModel booking = new BookingModel(rs.getInt("booking_id"),
	                    		rs.getInt("property_id"),rs.getInt("user_id"),BookingStatus.valueOf(rs.getString("booking_status")),
	                    		rs.getDate("check_in_date"),rs.getDate("check_out_date"),rs.getBigDecimal("base_price"),
	                    		rs.getBigDecimal("total_price"),rs.getInt("discount_percent"),rs.getInt("number_of_guests"),
	                    		rs.getTimestamp("booking_created_at"));
	                   
	                    
	                    return booking;
	                } else {
	                    return null; // Not found
	                }
	            }

	        } catch (SQLException e) {
	            throw new DataAccessException("Error fetching booking by ID", e);
	        }
	    }
	    public boolean updateBookingStatus(int bookingId, BookingStatus newStatus) throws DataAccessException {
	        String sql = "UPDATE booking SET booking_status = ? WHERE booking_id = ?";

	        try (Connection conn = DBconnection.getDbConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {

	            stmt.setString(1, newStatus.toString());
	            stmt.setInt(2, bookingId);

	            int rowsAffected = stmt.executeUpdate();
	            return rowsAffected > 0;

	        } catch (SQLException e) {
	            throw new DataAccessException("Error updating booking status", e);
	        }
	    }
	    public List<BookingModel> findAllBookings() throws DataAccessException {
	        String sql = "SELECT * FROM booking ORDER BY booking_created_at DESC"; // use ASC for oldest first

	        List<BookingModel> bookings = new ArrayList<>();

	        try (Connection conn = DBconnection.getDbConnection();
	             PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            while (rs.next()) {
	                BookingModel booking = new BookingModel(
	                    rs.getInt("booking_id"),
	                    rs.getInt("property_id"),
	                    rs.getInt("user_id"),
	                    BookingStatus.valueOf(rs.getString("booking_status")),
	                    rs.getDate("check_in_date"),
	                    rs.getDate("check_out_date"),
	                    rs.getBigDecimal("base_price"),
	                    rs.getBigDecimal("total_price"),
	                    rs.getInt("discount_percent"),
	                    rs.getInt("number_of_guests"),
	                    rs.getTimestamp("booking_created_at")
	                );
	                bookings.add(booking);
	            }

	            return bookings;

	        } catch (SQLException e) {
	            throw new DataAccessException("Error fetching all bookings", e);
	        }
	    }
	    public List<BookingModel> findBookingsByStatus(BookingStatus status) throws DataAccessException {
	        String sql = "SELECT * FROM booking WHERE booking_status = ? ORDER BY booking_created_at DESC";
	        
	        List<BookingModel> bookings = new ArrayList<>();
	        
	        try (Connection conn = DBconnection.getDbConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            
	            ps.setString(1, status.toString());
	            
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                	 BookingModel booking = new BookingModel(
	     	                    rs.getInt("booking_id"),
	     	                    rs.getInt("property_id"),
	     	                    rs.getInt("user_id"),
	     	                    BookingStatus.valueOf(rs.getString("booking_status")),
	     	                    rs.getDate("check_in_date"),
	     	                    rs.getDate("check_out_date"),
	     	                    rs.getBigDecimal("base_price"),
	     	                    rs.getBigDecimal("total_price"),
	     	                    rs.getInt("discount_percent"),
	     	                    rs.getInt("number_of_guests"),
	     	                    rs.getTimestamp("booking_created_at")
	     	                );
	     	                bookings.add(booking);
	                }
	            }
	            
	            return bookings;
	            
	        } catch (SQLException e) {
	            throw new DataAccessException("Error fetching bookings by status", e);
	        }
	    }
	    public List<BookingModel> getBookingsBypropertyId(int id) throws DataAccessException {
	        String sql = "SELECT * FROM booking WHERE property_id = ?";
	        List<BookingModel> bookings = new ArrayList<>();
	        
	        try (Connection conn = DBconnection.getDbConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	             
	            ps.setInt(1, id);
	            
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    BookingModel booking = new BookingModel(
	                        rs.getInt("booking_id"),
	                        rs.getInt("property_id"),
	                        rs.getInt("user_id"),
	                        BookingStatus.valueOf(rs.getString("booking_status")),
	                        rs.getDate("check_in_date"),
	                        rs.getDate("check_out_date"),
	                        rs.getBigDecimal("base_price"),
	                        rs.getBigDecimal("total_price"),
	                        rs.getInt("discount_percent"),
	                        rs.getInt("number_of_guests"),
	                        rs.getTimestamp("booking_created_at")
	                    );
	                    
	                    bookings.add(booking);
	                }
	            }
	            
	            return bookings;
	            
	        } catch (SQLException e) {
	            throw new DataAccessException("Error fetching bookings by user ID", e);
	        }
	    }
	  
	}



