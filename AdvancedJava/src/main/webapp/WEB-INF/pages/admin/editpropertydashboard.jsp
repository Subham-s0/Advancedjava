<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ page import="com.Advancedjava.util.Sessionutil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NESTAWAY</title>
</head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admin/editpropertydetails.css">
<script src="https://unpkg.com/lucide@latest"></script>

<body>
	<jsp:include page="/WEB-INF/pages/admin/Adminheader.jsp" />

	<div class="main-content">
		<div class="heading">Property Details</div>
		<div class="container">
			<%
			String error = (String) session.getAttribute("error");
			%>
			<%
Integer propertyId = (Integer) request.getAttribute("propertyId"); // Get as Integer from attribute

// If not found in attributes, check parameter
if (propertyId == null) {
    String paramValue = request.getParameter("propertyId");
    try {
        if (paramValue != null && !paramValue.equalsIgnoreCase("null")) {
            propertyId = Integer.parseInt(paramValue);
        }
    } catch (NumberFormatException e) {
        // Handle invalid parameter format
        request.setAttribute("error", "Invalid property ID format");
        propertyId = -1; // Default error value
    }
}

// Now use propertyId safely as Integer
int current_propertyId = (propertyId != null) ? propertyId : -1;
%>



			<div class="add-property">

				<div
					class="error-message <%=(error != null && !error.isEmpty()) ? "visible" : ""%>">
					<c:if test="${not empty error}">
						<p style="color: red;">
							<c:out value="${error}" />
						</p>
					</c:if>

				</div>
				 <%
        String success = (String) session.getAttribute("success");
    %>
    <div class="success-message <%= (success != null && !success.isEmpty()) ? "visible" : "" %>">
        <%
            if (success != null && !success.isEmpty()) {
        %>
            <p><%= success %></p>
        <%
            }
            Sessionutil.removeAttribute(request, "success");
            Sessionutil.removeAttribute(request, "error");
        %>
    </div>

				<form action="updatepropertycontroller" method="POST"
					class="property-form">
					<input type="hidden" name="propertyId" value="<%= current_propertyId %>" />
					<input type="hidden" name="formType" value="propertydetails" />
					<div class="form-grid">
						<div class="form-group">
							<label for="name">Property Name</label> <input type="text"
								id="name" name="name" value="${property.propertyName }">
						</div>

						<div class="form-group">
							<label for="address">Address</label> <input type="text"
								id="address" name="address" value="${property.propertyAddress }">
						</div>

						<div class="form-group">
							<label for="city">City</label> <input type="text" id="city"
								name="city" value="${property.propertyCity}">
						</div>

						<div class="form-group ">
							<label for="country">Country</label> <input type="text"
								id="country" name="country" value="${property.propertyCountry }">
						</div>

						<div class="form-group ">
							<label for="hostName">Host Name</label> <input type="text"
								id="hostName" name="hostName" value="${property.hostName }">
						</div>

						<div class="form-group ">
							<label for="category">Category</label> <select id="category"
								name="category">

								<option value="0">choose any</option>
								<c:choose>
									<c:when test="${not empty categories}">
										<c:forEach items="${categories}" var="category">
											<option value="${category.categoryId}"
												<c:if test="${category.categoryId == property.categoryId}">selected</c:if>>
												${category.categoryName}</option>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<option value="" disabled>No categories available</option>
									</c:otherwise>
								</c:choose>
							</select>
						</div>
						<div class="form-group full-width four-cols">
							<div>
								<label for="maximumGuests">Maximum Guests</label> <input
									type="number" id="maximumGuests" name="maximumGuests" min="1"
									step="1" value="${property.maximumGuests }">
							</div>
							<div>
								<label for="totalBedrooms">Total Bedrooms</label> <input
									type="number" id="totalBedrooms" name="totalBedrooms" min="0"
									step="1" value="${property.totalBedrooms }">
							</div>
							<div>
								<label for="totalBath">Total Bathrooms</label> <input
									type="number" id="totalBath" name="totalBath" min="0" step="1"
									value="${property.totalBath }">
							</div>
							<div>
								<label for="totalRooms">Total Rooms</label> <input type="number"
									id="totalRooms" name="totalRooms" min="0" step="1"
									value="${property.totalRooms }">
							</div>
						</div>

						<div class="form-group ">
							<label for="pricePerNight">Price Per Night ($)*</label> <input
								type="number" id="pricePerNight" name="pricePerNight" min="1"
								step="0.01" max="99999999.99" value="${property.pricePerNight }">
						</div>
						<div class="form-group">
							<label for="taxRate">Tax Rate (%)</label> <input type="number"
								id="taxRate" name="taxRate" min="0" max="30" step="1"
								value="${property.taxRate}">
						</div>

						<div class="form-group ">
							<label for="ServiceCharge">Service Charge </label> <input
								type="number" id="ServiceCharge" name="ServiceCharge" min="1"
								step="1" value="${property.serviceFee}">
						</div>

						<div class="form-group">
							<label for="cleaningFee">Cleaning Fee ($)</label> <input
								type="number" id="cleaningFee" name="cleaningFee" min="0"
								step="1" value="${property.cleaningFee}">
						</div>


						<div class="form-group full-width">
							<label for="description">Description</label>
							<textarea id="description" name="description" rows="4">${property.propertyDescription}</textarea>
						</div>

						<div class="form-buttons">
							<button type="submit" class="btn-submit">Submit →</button>
						</div>
					</div>
				</form>
			</div>
			<div class="property-sidebar">
				<!-- Image Upload -->
				<div class="image-section">
					<h1>Property Images</h1>
					<div class="image-upload-preview">

						<c:choose>
							<c:when test="${not empty property.images}">
								<div id="imageList">
									<c:forEach var="img" items="${property.images}">
										<form action="updatepropertycontroller"  method="POST">
										<input type="hidden" name="formType" value="deleteImage" />
										 <input type="hidden" name="imageId" value="${img.imageId}"/>
										<input type="hidden" name="propertyId" value="<%= current_propertyId %>" />
											<div class="img-card-horizontal">
												<div style="position: relative;">
													<img
														src="${pageContext.request.contextPath}/resources/images/property/${img.fileName}"
														alt="${property.propertyName}"
														onerror="this.src='${pageContext.request.contextPath}/resources/images/property/default-img.jpg'">

													<div class="img-name">${img.fileName}</div>
													<button type="submit" class="remove-img-btn-horizontal"
														onclick="return confirm('Do you want to Delete this  Image?')">
														<i data-lucide="circle-minus"></i>
													</button>

												</div>
											</div>
										</form>
									</c:forEach>
								</div>
							</c:when>
							<c:otherwise>
								<div id="imageList">
									<div class="img-card-horizontal">
										<div style="position: relative;">
											<img
												src="${pageContext.request.contextPath}/resources/images/property/default-img.jpg"
												alt="Default property image">

											<div class="img-name">Default Image</div>
										</div>
										<button type="button" class="remove-img-btn-horizontal"
											onclick="return confirm('Do you want to Delete this  amenity?')">
											<i data-lucide="x"></i>
										</button>
									</div>
								</div>
							</c:otherwise>
						</c:choose>
					<a href="addpropertyimages?propertyId=${property.propertyId}" class="uplaod-button"  >Add Images</a>
					</div>
				</div>

				<!-- Facilities -->
				<div class="facility-section">
					<h3>Amenities/Facilities</h3>
					<div class="facilities-list">
						<c:forEach items="${propertyAmenities}" var="amenity">
							<form action="updatepropertycontroller"  method="POST">
							<input type="hidden" name="formType" value="deleteAmenity" />
								<div class="amenity-item">
								<input type="hidden" name="propertyId" value="<%= current_propertyId %>" />
									<input type="hidden" name="amenityId" value="${amenity.amenityId}" /> <span><i
										data-lucide="${amenity.amenityIcon}" class="amenity-icon"></i></span>
									<span class="amenity-name">${amenity.amenityName}</span>
									<button type="submit" class="amenity-remove-btn"
										onclick="return confirm('Do you want to Delete this  amenity?')">
										<i data-lucide="circle-minus"></i>
									</button>
								</div>
							</form>
						</c:forEach>
							
					</div>
					<a href="addamenities?propertyId=${property.propertyId}" class="uplaod-button"  >Add Amenities</a>
				</div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/pages/admin/Adminfooter.jsp" />
	</div>
	<script>
		lucide.createIcons();
	</script>
</body>
</html>