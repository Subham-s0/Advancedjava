package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.Advancedjava.dao.UserDaoimpl;
import com.Advancedjava.model.usermodel;
import com.Advancedjava.model.usermodel.userStatus;
import com.Advancedjava.util.Sessionutil;

/**
 * Servlet implementation class usersdashboard
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/usersdashboard" })
public class usersdashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public usersdashboard() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserDaoimpl userDao = new UserDaoimpl();

		try {
			String statusParam = request.getParameter("status"); // e.g. "active", "inactive", "blocked"

			List<usermodel> users;

			if (statusParam != null && !statusParam.isEmpty()) {
				// Filtered list
				users = userDao.getUsersByStatus(statusParam); // You must implement this method

			} else {
				// Unfiltered list (all users)
				users = userDao.getAllUsers();
			}
			List<Integer> bookingCounts = new ArrayList<>();
			for (usermodel user : users) {
				Integer count = Integer.valueOf(userDao.countBookingsByUserId(user.getUserId())); // Redundant

				bookingCounts.add(count);
			}
			request.setAttribute("bookingCounts", bookingCounts);
			request.setAttribute("users", users);
			request.setAttribute("activeSection", "user");
			request.getRequestDispatcher("/WEB-INF/pages/admin/Userdashboard.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("activeSection", "dashboard");
			request.getRequestDispatcher("/WEB-INF/pages/admin/dashboard.jsp").forward(request, response);
			;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String action = request.getParameter("action");
			UserDaoimpl userDao = new UserDaoimpl();
			if ("delete".equals(action)) {
				String userId = request.getParameter("userId");
				String currentStatus = request.getParameter("currentStatus");

				if (userId != null && currentStatus != null) {
					userStatus newStatus;

					// Determine the new status based on current status
					if ("active".equalsIgnoreCase(currentStatus) || "inactive".equalsIgnoreCase(currentStatus)) {
						newStatus = userStatus.blocked;
					} else if ("blocked".equalsIgnoreCase(currentStatus)) {
						newStatus = userStatus.active;
					} else {
						// Handle unexpected status
						Sessionutil.setAttribute(request, "error", "Invalid current status provided.");
						response.sendRedirect(request.getContextPath() + "/admin/usersdashboard");
						return;
					}

					// Update the user's status in DB
					boolean ok = userDao.updateUserStatusById(userId, newStatus);
					if (!ok) {
						Sessionutil.setAttribute(request, "error", "Failed to update user status.");
						response.sendRedirect(request.getContextPath() + "/admin/usersdashboard");
						return;
					}
					// Optionally set a success message
					Sessionutil.setAttribute(request, "success", "User status updated successfully.");
				} else {
					Sessionutil.setAttribute(request, "error", "Missing parameters.");
				}

				response.sendRedirect(request.getContextPath() + "/usersdashboard");
				return;
			}

			// Unknown action
			Sessionutil.setAttribute(request, "error", "Unknown action.");
			response.sendRedirect(request.getContextPath() + "/usersdashboard");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			Sessionutil.setAttribute(request, "error", "An error occurred while processing the request.");
			response.sendRedirect(request.getContextPath() + "/admin/usersdashboard");
		}
	}

}
