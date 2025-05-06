package com.Advancedjava.util;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {
	
	

	    // Cost factor (4-31). Higher = more secure but slower
	    private static final int ROUNDS = 12;

	    public static String hash(String plainPassword) {
	      
	    	return BCrypt.hashpw(plainPassword, BCrypt.gensalt(ROUNDS));
	    }

	    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
	        return BCrypt.checkpw(plainPassword, hashedPassword);
	    }
}
