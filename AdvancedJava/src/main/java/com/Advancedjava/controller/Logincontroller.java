package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import com.Advancedjava.dao.UserDao;
import com.Advancedjava.dao.UserDaoimpl;
import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.usermodel;
import com.Advancedjava.util.Cookiesutil;
import com.Advancedjava.util.PasswordHasher;
import com.Advancedjava.util.Sessionutil;
import com.Advancedjava.util.ValidationUtil;

/**
 * Servlet implementation class login
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/login", "/" })
public class Logincontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Logincontroller() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<String> errors = new ArrayList<>();
		String usernameOrEmail = request.getParameter("user_name");
		String password = request.getParameter("user_password");
		String rememberMe = request.getParameter("remember_me");

		// Validate credentials
		if (ValidationUtil.isNullOrEmpty(usernameOrEmail)) {
			errors.add("Username/Email is required. \n");
		}

		if (ValidationUtil.isNullOrEmpty(password)) {
			errors.add("Password is required.");
		}

		if (!errors.isEmpty()) {
			request.setAttribute("error", String.join(" ", errors));
			request.setAttribute("preservedUsername", usernameOrEmail);
			request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
			return;
		}

		try {
			UserDao userDao = new UserDaoimpl();
			usermodel user = userDao.findByUsernameOrEmail(usernameOrEmail);

			if (user == null) {
				errors.add("Invalid credentials");
			} else {
				if (!PasswordHasher.verifyPassword(password, user.getUserPassword())) {
					errors.add("Invalid credentials");
				}
			}
			if (user.getUserStatus() == usermodel.userStatus.blocked) {
				errors.add("Your account is blocked. Please contact support.");
			}

			if (!errors.isEmpty()) {
				request.setAttribute("error", String.join(" ", errors));

				request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
			} else {
				Sessionutil.setAttribute(request, "username", user.getUserName());
				Sessionutil.setAttribute(request, "userId", user.getUserId());
				Sessionutil.setAttribute(request, "userrole", user.getuserRole());
				if ("on".equals(rememberMe)) { // Check if the checkbox was checked
					// Use Cookiesutil to set the cookies
					Cookiesutil.setcookies(response, "rememberedUserName", user.getUserName(), 24 * 60 * 60);

					Cookiesutil.setcookies(response, "rememberedUserId", user.getUserId(), 24 * 60 * 60);
					Cookiesutil.setcookies(response, "rememberedUserRole", user.getuserRole(), 24 * 60 * 60);
				} else {
					// Use Cookiesutil to delete cookies
					Cookiesutil.deletecookie(response, "rememberedUserId");
					Cookiesutil.deletecookie(response, "rememberedUserRole");
				}
				System.out.println(
						"The logged  in role of user is " + (String) Sessionutil.getAttribute(request, "userrole"));
				if (user.getuserRole().equals("admin")) {
					response.sendRedirect(request.getContextPath() + "/admindashboard");
				} else {
					response.sendRedirect(request.getContextPath() + "/home");
				}
			}

		} catch (DataAccessException e) {
			request.setAttribute("error", "System error. Please try again later.");
			request.setAttribute("preservedUsername", usernameOrEmail);
			request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
		}
	}
}
