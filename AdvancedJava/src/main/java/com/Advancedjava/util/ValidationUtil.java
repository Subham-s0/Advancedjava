package com.Advancedjava.util;

import jakarta.servlet.http.Part;

public class ValidationUtil {
	public static boolean isNullOrEmpty(String text) {
	    boolean isEmptyOrNull = (text == null || text.trim().isEmpty());
	    return isEmptyOrNull;
	}
	public static boolean isAlphabetic(String text) {
		boolean isalpha= (text!=null && text.matches("^[a-zA-Z]+$"));
		return isalpha;
	}
	public static boolean isAlphanumericStartingWithLetter(String value) {
		boolean isAlphanumeric = (value != null && value.matches("^[a-zA-Z][a-zA-Z0-9]*$")) ;
		return isAlphanumeric; 
		}   
	public static boolean isAlphabeticupto20char(String text) {
		boolean isAlphabeticupto20 = (text.matches("^[a-zA-Z0-9][a-zA-Z0-9._-]{2,10}$"));
		return isAlphabeticupto20; 
		}   
	public static boolean isValidEmail(String email) {
       boolean ismail =email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") ;
        return ismail;
    }
	public static boolean isValidPhoneNumber(String number) {
		boolean  isnumber =(number != null && number.matches("^(98|97)\\d{8}$")) ;
		return isnumber; 
    }
	public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password != null && password.matches(passwordRegex);
    }
	public static boolean isValidImageExtension(Part imagePart) {
        if (imagePart == null || isNullOrEmpty(imagePart.getSubmittedFileName())) {
            return false;
        }
        String fileName = imagePart.getSubmittedFileName().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif");
    }

	    }
