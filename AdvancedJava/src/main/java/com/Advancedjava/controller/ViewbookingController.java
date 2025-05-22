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
import com.Advancedjava.model.Propertymodel;
import com.Advancedjava.model.usermodel;
import com.Advancedjava.util.Sessionutil;

/**
 * Servlet implementation class ViewbookingController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/Viewbooking" })
public class ViewbookingController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		try {
			String username = (String) Sessionutil.getAttribute(request, "username");
			UserDaoimpl userdao = new UserDaoimpl();
			usermodel user = userdao.findByUsernameOrEmail(username);
			BookingDao bookingDao = new BookingDao();
			PropertyDaoImpl propertyDao = new PropertyDaoImpl();
			List<BookingModel> bookings = bookingDao.getBookingsByUserId(user.getUserId());
			List<Propertymodel> properties = new ArrayList<>();
			for (BookingModel booking : bookings) {
				Propertymodel property = propertyDao.findById(booking.getPropertyId());
				properties.add(property);
			}
			request.setAttribute("properties", properties);
			request.setAttribute("bookings", bookings);
			request.getRequestDispatcher("/WEB-INF/pages/mybookings.jsp").forward(request, response);
		} catch (DataAccessException e) {
			Sessionutil.setAttribute(request, "error", "Error occured" + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/home");
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String formType = request.getParameter("formType");

		if ("cancelbooking".equals(formType)) {
			try {
				// Get the booking ID from the request
				int bookingId = Integer.parseInt(request.getParameter("bookingId"));
				BookingDao bookingDao = new BookingDao();
				// Call the updateBookingStatus method to cancel the booking
				boolean updated = bookingDao.updateBookingStatus(bookingId, BookingStatus.cancelled);

				if (updated) {
					// Booking was successfully cancelled
					request.setAttribute("message", "Booking cancelled successfully");
				} else {
					// No booking was found with the given ID
					request.setAttribute("error", "Unable to cancel booking. Booking not found.");
				}

			} catch (NumberFormatException e) {
				Sessionutil.setAttribute(request, "error", "Invalid booking ID format");
			} catch (DataAccessException e) {
				Sessionutil.setAttribute(request, "error", "Database error: " + e.getMessage());
			}

			// Redirect back to the booking view page
			Sessionutil.setAttribute(request, "success", "The booking Status updated .  ");

			response.sendRedirect(request.getContextPath() + "/Viewbooking");
		} else {
			return; // Handle other form types or do nothing
		}
	}

}
