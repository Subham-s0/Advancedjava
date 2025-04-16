package com.Advancedjava.dao;

import java.sql.*;
import com.Advancedjava.model.usermodel;
import com.Advancedjava.connection.DBconnection;
import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.util.PasswordHasher;

public class UserDaoimpl implements UserDao {

    @Override
    public usermodel findByUsernameOrEmail(String identifier) throws DataAccessException {
        String sql = "SELECT * FROM users WHERE user_name = ? OR user_email = ? OR user_phnno= ?" ;
        usermodel user = null;
        
        try (Connection conn = DBconnection.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, identifier);
            pstmt.setString(2, identifier);
            pstmt.setString(3, identifier);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                	user = new usermodel(
                        rs.getString("user_firstname"),
                        rs.getString("user_lastname"),
                        rs.getString("user_email"),
                        rs.getString("user_name"),
                        rs.getDate("user_dob").toString(),
                        rs.getString("user_phnno"),
                        rs.getString("user_password")
                    );
                    
                }
                return user;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Database error while fetching user", e);}
        }
        

    @Override
    public boolean existsByEmail(String email) throws DataAccessException {
        return checkExists("user_email", email);
    }

    @Override
    public boolean existsByUsername(String username) throws DataAccessException {
        return checkExists("user_name", username);
    }

    @Override
    public boolean existsByPhone(String phone) throws DataAccessException {
        return checkExists("user_phnno", phone);
    }

    private boolean checkExists(String column, String value) throws DataAccessException {
        String query = "SELECT COUNT(*) FROM users WHERE " + column + " = ?";
        
        try (Connection conn = DBconnection.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, value);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Database error checking existence", e);
        }
    }
}