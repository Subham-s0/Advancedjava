package com.Advancedjava.util;

public class ValidationUtil {
	public static boolean isNullOrEmpty(String text) {
	    boolean isEmptyOrNull = (text == null || text.trim().isEmpty());
	    return isEmptyOrNull;
	}
	public static boolean isAlphabetic(String text) {
		boolean isalpha= (text!=null && text.matches("^[a-zA-Z]+$"));
		return isalpha;
	}
	}
	