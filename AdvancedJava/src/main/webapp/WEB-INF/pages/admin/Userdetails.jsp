<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
 <%@ page import="com.Advancedjava.util.Sessionutil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Property Dashboard</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admin/Userdetails.css">
<script src="https://unpkg.com/lucide@latest"></script>


</head>
<body>
	<jsp:include page="/WEB-INF/pages/admin/Adminheader.jsp" />
	<div class="main-content">
		<div class="container">
		 <div class="card">
        <div class="profile-container">
            <div class="profile-image-container">
                
                   <c:choose>
                            <c:when test="${not empty user.userProfilePicture}">
                               <div class="profile-image">
                                <img src="${pageContext.request.contextPath}/resources/images/profile/${user.userProfilePicture}" alt="User Photo" class="user-photo">
                                <button class="photo-edit-btn" id="editPhotoBtn"
						aria-label="Edit profile photo" type="submit">
						<svg width="16" height="16" viewBox="0 0 24 24" fill="none"
							stroke="currentColor" stroke-width="2" stroke-linecap="round"
							stroke-linejoin="round">
                            <path
								d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z" />
                            <circle cx="12" cy="13" r="4" />
                        </svg>
					</button>
                               </div>
                            </c:when>
                            <c:otherwise>
                             <div class="profile-image">
                                <img src="${pageContext.request.contextPath}/resources/images/profile/default.png" alt="Default User Photo" class="user-photo">
                                <button class="photo-edit-btn" id="editPhotoBtn"
						aria-label="Edit profile photo" type="submit">
						<svg width="16" height="16" viewBox="0 0 24 24" fill="none"
							stroke="currentColor" stroke-width="2" stroke-linecap="round"
							stroke-linejoin="round">
                            <path
								d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z" />
                            <circle cx="12" cy="13" r="4" />
                        </svg>
					</button>
                                 </div>
                            </c:otherwise>
                        </c:choose>
                
            </div>
            <div class="profile-info-container">
                <div class="profile-info">
                    <h2>${user.userFirstName} ${user.userLastName}    </h2>#<span class="info-value">${user.userId}</span>
                    <p>
                        ${user.userRole}
                        <c:choose>
                            <c:when test="${user.userStatus == 'active'}">
                                <span class="active-status">Active</span>
                            </c:when>
                            <c:when test="${user.userStatus == 'inactive'}">
                                <span class="inactive-status">Inactive</span>
                            </c:when>
                            <c:when test="${user.userStatus == 'blocked'}">
                                <span class="blocked-status">Blocked</span>
                            </c:when>
                        </c:choose>
                    </p>
                    <p>${requestScope.location}</p>
                </div>
            </div>
        </div>
        
        <div class="section-header">
            <h2>Personal Information</h2>
           
        </div>
        
        <div class="info-grid">
            <div class="info-item">
                <span class="info-label">Email Address</span>
                <span class="info-value">${user.userEmail}</span>
            </div>
            <div class="info-item">
                <span class="info-label">Date of Birth</span>
                <span class="info-value">${user.userDob}</span>
            </div>
            
            <div class="info-item">
                <span class="info-label">Phone Number</span>
                <span class="info-value">${user.userPhnNo}</span>
            </div>
            <div class="info-item">
                <span class="info-label">User Role</span>
                <span class="info-value">${user.userRole}</span>
            </div>
            <div class="info-item">
                <span class="info-label">Username</span>
                <span class="info-value">${user.userName}</span>
            </div>
            <div class="info-item">
                <span class="info-label">Gender</span>
                <span class="info-value">${user.gender}</span>
            </div>
            
        </div>
    </div>

    
   <%
String error = (String) Sessionutil.getAttribute(request, "error");
if (error != null && !error.isEmpty()) {
%>
    <div class="error-message" id="errorMessage">
        <%= error %>
        <i data-lucide="x" class="close-icon" onclick="closeErrorMessage()"></i>
    </div>
<%
    Sessionutil.removeAttribute(request, "error");
}
%>

<%
String success = (String) Sessionutil.getAttribute(request, "success");
if (success != null && !success.isEmpty()) {
%>
    <div class="success-message" id="successMessage">
        <%= success %>
        <i data-lucide="x" class="close-icon" onclick="closeSuccessMessage()"></i>
    </div>
<%
    Sessionutil.removeAttribute(request, "success");
}
%>

    <script>
    // Auto-hide error and success messages after 5 seconds
    setTimeout(function() {
        const errorElement = document.getElementById('errorMessage');
        const successElement = document.getElementById('successMessage');

        if (errorElement) {
            errorElement.style.display = 'none';
            fetch('<%= request.getContextPath() %>/clearError', {
                method: 'POST',
                credentials: 'include'
            });
        }

        if (successElement) {
            successElement.style.display = 'none';
            fetch('<%= request.getContextPath() %>/clearSuccess', {
                method: 'POST',
                credentials: 'include'
            });
        }
    }, 10000);

    function closeErrorMessage() {
        const errorElement = document.getElementById('errorMessage');
        if (errorElement) {
            errorElement.style.display = 'none';
            fetch('<%= request.getContextPath() %>/clearError', {
                method: 'POST',
                credentials: 'include'
            });
        }
    }

    function closeSuccessMessage() {
        const successElement = document.getElementById('successMessage');
        if (successElement) {
            successElement.style.display = 'none';
            fetch('<%= request.getContextPath() %>/clearSuccess', {
                method: 'POST',
                credentials: 'include'
            });
        }
    }
</script>
<div class="header-section">
				<h1>Bookings</h1>
			</div>

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
							<th class="table-header">Property</th>
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
									<div class="hotel-image-gallery">
										<c:choose>
											<c:when test="${not empty properties[loop.index].images}">
												<img
													src="${pageContext.request.contextPath}/resources/images/property/${properties[loop.index].images[0].fileName}"
													alt="${properties[loop.index].propertyName}"
													onerror="this.src='${pageContext.request.contextPath}/resources/images/property/default-img.jpg'"
													class="main-image">
											</c:when>
											<c:otherwise>
												<img
													src="${pageContext.request.contextPath}/resources/images/property/default-img.jpg"
													alt="Default property image" class="main-image">
											</c:otherwise>
										</c:choose>
										
									</div>
								</td>
								<td class="table-cell" colspan="2">
									<div class="booking-dates">
						
										<div class="property-name">${properties[loop.index].propertyName}</div>
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
									
									<div class="booking-location">
										${properties[loop.index].propertyCity}, ${properties[loop.index].propertyCountry}
									</div>
									</div>
								</td>
								<td class="table-cell">
									<div class="booking-user">
										${users[loop.index].userFirstName} ${users[loop.index].userLastName}
									</div>
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
        Showing <span id="showingCount">1-${users.size() > 0 ? users.size() : 0}</span> of ${users.size()} users
    </div>
    <div class="pagination-controls" id="paginationControls"></div>
</div>
        </div>
        <jsp:include page="/WEB-INF/pages/admin/Adminfooter.jsp" />
    </div>
    
	<script>
    window.addEventListener('DOMContentLoaded', () => {
        lucide.createIcons();
    });
</script>
<script>
document.querySelectorAll('.thumbnail').forEach(thumb => {
    thumb.addEventListener('click', function() {
        const fullImageUrl = this.getAttribute('data-full-image');
        const mainImage = this.closest('.hotel-image-gallery').querySelector('.main-image');
        if (mainImage) {
            mainImage.src = fullImageUrl;
        }
    });
});
</script>
	<script>
document.addEventListener('DOMContentLoaded', function() {
    // Get all property rows and elements
    const allRows = document.querySelectorAll('.property-row');
    const paginationControls = document.getElementById('paginationControls');
    const showingCount = document.getElementById('showingCount');
    
    // Pagination configuration
    const itemsPerPage = 8;
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
                (currentPage == 1 && button.textContent == 'Previous') ||
                (currentPage == pageCount && button.textContent == 'Next') : 
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
    initPagination();
});
</script>

</body>
</html>