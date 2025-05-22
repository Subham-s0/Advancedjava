package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.Advancedjava.dao.UserDao;
import com.Advancedjava.dao.UserDaoimpl;
import com.Advancedjava.model.usermodel;

/**
 * Servlet implementation class Profile_pictureservlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/Profile_pictureservlet" })
public class Profile_pictureservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Profile_pictureservlet() {
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

		String username = (String) request.getSession().getAttribute("username");
		String defaultImage = "default.png";
		String imagePath;

		try {
			UserDao userDao = new UserDaoimpl();
			usermodel user = userDao.findByUsernameOrEmail(username);
			imagePath = user.getUserProfilePicture();
		} catch (Exception e) {
			System.out.println("the image didnot load");
			imagePath = defaultImage;
		}
		String fullPath = "C:/Users/Acer/git/Advancedjava/AdvancedJava/src/main/webapp/resources/images/profile/"
				+ imagePath;
		File imageFile = new File(fullPath);

		if (!imageFile.exists()) {
			imageFile = new File(getServletContext().getRealPath("/resources/images/profile/" + defaultImage));

		}

		response.setContentType(getServletContext().getMimeType(fullPath));
		Files.copy(imageFile.toPath(), response.getOutputStream());

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
