package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

import com.Advancedjava.dao.BookingDao;
import com.Advancedjava.dao.PropertyDaoImpl;
import com.Advancedjava.dao.Reviewdao;
import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.BookingModel;
import com.Advancedjava.model.Propertymodel;
import com.Advancedjava.model.Review;
import com.Advancedjava.util.Sessionutil;

/**
 * Servlet implementation class ReviewController
 */
@WebServlet("/review")
public class ReviewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            // Get booking ID from the request
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            
            String username = (String) Sessionutil.getAttribute(request, "username");
            System.out.println("Username: " + username);
            
            
            BookingDao bookingDao = new BookingDao();
            BookingModel booking = bookingDao.findBookingById(bookingId);
         
            if (booking == null) {
                Sessionutil.setAttribute(request, "error", "Booking not found");
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }
            
            // Get property details
            PropertyDaoImpl propertyDao = new PropertyDaoImpl();
            Propertymodel property = propertyDao.findById(booking.getPropertyId());
            
            // Calculate night count
            long dayDiff = (booking.getCheckInDate().getTime() - booking.getCheckInDate().getTime()) / (1000 * 60 * 60 * 24);
            
            request.setAttribute("booking", booking);
            request.setAttribute("property", property);
            request.setAttribute("nightCount", dayDiff);
            request.setAttribute("basePrice", booking.getBasePrice());
            request.setAttribute("totalPrice", booking.getTotalPrice());
           
                   
            request.getRequestDispatcher("/WEB-INF/pages/review.jsp").forward(request, response);
           
	} catch (Exception e) {
        e.printStackTrace();
        Sessionutil.setAttribute(request, "error", "Error loading payment page: " + e.getMessage());
        response.sendRedirect(request.getContextPath() + "/home");
    }
		}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
	    
	  
	    
	    try {
	        // Get form parameters
	        String bookingIdParam = request.getParameter("bookingId");
	        String ratingParam = request.getParameter("rating");
	        String comment = request.getParameter("comment");
	        
	        // Validate required parameters
	        if (bookingIdParam == null || bookingIdParam.trim().isEmpty()) {
	            Sessionutil.setAttribute(request,"error", "Booking ID is required");
	            response.sendRedirect(request.getContextPath() + "/review?bookingId=" + bookingIdParam);
	            return;
	        }
	        
	        if (ratingParam == null || ratingParam.trim().isEmpty()) {
	        	Sessionutil.setAttribute(request,"error", "Please select a rating");
	            response.sendRedirect(request.getContextPath() + "/review?bookingId=" + bookingIdParam);
	            return;
	        }
	        
	        // Parse and validate parameters
	        int bookingId;
	        int rating;
	        
	        try {
	            bookingId = Integer.parseInt(bookingIdParam);
	            rating = Integer.parseInt(ratingParam);
	        } catch (NumberFormatException e) {
	            Sessionutil.setAttribute(request,"error", "Invalid booking ID or rating");
	            response.sendRedirect(request.getContextPath() + "/review?bookingId=" + bookingIdParam);
	            return;
	        }
	        
	        // Validate rating range
	        if (rating < 1 || rating > 5) {
	        	Sessionutil.setAttribute(request,"error", "Rating must be between 1 and 5 stars");
	            response.sendRedirect(request.getContextPath() + "/review?bookingId=" + bookingIdParam);
	            return;
	        }
	        
	        // Validate comment (optional but if provided, check length)
	        if (comment != null && comment.trim().length() > 1000) {
	        	Sessionutil.setAttribute(request,"error", "Review comment is too long (maximum 1000 characters)");
	            response.sendRedirect(request.getContextPath() + "/review?bookingId=" + bookingIdParam);
	            return;
	        }
	        
	        // Optional: Check if user is authorized to review this booking
	        // You might want to verify that the logged-in user owns this booking
	       
	        
	        // Optional: Check if review already exists for this booking
	        Reviewdao reviewDao = new Reviewdao();
	        if (reviewDao.reviewExistsForBooking(bookingId)) {
	        	Sessionutil.setAttribute(request,"error", "You have already submitted a review for this booking");
	            response.sendRedirect(request.getContextPath() + "/review?bookingId=" + bookingIdParam);
	            return;
	        }
	        
	        // Create Review object
	        Review review = new Review(rating,comment != null ? comment.trim() : "",new java.sql.Date(System.currentTimeMillis()),bookingId);
	       
	        
	        // Save review to database
	        int reviewId = reviewDao.saveReview(review);
	        
	        if (reviewId > 0) {
	        	Sessionutil.setAttribute(request,"success", "Thank you! Your review has been submitted successfully.");
	            
	            // Redirect to a success page or back to bookings
	            response.sendRedirect(request.getContextPath() + "/home");
	        } else {
	        	Sessionutil.setAttribute(request,"error", "Failed to submit review. Please try again.");
	            response.sendRedirect(request.getContextPath() + "/review?bookingId=" + bookingIdParam);
	        }
	        
	    } catch (Exception e) {
	        // Log unexpected errors
	        System.err.println("Unexpected error in review submission: " + e.getMessage());
	        e.printStackTrace();
	        
	        Sessionutil.setAttribute(request,"error", "An unexpected error occurred. Please try again.");
	        String bookingId = request.getParameter("bookingId");
	        response.sendRedirect(request.getContextPath() + "/review?bookingId=" + bookingId);
	    }
	}
}
