package com.Advancedjava.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.Advancedjava.dao.AmenityDaoImpl;
import com.Advancedjava.dao.BookingDao;
import com.Advancedjava.dao.CategoryDaoImpl;
import com.Advancedjava.dao.PropertyDaoImpl;
import com.Advancedjava.dao.PropertyImageDaoImpl;
import com.Advancedjava.dao.UserDaoimpl;
import com.Advancedjava.model.AmenityModel;
import com.Advancedjava.model.BookingModel;
import com.Advancedjava.model.Categorymodel;
import com.Advancedjava.model.PropertyImagemodel;
import com.Advancedjava.model.Property_Amenity;
import com.Advancedjava.model.Propertymodel;
import com.Advancedjava.model.usermodel;
import com.Advancedjava.util.ImageUtil;
import com.Advancedjava.util.Sessionutil;
import com.Advanedjava.service.UpdatePropertyService;

@WebServlet(asyncSupported = true, urlPatterns = { "/updatepropertycontroller" })
public class updatepropertycontroller extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public updatepropertycontroller() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String propertyIdStr = request.getParameter("propertyId");
            
            if (propertyIdStr == null && request.getAttribute("propertyId") == null) {
                response.sendRedirect(request.getContextPath() + "/propertydashboard");
                return;
            }

            int propertyId = (request.getAttribute("propertyId") != null)
                    ? (int) request.getAttribute("propertyId")
                    : Integer.parseInt(propertyIdStr);

            CategoryDaoImpl categoryDao = new CategoryDaoImpl();
            PropertyDaoImpl propertyDao = new PropertyDaoImpl();
            AmenityDaoImpl amenityDao = new AmenityDaoImpl();
        	List<usermodel> users = new ArrayList<>();
        	UserDaoimpl userdao=new UserDaoimpl();

            List<Categorymodel> categories = categoryDao.findAllcategories();
            request.setAttribute("categories", categories);

            Propertymodel property = propertyDao.findById(propertyId);
            request.setAttribute("property", property);
            
            List<Integer> amenityIds = propertyDao.findAmenityIdsByPropertyId(propertyId);
            List<AmenityModel> property_amenities = new ArrayList<>();
            BookingDao bookingDao = new BookingDao();
            List<BookingModel> bookingList = bookingDao.getBookingsBypropertyId(propertyId);
            for (Integer amenityId : amenityIds) {
                AmenityModel amenity = amenityDao.findById(amenityId);
                if (amenity != null) {
                    property_amenities.add(amenity);
                }
            }
            for (BookingModel booking : bookingList) {
            	String userIdStr = String.valueOf(booking.getUserId());
				users.add(userdao.findByUserId(userIdStr));
				
			}
            request.setAttribute("users", users);
            request.setAttribute("bookingList", bookingList);
            request.setAttribute("propertyAmenities", property_amenities);

            request.getRequestDispatcher("WEB-INF/pages/admin/editpropertydashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            Sessionutil.setAttribute(request, "error", "An error occurred. Please try again. " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/propertydashboard");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPut(request, response);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("The form was submitted");

            String formType = request.getParameter("formType");
            String propertyIdStr = request.getParameter("propertyId");
            if (propertyIdStr == null || formType == null) {
                Sessionutil.setAttribute(request, "error", "Invalid request.");
                response.sendRedirect(request.getContextPath() + "/propertydashboard");
                return;
            }

            int propertyId = Integer.parseInt(propertyIdStr);
            PropertyImageDaoImpl propertyImageDao = new PropertyImageDaoImpl();
            AmenityDaoImpl amenityDao = new AmenityDaoImpl();

            switch (formType) {
                case "propertydetails":
                    UpdatePropertyService service = new UpdatePropertyService();
                    boolean status = service.Updatedetails(request, response, propertyId);
                    if (response.isCommitted()) return;
                    if (status) {
                        Sessionutil.setAttribute(request, "success", "Property updated successfully.");
                    } else {
                        Sessionutil.setAttribute(request, "error", "Failed to update property.");
                    }

                    response.sendRedirect(request.getContextPath() + "/updatepropertycontroller?propertyId=" + propertyId);
                    return;

                case "deleteImage":
                	doDelete(request, response); // Reuse the logic
                    return;
                case "deleteAmenity":
                    doDelete(request, response); // Reuse the logic
                    return;
                default:
                    Sessionutil.setAttribute(request, "error", "Invalid form submission.");
                    response.sendRedirect(request.getContextPath() + "/propertydashboard");
                    return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Sessionutil.setAttribute(request, "error", "An error occurred. Please try again. " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/propertydashboard");
        }
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String formType = request.getParameter("formType");
            String propertyIdStr = request.getParameter("propertyId");

             if (formType == null || propertyIdStr == null) {
                Sessionutil.setAttribute(request, "error", "Missing formType or propertyId.");
                response.sendRedirect(request.getContextPath() + "/propertydashboard");
                return;
            }

             int propertyId = Integer.parseInt(propertyIdStr);
             PropertyImageDaoImpl propertyImageDao = new PropertyImageDaoImpl();
             AmenityDaoImpl amenityDao = new AmenityDaoImpl();

             switch (formType) {
                 case "deleteImage":
                     String imageIdStr = request.getParameter("imageId");

                     if (imageIdStr != null) {
                         int imageId = Integer.parseInt(imageIdStr);
                         try {
                             PropertyImagemodel image = propertyImageDao.findByImageId(imageId);
                             if (image != null) {
                                 String folder = "property";
                                 ImageUtil imgUtil = new ImageUtil();
                                 String imagePath = imgUtil.getSavePath(folder) + image.getFileName();

                                 File file = new File(imagePath);
                                 boolean fileDeleted = !file.exists() || file.delete();
                                 boolean dbDeleted = propertyImageDao.delete(imageId);

                                 if (fileDeleted && dbDeleted) {
                                     Sessionutil.setAttribute(request, "success", "Image deleted successfully.");
                                 } else {
                                     Sessionutil.setAttribute(request, "error", "Failed to delete image.");
                                 }
                             } else {
                                 Sessionutil.setAttribute(request, "error", "Image not found.");
                             }
                         } catch (Exception e) {
                             e.printStackTrace();
                             Sessionutil.setAttribute(request, "error", "Error deleting image: " + e.getMessage());
                         }
                     } else {
                         Sessionutil.setAttribute(request, "error", "Image ID not provided.");
                     }

                     response.sendRedirect(request.getContextPath() + "/updatepropertycontroller?propertyId=" + propertyId);
                     return;

                 case "deleteAmenity":
                     String amenityIdStr = request.getParameter("amenityId");

                     if (amenityIdStr != null) {
                         int amenityId = Integer.parseInt(amenityIdStr);
                         Property_Amenity propertyAmenity = new Property_Amenity(propertyId, amenityId);
                         boolean removed = amenityDao.deletePropertyAmenity(propertyAmenity);

                         if (removed) {
                             Sessionutil.setAttribute(request, "success", "Amenity removed successfully.");
                         } else {
                             Sessionutil.setAttribute(request, "error", "Failed to remove amenity.");
                         }
                     } else {
                         Sessionutil.setAttribute(request, "error", "Amenity ID not provided.");
                     }

                     response.sendRedirect(request.getContextPath() + "/updatepropertycontroller?propertyId=" + propertyId);
                     return;

                 default:
                     Sessionutil.setAttribute(request, "error", "Invalid formType for delete request.");
                     response.sendRedirect(request.getContextPath() + "/propertydashboard");
             }

         } catch (Exception e) {
             e.printStackTrace();
             Sessionutil.setAttribute(request, "error", "An error occurred: " + e.getMessage());
             response.sendRedirect(request.getContextPath() + "/propertydashboard");
         }
     }

}
