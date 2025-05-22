package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.Advancedjava.dao.CategoryDaoImpl;
import com.Advancedjava.dao.PropertyDao;
import com.Advancedjava.dao.PropertyDaoImpl;
import com.Advancedjava.dao.UserDaoimpl;
import com.Advancedjava.dao.WishlistImpl;
import com.Advancedjava.model.Categorymodel;
import com.Advancedjava.model.Propertymodel;
import com.Advancedjava.util.Sessionutil;

/**
 * Servlet implementation class FilterHotelsServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/filter-hotels" })
public class FilterHotelsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FilterHotelsServlet() {
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

		String categoryId = request.getParameter("categoryId");

		try {
			PropertyDao propertydao = new PropertyDaoImpl();
			List<Propertymodel> filteredProperties = new ArrayList<>();

			if (categoryId == null || categoryId.isEmpty() || "0".equals(categoryId)) {
				filteredProperties = propertydao.findallproperties();
				System.out.println("Fetching ALL properties");
			} else {

				filteredProperties = propertydao.listAllPropertiesByCategory(Integer.parseInt(categoryId));
				System.out.println("Category ID: " + categoryId);
				System.out.print("Property IDs for this category: ");
				for (Propertymodel property : filteredProperties) {
					System.out.print(property.getPropertyId() + " ");
				}
				System.out.println(); // Move to new line after listing IDs

			}
			CategoryDaoImpl categoryDao = new CategoryDaoImpl();
			String userId = (String) Sessionutil.getAttribute(request, "userId");

			List<Categorymodel> categories = categoryDao.findAllcategories();
			request.setAttribute("categories", categories);

			request.setAttribute("properties", filteredProperties);

			// Only include the property grid (not the full page)
			WishlistImpl wishlistDao = new WishlistImpl();
			List<Integer> wishlist = wishlistDao.getWishlistByUserId(userId);
			System.out.println(wishlist);
			request.setAttribute("wishlistIds", wishlist);
			request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("<p>Error in servlet loading properties. Please try again.</p>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
