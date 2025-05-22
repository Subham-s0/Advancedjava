package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class Searchrouter
 */
@WebServlet("/Searchrouter")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
		maxFileSize = 5 * 1024 * 1024, // 5 MB
		maxRequestSize = 10 * 1024 * 1024 // 10 MB
)
public class Searchrouter extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Searchrouter() {
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
		String query = request.getParameter("query");
		if (query == null)
			query = "";

		// Normalize and forward to matching page
		String forwardPath;

		switch (query.toLowerCase()) {
		case "dashboard":
			forwardPath = "/admindashboard";
			break;
		case "property dashboard":
			forwardPath = "/propertydashboard";
			break;
		case "add property":
			forwardPath = "/AddPropertyController";
			break;
		case "add images":
			forwardPath = "/addpropertyimages";
			break;
		case "add amenities":
			forwardPath = "/addamenities";
			break;
		case "success page":
			forwardPath = "/propertysucess";
			break;
		case "update property":
			forwardPath = "/updatepropertycontroller";
			break;
		case "users dashboard":
			forwardPath = "/usersdashboard";
			break;
		case "user details":
			forwardPath = "/usersdetails";
			break;
		case "bookings dashboard":
			forwardPath = "/bookingsdashboard";
			break;
		case "admin profile":
			forwardPath = "/adminprofile";
			break;
		default:
			forwardPath = "/admindashboard";
			break;
		}

		request.getRequestDispatcher(forwardPath).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	// Normalize and redirect to matching page
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
}
