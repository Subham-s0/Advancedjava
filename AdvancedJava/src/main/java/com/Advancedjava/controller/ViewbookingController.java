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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		try {
			String username = (String) Sessionutil.getAttribute(request, "username");
			UserDaoimpl userdao = new UserDaoimpl();
			usermodel user = userdao.findByUsernameOrEmail(username);
			BookingDao bookingDao = new BookingDao();
			PropertyDaoImpl propertyDao = new PropertyDaoImpl();
			List<BookingModel> bookings = bookingDao.getBookingsByUserId(user.getUserId());
			List<Propertymodel> properties =new ArrayList<>();
			for(BookingModel booking : bookings) {
				Propertymodel property = propertyDao.findById(booking.getPropertyId());
				properties.add(property);
						}
			request.setAttribute("properties", properties);
			request.setAttribute("bookings", bookings);
		    request.getRequestDispatcher("/WEB-INF/pages/mybookings.jsp").forward(request, response);	
		} catch (DataAccessException e) {
			Sessionutil.setAttribute(request, "error", "Error occured"+e.getMessage());
			response.sendRedirect(request.getContextPath()+"/home");
			e.printStackTrace();
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
