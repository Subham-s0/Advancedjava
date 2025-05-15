package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.Advancedjava.dao.CategoryDao;
import com.Advancedjava.dao.CategoryDaoImpl;
import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.Categorymodel;

/**
 * Servlet implementation class AddPropertyController
 */
@WebServlet("/AddPropertyController")
public class AddPropertyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private CategoryDao categoryDao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddPropertyController() {
    	
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		categoryDao = new CategoryDaoImpl();
		   List<Categorymodel> categories;
		try {
			categories = categoryDao.findAllcategories();
			request.setAttribute("categories", categories);
			request.setAttribute("activeSection", "property");
			request.getRequestDispatcher("WEB-INF/pages/admin/addproperty.jsp").forward(request, response);
		} catch (DataAccessException e) {
			
			e.printStackTrace();
		}
           
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	 response.sendRedirect(request.getContextPath() + "/addpropertyimages");
	}

}
