package com.Advancedjava.dao;

import java.sql.*;
import com.Advancedjava.model.usermodel;
import com.Advancedjava.model.usermodel.gender;
import com.Advancedjava.model.usermodel.userStatus;
import com.Advancedjava.connection.DBconnection;
import com.Advancedjava.exception.DataAccessException;

public class UserDaoimpl implements UserDao {

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
                	   String genderStr = rs.getString("gender");
                       gender genderEnum = null;
                       if (genderStr != null) {
                           genderEnum = gender.valueOf(genderStr.toLowerCase());
                       }

                	user = new usermodel(
                		rs.getString("user_id"),
                        rs.getString("user_firstname"),
                        rs.getString("user_lastname"),
                        rs.getString("user_email"),
                        rs.getString("user_name"),
                        rs.getDate("user_dob").toString(),
                       rs.getString("user_phnno"),
                       genderEnum,
                        rs.getString("user_password"),
                        rs.getString("role_type"),
                        rs.getString("user_profile"),
                        userStatus.valueOf(rs.getString("user_status").toLowerCase())
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
    
    @Override
    public int saveUser(usermodel user) throws DataAccessException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBconnection.getDbConnection();
            String sql = "INSERT INTO users (user_firstname, user_lastname, user_email, " +
                         "user_name, user_dob, user_phnno, user_password) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUserFirstName());
            pstmt.setString(2, user.getUserLastName());
            pstmt.setString(3, user.getUserEmail());
            pstmt.setString(4, user.getUserName());
            pstmt.setString(5, user.getUserDob());
            pstmt.setString(6, user.getUserPhnNo());
            pstmt.setString(7, user.getUserPassword());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected <= 0) {
                throw new SQLException("Failed to create user");
            }
            return rowsAffected;

        } catch (SQLException e) {
            throw new DataAccessException("Failed to save user to the database", e);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
     }
    @Override
    public boolean updateUser(usermodel user) throws DataAccessException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBconnection.getDbConnection();
            String sql = "UPDATE users SET user_firstname = ?, user_lastname = ?, user_email = ?,user_name= ?, " +
                         "user_dob = ?, user_phnno = ?, user_password = ?, role_type = ?, " +
                         "user_profile = ?, user_status = ?, gender = ? WHERE user_id = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUserFirstName());
            pstmt.setString(2, user.getUserLastName());
            pstmt.setString(3, user.getUserEmail());
            pstmt.setString(4, user.getUserName());
            pstmt.setString(5, user.getUserDob());
            pstmt.setString(6, user.getUserPhnNo());
            pstmt.setString(7, user.getUserPassword());
            pstmt.setString(8, user.getuserRole());
            pstmt.setString(9, user.getUserProfilePicture());
            pstmt.setString(10, user.getUserStatus().name());  // enum to string
            pstmt.setString(11, user.getGender().name());     // enum to string
            pstmt.setString(12, user.getUserId());          // userID for WHERE

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected <= 0) {
                throw new SQLException("No user found with the given user_name.");
            }
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new DataAccessException("Failed to update user in the database", e);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public usermodel findByUserId(String identifier) throws DataAccessException {
        String sql = "SELECT * FROM users WHERE user_Id= ?" ;
        usermodel user = null;
        
        try (Connection conn = DBconnection.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, identifier);
          
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                	   String genderStr = rs.getString("gender");
                       gender genderEnum = null;
                       if (genderStr != null) {
                           genderEnum = gender.valueOf(genderStr.toLowerCase());
                       }

                	user = new usermodel(
                		rs.getString("user_id"),
                        rs.getString("user_firstname"),
                        rs.getString("user_lastname"),
                        rs.getString("user_email"),
                        rs.getString("user_name"),
                        rs.getDate("user_dob").toString(),
                       rs.getString("user_phnno"),
                       genderEnum,
                        rs.getString("user_password"),
                        rs.getString("role_type"),
                        rs.getString("user_profile"),
                        userStatus.valueOf(rs.getString("user_status").toLowerCase())
                    );
                    
                }
                return user;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Database error while fetching user", e);}
        }
        

}