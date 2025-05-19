package com.Advanedjava.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import com.Advancedjava.dao.UserDao;
import com.Advancedjava.dao.UserDaoimpl;
import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.usermodel;
import com.Advancedjava.util.PasswordHasher;
import com.Advancedjava.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegisterService {
	
	 public boolean registerUser(HttpServletRequest request, HttpServletResponse response) 
	            throws IOException, ServletException {
		 
		 
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
		    
		    try {
		        UserDao userDao = new UserDaoimpl();

		        if (userDao.existsByEmail(email)) {
		            errors.add("This email is already registered.");
		        }

		        if (userDao.existsByUsername(username)) {
		            errors.add("This username is already taken.");
		        }

		        if (userDao.existsByPhone(phone)) {
		            errors.add("This phone number is already registered.");
		        }

		    } catch (DataAccessException e) {
		        e.printStackTrace();
		        errors.add("An internal error occurred while checking existing records.");
		    }
		    if (!errors.isEmpty()==true) {
	            request.setAttribute("error", String.join(" ", errors));
	            request.setAttribute("user_firstname", firstName);
	            request.setAttribute("user_lastname", lastName);
	            request.setAttribute("user_name", username);
	            request.setAttribute("user_dob", dob);
	            request.setAttribute("user_email", email);
	            request.setAttribute("user_phnno", phone);
	            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
	            return false;
	        }
		    usermodel user = new usermodel(firstName, 
	                lastName,
	                email, 
	                username,
	                dob,
	                phone, 
	                PasswordHasher.hash(password)
	            );
		    

		    try {
		        UserDao userDao = new UserDaoimpl();
		        int rowsAffected = userDao.saveUser(user);

		        return rowsAffected > 0;

		    } catch (Exception e) {
		        e.printStackTrace();
		        request.setAttribute("error", "Internal server error during registration.");
		        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
		        return false;
		    }
	    }
}
