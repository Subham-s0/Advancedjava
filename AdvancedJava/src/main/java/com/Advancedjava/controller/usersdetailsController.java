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

/**
 * Servlet implementation class usersdetails
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/usersdetails" })
public class usersdetailsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public usersdetailsController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userId = request.getParameter("userId");
		BookingDao bookingdao = new BookingDao();
		 List<BookingModel> bookingList =new ArrayList<>();
		 PropertyDaoImpl propertydao=new PropertyDaoImpl();
		 List<Propertymodel> properties = new ArrayList<>();
		try {
			 
			  UserDaoimpl userdao = new UserDaoimpl();
			  usermodel user = userdao.findByUserId(userId);
			  bookingList = bookingdao.getBookingsByUserId(userId);
			  
			  for (BookingModel booking : bookingList) {
					properties.add(propertydao.findById(booking.getPropertyId()));
					}
				request.setAttribute("bookingList", bookingList);
				request.setAttribute("user", user);
				request.setAttribute("properties", properties);
				request.setAttribute("activeSection", "users");
				request.getRequestDispatcher("/WEB-INF/pages/admin/Userdetails.jsp").forward(request, response);
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
		doGet(request, response);
	}

}
