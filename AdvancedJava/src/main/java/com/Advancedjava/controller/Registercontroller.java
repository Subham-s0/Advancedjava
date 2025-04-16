package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.sql.PreparedStatement;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.Advancedjava.connection.DBconnection;
import com.Advancedjava.model.usermodel;
import com.Advancedjava.util.PasswordHasher;
import com.Advancedjava.util.ValidationUtil;


/**
 * Servlet implementation class registerservlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/register" })
public class Registercontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errors = new ArrayList<String>();
		String firstName = request.getParameter("user_firstname");
		String lastName = request.getParameter("user_lastname");
	    String email = request.getParameter("user_email");
	    String username = request.getParameter("user_name");
	    String dob = request.getParameter("user_dob");
	    String phone = request.getParameter("user_phnno");
	    String password = request.getParameter("user_password");
	    String confirmpwd =request.getParameter("confirm_password");
	    
	    if (ValidationUtil.isNullOrEmpty(firstName)) {
	        errors.add("First name is required.");
	    } 
	    else if (!ValidationUtil.isAlphabeticupto20char(firstName)) {
	        errors.add("First name can only contain letters, apostrophes, hyphens, and spaces (2-20 characters).");
	    }

	    // Validate Last Name  
	    if (ValidationUtil.isNullOrEmpty(lastName)) {
	        errors.add("Last name is required.");
	    } else if (!ValidationUtil.isAlphabeticupto20char(lastName)) {
	        errors.add("Last name can only contain letters, apostrophes, hyphens, and spaces (2-20 characters).");
	    }

	    // Validate Email
	    if (ValidationUtil.isNullOrEmpty(email)) {
	        errors.add("Email is required.");
	    } else if (!ValidationUtil.isValidEmail(email)) {
	        errors.add("Invalid email format.");
	    }

	    // Validate Username
	    if (ValidationUtil.isNullOrEmpty(username)) {
	        errors.add("Username is required.");
	    } else if (!ValidationUtil.isAlphanumericStartingWithLetter(username)) {
	        errors.add("Username must be 5-20 characters and can only contain letters, numbers, . _ -");
	    }

	    // Validate Date of Birth
	    if (ValidationUtil.isNullOrEmpty(dob)) {
	        errors.add("Date of birth is required.");
	    } else {
	        try {
	        	
	            LocalDate dob2 = LocalDate.parse(dob);
	            LocalDate minDate = LocalDate.now().minusYears(120);
	            LocalDate maxDate = LocalDate.now().minusYears(16);
	            
	            if (dob2.isBefore(minDate) || dob2.isAfter(maxDate)) {
	                errors.add("You must be between 16-120 years old.");
	            }
	        } catch (DateTimeParseException e) {
	            errors.add("Invalid date format (YYYY-MM-DD required).");
	        }
	    }

	    // Validate Phone Number
	    if (ValidationUtil.isNullOrEmpty(phone)) {
	        errors.add("Phone number is required.");
	    }
	    else if (!ValidationUtil.isValidPhoneNumber(phone)) {
	        errors.add("Phone number must be 10 digits.");
	    }

	    // Validate Password
	    if (ValidationUtil.isNullOrEmpty(password)) {
	        errors.add("Password is required.");
	    } else if (!ValidationUtil.isValidPassword(password)) {
	        errors.add("Password Format Mismatch.");
	    } 

	    // Validate Password Match (if you have confirm_password field)
	    if (password.equals(confirmpwd)==false) {
	        errors.add("Passwords do not match.");
	    }
	 // If errors exist, return to form with messages
        if (!errors.isEmpty()==true) {
            request.setAttribute("error", String.join(" ", errors));
            request.setAttribute("user_firstname", firstName);
            request.setAttribute("user_lastname", lastName);
            request.setAttribute("user_name", username);
            request.setAttribute("user_dob", dob);
            request.setAttribute("user_email", email);
            request.setAttribute("user_phnno", phone);
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
            return;
        }
        try {
            // Create UserModule object with your variables
            usermodel user = new usermodel(firstName, 
                lastName,
                email, 
                username,
                dob,
                phone, 
                password
            );
            
            // Database connection and insertion
            Connection conn = null;
            PreparedStatement pstmt = null;
            try {
                conn = DBconnection.getDbConnection(); // Use your DB connection class
            	System.out.println("DAtabase connected Sucessfully");
                
                String sql = "INSERT INTO users (user_firstname, user_lastname, user_email ,user_name, user_dob,user_phnno, user_password) "
                           + "VALUES (?, ?, ?, ?, ?, ?, ?)";
                
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, user.getUserFirstName());
                pstmt.setString(2, user.getUserLastName());
                pstmt.setString(3, user.getUserEmail());
                pstmt.setString(4, user.getUserName());
                pstmt.setDate(5, Date.valueOf(LocalDate.parse(user.getUserDob()))); // Convert String to SQL Date
                pstmt.setString(6, user.getUserPhnNo());
                pstmt.setString(7, PasswordHasher.hash(user.getUserPassword()));
                
                int rowsAffected = pstmt.executeUpdate();
                 
                if (rowsAffected > 0) {
                    HttpSession session = request.getSession();
                    session.setAttribute("success", "Registration successful!");
                    response.sendRedirect(request.getContextPath() + "/login");
                } else {
                    throw new SQLException("Failed to create user");
                }
            } finally {
                // Close resources
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Registration failed: " + e.getMessage());
            request.setAttribute("preservedValues", Map.of(
                "user_firstname", firstName,
                "user_lastname", lastName,
                "user_email", email,
                "user_name", username,
                "user_dob", dob,
                "user_phnno", phone
            ));  
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);

        }
        
	     
	}

	}
