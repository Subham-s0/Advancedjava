package com.Advancedjava.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.Advancedjava.model.PropertyImagemodel;
import com.Advancedjava.model.Propertymodel;
import com.Advancedjava.model.Propertymodel.PropertyStatus;
import com.Advancedjava.connection.DBconnection;
import com.Advancedjava.exception.DataAccessException;

public class PropertyDaoImpl implements PropertyDao {

	private PropertyImageDao propertyImageDao;

	public PropertyDaoImpl() {
		this.propertyImageDao = new PropertyImageDaoImpl();
	}
	

	@Override
	public Propertymodel findById(int propertyId) throws DataAccessException {
		String sql = "SELECT * FROM properties WHERE property_id = ?";
		Propertymodel property = null;

		try (Connection conn = DBconnection.getDbConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, propertyId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					property = new Propertymodel(rs.getInt("property_id"), rs.getString("property_name"),
							rs.getString("property_address"), rs.getString("property_city"),
							rs.getString("property_country"), rs.getString("property_description"),
							PropertyStatus.valueOf(rs.getString("property_status").toUpperCase()),
							rs.getBigDecimal("price_per_night"), rs.getInt("maximum_guests"), rs.getInt("total_bedrooms"),
							rs.getInt("total_bath"), rs.getInt("total_rooms"), rs.getInt("category_id"),
							rs.getString("host_name"), propertyImageDao.findByPropertyId(propertyId),
							rs.getInt("cleaning_fee"), rs.getInt("tax_rate"), rs.getInt("service_fee"));
				}
				return property;
			}
		} catch (SQLException e) {
			throw new DataAccessException("Database error while fetching property", e);
		}
	}

	@Override
	public List<Propertymodel> listAllPropertiesByCategory(int categoryId) throws DataAccessException {
		String sql = "SELECT * FROM properties WHERE category_id = ?";
		List<Propertymodel> properties = new ArrayList<>();

		try (Connection conn = DBconnection.getDbConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, categoryId);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					int propertyId = rs.getInt("property_id");
					Propertymodel property = new Propertymodel(propertyId, rs.getString("property_name"),
							rs.getString("property_address"), rs.getString("property_city"),
							rs.getString("property_country"), rs.getString("property_description"),
							PropertyStatus.valueOf(rs.getString("property_status").toUpperCase()),
							rs.getBigDecimal("price_per_night"), rs.getInt("maximum_guests"), rs.getInt("total_bedrooms"),
							rs.getInt("total_bath"), rs.getInt("total_rooms"), rs.getInt("category_id"),
							rs.getString("host_name"), propertyImageDao.findByPropertyId(propertyId));
					properties.add(property);
				}
				return properties;
			}
		} catch (SQLException e) {
			throw new DataAccessException("Database error while fetching properties by category", e);
		}
	}

	@Override
	public List<Propertymodel> findallproperties() throws DataAccessException {
		String sql = "SELECT * FROM properties";
		List<Propertymodel> properties = new ArrayList<>();

		try (Connection conn = DBconnection.getDbConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				int propertyId = rs.getInt("property_id");
				Propertymodel property = new Propertymodel(propertyId, rs.getString("property_name"),
						rs.getString("property_address"), rs.getString("property_city"),
						rs.getString("property_country"), rs.getString("property_description"),
						PropertyStatus.valueOf(rs.getString("property_status").toUpperCase()),
						rs.getBigDecimal("price_per_night"), rs.getInt("maximum_guests"), rs.getInt("total_bedrooms"),
						rs.getInt("total_bath"), rs.getInt("total_rooms"), rs.getInt("category_id"),
						rs.getString("host_name"), propertyImageDao.findByPropertyId(propertyId));
				properties.add(property);
			}
			return properties;
		} catch (SQLException e) {
			throw new DataAccessException("Database error while fetching all properties", e);
		}
	}
	@Override
	public int save(Propertymodel property) throws DataAccessException {
	    String sql = "INSERT INTO properties (property_name, property_address, property_city, property_country, "
	            + "property_description, property_status, price_per_night, maximum_guests, total_bedrooms, "
	            + "total_bath, total_rooms, category_id, host_name, cleaning_fee, tax_rate, service_fee) "
	            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try (Connection conn = DBconnection.getDbConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

	        pstmt.setString(1, property.getPropertyName());
	        pstmt.setString(2, property.getPropertyAddress());
	        pstmt.setString(3, property.getPropertyCity());
	        pstmt.setString(4, property.getPropertyCountry());
	        pstmt.setString(5, property.getPropertyDescription());
	        pstmt.setString(6, property.getPropertyStatus().toString());
	        pstmt.setBigDecimal(7, property.getPricePerNight());
	        pstmt.setInt(8, property.getMaximumGuests());
	        pstmt.setInt(9, property.getTotalBedrooms());
	        pstmt.setInt(10, property.getTotalBath());
	        pstmt.setInt(11, property.getTotalRooms());
	        pstmt.setInt(12, property.getCategoryId());
	        pstmt.setString(13, property.getHostName());
	        
	        pstmt.setInt(14,property.getCleaningFee());
	        pstmt.setInt(15, property.getTaxRate());
	        pstmt.setInt(16, property.getServiceFee());

	        int affectedRows = pstmt.executeUpdate();

	        if (affectedRows == 0) {
	            throw new DataAccessException("Creating property failed, no rows affected.");
	        }

	        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                int propertyId = generatedKeys.getInt(1);
	                // Save images if any
	                if (property.getImages() != null && !property.getImages().isEmpty()) {
	                    for (PropertyImagemodel image : property.getImages()) {
	                        propertyImageDao.save(image, propertyId);
	                    }
	                }
	                return propertyId;
	            } else {
	                throw new DataAccessException("Creating property failed, no ID obtained.");
	            }
	        }
	    } catch (SQLException ex) {
	        throw new DataAccessException("Error saving property", ex);
	    }
	}
	
	
	@Override
	public boolean update(Propertymodel property) throws DataAccessException {
		String sql = "UPDATE properties SET property_name = ?, property_address = ?, property_city = ?, "
				+ "property_country = ?, property_description = ?, property_status = ?, price_per_night = ?, "
				+ "maximum_guests = ?, total_bedrooms = ?, total_bath = ?, total_rooms = ?, category_id = ?, "
				+ "host_name = ?, cleaning_fee = ?, tax_rate = ?, service_fee = ? WHERE property_id = ?";

		try (Connection conn = DBconnection.getDbConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, property.getPropertyName());
			pstmt.setString(2, property.getPropertyAddress());
			pstmt.setString(3, property.getPropertyCity());
			pstmt.setString(4, property.getPropertyCountry());
			pstmt.setString(5, property.getPropertyDescription());
			pstmt.setString(6, property.getPropertyStatus().toString());
			pstmt.setBigDecimal(7, property.getPricePerNight());
			pstmt.setInt(8, property.getMaximumGuests());
			pstmt.setInt(9, property.getTotalBedrooms());
			pstmt.setInt(10, property.getTotalBath());
			pstmt.setInt(11, property.getTotalRooms());
			pstmt.setInt(12, property.getCategoryId());
			pstmt.setString(13, property.getHostName());
			 pstmt.setInt(14,property.getCleaningFee());
		        pstmt.setInt(15, property.getTaxRate());
		        pstmt.setInt(16, property.getServiceFee());
			pstmt.setInt(17, property.getPropertyId());
			

			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new DataAccessException("Database error while updating property", e);
		}
	}

	@Override
	public boolean delete(int propertyId) throws DataAccessException {
		// First delete all images associated with the property
		boolean imgok = propertyImageDao.deleteByPropertyId(propertyId);
		AmenityDaoImpl amenityDao = new AmenityDaoImpl();
		boolean faok= amenityDao.deletePropertyAmenity(propertyId);
		String sql = "DELETE FROM properties WHERE property_id = ?";
		if (!imgok ) {
			throw new DataAccessException("Error deleting property images ");
		}
			if (!faok) {
			throw new DataAccessException("Error deleting property  amenities");
		}
		try (Connection conn = DBconnection.getDbConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, propertyId);
			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
			throw new DataAccessException("Database error while deleting property", e);
		}
	}

	@Override
	public List<Propertymodel> findByStatus(PropertyStatus status) throws DataAccessException {
		String sql = "SELECT * FROM properties WHERE property_status = ?";
		List<Propertymodel> properties = new ArrayList<>();

		try (Connection conn = DBconnection.getDbConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, status.toString());

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					int propertyId = rs.getInt("property_id");
					Propertymodel property = new Propertymodel(propertyId, rs.getString("property_name"),
							rs.getString("property_address"), rs.getString("property_city"),
							rs.getString("property_country"), rs.getString("property_description"),
							PropertyStatus.valueOf(rs.getString("property_status").toUpperCase()),
							rs.getBigDecimal("price_per_night"), rs.getInt("maximum_guests"), rs.getInt("total_bedrooms"),
							rs.getInt("total_bath"), rs.getInt("total_rooms"), rs.getInt("category_id"),
							rs.getString("host_name"), propertyImageDao.findByPropertyId(propertyId));
					properties.add(property);
				}
				return properties;
			}
		} catch (SQLException e) {
			throw new DataAccessException("Database error while fetching properties by status", e);
		}
	}

	@Override
	public List<Propertymodel> findByCity(String city) throws DataAccessException {
		String sql = "SELECT * FROM properties WHERE property_city = ?";
		List<Propertymodel> properties = new ArrayList<>();

		try (Connection conn = DBconnection.getDbConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, city);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					int propertyId = rs.getInt("property_id");
					Propertymodel property = new Propertymodel(propertyId, rs.getString("property_name"),
							rs.getString("property_address"), rs.getString("property_city"),
							rs.getString("property_country"), rs.getString("property_description"),
							PropertyStatus.valueOf(rs.getString("property_status").toUpperCase()),
							rs.getBigDecimal("price_per_night"), rs.getInt("maximum_guests"), rs.getInt("total_bedrooms"),
							rs.getInt("total_bath"), rs.getInt("total_rooms"), rs.getInt("category_id"),
							rs.getString("host_name"), propertyImageDao.findByPropertyId(propertyId));
					properties.add(property);
				}
				return properties;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Database error while fetching properties by city", e);
		}
	}

	@Override
	public boolean Propertyexists(String propertyName, String address, String city, String country) 
		    throws DataAccessException {
		    
		    String sql = "SELECT COUNT(*) FROM properties " +
		                 "WHERE property_name = ? " +
		                 "AND property_address = ? " +
		                 "AND property_city = ? " +
		                 "AND property_country = ?";
		    
		    try (Connection conn = DBconnection.getDbConnection();
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {
		        
		        pstmt.setString(1, propertyName);
		        pstmt.setString(2, address);
		        pstmt.setString(3, city);
		        pstmt.setString(4, country);
		        
		        try (ResultSet rs = pstmt.executeQuery()) {
		            return rs.next() && rs.getInt(1) > 0;
		        }
		    } catch (SQLException e) {
		        throw new DataAccessException("Error checking property existence by details", e);
		    }
		}
	public List<Integer> findAmenityIdsByPropertyId(int propertyId) throws DataAccessException {
	    String sql = "SELECT amenity_id FROM property_amenity WHERE property_id = ?";
	    List<Integer> amenityIds = new ArrayList<>();

	    try (Connection conn = DBconnection.getDbConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setInt(1, propertyId);
	        
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                int amenityId = rs.getInt("amenity_id");
	                amenityIds.add(amenityId);
	            }
	        }
	        return amenityIds;
	        
	    } catch (SQLException e) {
	        throw new DataAccessException("Error fetching amenity IDs for property ID: " + propertyId, e);
	    }
	}
	public boolean propertyExistsExcludingId(String propertyName, String address, 
            String city, String country, int excludeId) 
            throws DataAccessException {

String sql = "SELECT COUNT(*) FROM properties " +
"WHERE property_name = ? " +
"AND property_address = ? " +
"AND property_city = ? " +
"AND property_country = ? " +
"AND property_id != ?";  // Exclusion clause

try (Connection conn = DBconnection.getDbConnection();
PreparedStatement pstmt = conn.prepareStatement(sql)) {

pstmt.setString(1, propertyName);
pstmt.setString(2, address);
pstmt.setString(3, city);
pstmt.setString(4, country);
pstmt.setInt(5, excludeId);  // New parameter

try (ResultSet rs = pstmt.executeQuery()) {
return rs.next() && rs.getInt(1) > 0;
}
} catch (SQLException e) {
throw new DataAccessException("Error checking property existence with exclusion", e);
}
}
}