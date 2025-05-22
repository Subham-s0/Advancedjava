package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.Advancedjava.dao.CategoryDaoImpl;
import com.Advancedjava.dao.PropertyDaoImpl;
import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.Categorymodel;
import com.Advancedjava.model.Propertymodel;
import com.Advancedjava.util.Sessionutil;

/**
 * Servlet implementation class Propertydashboard
 */
@WebServlet("/propertydashboard")
public class PropertydashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PropertydashboardController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CategoryDaoImpl categoryDao = new CategoryDaoImpl();
		PropertyDaoImpl propertyDao = new PropertyDaoImpl();

		String categoryParam = request.getParameter("category");
		String priceRange = request.getParameter("priceRange");

		List<Propertymodel> filteredProperties;

		try {
			List<Categorymodel> categories = categoryDao.findAllcategories();
			// Convert category
			Integer categoryId = (categoryParam != null && !categoryParam.isEmpty()) ? Integer.parseInt(categoryParam)
					: null;

			// Parse price range
			Double minPrice = null, maxPrice = null;
			if (priceRange != null && !priceRange.isEmpty()) {
				if (priceRange.equals("500+")) {
					minPrice = 500.0;
				} else {
					String[] parts = priceRange.split("-");
					minPrice = Double.parseDouble(parts[0]);
					maxPrice = Double.parseDouble(parts[1]);
				}
			}

			filteredProperties = propertyDao.findPropertiesByFilters(categoryId, minPrice, maxPrice);

			request.setAttribute("categories", categories);
			request.setAttribute("properties", filteredProperties);
			request.setAttribute("activeSection", "property");
			request.getRequestDispatcher("WEB-INF/pages/admin/propertydashboard.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Handle form submission or other POST reques
		String action = request.getParameter("formType");
		if (action.equals("deleteProperty")) {
			doDelete(request, response);
		} else {
			doGet(request, response);
		}

	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PropertyDaoImpl propertyDao = new PropertyDaoImpl();
		String propertyIdstr = request.getParameter("propertyId");
		try {
			if (propertyIdstr != null && propertyIdstr.matches("\\d+")) {
				int propertyId = Integer.parseInt(propertyIdstr);
				propertyDao.delete(propertyId);
				Sessionutil.setAttribute(request, "success", "Property deleted successfully");
			}

			response.sendRedirect(request.getContextPath() + "/propertydashboard");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}