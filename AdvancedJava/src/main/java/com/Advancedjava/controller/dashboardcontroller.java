package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.Advancedjava.dao.BookingDao;
import com.Advancedjava.dao.PaymentDao;
import com.Advancedjava.dao.PropertyDaoImpl;
import com.Advancedjava.dao.UserDaoimpl;
import com.Advancedjava.model.BookingModel;
import com.Advancedjava.model.CategoryBookingCount;
import com.Advancedjava.model.Propertymodel;
import com.Advancedjava.model.usermodel;

/**
 * Servlet implementation class dashboardcontroller
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/admindashboard" })
public class dashboardcontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public dashboardcontroller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	UserDaoimpl userDao = new UserDaoimpl();
	PropertyDaoImpl propertyDao = new PropertyDaoImpl();
	PaymentDao paymentDao = new PaymentDao();
	BookingDao bookingDao = new BookingDao();
	try {
		int totalUsers = userDao.getTotalUsers();
		int totalProperties = propertyDao.getTotalProperties();
		int totalBookings = bookingDao.getTotalBookings();
		BigDecimal totalPayments = paymentDao.getTotalPaidAmount();
		  List<CategoryBookingCount> categoryCounts = bookingDao.getBookingCountByCategory();
	        request.setAttribute("categoryCounts", categoryCounts);

String topCategory = "";
int topBookings = 0;
int totalCategoryBookings = 0;

if (!categoryCounts.isEmpty()) {
    CategoryBookingCount top = categoryCounts.get(0);
    topCategory = top.getCategoryName();
    topBookings = top.getTotalBookings();
    
    for (CategoryBookingCount c : categoryCounts) {
        totalCategoryBookings += c.getTotalBookings();
    }
}
List<BookingModel> allBookings = bookingDao.findAllBookings();
List<BookingModel> recentBookings = allBookings.stream()
        .limit(5)
        .collect(Collectors.toList());
List<usermodel> recentBookingUsers = new ArrayList<>();
for (BookingModel booking : recentBookings) {
	usermodel user = userDao.findByUserId(String.valueOf(booking.getUserId()));
  // assuming this method exists
    recentBookingUsers.add(user);
   
}
List<Propertymodel> topProperties = new ArrayList<>(); 
topProperties = propertyDao.getTop5PropertiesByMostBookings();
request.setAttribute("topProperties", topProperties);
request.setAttribute("users", recentBookingUsers);
request.setAttribute("recentBookings", recentBookings);
			request.setAttribute("topCategory", topCategory);
			request.setAttribute("topBookings", topBookings);
			request.setAttribute("totalCategoryBookings", totalCategoryBookings);
			request.setAttribute("totalUsers", totalUsers);
			request.setAttribute("totalProperties", totalProperties);
			request.setAttribute("totalBookings", totalBookings);
			request.setAttribute("totalPayments", totalPayments);
		
		 request.setAttribute("activeSection", "dashboard");
		request.getRequestDispatcher("/WEB-INF/pages/admin/admindashboard.jsp").forward(request, response);
	}catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
