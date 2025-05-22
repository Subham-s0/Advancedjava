package com.Advancedjava.controller;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.Advanedjava.service.RegisterService;

/**
 * Servlet implementation class registerservlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/register" })
public class Registercontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RegisterService registerService = new RegisterService();
		boolean isRegistered = registerService.registerUser(request, response);

		if (isRegistered) {
			HttpSession session = request.getSession();
			session.setAttribute("success", "Registered successfully!");
			response.sendRedirect(request.getContextPath() + "/login");
		}

	}

}