package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.Advancedjava.util.Cookiesutil;
import com.Advancedjava.util.Sessionutil;

/**
 * Servlet implementation class LogoutController
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/logout"})
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutController() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		Sessionutil.invalidateSession(request);
		Cookiesutil.deletecookie(response, "rememberedUsername");
        Cookiesutil.deletecookie(response, "rememberedUserRole");
		response.sendRedirect(request.getContextPath() + "/login");
		
    }
	
	
}
