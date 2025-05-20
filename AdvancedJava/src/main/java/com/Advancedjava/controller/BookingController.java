package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.Advancedjava.dao.AmenityDaoImpl;
import com.Advancedjava.dao.BookingDao;
import com.Advancedjava.dao.CategoryDaoImpl;
import com.Advancedjava.dao.PropertyDaoImpl;
import com.Advancedjava.dao.UserDaoimpl;
import com.Advancedjava.dao.WishlistImpl;
import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.AmenityModel;
import com.Advancedjava.model.BookingModel;
import com.Advancedjava.model.BookingModel.BookingStatus;
import com.Advancedjava.model.Categorymodel;
import com.Advancedjava.model.Propertymodel;
import com.Advancedjava.model.usermodel;
import com.Advancedjava.util.Sessionutil;

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
		
				try {
					String propertyIdStr = request.getParameter("propertyId");

				    if (propertyIdStr != null && propertyIdStr.matches("\\d+")) {
				        int propertyId = Integer.parseInt(propertyIdStr);
				        CategoryDaoImpl categoryDao = new CategoryDaoImpl();
			            PropertyDaoImpl propertyDao = new PropertyDaoImpl();
			            AmenityDaoImpl amenityDao = new AmenityDaoImpl();
				        
				        Propertymodel property;
					
					property = propertyDao.findById(propertyId);
					Categorymodel category = categoryDao.findById(property.getCategoryId());
					 request.setAttribute("category", category);
					 List<Integer> amenityIds = propertyDao.findAmenityIdsByPropertyId(propertyId);
			            List<AmenityModel> property_amenities = new ArrayList<>();

			            for (Integer amenityId : amenityIds) {
			                AmenityModel amenity = amenityDao.findById(amenityId);
			                if (amenity != null) {
			                    property_amenities.add(amenity);
			                }
			            }
			            request.setAttribute("propertyAmenities", property_amenities);
					request.setAttribute("selectedProperty", property);
					WishlistImpl wishlistDao = new WishlistImpl();
					UserDaoimpl userDao = new UserDaoimpl();
		            String username = (String) Sessionutil.getAttribute(request, "username");
				
			         usermodel current_user = userDao.findByUsernameOrEmail(username);
		 
			        List<Integer> wishlistIds = wishlistDao.getWishlistByUserId(current_user.getUserId());
			        request.setAttribute("wishlistIds", wishlistIds);
					 request.getRequestDispatcher("/WEB-INF/pages/propertydescription.jsp").forward(request, response);
					 
				}
				    else {
				    	 Sessionutil.setAttribute(request, "error", "enter property id");
				            response.sendRedirect(request.getContextPath()+"/home");
				    }  
				    } catch (Exception e) {
					e.printStackTrace();
					response.sendRedirect(request.getContextPath()+"/error.jsp");
				}

		   
		       // Forward to the booking details page
		       
		    
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 int propertyId = Integer.parseInt(request.getParameter("propertyId"));
		 System.out.print("the prop id is " +propertyId);
		try {
	        List<String> errors = new ArrayList<>();
	        String username = (String) Sessionutil.getAttribute(request, "username");

	        UserDaoimpl userDao = new UserDaoimpl();
	        PropertyDaoImpl propertyDao = new PropertyDaoImpl();
	        BookingDao bookingdao = new BookingDao();

	        usermodel current_user = userDao.findByUsernameOrEmail(username);
	        int userId = Integer.parseInt(current_user.getUserId());

	        int numberOfGuests = Integer.parseInt(request.getParameter("totalGuests"));
	        Date checkInDate = Date.valueOf(request.getParameter("checkin"));
	        Date checkOutDate = Date.valueOf(request.getParameter("checkout"));
	        if (!checkInDate.before(checkOutDate)) {
	            errors.add("Check-in date must be before check-out date.");
	        }
	        Propertymodel property = propertyDao.findById(propertyId);
	        
	            boolean available = bookingdao.isPropertyAvailable(propertyId, checkInDate, checkOutDate);
	            if (!available) 
	                errors.add("The property is not available for the selected dates.");
	          

	            int maxGuests = property.getMaximumGuests();
	            if (numberOfGuests > maxGuests) {
	                errors.add("Number of guests exceeds the allowed limit of " + maxGuests + ".");
	            }
	      

	        long days = (checkOutDate.getTime() - checkInDate.getTime()) / (1000 * 60 * 60 * 24);
	        if (days <= 0) {
	            errors.add("Invalid stay duration.");
	        }
	        Date today = new Date(System.currentTimeMillis());
	        if (checkInDate.before(today)) {
	            errors.add("Check-in date cannot be in the past.");
	        }
	        if (checkOutDate.before(today)) {
	            errors.add("Check-out date cannot be in the past.");
	        }

	        // If there are any errors, redirect back with error messages
	        if (!errors.isEmpty()) {
	            String errorMessage = String.join(" ", errors);
	           Sessionutil.setAttribute(request, "error", errorMessage);
	            response.sendRedirect(request.getContextPath()+"/BookingController?propertyId=" + propertyId);
	            return;
	        }

	        // Pricing logic
	        BigDecimal basePrice = property.getPricePerNight();
	        BigDecimal cleaningFee = BigDecimal.valueOf(property.getCleaningFee());
	        BigDecimal serviceFee = BigDecimal.valueOf(property.getServiceFee());
	        BigDecimal nightsTotal = basePrice.multiply(BigDecimal.valueOf(days));

	        BigDecimal subtotal = nightsTotal.add(cleaningFee).add(serviceFee);
	        BigDecimal tax = subtotal.multiply(BigDecimal.valueOf(property.getTaxRate()))
	                                 .divide(BigDecimal.valueOf(100));
	        BigDecimal totalPrice = subtotal.add(tax);

	        // Build and save booking
	        BookingModel booking = new BookingModel(
	            propertyId,
	            userId,
	            checkInDate,
	            checkOutDate,
	            basePrice,
	            totalPrice,
	            0,
	            numberOfGuests
	        );
	        booking.setBookingStatus(BookingModel.BookingStatus.PENDING);
	        booking.setBookingCreatedAt(new Timestamp(System.currentTimeMillis()));

	        int bookingId = bookingdao.saveBooking(booking);
	        booking.display();

	        response.sendRedirect(request.getContextPath()+"/booking-success.jsp?bookingId=" + bookingId);

	    } catch (Exception e) {
	        e.printStackTrace();
	        Sessionutil.setAttribute(request, "error", "Booking failed: " + e.getMessage());
	        response.sendRedirect(request.getContextPath()+"/BookingController?propertyId=" + propertyId);
	    }
	}


	   
	}

