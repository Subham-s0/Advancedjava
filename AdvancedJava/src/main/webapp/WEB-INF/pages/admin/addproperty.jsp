<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Add Property - Step 1</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admin/addproperty.css">
</head>
<body>
	<jsp:include page="/WEB-INF/pages/admin/Adminheader.jsp" />

	<div class="main-content">
	<div class="container">
	 <%String error = (String) request.getAttribute("error"); %>
		<div class="heading">Add Property</div>
		<div class="step-indicator">
			<div class="step-indicator">
				<div class="steps">
					<div class="step <%= (error != null && !error.isEmpty()) ? "not-completed" : "active" %>">
						<div class="step-circle">1</div>
						<p class="step-label">Property Details</p>
					</div>
					<div class="step-line "></div>
					<div class="step ">
						<div class="step-circle">2</div>
						<p class="step-label">Add Images</p>
					</div>
					<div class="step-line"></div>
					<div class="step">
						<div class="step-circle">3</div>
						<p class="step-label">Add Amenities</p>
					</div>
				</div>
			</div>
			</div>


			<div class="add-property">
			  
                <div class="error-message <%= (error != null && !error.isEmpty()) ? "visible" : "" %>">
					   <c:if test="${not empty error}">
			        <p style="color: red;"><c:out value="${error}"/></p>
			    </c:if>
			
			    </div>
			    <div class="success-message">
				    <%
				String success = (String) request.getAttribute("success");
				if (success != null && !success.isEmpty()) {
				%>
				<p style="color: green;"><%= success %></p>
				<%
				}
				%>
				</div>
				
				<form action="AddPropertyController" method="POST"
					class="property-form">
					<div class="form-grid">
						<div class="form-group">
							<label for="name">Property Name</label> <input type="text"
								id="name" name="name">
						</div>

						<div class="form-group">
							<label for="address">Address</label> <input type="text"
								id="address" name="address">
						</div>

						<div class="form-group">
							<label for="city">City</label> <input type="text" id="city"
								name="city">
						</div>

						<div class="form-group ">
							<label for="country">Country</label> <input type="text"
								id="country" name="country">
						</div>

						<div class="form-group ">
							<label for="hostName">Host Name</label> <input type="text"
								id="hostName" name="hostName">
						</div>

						<div class="form-group ">
							<label for="category">Category</label> <select id="category"
								name="category">

								<option value="0">choose any</option>
								<c:choose>
									<c:when test="${not empty categories}">
										<c:forEach items="${categories}" var="category">
											<option value="${category.categoryId}">${category.categoryName}</option>
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
						    <label for="maximumGuests">Maximum Guests</label>
						    <input type="number" id="maximumGuests" name="maximumGuests" min="1" step="1">
						  </div>
						  <div>
						    <label for="totalBedrooms">Total Bedrooms</label>
						    <input type="number" id="totalBedrooms" name="totalBedrooms" min="0" step="1">
						  </div>
						  <div>
						    <label for="totalBath">Total Bathrooms</label>
						    <input type="number" id="totalBath" name="totalBath" min="0" step="1">
						  </div>
						  <div>
						    <label for="totalRooms">Total Rooms</label>
						    <input type="number" id="totalRooms" name="totalRooms" min="0" step="1">
						  </div>
						</div>

						<div class="form-group ">
							<label for="pricePerNight">Price Per Night ($)*</label> <input
								type="number" id="pricePerNight" name="pricePerNight" min="1"
								step="0.01" max="99999999.99">
						</div>
						<div class="form-group">
							<label for="taxRate">Tax Rate (%)</label> <input type="number"
								id="taxRate" name="taxRate" min="0" max="30" step="1"
								>
						</div>

						<div class="form-group ">
							<label for="ServiceCharge">Service Charge </label>
							 <input
								type="number" id="ServiceCharge" name="ServiceCharge" min="1"
								step="1">
						</div>

						<div class="form-group">
							<label for="cleaningFee">Cleaning Fee ($)</label> <input
								type="number" id="cleaningFee" name="cleaningFee" min="0"
								step="1" >
						</div>
						

						<div class="form-group full-width">
							<label for="description">Description</label>
							<textarea id="description" name="description" rows="4"></textarea>
						</div>

						<div class="form-buttons">
							<button type="submit" class="btn-next">Submit →</button>
						</div>
						</div>
				</form>
			</div>
			</div>
			<jsp:include page="/WEB-INF/pages/admin/Adminfooter.jsp" />
		</div>
		
</body>
</html>