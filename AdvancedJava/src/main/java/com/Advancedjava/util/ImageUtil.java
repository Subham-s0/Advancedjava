package com.Advancedjava.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;

/**
 * Utility class for handling image file uploads.
 * <p>
 * This class provides methods for extracting the file name from a {@link Part}
 * object and uploading the image file to a specified directory on the server.
 * </p>
 */
public class ImageUtil {
	private static final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2MB
	private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".jpg", ".jpeg", ".png", ".gif");
	private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif");

	/**
	 * Extracts the file name from the given {@link Part} object based on the
	 * "content-disposition" header.
	 * 
	 * <p>
	 * This method parses the "content-disposition" header to retrieve the file name
	 * of the uploaded image. If the file name cannot be determined, a default name
	 * "download.png" is returned.
	 * </p>
	 * 
	 * @param part the {@link Part} object representing the uploaded file.
	 * @return the extracted file name. If no filename is found, returns a default
	 *         name "download.png".
	 */
	public String getImageNameFromPart(Part part) {
		// Retrieve the content-disposition header from the part
		String contentDisp = part.getHeader("content-disposition");

		// Split the header by semicolons to isolate key-value pairs
		String[] items = contentDisp.split(";");

		// Initialize imageName variable to store the extracted file name
		String imageName = null;

		// Iterate through the items to find the filename
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				// Extract the file name from the header value
				imageName = s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}

		// Check if the filename was not found or is empty
		if (imageName == null || imageName.isEmpty()) {
			// Assign a default file name if none was provided
			imageName = "download.png";
		}

		// Return the extracted or default file name
		return imageName;
	}

	/**
	 * Uploads the image file from the given {@link Part} object to a specified
	 * directory on the server.
	 * 
	 * <p>
	 * This method ensures that the directory where the file will be saved exists
	 * and creates it if necessary. It writes the uploaded file to the server's file
	 * system. Returns {@code true} if the upload is successful, and {@code false}
	 * otherwise.
	 * </p>
	 * 
	 * @param part the {@link Part} object representing the uploaded image file.
	 * @return {@code true} if the file was successfully uploaded, {@code false}
	 *         otherwise.
	 */
	public boolean uploadImage(Part part, String rootPath, String saveFolder, String customFileName) {
		String savePath = getSavePath(saveFolder);
		File fileSaveDir = new File(savePath);

		// Ensure the directory exists
		if (!fileSaveDir.exists()) {
			if (!fileSaveDir.mkdirs()) { // Changed to mkdirs() to support nested folders
				return false;
			}
		}

		try {
			// Get the original file extension
			String originalFileName = getImageNameFromPart(part);
			String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
			if (customFileName.contains(".")) {
				customFileName = customFileName.substring(0, customFileName.lastIndexOf("."));
			}

			// Compose full file path with custom filename + original extension
			String fullFilePath = savePath + File.separator + customFileName + fileExtension;

			// Save the file
			part.write(fullFilePath);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getSavePath(String saveFolder) {
		return "C:/Users/Acer/git/Advancedjava/AdvancedJava/src/main/webapp/resources/images/" + saveFolder + "/";
	}

	public void validateImage(Part imagePart) throws ServletException {
		if (imagePart == null || imagePart.getSize() == 0) {
			throw new ServletException("No image uploaded.");
		}

		String fileName = getImageNameFromPart(imagePart).toLowerCase();

		boolean hasValidExtension = ALLOWED_EXTENSIONS.stream().anyMatch(fileName::endsWith);
		if (!hasValidExtension) {
			throw new ServletException("Invalid file extension. Allowed: .jpg, .jpeg, .png, .gif");
		}

		String contentType = imagePart.getContentType();
		if (!ALLOWED_MIME_TYPES.contains(contentType)) {
			throw new ServletException("Invalid MIME type: " + contentType);
		}

		if (imagePart.getSize() > MAX_FILE_SIZE) {
			throw new ServletException("Image file too large. Max size is 2MB.");
		}
	}
}
