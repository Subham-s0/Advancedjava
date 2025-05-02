package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.Advancedjava.connection.DBconnection;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 
        String username = (String) request.getSession().getAttribute("username");
        String defaultImage = "default.png";
        String imagePath;   
        
        
        try {
        	 Connection conn = DBconnection.getDbConnection();
        	 String sql = "SELECT user_profile FROM users WHERE user_name = ?";
             PreparedStatement pst = conn.prepareStatement(sql);
            
            pst.setString(1, username);
            		ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        imagePath = rs.getString("user_profile");
                    } else {
                        imagePath = defaultImage;
                    }
        }
        catch(Exception e) {
        	System.out.println("the image didnot load");
        	 imagePath = defaultImage;
        }
        
        String fullPath ="C:/Users/Acer/git/Advancedjava/AdvancedJava/src/main/webapp/resources/images/profile/" + imagePath;
        File imageFile = new File(fullPath);
        System.out.println("the full path is :"+fullPath);
        if (!imageFile.exists()) {
            imageFile = new File(getServletContext().getRealPath("/resources/images/profile/" + defaultImage));
            System.out.println("this aprt was hit is :"+fullPath);
        }

        response.setContentType(getServletContext().getMimeType(fullPath));
        Files.copy(imageFile.toPath(), response.getOutputStream());
        System.out.println("the full path is :"+fullPath);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
