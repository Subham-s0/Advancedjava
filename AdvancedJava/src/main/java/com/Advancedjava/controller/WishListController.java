package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.Advancedjava.dao.PropertyDaoImpl;
import com.Advancedjava.dao.UserDao;
import com.Advancedjava.dao.UserDaoimpl;
import com.Advancedjava.dao.WishlistImpl;
import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.Propertymodel;
import com.Advancedjava.model.usermodel;
import com.Advancedjava.util.Sessionutil;

/**
 * Servlet implementation class WishListController
 */
@WebServlet("/WishListController")
public class WishListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WishListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 WishlistImpl wishlistdao = new WishlistImpl();
		    PropertyDaoImpl propertydao = new PropertyDaoImpl();
		 // Get the list of wishlist IDs from the WishlistDaoImpl
        List<Integer> wishlistIds;
		try {
			
			wishlistIds = wishlistdao.getWishlistByUserId((String)Sessionutil.getAttribute(request, "userId"));
			List<Propertymodel> properties = new ArrayList<>();
	        for (Integer wishlistId : wishlistIds) {
	        	Propertymodel property= propertydao.findById(wishlistId);
	            properties.add(property);
	        }
	        String username = (String) Sessionutil.getAttribute(request, "username");
			 UserDao userDao = new UserDaoimpl();
	         usermodel Current_user = userDao.findByUsernameOrEmail(username);
	         request.setAttribute("Current_user", Current_user);

	        // Set attributes to be accessed in the JSP
	        request.setAttribute("wishlistIds", wishlistIds);
	        request.setAttribute("Current_user", Current_user);
	        request.setAttribute("properties", properties);
	        request.setAttribute("openSection", "wishlist");
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        request.getRequestDispatcher("WEB-INF/pages/Profile.jsp").forward(request, response);   
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
		    String referer = request.getHeader("referer");
            UserDaoimpl userDao = new UserDaoimpl();
            String username = (String) Sessionutil.getAttribute(request, "username");
		
	         usermodel Current_user = userDao.findByUsernameOrEmail(username);
 
            WishlistImpl wishlistDao =new WishlistImpl();
            // Property ID from the hidden field in the form
            String propertyIdStr = request.getParameter("propertyId");
            int productId = Integer.parseInt(propertyIdStr);
            		
            // Toggle logic: if it's in wishlist, remove it; otherwise, add it
            if (wishlistDao.isInWishlist(Current_user.getUserId(), productId)) {
                wishlistDao.removeFromWishlist(Current_user.getUserId(), productId);
                System.out.print("removed");
            } else {
                wishlistDao.addToWishlist(Current_user.getUserId(), productId);
                System.out.print("added");
            }
                 
            
            // Redirect back to the same page or a specific one
            response.sendRedirect(referer != null ? referer : request.getContextPath());
        } catch (DataAccessException | NumberFormatException | NullPointerException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Something went wrong with your wishlist action.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
	}

}
