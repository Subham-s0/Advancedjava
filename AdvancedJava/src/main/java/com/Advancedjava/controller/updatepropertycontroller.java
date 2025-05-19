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
import com.Advancedjava.dao.CategoryDaoImpl;
import com.Advancedjava.dao.PropertyDaoImpl;
import com.Advancedjava.dao.PropertyImageDaoImpl;
import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.AmenityModel;
import com.Advancedjava.model.Categorymodel;
import com.Advancedjava.model.PropertyImagemodel;
import com.Advancedjava.model.Propertymodel;
import com.Advancedjava.util.Sessionutil;
import com.mysql.cj.Session;

/**
 * Servlet implementation class updatepropertycontroller
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/updatepropertycontroller" })
public class updatepropertycontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updatepropertycontroller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
          try { 
      		String propertyIdStr = request.getParameter("propertyId");

    		CategoryDaoImpl categoryDao = new CategoryDaoImpl();
    		PropertyDaoImpl propertyDao = new PropertyDaoImpl();  
    		AmenityDaoImpl amenityDao =new AmenityDaoImpl();
    		List<Categorymodel> categories = categoryDao.findAllcategories();
              request.setAttribute("categories", categories);
      		  if (propertyIdStr != null) {
      			  int propertyId = Integer.parseInt(propertyIdStr);
      			Propertymodel property = propertyDao. findById(propertyId);
      				request.setAttribute("property", property);
      				List<Integer> amenityIds= propertyDao. findAmenityIdsByPropertyId(propertyId);
      				List<AmenityModel> property_amenities = new ArrayList<>();

      				for (Integer amenityId : amenityIds) {
      				    AmenityModel amenity = amenityDao.findById(amenityId); // assuming amenityDao is available
      				    if (amenity != null) {
      				    	property_amenities.add(amenity);
      				    }
      				}
      				request.setAttribute("propertyAmenities", property_amenities);
      			request.getRequestDispatcher("WEB-INF/pages/admin/editpropertydashboard.jsp").forward(request, response);
      			} 
      		  
      		
      	    }catch (Exception e) {
      				
      				e.printStackTrace();
      				Sessionutil.setAttribute(request,"error", "An error occurred. Please try again."+e.getMessage());
      		response.sendRedirect(request.getContextPath()+"/propertydashboard");
      			}
      			  
         
       	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPut(request, response);
	}
	
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("the form was submitted");
		try { 
      		String propertyIdStr = request.getParameter("propertyId");
      		int propertyId = Integer.parseInt(propertyIdStr);
      		
    		response.sendRedirect(request.getContextPath()+"/updatepropertycontroller?propertyId="+propertyId);
      		}
		catch (Exception e) {
			e.printStackTrace();
				Sessionutil.setAttribute(request,"error", "An error occurred. Please try again."+e.getMessage());
		response.sendRedirect(request.getContextPath()+"/propertydashboard");
			}
	}

}
