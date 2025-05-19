package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.Advancedjava.dao.UserDao;
import com.Advancedjava.dao.UserDaoimpl;
import com.Advancedjava.dao.WishlistImpl;
import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.usermodel;
import com.Advancedjava.util.Sessionutil;
import com.Advanedjava.service.UpdateProfileService;

/**
 * Servlet implementation class Profilecontroller
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/Profile" })
@MultipartConfig
public class Profilecontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profilecontroller() {
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
		request.getRequestDispatcher("/WEB-INF/pages/Profile.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		doPut(request, response);
	  
	}
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		  String formType = request.getParameter("formType");
		    String username = (String) Sessionutil.getAttribute(request, "username");
		    UserDao userDao = new UserDaoimpl();
		    usermodel Current_user = null;

		    // Always reload the current user into the request
		    try {
		        Current_user = userDao.findByUsernameOrEmail(username);
		        request.setAttribute("Current_user", Current_user);
		        
		    } catch (DataAccessException e) {
		        e.printStackTrace();
		        // You might want to forward to an error page here
		    }

		    if ("profileUpdate".equals(formType)) {
		        UpdateProfileService svc = new UpdateProfileService();
		        Boolean ok = svc.UpdateProfile(request, response);
		        if (ok) {
		            request.setAttribute("success", "Profile updated successfully.");
		            try {
		                String newusername = (String) Sessionutil.getAttribute(request, "username");
		                Current_user = userDao.findByUsernameOrEmail(newusername);
		                
		                request.setAttribute("Current_user", Current_user);
		            } catch (DataAccessException e) {
		                e.printStackTrace();
		                request.setAttribute("error", "Error retrieving updated user data: " + e.getMessage());
		            }

		            // Forward to profile.jsp
		            request.getRequestDispatcher("/WEB-INF/pages/Profile.jsp").forward(request, response);
		        } else {
		            // Handle error case
		            request.setAttribute("error", "Failed to update profile.");
		            
		        }
		        
		    }
		    else if ("ChangePassword".equals(formType)) {
		        // call your new ChangePassword method
		        UpdateProfileService svc = new UpdateProfileService();
		        Boolean ok = svc.ChangePassword(request, response);
		        // svc.ChangePassword already forwards back on error, so only handle success here:
		        if (ok) {request.setAttribute("successPassword", "Password updated successfully.");
		        	 
		            // on success, want to show change-password tab with success message
		            request.setAttribute("openSection", "change-password");
		            request.getRequestDispatcher("/WEB-INF/pages/Profile.jsp").forward(request, response);
		        }
		        // if ok==false, svc.ChangePassword has already forwarded with errorPassword + openSection

		    } else {
		        // unknown formType; just reload
		        doGet(request, response);
		    }
		
	}			 
}
	