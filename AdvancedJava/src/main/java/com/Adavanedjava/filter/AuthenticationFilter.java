package com.Adavanedjava.filter;

import java.io.IOException;

import com.Advancedjava.util.Cookiesutil;


import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter implements Filter{
	private static final String LOGIN = "/login";
	private static final String REGISTER = "/register";
	private static final String HOME = "/home";
	private static final String SEARCH = "/Search";
	private static final String PROFILE = "/PROFILE";
	private static final String DASHBOARD = "/admindashboard";
	private static final String INDEX = "/index";
	private static final String ROOT ="/";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String uri = req.getRequestURI();
		if (uri.endsWith(".png") || uri.endsWith(".jpg") || uri.endsWith(".css")) {
			chain.doFilter(request, response);
			return;
		}
		String userRole = Cookiesutil.getCookie(req, "role") != null ? Cookiesutil.getCookie(req, "role").getValue(): null;
		
	

	}

}