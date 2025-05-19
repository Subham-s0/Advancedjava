package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.Advancedjava.dao.CategoryDao;
import com.Advancedjava.dao.CategoryDaoImpl;
import com.Advancedjava.dao.PropertyDao;
import com.Advancedjava.dao.PropertyDaoImpl;
import com.Advancedjava.dao.UserDaoimpl;
import com.Advancedjava.dao.WishlistImpl;
import com.Advancedjava.model.Categorymodel;
import com.Advancedjava.model.Propertymodel;
import com.Advancedjava.model.usermodel;
import com.Advancedjava.util.Sessionutil;

/**
 * Servlet implementation class HomeController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/home" })
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private CategoryDao categoryDao;
	    private PropertyDao propertyDao;
	    @Override
	public void init() throws ServletException {
        super.init();
        categoryDao = new CategoryDaoImpl();
        propertyDao = new PropertyDaoImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 try {
			 
	            List<Categorymodel> categories = categoryDao.findAllcategories();
	            request.setAttribute("categories", categories);
	            
	            // Fetch all properties (for "All" category)
	            List<Propertymodel> properties = propertyDao.findallproperties();
	            request.setAttribute("properties", properties);
	 
	            
	            String userId = (String) Sessionutil.getAttribute(request, "userId");

	            
	            WishlistImpl wishlistDao =new WishlistImpl();
	            List<Integer> wishlist = wishlistDao.getWishlistByUserId(userId);
	            System.out.println("The wishlist ids are "+wishlist);
	            request.setAttribute("wishlistIds", wishlist);
	            
	            // Forward to JSP
	            request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
	            
	        } catch (Exception e) {
	            throw new ServletException("Error loading homepage data", e);
	        }
	    }
		 

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
