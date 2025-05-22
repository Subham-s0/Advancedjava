package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import com.Advancedjava.dao.UserDao;
import com.Advancedjava.dao.UserDaoimpl;
import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.usermodel;
import com.Advancedjava.model.usermodel.gender;
import com.Advancedjava.util.ImageUtil;
import com.Advancedjava.util.PasswordHasher;
import com.Advancedjava.util.Sessionutil;
import com.Advancedjava.util.ValidationUtil;
import com.Advanedjava.service.UpdateProfileService;

/**
 * Servlet implementation class adminprofile
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/adminprofile" })
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024,  // 1 MB
	    maxFileSize = 5 * 1024 * 1024,    // 5 MB
	    maxRequestSize = 10 * 1024 * 1024 // 10 MB
	)

public class adminprofile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public adminprofile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try { 
			
			 String username = (String) Sessionutil.getAttribute(request, "username");
			 UserDao userDao = new UserDaoimpl();
	         usermodel Current_user = userDao.findByUsernameOrEmail(username);
	         request.setAttribute("Current_user", Current_user);
	         
			 } catch (DataAccessException e) {
				 e.printStackTrace();
		        }
		request.getRequestDispatcher("/WEB-INF/pages/admin/adminprofile.jsp").forward(request, response);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String formType = request.getParameter("formType");
	    String Savedusername = (String) Sessionutil.getAttribute(request, "username");
	    UserDao userDao = new UserDaoimpl();
	    usermodel currentUser = null;

	    try {
	        currentUser = userDao.findByUsernameOrEmail(Savedusername);
	        request.setAttribute("Current_user", currentUser);
	    } catch (DataAccessException e) {
	        e.printStackTrace();
	        request.setAttribute("error", "Could not retrieve user details.");
	        request.getRequestDispatcher("/WEB-INF/pages/admin/adminprofile.jsp").forward(request, response);
	        return;
	    }

	    if ("profileUpdate".equals(formType)) {
	        try {  List<String> errors = new ArrayList<>();

            // Get parameters
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String username = request.getParameter("username");
            String dob = request.getParameter("dob");
            String selectedGender = request.getParameter("gender");
            String phone = request.getParameter("phone");

           
          
            gender genderEnum = null;

            // Validate gender
            if (!ValidationUtil.isNullOrEmpty(selectedGender)) {
                try {
                    genderEnum = gender.valueOf(selectedGender.toLowerCase());
                } catch (IllegalArgumentException e) {
                    errors.add("Invalid gender selected.");
                }
            }

            // Validate first name
            if (ValidationUtil.isNullOrEmpty(firstName)) {
                errors.add("First name is required.");
            } else if (!ValidationUtil.isAlphabeticupto20char(firstName)) {
                errors.add("First name can only contain letters, apostrophes, hyphens, and spaces (2-20 characters).");
            }

            // Validate last name
            if (ValidationUtil.isNullOrEmpty(lastName)) {
                errors.add("Last name is required.");
            } else if (!ValidationUtil.isAlphabeticupto20char(lastName)) {
                errors.add("Last name can only contain letters, apostrophes, hyphens, and spaces (2-20 characters).");
            }

            // Validate email
            if (ValidationUtil.isNullOrEmpty(email)) {
                errors.add("Email is required.");
            } else if (!ValidationUtil.isValidEmail(email)) {
                errors.add("Invalid email format.");
            } else if (!email.equalsIgnoreCase(currentUser.getUserEmail()) &&
                    userDao.existsByEmail(email)) {
                errors.add("Email already registered.");
            }

            // Validate username
            if (ValidationUtil.isNullOrEmpty(username)) {
                errors.add("Username is required.");
            } else if (!ValidationUtil.isAlphanumericStartingWithLetter(username)) {
                errors.add("Username must be 5-20 characters and can only contain letters, numbers, . _ -");
            } else if (!username.equalsIgnoreCase(currentUser.getUserName()) &&
                    userDao.existsByUsername(username)) {
                errors.add("Username already taken.");
            }

            // Validate DOB
            if (ValidationUtil.isNullOrEmpty(dob)) {
                errors.add("Date of birth is required.");
            } else {
                try {
                    LocalDate dobParsed = LocalDate.parse(dob);
                    LocalDate minDate = LocalDate.now().minusYears(120);
                    LocalDate maxDate = LocalDate.now().minusYears(16);
                    if (dobParsed.isBefore(minDate) || dobParsed.isAfter(maxDate)) {
                        errors.add("You must be between 16-120 years old.");
                    }
                } catch (DateTimeParseException e) {
                    errors.add("Invalid date format (YYYY-MM-DD required).");
                }
            }

            // Validate phone
            if (ValidationUtil.isNullOrEmpty(phone)) {
                errors.add("Phone number is required.");
            } else if (!ValidationUtil.isValidPhoneNumber(phone)) {
                errors.add("Phone number must be 10 digits.");
            } else if (!phone.equals(currentUser.getUserPhnNo()) &&
                    userDao.existsByPhone(phone)) {
                errors.add("Phone number already registered.");
            }

            Part file = request.getPart("image_file");
            String fileName = file.getSubmittedFileName();
            String customFilename;
            boolean imageUploaded = true;

            if (!ValidationUtil.isNullOrEmpty(fileName)) {
                try {
                    customFilename = username + fileName;
                    ImageUtil imageUtil = new ImageUtil();
                    imageUtil.validateImage(file);
                    imageUploaded = imageUtil.uploadImage(file, "", "profile", customFilename);

                    if (!imageUploaded) {
                        errors.add("Image upload failed.");
                    }
                } catch (ServletException e) {
                    errors.add("Image processing error: " + e.getMessage());
                    customFilename = currentUser.getUserProfilePicture(); // fallback if upload fails
                }
            } else {
                // No file uploaded; retain the old profile picture
                customFilename = currentUser.getUserProfilePicture();
            }
            // Update user if valid
            if (errors.isEmpty() && imageUploaded) {
                usermodel updatedUser = new usermodel(
                		currentUser.getUserId(),
                        firstName,
                        lastName,
                        email,
                        username,
                        dob,
                        phone,
                        genderEnum,
                        currentUser.getUserPassword(),
                        currentUser.getuserRole(),
                        customFilename,
                        currentUser.getUserStatus()
                );

	                if (userDao.updateUser(updatedUser)) {
	                    Sessionutil.setAttribute(request, "username", updatedUser.getUserName());
	                    request.setAttribute("success", "Profile updated successfully.");
	                    currentUser = updatedUser;
	                } else {
	                    errors.add("User update failed in the database.");
	                }
	            }

	            request.setAttribute("error", String.join(" ", errors));
	            request.setAttribute("Current_user", currentUser);
	            request.getRequestDispatcher("/WEB-INF/pages/admin/adminprofile.jsp").forward(request, response);

	        } catch (Exception e) {
	            e.printStackTrace();
	            request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
	            request.setAttribute("Current_user", currentUser);
	            request.getRequestDispatcher("/WEB-INF/pages/admin/adminprofile.jsp").forward(request, response);
	        }
	    }

	    else if ("ChangePassword".equals(formType)) {
	        try {
	        	  List<String> errorPassword = new ArrayList<>();

	             
	        
	       
	              currentUser.display();
	              String currentPassword = request.getParameter("currentPassword");
	              String newPassword     = request.getParameter("newPassword");
	              String confirmPassword = request.getParameter("confirmPassword");

	              // 2. Validate current password
	              if (ValidationUtil.isNullOrEmpty(currentPassword)) {
	              	errorPassword.add("Current password is required.");
	              } else if (!PasswordHasher.verifyPassword(currentPassword, currentUser.getUserPassword())) {
	              	errorPassword.add("Current password is incorrect.");
	              }

	              // 3. Validate new password
	              if (ValidationUtil.isNullOrEmpty(newPassword)) {
	              	errorPassword.add("New password is required.");
	              } else if (!ValidationUtil.isValidPassword(newPassword)) {
	              	errorPassword.add("New password format mismatch.");
	              }

	              // 4. Confirm new passwords match
	              if (!newPassword.equals(confirmPassword)) {
	              	errorPassword.add("New passwords do not match.");
	              }

	              // 5. If no errors, hash & update
	              if (errorPassword.isEmpty()) {
	                  String hashed = PasswordHasher.hash(newPassword);

	                  usermodel updatedUser = new usermodel(
	                  	currentUser.getUserId(),
	                      currentUser.getUserFirstName(),
	                      currentUser.getUserLastName(),
	                      currentUser.getUserEmail(),
	                      currentUser.getUserName(),
	                      currentUser.getUserDob(),
	                      currentUser.getUserPhnNo(),
	                      currentUser.getGender(),
	                      hashed,
	                      currentUser.getuserRole(),
	                      currentUser.getUserProfilePicture(),
	                      currentUser.getUserStatus()
	                  );

	                if (userDao.updateUser(updatedUser)) {
	                    request.setAttribute("success", "Password updated successfully.");
	                    currentUser = updatedUser;
	                } else {
	                    errorPassword.add("Failed to update password in the database.");
	                }
	            }

	            request.setAttribute("error", String.join(" ", errorPassword));
	         
	            request.setAttribute("Current_user", currentUser);
	            request.getRequestDispatcher("/WEB-INF/pages/admin/adminprofile.jsp").forward(request, response);

	        } catch (Exception e) {
	            e.printStackTrace();
	            request.setAttribute("error", "Unexpected error: " + e.getMessage());
	          
	            request.setAttribute("Current_user", currentUser);
	            request.getRequestDispatcher("/WEB-INF/pages/admin/adminprofile.jsp").forward(request, response);
	        }
	    }
	}

}