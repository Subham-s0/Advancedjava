package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.Advancedjava.dao.AmenityDaoImpl;
import com.Advancedjava.dao.PropertyDaoImpl;
import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.AmenityModel;
import com.Advancedjava.model.Property_Amenity;

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
			String propertyIdStr = request.getParameter("propertyId");
			if (propertyIdStr != null && !propertyIdStr.trim().isEmpty() && !"null".equalsIgnoreCase(propertyIdStr.trim())) {
				int propertyId = Integer.parseInt(propertyIdStr);
				PropertyDaoImpl PropertyDao = new PropertyDaoImpl();
				   
				   Set<Integer> existingAmenityIdsSet = new HashSet<>(PropertyDao.findAmenityIdsByPropertyId(propertyId));
				   request.setAttribute("existingAmenityIdsSet", existingAmenityIdsSet);

				   	System.out.println("The existingAmenityIds are "+existingAmenityIdsSet);
				  
			}
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
		List<String> errors = new ArrayList<>();
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
			 try {
		            int propertyId = Integer.parseInt(propertyIdStr);
		            String[] amenityIds = request.getParameterValues("amenityIds");
		            
		            if (amenityIds == null || amenityIds.length == 0) {
		            	errors.add("At least one amenity must be selected");
		            }
		            if(!errors.isEmpty()) {
		            	try {
		            	
					    	AmenityDaoImpl AmenityDao =new AmenityDaoImpl();
							List<AmenityModel> amenities = new ArrayList<>();
							amenities = AmenityDao.findAll();
							request.setAttribute("error", String.join(" ", errors));
							request.setAttribute("amenities", amenities);
							request.getRequestDispatcher("WEB-INF/pages/admin/addamenities.jsp").forward(request, response);
						} catch (DataAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
		            }
		            
		            AmenityDaoImpl amenityDao = new AmenityDaoImpl();
		            
		            for (String amenityIdStr : amenityIds) {
		                int amenityId = Integer.parseInt(amenityIdStr);
		                Property_Amenity pa = new Property_Amenity(propertyId, amenityId);
		                amenityDao.savePropertyAmenity(pa);
		            }
		            
		            response.sendRedirect(request.getContextPath() + "/propertysucess");
		            
		        } catch (Exception e) {
		            e.printStackTrace();
		            request.setAttribute("error", "Invalid property ID format");
		            request.getRequestDispatcher("WEB-INF/pages/admin/addamenities.jsp").forward(request, response);
		        } 
		    }
		}
		
		 }

