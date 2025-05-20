package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.Advancedjava.dao.PropertyImageDaoImpl;
import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.PropertyImagemodel;
import com.Advancedjava.util.ImageUtil;
import com.Advancedjava.util.Sessionutil;
import com.Advancedjava.util.ValidationUtil;

/**
 * Servlet implementation class addpropertyimages
 */
@WebServlet("/addpropertyimages")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024, // 1MB
	    maxFileSize = 2 * 1024 * 1024,    // 2MB
	    maxRequestSize = 10 * 1024 * 1024 // 10MB
	)
public class addpropertyimages extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addpropertyimages() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try { 
		String propertyIdStr = request.getParameter("propertyId");
		
		  if (propertyIdStr != null) {
			  int propertyId = Integer.parseInt(propertyIdStr);
			  PropertyImageDaoImpl propertyImageDao = new PropertyImageDaoImpl();
				List<PropertyImagemodel> existingImages = propertyImageDao.findByPropertyId(propertyId);
				request.setAttribute("existingImages", existingImages);
				
			} 
		  
		  request.getRequestDispatcher("WEB-INF/pages/admin/addpropertyimages.jsp").forward(request, response);
	    }catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Sessionutil.setAttribute(request, "error", "An error occurred. Please try again. " + e.getMessage());
	            response.sendRedirect(request.getContextPath() + "/propertydashboard");
			}
			  
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<String> errors = new ArrayList<>();
		 String propertyIdStr = request.getParameter("propertyId");
		 if (propertyIdStr == null || propertyIdStr.trim().isEmpty() ||"null".equalsIgnoreCase(propertyIdStr)) {
		     		
		    	request.setAttribute("error", "The property id is missing");
				request.getRequestDispatcher("WEB-INF/pages/admin/addpropertyimages.jsp").forward(request, response);
		    } else {
		        int propertyId = Integer.parseInt(propertyIdStr);
		        List<Part> imageParts = new ArrayList<>();
		        System.out.print("after parsing"+propertyId);
	            for (Part part : request.getParts()) {
	                if ("images".equals(part.getName()) && part.getSize() > 0) {
	                    imageParts.add(part);
	                }
	            }
	            String[] imageNames = request.getParameterValues("imageNames");

                // 3. Basic validation
                if (imageParts.isEmpty()) {
                    errors.add("Please upload at least one image");
                }

                if (imageNames == null) {
                    errors.add("Image names are required");
                } else if (imageNames.length != imageParts.size()) {
                    errors.add("Number of image names must match uploaded files");
                }
                if (errors.isEmpty()) {
                	PropertyImageDaoImpl propertyImageDao = new PropertyImageDaoImpl();
                    List<PropertyImagemodel> existingImages = null;
					try {
						existingImages = propertyImageDao.findByPropertyId(propertyId);
					
					} catch (DataAccessException e) {
						// TODO Auto-generated catch block
						errors.add(e.getMessage());
						 request.setAttribute("propertyId", propertyId);
		                    request.getRequestDispatcher("WEB-INF/pages/admin/addpropertyimages.jsp").forward(request, response);
					}
					

                    for (int i = 0; i < imageParts.size(); i++) {
                        Part imagePart = imageParts.get(i);
                        String imageName = imageNames[i];
                        String sanitizedName = imageName.trim().replaceAll("[^a-zA-Z0-9.-]", "_");
                        
                        // 5. Individual image validation
                        if (ValidationUtil.isNullOrEmpty(imageName)) {
                            errors.add("Image #" + (i+1) + " needs a valid name");
                            continue;
                        }
                        

                        try {
                        	 ImageUtil imageUtil = new ImageUtil();
                        	 String originalFileName = imageUtil.getImageNameFromPart(imagePart);  // Using the method
                        	imageUtil.validateImage(imagePart);
                            String saveFolder = "property";
                            String customFileName = propertyId + "_" + originalFileName;
                            boolean duplicateFileName = existingImages.stream()
                                    .anyMatch(img -> img.getFileName().equalsIgnoreCase(customFileName)); // ✅ Compare against custom filename

                            if (duplicateFileName) {
                                errors.add("File already exists: " + originalFileName); // 🚨 Show original name, not sanitized
                                continue;
                            }
                          
                            boolean uploaded = imageUtil.uploadImage(imagePart,
                               "",
                                saveFolder,
                                customFileName
                            );

                            if (!uploaded) {
                                errors.add("Failed to upload: " + sanitizedName);
                                continue;
                            }

                            // 9. Save to database
                            PropertyImagemodel image = new PropertyImagemodel(propertyId,customFileName,sanitizedName);
                          
           
                            if (propertyImageDao.save(image,propertyId) <= 0) {
                                errors.add("Failed to save: " + sanitizedName);
                            }

                        } catch (ServletException | DataAccessException e) {
                            errors.add("Invalid image: " + sanitizedName + " - " + e.getMessage());
                        }
                    }
                }
            
		
                if (!errors.isEmpty()) {
                	request.setAttribute("error", String.join(" ", errors));
                    request.setAttribute("propertyId", propertyId);
                    request.getRequestDispatcher("WEB-INF/pages/admin/addpropertyimages.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/propertysucess?propertyId=" + propertyId);
                }
	}
	}

}
