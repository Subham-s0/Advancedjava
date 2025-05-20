package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.Advancedjava.dao.BookingDao;
import com.Advancedjava.dao.PaymentDao;
import com.Advancedjava.dao.PropertyDaoImpl;
import com.Advancedjava.model.BookingModel;
import com.Advancedjava.model.PaymentModel;
import com.Advancedjava.model.PaymentModel.PaymentMethod;
import com.Advancedjava.model.PaymentModel.PaymentStatus;
import com.Advancedjava.model.Propertymodel;
import com.Advancedjava.util.Sessionutil;

/**
 * Servlet implementation class paymentController
 */
@WebServlet("/payment")
public class paymentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public paymentController() {
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
            if (username == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            
            BookingDao bookingDao = new BookingDao();
            BookingModel booking = bookingDao.findBookingById(bookingId);
         
            if (booking == null) {
                Sessionutil.setAttribute(request, "error", "Booking not found");
                response.sendRedirect(request.getContextPath() + "/dashboard");
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
            request.setAttribute("cleaningFee", property.getCleaningFee());
            request.setAttribute("serviceFee", property.getServiceFee());
            request.setAttribute("tax", booking.getTotalPrice().subtract(
                    booking.getBasePrice().multiply(BigDecimal.valueOf(dayDiff))
                    .add(BigDecimal.valueOf(property.getCleaningFee()))
                    .add(BigDecimal.valueOf(property.getServiceFee()))
            ));
            request.getRequestDispatcher("/WEB-INF/pages/payment.jsp").forward(request, response);
           
	} catch (Exception e) {
        e.printStackTrace();
        Sessionutil.setAttribute(request, "error", "Error loading payment page: " + e.getMessage());
        response.sendRedirect(request.getContextPath() + "/home");
    }}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 try {
	            // Get booking ID and payment method
	            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
	            String paymentMethodStr = request.getParameter("paymentMethod");
	            
	            // Check if user is logged in
	            String username = (String) Sessionutil.getAttribute(request, "username");
	            if (username == null) {
	                response.sendRedirect(request.getContextPath() + "/login");
	                return;
	            }
	            
	            // Validate payment method
	            List<String> errors = new ArrayList<>();
	            if (paymentMethodStr == null || paymentMethodStr.isEmpty()) {
	                errors.add("Payment method is required.");
	            } else if (!isValidPaymentMethod(paymentMethodStr)) {
	                errors.add("Invalid payment method selected.");
	            }
	            
	            // Get booking details
	            BookingDao bookingDao = new BookingDao();
	            BookingModel booking = bookingDao.findBookingById(bookingId);
	            
	            // Verify booking exists and belongs to the current user
	            if (booking == null) {
	                errors.add("Booking not found");
	            }
	            
	            // If there are any errors, redirect back with error messages
	            if (!errors.isEmpty()) {
	                String errorMessage = String.join(" ", errors);
	                Sessionutil.setAttribute(request, "error", errorMessage);
	                response.sendRedirect(request.getContextPath() + "/payment?bookingId=" + bookingId);
	                return;
	            }
	            
	            PaymentMethod paymentMethod;
	            if (paymentMethodStr.equals("card")) {
	                paymentMethod = PaymentMethod.card;
	            } else if (paymentMethodStr.equals("bank")) {
	                paymentMethod = PaymentMethod.bank;
	            } else if(paymentMethodStr.equals("bank")) {
	                paymentMethod = PaymentMethod.digital_wallet;
	            }
	            else {
	            	  paymentMethod = PaymentMethod.none;
	            }
	            PaymentStatus paymentStatus;
	            if (paymentMethodStr.equals("payLater")) {
	                paymentStatus = PaymentStatus.unpaid;
	            } else {
	                paymentStatus = PaymentStatus.paid;
	            }
	            
	            PaymentModel payment = new PaymentModel(
	                    booking.getTotalPrice(),
	                    paymentMethod,
	                    paymentStatus,
	                    new Date(System.currentTimeMillis()),
	                    bookingId
	                );
	           
	            
	            // Save payment details
	            PaymentDao paymentDao = new PaymentDao();
	            int paymentId = paymentDao.savePayment(payment);
	            
	            if (paymentStatus == PaymentStatus.paid) {
	            	 bookingDao.updateBookingStatus(bookingId,BookingModel.BookingStatus.confirmed);
	            } else {
	           	 bookingDao.updateBookingStatus(bookingId,BookingModel.BookingStatus.pending);
	            }
	           
	            
	            // Set success message and redirect to confirmation page
	            Sessionutil.setAttribute(request, "success", "Payment successful! and booking status updates");
	            response.sendRedirect(request.getContextPath() + "/home");
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
	            Sessionutil.setAttribute(request, "error", "Payment failed: " + e.getMessage());
	            response.sendRedirect(request.getContextPath() + "/payment?bookingId=" + bookingId);
	        }
	    }
	    
	    private boolean isValidPaymentMethod(String paymentMethod) {
	        return paymentMethod.equals("card") || 
	               paymentMethod.equals("bank") || 
	               paymentMethod.equals("wallet") || 
	               paymentMethod.equals("payLater");
	    }
	

}
