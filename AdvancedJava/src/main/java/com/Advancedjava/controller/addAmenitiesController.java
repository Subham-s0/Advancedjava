package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.Advancedjava.dao.AmenityDaoImpl;
import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.AmenityModel;

/**
 * Servlet implementation class addamenitiescontroller
 */
@WebServlet("/addamenities")
public class addAmenitiesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addAmenitiesController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			AmenityDaoImpl AmenityDao =new AmenityDaoImpl();
			List<AmenityModel> amenities = new ArrayList<>();
			amenities = AmenityDao.findAll();
			request.setAttribute("amenities", amenities);
			request.getRequestDispatcher("/WEB-INF/pages/admin/addamenities.jsp").forward(request, response);
		
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String propertyIdStr = request.getParameter("propertyId");
		 if (propertyIdStr == null || propertyIdStr.trim().isEmpty() ||"null".equalsIgnoreCase(propertyIdStr)) {
		     		
		    	
				try {
					request.setAttribute("error", "The property id is missing");
			    	AmenityDaoImpl AmenityDao =new AmenityDaoImpl();
					List<AmenityModel> amenities = new ArrayList<>();
					amenities = AmenityDao.findAll();
					request.setAttribute("amenities", amenities);
					request.getRequestDispatcher("WEB-INF/pages/admin/addamenities.jsp").forward(request, response);
				} catch (DataAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		    }
		 else {
			 response.sendRedirect(request.getContextPath() + "/propertysucess");

			 
		 }

	}

}
