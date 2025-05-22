package com.Advancedjava.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.Advancedjava.model.PropertyImagemodel;
import com.Advancedjava.connection.DBconnection;
import com.Advancedjava.exception.DataAccessException;

public class PropertyImageDaoImpl implements PropertyImageDao {

	@Override
	public List<PropertyImagemodel> findByPropertyId(int propertyId) throws DataAccessException {
		String sql = "SELECT * FROM property_images WHERE property_id = ?";
		List<PropertyImagemodel> images = new ArrayList<>();

		try (Connection conn = DBconnection.getDbConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, propertyId);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					PropertyImagemodel image = new PropertyImagemodel(rs.getInt("image_id"), propertyId,
							rs.getString("file_name"), rs.getString("image_name"));

					images.add(image);
				}
			}
			return images;
		} catch (SQLException e) {
			throw new DataAccessException("Database error while fetching property images", e);
		}
	}

	@Override
	public int save(PropertyImagemodel image, int propertyId) throws DataAccessException {
		String sql = "INSERT INTO property_images (property_id, file_name, image_name) VALUES (?, ?, ?)";

		try (Connection conn = DBconnection.getDbConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setInt(1, propertyId);
			pstmt.setString(2, image.getFileName());
			pstmt.setString(3, image.getImageName());

			int affectedRows = pstmt.executeUpdate();

			if (affectedRows == 0) {
				throw new DataAccessException("Creating image failed, no rows affected.");
			}

			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				} else {
					throw new DataAccessException("Creating image failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			throw new DataAccessException("Database error while saving property image", e);
		}
	}

	@Override
	public boolean delete(int imageId) throws DataAccessException {
		String sql = "DELETE FROM property_images WHERE image_id = ?";

		try (Connection conn = DBconnection.getDbConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, imageId);
			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
			throw new DataAccessException("Database error while deleting property image", e);
		}
	}

	@Override
	public boolean deleteByPropertyId(int propertyId) throws DataAccessException {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM property_images WHERE property_id= ?";
		try (Connection conn = DBconnection.getDbConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, propertyId);
			int afceted = pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			throw new DataAccessException("Database error while deleting property image", e);
		}
	}

	public PropertyImagemodel findByImageId(int imageId) throws DataAccessException {
		String sql = "SELECT * FROM property_images WHERE image_id = ?";

		try (Connection conn = DBconnection.getDbConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, imageId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) { // Use if instead of while since we expect at most 1 result
					return new PropertyImagemodel(rs.getInt("image_id"), rs.getInt("property_id"),
							rs.getString("file_name"), rs.getString("image_name"));
				}
				return null; // Explicit return if no results found
			}
		} catch (SQLException e) {
			throw new DataAccessException("Error fetching image by ID: " + imageId, e);
		}
	}

}