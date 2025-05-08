package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.Advancedjava.dao.PropertyDaoImpl;
import com.Advancedjava.dao.UserDaoimpl;
import com.Advancedjava.dao.WishlistImpl;
import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.Propertymodel;
import com.Advancedjava.model.usermodel;
import com.Advancedjava.util.Sessionutil;

/**
 * Servlet implementation class Propertycontroller
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/Property" })
public class Propertycontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Propertycontroller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   PropertyDaoImpl propertyDao = new PropertyDaoImpl();
		   List<Propertymodel> properties=null;
		   
		try {
			properties = propertyDao.findallproperties();
			String userId = (String) Sessionutil.getAttribute(request, "userId");
			
            WishlistImpl wishlistDao =new WishlistImpl();
            List<Integer> wishlist = wishlistDao.getWishlistByUserId(userId);
            request.setAttribute("wishlistIds", wishlist);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
           request.setAttribute("properties", properties);
		request.getRequestDispatcher("/WEB-INF/pages/Property.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
