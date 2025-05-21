package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.Advancedjava.dao.BookingDao;
import com.Advancedjava.dao.PropertyDaoImpl;
import com.Advancedjava.dao.UserDaoimpl;
import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.BookingModel;
import com.Advancedjava.model.BookingModel.BookingStatus;
import com.Advancedjava.model.usermodel.userStatus;
import com.Advancedjava.util.Sessionutil;
import com.Advancedjava.model.Propertymodel;
import com.Advancedjava.model.usermodel;

/**
 * Servlet implementation class BookigsdashboardController
 */
@WebServlet("/bookingsdashboard")
public class BookigsdashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BookingDao bookingdao=new BookingDao();
		PropertyDaoImpl propertydao=new PropertyDaoImpl();
		UserDaoimpl userdao=new UserDaoimpl();
		List<BookingModel> bookingList = new ArrayList<>();
		List<Propertymodel> properties = new ArrayList<>();
		List<usermodel> users = new ArrayList<>();
		try {
			String statusParam = request.getParameter("status");
            
         
            
            // Check if status filter is applied
            if (statusParam != null && !statusParam.isEmpty()) {
                BookingStatus status = BookingStatus.valueOf(statusParam);
                bookingList = bookingdao.findBookingsByStatus(status);
            } else {
            	bookingList = bookingdao.findAllBookings();
            }
			
			for (BookingModel booking : bookingList) {
				properties.add(propertydao.findById(booking.getPropertyId()));
				String userIdStr = String.valueOf(booking.getUserId());
				users.add(userdao.findByUserId(userIdStr));
				
			}
			request.setAttribute("bookingList", bookingList);
			request.setAttribute("users", users);
			request.setAttribute("properties", properties);
			request.setAttribute("activeSection", "bookings");
			request.getRequestDispatcher("/WEB-INF/pages/admin/bookingsdashboard.jsp").forward(request, response);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String action = request.getParameter("formType");

	    if ("updateStatus".equals(action)) {
	        String bookingIdParam = request.getParameter("bookingId");
	        String newStatusParam = request.getParameter("newStatus");

	        if (bookingIdParam != null && newStatusParam != null) {
	            try {
	                int bookingId = Integer.parseInt(bookingIdParam);
	                BookingStatus newStatus = BookingStatus.valueOf(newStatusParam.toLowerCase());

	                BookingDao bookingDao = new BookingDao();
	                boolean success = bookingDao.updateBookingStatus(bookingId, newStatus);
	                System.out.println("Succeess ID: " + success);
	                if (success) {
	                    Sessionutil.setAttribute(request, "success", "Booking status updated successfully.");
	                } else {
	                    Sessionutil.setAttribute(request, "error", "Failed to update booking status.");
	                }

	            } catch (Exception e) {
	                Sessionutil.setAttribute(request, "error", "Exception Catched." +e.getMessage());
	            } 
	        } else {
	            Sessionutil.setAttribute(request, "error", "Missing booking ID or status.");
	        }

	        // Redirect to prevent form resubmission
	        
	        response.sendRedirect(request.getContextPath() + "/bookingsdashboard");
	    } else {
	        // Other formType logic can go here if needed
	        response.sendRedirect(request.getContextPath() + "/bookingsdashboard");
	    }
	}


}
