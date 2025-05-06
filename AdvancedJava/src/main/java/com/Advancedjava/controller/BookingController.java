package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.Advancedjava.dao.PropertyDaoImpl;
import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.Propertymodel;

/**
 * Servlet implementation class BookingController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/BookingController" })
public class BookingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 String propertyIdStr = request.getParameter("propertyId");

		    if (propertyIdStr != null) {
		        int propertyId = Integer.parseInt(propertyIdStr);

		        // Use DAO to fetch full property details by ID
		        PropertyDaoImpl propertyDao = new PropertyDaoImpl();
		        Propertymodel property;
				try {
					property = propertyDao.findById(propertyId);
					request.setAttribute("selectedProperty", property);
					System.out.println(property);  
					 request.getRequestDispatcher("/WEB-INF/pages/propertydescription.jsp").forward(request, response);
					 
				} catch (DataAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		   
		        // Forward to the booking details page
		       
		    } else {
		        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing property ID.");
		    }
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	   
	}

}
