package com.Advancedjava.filter;

import java.io.IOException;
import com.Advancedjava.util.Cookiesutil;
import com.Advancedjava.util.Sessionutil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebFilter(asyncSupported = true, urlPatterns = "/*")
public class AuthenticationFilter implements Filter {
    // Public pages (accessible without login)
    private static final String LOGIN = "/login";
    private static final String REGISTER = "/register";
    private static final String INDEX = "/index";
    private static final String ROOT = "/";
    private static final String LOGOUT = "/logout";
    
    // User pages (accessible to logged-in users with 'customer' role)
    private static final String HOME = "/home";
    private static final String RESULT = "/Result";
    private static final String PROFILE = "/Profile";
    private static final String PROPERTY = "/Property";
    private static final String PROPERTY_DESCRIPTION = "/propertydescription";
    
    private static final String BOOKING  = "/BookingController";
    
    // Admin pages (accessible only to admin)
    private static final String DASHBOARD = "/admindashboard";
    
    private static final String PROFILE_IMAGE = "/Profile_pictureservlet";
   
    // Error pages
    private static final String ACCESS_DENIED = "/accessDenied";
    
    // Static resources
    private static final String[] PUBLIC_RESOURCES = {
        ".css", ".js", ".png", ".jpg", ".jpeg", ".gif", ".ico", 
        ".woff", ".woff2", ".ttf", ".svg"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        
        // Remove context path from URI if present
        if (contextPath != null && !contextPath.isEmpty() && uri.startsWith(contextPath)) {
            uri = uri.substring(contextPath.length());
        }

        // Allow static resources to pass through
        if (isPublicResource(uri)) {
            chain.doFilter(request, response);
            return;
        }

        // Get authentication info from SESSION (more secure than cookies)
        String username = (String) Sessionutil.getAttribute(req, "username");
        String userRole = (String) Sessionutil.getAttribute(req, "userrole");

        // Not logged in - only allow public pages
        if (username == null) {
            if (isPublicPage(uri)) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(contextPath + LOGIN);
            }
            return;
        }
        
        
       

        // Admin access control
        if ("admin".equals(userRole)) {
        	
        	 if(uri.equals(LOGIN) || uri.equals(REGISTER) || 
                     uri.equals(INDEX) || uri.equals(ROOT)) {
        		 res.sendRedirect(contextPath + DASHBOARD);
        		 return;
             }
            if (isAdminAllowedPage(uri)) {
                chain.doFilter(request, response);
            } else if(isCustomerAllowedPage(uri)){
                // Admin trying to access customer page - redirect to dashboard with error
                Sessionutil.setAttribute(req, "error", "Admins cannot access customer pages");
                res.sendRedirect(contextPath + DASHBOARD);
            }
            else {
                res.sendRedirect(contextPath + ACCESS_DENIED);
                return;
        } }
        // Customer access control
        else if ("customer".equals(userRole)) {
        	 if(uri.equals(LOGIN) || uri.equals(REGISTER) || 
                     uri.equals(INDEX) || uri.equals(ROOT)) {
        		 res.sendRedirect(contextPath + HOME);
        		 return;
             }
            if (isCustomerAllowedPage(uri)) {
                chain.doFilter(request, response);
            } else if (isAdminAllowedPage(uri)) {
                // Customer trying to access admin page - redirect to home with error
                Sessionutil.setAttribute(req, "error", "Customers cannot access admin pages");
                res.sendRedirect(contextPath + HOME);
            } 
            else {
                res.sendRedirect(contextPath + ACCESS_DENIED);
                return;
            }
        }
        // Invalid role (shouldn't happen if your login works correctly)
        else {
            Sessionutil.setAttribute(req, "error", "Invalid user role");
            res.sendRedirect(contextPath + LOGIN);
            return;
        }
    }

    private boolean isPublicResource(String uri) {
        if (uri == null) return false;
        for (String extension : PUBLIC_RESOURCES) {
            if (uri.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPublicPage(String uri) {
        return uri.equals(LOGIN) || 
               uri.equals(REGISTER) || 
               uri.equals(INDEX) || 
               uri.equals(ROOT)||
             
               uri.equals(LOGOUT);
    }

    private boolean isCustomerAllowedPage(String uri) {
        return uri.equals(HOME) ||
               uri.equals(RESULT) ||
               uri.equals(PROFILE) ||
               uri.equals(PROPERTY) ||
               uri.equals(BOOKING)||
               uri.equals(PROPERTY_DESCRIPTION)||
               uri.equals(PROFILE_IMAGE)||
               uri.equals(LOGOUT);
    }

    private boolean isAdminAllowedPage(String uri) {
        return uri.equals(DASHBOARD)||
        		  uri.equals(PROFILE_IMAGE)||
        		uri.equals(LOGOUT);
        // Add other admin-specific pages here if needed
    }
}