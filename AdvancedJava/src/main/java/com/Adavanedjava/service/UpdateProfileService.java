package com.Adavanedjava.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import com.Advancedjava.dao.UserDao;
import com.Advancedjava.dao.UserDaoimpl;
import com.Advancedjava.model.usermodel;
import com.Advancedjava.model.usermodel.gender;
import com.Advancedjava.util.ImageUtil;
import com.Advancedjava.util.PasswordHasher;
import com.Advancedjava.util.Sessionutil;
import com.Advancedjava.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

public class UpdateProfileService {

    public Boolean UpdateProfile(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<String> errors = new ArrayList<>();

            // Get parameters
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String username = request.getParameter("username");
            String dob = request.getParameter("dob");
            String selectedGender = request.getParameter("gender");
            String phone = request.getParameter("phone");

            usermodel currentUser = (usermodel) request.getAttribute("Current_user");
            currentUser.display();
            UserDao userDao = new UserDaoimpl();
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
                
                boolean result = userDao.updateUser(updatedUser);
                if (result) {
                	   Sessionutil.setAttribute(request, "username",updatedUser.getUserName());
                return true;
                }
                else errors.add("User update failed in the database.");
            }
            // Forward errors back to profile page
            request.setAttribute("error", String.join(" ", errors));
            request.setAttribute("Current_user", currentUser);
            request.getRequestDispatcher("/WEB-INF/pages/Profile.jsp").forward(request, response);
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
                request.getRequestDispatcher("/WEB-INF/pages/Profile.jsp").forward(request, response);
            } catch (ServletException | IOException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }
    public Boolean ChangePassword(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<String> errorPassword = new ArrayList<>();

            // 1. Retrieve current user and form fields
        String username = (String)Sessionutil.getAttribute(request,"username");
        UserDao userDao = new UserDaoimpl();
        usermodel currentUser = userDao.findByUsernameOrEmail(username);
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
                
                    return true;
                } else {
                	errorPassword.add("Failed to update password in the database.");
                }
            }

            // 6. On error, forward back to change-password section
            request.setAttribute("errorPassword", String.join(" ", errorPassword));
            request.setAttribute("openSection", "change-password");
            request.setAttribute("Current_user", currentUser);
            request.getRequestDispatcher("/WEB-INF/pages/Profile.jsp").forward(request, response);
            return false;

        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                request.setAttribute("errorPassword", "Unexpected error: " + ex.getMessage());
                request.setAttribute("openSection", "change-password");
                request.getRequestDispatcher("/WEB-INF/pages/Profile.jsp").forward(request, response);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

}
