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
        
        try (Connection conn = DBconnection.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, propertyId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    property = new Propertymodel(
                        rs.getInt("property_id"),
                        rs.getString("property_name"),
                        rs.getString("property_address"),
                        rs.getString("property_city"),
                        rs.getString("property_country"),
                        rs.getString("property_description"),
                        PropertyStatus.valueOf(rs.getString("property_status").toUpperCase()),
                        rs.getDouble("price_per_night"),
                        rs.getInt("maximum_guests"),
                        rs.getInt("total_bedrooms"),
                        rs.getInt("total_bath"),
                        rs.getInt("total_rooms"),
                        rs.getInt("category_id"),
                        rs.getString("host_name"),
                        propertyImageDao.findByPropertyId(propertyId),
                        rs.getDouble("cleaning_fee"),
                        rs.getDouble("tax_rate"),
                        rs.getDouble("service_fee")
                    );
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
        
        try (Connection conn = DBconnection.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, categoryId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int propertyId = rs.getInt("property_id");
                    Propertymodel property = new Propertymodel(
                        propertyId,
                        rs.getString("property_name"),
                        rs.getString("property_address"),
                        rs.getString("property_city"),
                        rs.getString("property_country"),
                        rs.getString("property_description"),
                        PropertyStatus.valueOf(rs.getString("property_status").toUpperCase()),
                        rs.getDouble("price_per_night"),
                        rs.getInt("maximum_guests"),
                        rs.getInt("total_bedrooms"),
                        rs.getInt("total_bath"),
                        rs.getInt("total_rooms"),
                        rs.getInt("category_id"),
                        rs.getString("host_name"),
                        propertyImageDao.findByPropertyId(propertyId)
                    );
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
                Propertymodel property = new Propertymodel(
                    propertyId,
                    rs.getString("property_name"),
                    rs.getString("property_address"),
                    rs.getString("property_city"),
                    rs.getString("property_country"),
                    rs.getString("property_description"),
                    PropertyStatus.valueOf(rs.getString("property_status").toUpperCase()),
                    rs.getDouble("price_per_night"),
                    rs.getInt("maximum_guests"),
                    rs.getInt("total_bedrooms"),
                    rs.getInt("total_bath"),
                    rs.getInt("total_rooms"),
                    rs.getInt("category_id"),
                    rs.getString("host_name"),
                    propertyImageDao.findByPropertyId(propertyId)
                );
                properties.add(property);
            }
            return properties;
        } catch (SQLException e) {
            throw new DataAccessException("Database error while fetching all properties", e);
        }
    }

    @Override
    public int save(Propertymodel property) throws DataAccessException {
        String sql = "INSERT INTO properties (property_name, property_address, property_city, property_country, " +
                     "property_description, property_status, price_per_night, maximum_guests, total_bedrooms, " +
                     "total_bath, total_rooms, category_id, host_name) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBconnection.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, property.getPropertyName());
            pstmt.setString(2, property.getPropertyAddress());
            pstmt.setString(3, property.getPropertyCity());
            pstmt.setString(4, property.getPropertyCountry());
            pstmt.setString(5, property.getPropertyDescription());
            pstmt.setString(6, property.getPropertyStatus().toString());
            pstmt.setDouble(7, property.getPricePerNight());
            pstmt.setInt(8, property.getMaximumGuests());
            pstmt.setInt(9, property.getTotalBedrooms());
            pstmt.setInt(10, property.getTotalBath());
            pstmt.setInt(11, property.getTotalRooms());
            pstmt.setInt(12, property.getCategoryId());
            pstmt.setString(13, property.getHostName());
            
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
        } catch (SQLException e) {
            throw new DataAccessException("Database error while saving property", e);
        }
    }

    @Override
    public boolean update(Propertymodel property) throws DataAccessException {
        String sql = "UPDATE properties SET property_name = ?, property_address = ?, property_city = ?, " +
                     "property_country = ?, property_description = ?, property_status = ?, price_per_night = ?, " +
                     "maximum_guests = ?, total_bedrooms = ?, total_bath = ?, total_rooms = ?, category_id = ?, " +
                     "host_name = ? WHERE property_id = ?";
        
        try (Connection conn = DBconnection.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, property.getPropertyName());
            pstmt.setString(2, property.getPropertyAddress());
            pstmt.setString(3, property.getPropertyCity());
            pstmt.setString(4, property.getPropertyCountry());
            pstmt.setString(5, property.getPropertyDescription());
            pstmt.setString(6, property.getPropertyStatus().toString());
            pstmt.setDouble(7, property.getPricePerNight());
            pstmt.setInt(8, property.getMaximumGuests());
            pstmt.setInt(9, property.getTotalBedrooms());
            pstmt.setInt(10, property.getTotalBath());
            pstmt.setInt(11, property.getTotalRooms());
            pstmt.setInt(12, property.getCategoryId());
            pstmt.setString(13, property.getHostName());
            pstmt.setInt(14, property.getPropertyId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Database error while updating property", e);
        }
    }

    @Override
    public boolean delete(int propertyId) throws DataAccessException {
        // First delete all images associated with the property
        propertyImageDao.deleteByPropertyId(propertyId);
        
        String sql = "DELETE FROM properties WHERE property_id = ?";
        
        try (Connection conn = DBconnection.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
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
        
        try (Connection conn = DBconnection.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status.toString());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int propertyId = rs.getInt("property_id");
                    Propertymodel property = new Propertymodel(
                        propertyId,
                        rs.getString("property_name"),
                        rs.getString("property_address"),
                        rs.getString("property_city"),
                        rs.getString("property_country"),
                        rs.getString("property_description"),
                        PropertyStatus.valueOf(rs.getString("property_status").toUpperCase()),
                        rs.getDouble("price_per_night"),
                        rs.getInt("maximum_guests"),
                        rs.getInt("total_bedrooms"),
                        rs.getInt("total_bath"),
                        rs.getInt("total_rooms"),
                        rs.getInt("category_id"),
                        rs.getString("host_name"),
                        propertyImageDao.findByPropertyId(propertyId)
                    );  
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
        
        try (Connection conn = DBconnection.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, city);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int propertyId = rs.getInt("property_id");
                    Propertymodel property = new Propertymodel(
                        propertyId,
                        rs.getString("property_name"),
                        rs.getString("property_address"),
                        rs.getString("property_city"),
                        rs.getString("property_country"),
                        rs.getString("property_description"),
                        PropertyStatus.valueOf(rs.getString("property_status").toUpperCase()),
                        rs.getDouble("price_per_night"),
                        rs.getInt("maximum_guests"),
                        rs.getInt("total_bedrooms"),
                        rs.getInt("total_bath"),
                        rs.getInt("total_rooms"),
                        rs.getInt("category_id"),
                        rs.getString("host_name"),
                        propertyImageDao.findByPropertyId(propertyId)
                    );
                    properties.add(property);
                }
                return properties;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Database error while fetching properties by city", e);
        }
    }
}