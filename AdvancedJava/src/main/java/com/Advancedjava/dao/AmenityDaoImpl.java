package com.Advancedjava.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Advancedjava.connection.DBconnection;
import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.AmenityModel;
import java.sql.Statement; 

public class AmenityDaoImpl implements AmenityDao {
	 @Override
	    public List<AmenityModel> findAll()throws DataAccessException{
	        String sql = "SELECT * FROM amenity";
	        List<AmenityModel> amenities = new ArrayList<>();
	        
	        try (Connection conn = DBconnection.getDbConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql);
	             ResultSet rs = pstmt.executeQuery()) {
	            
	            while (rs.next()) {
	            	AmenityModel amenity = new AmenityModel( rs.getInt("amenity_id"),
	                        rs.getString("amenity_name"),
	                        rs.getString("amenity_description"),
	                        rs.getString("amenity_icon"));
	                amenities.add(amenity);
	            }
	            
	            return amenities;
	        } catch (SQLException e) {
	        	throw new DataAccessException("Database error while fetching all properties", e);
	        }
	    
	 }
	    /**
	     * Finds an amenity by its ID
	     * 
	     * @param amenityId The ID of the amenity to find
	     * @return The found amenity or null if not found
	     */
	    @Override
	    public AmenityModel findById(int amenityId)throws DataAccessException {
	        String sql = "SELECT * FROM amenity WHERE amenity_id = ?";
	        AmenityModel amenity = null;
	        
	        try (Connection conn = DBconnection.getDbConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            
	            pstmt.setInt(1, amenityId);
	            
	            try (ResultSet rs = pstmt.executeQuery()) {
	                if (rs.next()) {
	                    amenity =new AmenityModel( rs.getInt("amenity_id"),
		                        rs.getString("amenity_name"),
		                        rs.getString("amenity_description"),
		                        rs.getString("amenity_icon"));
		           
	                }
	                return amenity;
	            }
	        } catch (SQLException e) {
	        	throw new DataAccessException("Database error while fetching all properties", e);
	        }
	    }
	    
	    /**
	     * Adds a new amenity to the database
	     * 
	     * @param amenity The amenity to add
	     * @return The ID of the newly added amenity, or -1 if the operation failed
	     */
	    @Override
	    public int add(AmenityModel amenity) throws DataAccessException {
	        String sql = "INSERT INTO amenity (amenity_name, amenity_description, amenity_icon) VALUES (?, ?, ?)";
	        
	        try (Connection conn = DBconnection.getDbConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	            
	            pstmt.setString(1, amenity.getAmenityName());
	            pstmt.setString(2, amenity.getAmenityDescription());
	            pstmt.setString(3, amenity.getAmenityIcon());
	            
	            int affectedRows = pstmt.executeUpdate();
	            
	            if (affectedRows == 0) {
	                return -1;
	            }
	            
	            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    return generatedKeys.getInt(1);
	                } else {
	                    return -1;
	                }
	            }
	        } catch (SQLException e) {
	            throw new DataAccessException("Database error while adding new amenity", e);
	        }
	    }
	    
	    /**
	     * Deletes an amenity from the database
	     * 
	     * @param amenityId The ID of the amenity to delete
	     * @return true if successful, false otherwise
	     */
	    @Override
	    public boolean delete(int amenityId) throws DataAccessException {
	        String sql = "DELETE FROM amenity WHERE amenity_id = ?";
	        
	        try (Connection conn = DBconnection.getDbConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            
	            pstmt.setInt(1, amenityId);
	            
	            int affectedRows = pstmt.executeUpdate();
	            return affectedRows > 0;
	        } catch (SQLException e) {
	            throw new DataAccessException("Database error while deleting amenity with ID " + amenityId, e);
	        }
	    }
	    
	    /**
	     * Updates an existing amenity in the database
	     * 
	     * @param amenity The amenity with updated information
	     * @return true if successful, false otherwise
	     */
	    @Override
	    public boolean update(AmenityModel amenity) throws DataAccessException {
	        String sql = "UPDATE amenity SET amenity_name = ?, amenity_description = ?, amenity_icon = ? WHERE amenity_id = ?";
	        
	        try (Connection conn = DBconnection.getDbConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            
	            pstmt.setString(1, amenity.getAmenityName());
	            pstmt.setString(2, amenity.getAmenityDescription());
	            pstmt.setString(3, amenity.getAmenityIcon());
	            pstmt.setInt(4, amenity.getAmenityId());
	            
	            int affectedRows = pstmt.executeUpdate();
	            return affectedRows > 0;
	        } catch (SQLException e) {
	            throw new DataAccessException("Database error while updating amenity with ID " + amenity.getAmenityId(), e);
	        }
	    }
	  }
