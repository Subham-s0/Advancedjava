package com.Advancedjava.util;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Cookiesutil {

	public static void setcookies(HttpServletResponse response,String name,String value,int maxage){
		Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxage);// sets the duration of the cookie in seconds
        cookie.setPath("/"); // Makes the cookie available to the entire application
        response.addCookie(cookie);
	}

public static void deletecookie(HttpServletResponse response, String name) {
    Cookie cookie = new Cookie(name, null);
    cookie.setMaxAge(0);
    cookie.setPath("/"); // Make cookie available to the entire application
    response.addCookie(cookie);
}
public static Cookie getCookie(HttpServletRequest request, String name) {
    	Cookie[] cookies = request.getCookies();
    	if (cookies != null) {
    	    for (Cookie c : cookies) {
    	        if (name.equals(c.getName())) {
    	            return c;
    	        }
    	    }
    	}
    	return null;

	}
}