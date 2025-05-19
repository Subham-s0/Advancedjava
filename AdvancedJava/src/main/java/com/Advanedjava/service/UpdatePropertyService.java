package com.Advanedjava.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.Advancedjava.dao.AmenityDaoImpl;
import com.Advancedjava.dao.CategoryDao;
import com.Advancedjava.dao.CategoryDaoImpl;
import com.Advancedjava.dao.PropertyDaoImpl;
import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.AmenityModel;
import com.Advancedjava.model.Categorymodel;
import com.Advancedjava.model.Propertymodel;
import com.Advancedjava.model.Propertymodel.PropertyStatus;
import com.Advancedjava.util.Sessionutil;
import com.Advancedjava.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UpdatePropertyService {
	 public Boolean Updatedetails(HttpServletRequest request, HttpServletResponse response,int PropertyId) throws ServletException, IOException {
		 
          
		 PropertyDaoImpl propertyDao = new PropertyDaoImpl();
	        CategoryDao categoryDao = new CategoryDaoImpl();
	        List<String> errors = new ArrayList<>();
	        
	        String name = request.getParameter("name");
	        String address = request.getParameter("address");
	        String city = request.getParameter("city");
	        String country = request.getParameter("country");
	        String hostName = request.getParameter("hostName");
	        String categoryIdStr = request.getParameter("category");
	        String pricePerNightStr = request.getParameter("pricePerNight");
	        String serviceChargeStr = request.getParameter("ServiceCharge");
	        String cleaningFeeStr = request.getParameter("cleaningFee");
	        String taxRateStr = request.getParameter("taxRate");
	        String description = request.getParameter("description");
	        String maxGuestsStr = request.getParameter("maximumGuests");
	        String bedroomsStr = request.getParameter("totalBedrooms");
	        String bathStr = request.getParameter("totalBath");
	        String roomsStr = request.getParameter("totalRooms");
	        if (ValidationUtil.isNullOrEmpty(name)) { 
	        	errors.add("Property Name is required");
	        	}
	        if (ValidationUtil.isNullOrEmpty(address)) {
	        	errors.add("Address is required");
	        	}
	        if (ValidationUtil.isNullOrEmpty(city)) {
	        	errors.add("City is required");
	        	}
	        if (ValidationUtil.isNullOrEmpty(country)) {
	        	errors.add("Country is required");
	        	}
	        if (ValidationUtil.isNullOrEmpty(hostName)) {
	        	errors.add("Host Name is required");
	        	}

	        // Validate category
	        int categoryId = 0;
	        try {
	            categoryId = Integer.parseInt(categoryIdStr);
	            
	            if (categoryDao.findById(categoryId) == null) {
	                errors.add("Invalid category selected");
	            }
	        } catch (NumberFormatException e) {
	            errors.add("Invalid category format");
	        } catch (Exception e) {
	            errors.add("Error validating category: " + e.getMessage());
	        }
	      
	        // Validate price
	        BigDecimal pricePerNight = null;
	        if (ValidationUtil.isNullOrEmpty(pricePerNightStr)) {
	            errors.add("Price is required");
	        } else {
	            try {
	                // Parse and validate decimal format
	                pricePerNight = new BigDecimal(pricePerNightStr);
	                
	                // Validate scale (max 2 decimal places)
	                if (pricePerNight.scale() > 2) {
	                    errors.add("Price must have up to 2 decimal places");
	                }
	                
	                // Validate precision (max 10 digits total)
	                if (pricePerNight.precision() > 10) {
	                    errors.add("Price exceeds maximum value (99999999.99)");
	                }
	                
	                // Validate value range
	                if (pricePerNight.compareTo(BigDecimal.ZERO) <= 0) {
	                    errors.add("Price must be greater than 0");
	                }
	                
	                // Ensure proper scaling for database
	                pricePerNight = pricePerNight.setScale(2, RoundingMode.HALF_UP);
	                
	            } catch (NumberFormatException e) {
	                errors.add("Invalid price format - must be a decimal number");
	            }
	        }

	        // Validate service charge
	        int serviceCharge = 0;
	        try {
	        if (ValidationUtil.isNullOrEmpty(serviceChargeStr)) {
	        	serviceCharge=5 ;
	        	}
	        	serviceCharge = Integer.parseInt(serviceChargeStr);
	        	System.out.println("it was here"+serviceCharge);
	              if (serviceCharge<0) {
	                  errors.add("cant be negative selected");
	              }
	        
		 } catch (NumberFormatException e) {
	         errors.add("Invalid number format format");
	     } catch (Exception e) {
	         errors.add("Error validating service charge : " + e.getMessage());
	     }

	        // Validate cleaning fee
	        int cleaningFee = 0;
	        try {
	            if (ValidationUtil.isNullOrEmpty(cleaningFeeStr)) {
	            	cleaningFee=5 ;
	            	}
	            cleaningFee = Integer.parseInt(cleaningFeeStr);
	                  if (cleaningFee<0) {
	                      errors.add("cant be negative selected");
	                  }
	            
	    	 } catch (NumberFormatException e) {
	             errors.add("Invalid number  format");
	         } catch (Exception e) {
	             errors.add("Error validating service charge : " + e.getMessage());
	         }
	        // Validate tax rate
	        int taxRate = 0;
	        if (ValidationUtil.isNullOrEmpty(taxRateStr)) {
	            errors.add("Tax rate is required");
	        } else if (!ValidationUtil.isValidInt(taxRateStr)) {
	            errors.add("Tax rate must be a non-negative integer");
	        } else {
	            taxRate = Integer.parseInt(taxRateStr);
	            if (taxRate > 100) {
	                errors.add("Tax rate cannot exceed 100%");
	            }
	        }
	         // Validate Maximum Guests (required, min 1)
	        if (ValidationUtil.isNullOrEmpty(maxGuestsStr)) {
	            errors.add("Maximum guests is required");
	        } else if (!ValidationUtil.isValidInt(maxGuestsStr)) {
	            errors.add("Invalid maximum guests value");
	        } else {
	            int maxGuests = Integer.parseInt(maxGuestsStr);
	            if (maxGuests < 1) {
	                errors.add("Maximum guests must be at least 1");
	            }
	        }

	        // Validate Total Bedrooms (optional but must be >= 0)
	        if (!ValidationUtil.isNullOrEmpty(bedroomsStr) && !ValidationUtil.isValidInt(bedroomsStr)) {
	            errors.add("Bedrooms must be a whole number");
	        }

	        // Validate Total Bath (optional but must be >= 0)
	        if (!ValidationUtil.isNullOrEmpty(bathStr) && !ValidationUtil.isValidInt(bathStr)) {
	            errors.add("Bathrooms must be a whole number");
	        }

	        // Validate Total Rooms (optional but must be >= 0)
	        if (!ValidationUtil.isNullOrEmpty(roomsStr) && !ValidationUtil.isValidInt(roomsStr)) {
	            errors.add("Total rooms must be a whole number");
	        }
	        int maximumGuests = 0;
	        int totalBedrooms = 0;
	        int totalBath = 0;
	        int totalRooms = 0;
	        try {
	            // These can be safely parsed after validation
	            totalBedrooms = !ValidationUtil.isNullOrEmpty(bedroomsStr) ? 
	                           Integer.parseInt(bedroomsStr) : 0;
	            
	            totalBath = !ValidationUtil.isNullOrEmpty(bathStr) ? 
	                       Integer.parseInt(bathStr) : 0;
	            
	            totalRooms = !ValidationUtil.isNullOrEmpty(roomsStr) ? 
	                        Integer.parseInt(roomsStr) : 0;
	            
	            totalBath = !ValidationUtil.isNullOrEmpty(bathStr) ? 
	                    Integer.parseInt(bathStr) : 0;
	         
	            maximumGuests = !ValidationUtil.isNullOrEmpty(maxGuestsStr) ? 
	                     Integer.parseInt(maxGuestsStr) : 0;
	        } catch (NumberFormatException e) {
	            // This catch block becomes redundant due to prior validation
	            // But kept as safety net
	            errors.add("Unexpected error parsing numeric values");
	        }
	        // Check property name uniqueness
	        if (!ValidationUtil.isNullOrEmpty(name) &&
	        	    !ValidationUtil.isNullOrEmpty(address) &&
	        	    !ValidationUtil.isNullOrEmpty(city) &&
	        	    !ValidationUtil.isNullOrEmpty(country)) {
	        	    
	        	    try {
	        	        if (propertyDao.propertyExistsExcludingId(name, address, city, country,PropertyId)) {
	        	            errors.add("Property already exists");
	        	        }
	        	    } catch (DataAccessException e) {
	        	    	e.printStackTrace();
	        	        errors.add("Some error occured");
	        	    }
	        	}
	        if (!errors.isEmpty()) {
	            try {
	            	Propertymodel property = propertyDao. findById(PropertyId);
      				request.setAttribute("property", property);
      				List<Integer> amenityIds= propertyDao. findAmenityIdsByPropertyId(PropertyId);
      				List<AmenityModel> property_amenities = new ArrayList<>();
      				AmenityDaoImpl amenityDao = new AmenityDaoImpl();

      				for (Integer amenityId : amenityIds) {
      				    AmenityModel amenity = amenityDao.findById(amenityId); // assuming amenityDao is available
      				    if (amenity != null) {
      				    	property_amenities.add(amenity);
      				    }
      				}
      				request.setAttribute("propertyAmenities", property_amenities);
      				Sessionutil.setAttribute(request, "error", String.join(" ", errors));
      			    List<Categorymodel> categories = categoryDao.findAllcategories();
                    request.setAttribute("categories", categories);
	                request.setAttribute("propertyId",PropertyId);
	            	request.getRequestDispatcher("WEB-INF/pages/admin/editpropertydashboard.jsp").forward(request, response);;
	            } catch (Exception e) {
	            	request.setAttribute("error", "System error: " + e.getMessage());
	            	e.printStackTrace();
	            	            	 return false;
	            }
	            return false;
	        }
	        try {
	        	Propertymodel property = new Propertymodel(
	        			PropertyId,
	        	        name,                 
	        	        address,           
	        	        city,              
	        	        country,          
	        	        description,        
	        	        PropertyStatus.AVAILABLE,            
	        	        pricePerNight,              
	        	        maximumGuests,       
	                    totalBedrooms,   
	                    totalBath,      
	                    totalRooms,     
	        	        categoryId,         
	        	        hostName, 
	        	        cleaningFee,
	        	        taxRate,       
	        	        serviceCharge     
	        	    );
	        		
	        	boolean update = propertyDao.update(property);
	        	return update;	           
	        	}
	        catch(Exception e){
	        	
	        	request.setAttribute("error", "System error: " + e.getMessage());
            	e.printStackTrace();
            	 request.getRequestDispatcher("/WEB-INF/pages/admin/addproperty.jsp").forward(request, response);
            	 return false;
	        }

}
}
