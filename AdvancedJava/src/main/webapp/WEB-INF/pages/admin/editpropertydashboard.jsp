<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ page import="com.Advancedjava.util.Sessionutil" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NESTAWAY</title>
</head>
<link rel="stylesheet"href="${pageContext.request.contextPath}/css/admin/editpropertydetails.css">
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
		
		
			<div class="header-section">
				<h1>Bookings</h1>
			</div>

			<!-- Filters -->
			<div class="filter-section">
				<form id="filterForm" action="${pageContext.request.contextPath}/bookingsdashboard" method="GET">
					<select class="filter-control" name="status">
						<option value="">All Statuses</option>
						<option value="confirmed">Confirmed</option>
						<option value="pending">Pending</option>
						<option value="cancelled">Cancelled</option>
						
					</select>

					<button type="submit" class="filter-button">Filter</button>
				</form>
			</div>

			<!-- Bookings Table -->
			<div class="table-container">
				<table class="property-table">
					<thead>
						<tr>
							<th class="table-header">Profile</th>
							<th class="table-header">Username</th>
							<th class="table-header" colspan="2">Booking Details</th>
							<th class="table-header">Guest</th>
							<th class="table-header">Status</th>
							<th class="table-header">Total Amount</th>
							<th class="table-header">Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${bookingList}" var="booking" varStatus="loop">
							<tr class="booking-row" data-page="${loop.index / 5 + 1}">
								<td class="table-cell">
								
								
								
									<c:choose>
                            <c:when test="${not empty user.userProfilePicture}">
                               <div class="img-profile">
                                <img src="${pageContext.request.contextPath}/resources/images/profile/${user.userProfilePicture}" alt="User Photo" class="user-photo">
                               </div>
                            </c:when>
                            <c:otherwise>
                             <div class="img-profile">
                                <img src="${pageContext.request.contextPath}/resources/images/profile/default.png" alt="Default User Photo" class="user-photo">
                                 </div>
                            </c:otherwise>
                        </c:choose>
							
								</td>
								<td class="table-cell">
									
									<div class="booking-user">
										${users[loop.index].userFirstName} ${users[loop.index].userLastName}
									</div>
								</td>
								<td class="table-cell" colspan="2">
									<div class="booking-dates">
						
										
											<div class="booking-datesinside">
  <span class="date-label">
    <i class="fas fa-calendar-alt"></i>
  </span>
  <span class="date-value">
    <fmt:formatDate value="${booking.checkInDate}" pattern="MMM dd, yyyy" />
  </span>

  <div><i class="fas fa-arrow-right"></i></div>

  <div>
    <span class="date-label">
      <i class="fas fa-calendar-check"></i> 
    </span>
    <span class="date-value">
      <fmt:formatDate value="${booking.checkOutDate}" pattern="MMM dd, yyyy" />
    </span>
  </div>
</div>
									
									
									</div>
								</td>
								<td class="table-cell">
									
									<div class="booking-guests">
										<i data-lucide="users" class="meta-icon"></i> ${booking.numberOfGuests} <p>Guests</p>
									</div>
								</td>
								
								<td class="table-cell">
									<span class="status-badge status-${booking.bookingStatus}">
										${booking.bookingStatus}
									</span>
								</td>
								<td class="table-cell">
									<div class="booking-amount">
										<fmt:formatNumber value="${booking.totalPrice}" type="currency" currencySymbol="$" />
									</div>
								</td>
								<td class="table-cell">
									<form class="update-status-form" action="${pageContext.request.contextPath}/bookingsdashboard" method="POST">
										<input type="hidden" name="formType" value="updateStatus">
										<input type="hidden" name="bookingId" value="${booking.bookingId}">
										<select name="newStatus">
											<option value="pending" ${booking.bookingStatus == 'pending' ? 'selected' : ''}>Pending</option>
											<option value="confirmed" ${booking.bookingStatus == 'confirmed' ? 'selected' : ''}>Confirmed</option>
											<option value="cancelled" ${booking.bookingStatus == 'cancelled' ? 'selected' : ''}>Cancelled</option>
										
										</select>
										<button type="submit" class="update-btn">Update</button>
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<!-- Pagination -->
			<div class="pagination-container">
				<div class="pagination-info">
					Showing <span id="showingCount">5</span> of ${fn:length(bookingList)} bookings
				</div>
				<div class="pagination-controls" id="paginationControls"></div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/pages/admin/Adminfooter.jsp" />
	
	<script>
    window.addEventListener('DOMContentLoaded', () => {
        lucide.createIcons();
    });
</script>
	<script>
document.addEventListener('DOMContentLoaded', function() {
    // Get all booking rows and elements
    const allRows = document.querySelectorAll('.property-row');
    const paginationControls = document.getElementById('paginationControls');
    const showingCount = document.getElementById('showingCount');
    
    // Pagination configuration
    const itemsPerPage = 5;
    let currentPage = 1;
    const totalItems = allRows.length;
    const pageCount = Math.ceil(totalItems / itemsPerPage);

    // Initialize pagination
    function initPagination() {
        // Clear existing controls
        paginationControls.innerHTML = '';

        // Create Previous button
        const prevButton = document.createElement('button');
        prevButton.className = 'pagination-btn';
        prevButton.innerHTML = '&laquo; Previous';
        prevButton.addEventListener('click', () => changePage(currentPage - 1));
        paginationControls.appendChild(prevButton);

        // Create page number buttons
        for(let i = 1; i <= pageCount; i++) {
            const pageButton = document.createElement('button');
            pageButton.className = `pagination-btn ${i == 1 ? 'active' : ''}`;
            pageButton.textContent = i;
            pageButton.addEventListener('click', () => changePage(i));
            paginationControls.appendChild(pageButton);
        }

        // Create Next button
        const nextButton = document.createElement('button');
        nextButton.className = 'pagination-btn';
        nextButton.innerHTML = 'Next &raquo;';
        nextButton.addEventListener('click', () => changePage(currentPage + 1));
        paginationControls.appendChild(nextButton);

        // Show initial page
        updateVisibleItems();
        updatePaginationButtons();
    }

    // Change current page
    function changePage(newPage) {
        currentPage = Math.max(1, Math.min(newPage, pageCount));
        updateVisibleItems();
        updatePaginationButtons();
        updateShowingCount();
    }

    // Update visible items
    function updateVisibleItems() {
        const startIndex = (currentPage - 1) * itemsPerPage;
        const endIndex = startIndex + itemsPerPage;

        allRows.forEach((row, index) => {
            row.style.display = (index >= startIndex && index < endIndex) ? '' : 'none';
        });
    }

    // Update pagination buttons state
    function updatePaginationButtons() {
        const buttons = paginationControls.querySelectorAll('.pagination-btn');
        
        buttons.forEach(button => {
            const pageNumber = parseInt(button.textContent);
            button.classList.toggle('active', pageNumber == currentPage);
            button.disabled = isNaN(pageNumber) ? 
                (currentPage == 1 && button.textContent.includes('Previous')) ||
                (currentPage == pageCount && button.textContent.includes('Next')) : 
                false;
        });
    }

    // Update showing count
    function updateShowingCount() {
        const start = (currentPage - 1) * itemsPerPage + 1;
        const end = Math.min(currentPage * itemsPerPage, totalItems);
        showingCount.textContent = `${start}-${end}`;
    }

    // Initial setup
    if (pageCount > 0) {
        initPagination();
    }
});
</script>
		
	<script>
		lucide.createIcons();
	</script>
</body>
</html>