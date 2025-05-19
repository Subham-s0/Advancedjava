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
	href="${pageContext.request.contextPath}/css/admin/propertydashboard.css">
<script src="https://unpkg.com/lucide@latest"></script>


</head>
<body>
	<jsp:include page="/WEB-INF/pages/admin/Adminheader.jsp" />
	<div class="main-content">
		<div class="container">
	  <% 
String error = (String) Sessionutil.getAttribute(request, "error");
if (error != null && !error.isEmpty()) { 
%>
    <div class="error-catch-message" id="errorMessage">
        <%= error %>
        <i data-lucide="x" class="close-icon" onclick="closeErrorMessage()"></i>
    </div>
    <script>
        // Auto-hide error message after 5 seconds
        setTimeout(function() {
            document.getElementById('errorMessage').style.display = 'none';
            // Clear the error from session
            fetch('<%= request.getContextPath() %>/clearError', {
                method: 'POST',
                credentials: 'include'
            });
        }, 5000);
        
        function closeErrorMessage() {
            document.getElementById('errorMessage').style.display = 'none';
            // Clear the error from session
            fetch('<%= request.getContextPath() %>/clearError', {
                method: 'POST',
                credentials: 'include'
            });
        }
    </script>
<%
    // Clear the error message from session immediately after displaying
    Sessionutil.removeAttribute(request, "error");
}
%>
	  
			<div class="header-section">
				<h1>Properties</h1>
				<a href="${pageContext.request.contextPath}/AddPropertyController"
					class="add-button"> <i data-lucide="plus"></i> Add Property
				</a>
			</div>

			<!-- Filters -->
			<div class="filter-section">
				<form id="filterForm">
					<select class="filter-control" name="status">
						<option value="">All Statuses</option>
						<c:forEach items="${statuses}" var="status">
							<option value="${status}"
								${param.status == status ? 'selected' : ''}>${status}</option>
						</c:forEach>
					</select>
					 <select class="filter-control" name="category">
						
						<c:forEach items="${categories}" var="category">
							<option value="${category.categoryId}"
								${param.category == category.categoryId ? 'selected' : ''}>
								${category.categoryName}</option>
						</c:forEach>
					</select>

					<button type="submit" class="filter-button">Filter</button>
				</form>

			</div>

			<!-- Properties Table -->
			<div class="table-container">
				<table class="property-table">
					<thead>
						<tr>
							<th class="table-header">Photo</th>
							<th class="table-header">Property Info</th>
							<th class="table-header">Category</th>
							<th class="table-header">Host</th>
							<th class="table-header">Status</th>
							<th class="table-header">Price/Night</th>
							<th class="table-header">Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${properties}" var="property" varStatus="loop">
							<tr class="property-row" data-page="${loop.index / 5 + 1}">
								<td class="table-cell">
									<div class="hotel-image-gallery">
										<c:choose>
											<c:when test="${not empty property.images}">
												<img
													src="${pageContext.request.contextPath}/resources/images/property/${property.images[0].fileName}"
													alt="${property.propertyName}"
													onerror="this.src='${pageContext.request.contextPath}/resources/images/property/default-img.jpg'"
													class="main-image">

												<div class="thumbnail-scrollbar">
													<div class="thumbnails-container">
														<c:forEach items="${property.images}" var="image">
															<img class="thumbnail"
																src="${pageContext.request.contextPath}/resources/images/property/${image.fileName}"
																data-full-image="${pageContext.request.contextPath}/resources/images/property/${image.fileName}"
																onerror="this.src='${pageContext.request.contextPath}/resources/images/property/default-img.jpg'">
														</c:forEach>
													</div>
												</div>
											</c:when>
											<c:otherwise>
												<img
													src="${pageContext.request.contextPath}/resources/images/property/default-img.jpg"
													alt="Default property image" class="main-image">
											</c:otherwise>
										</c:choose>
									</div>


								</td>
								<td class="table-cell">
									<div class="property-info">
										<div class="property-name">${property.propertyName}</div>
										<div class="property-location">${property.propertyCity},
											${property.propertyCountry}</div>
										<div class="property-rooms">
											<div class="meta-item">
												<span class="meta-label">Beds</span>
												<div class="meta-value">
													<i class="meta-icon" data-lucide="bed"></i> <span>${property.totalBedrooms}</span>
												</div>
											</div>

											<div class="meta-item">
												<span class="meta-label">Baths</span>
												<div class="meta-value">
													<i class="meta-icon" data-lucide="bath"></i> <span>${property.totalBath}</span>
												</div>
											</div>

											<div class="meta-item">
												<span class="meta-label">Guests</span>
												<div class="meta-value">
													<i class="meta-icon" data-lucide="users"></i> <span>${property.maximumGuests}</span>
												</div>
											</div>
										</div>
									</div>
								</td>
								<td class="table-cell">
								<c:forEach items="${categories}"
										var="cat">
										<c:if test="${cat.categoryId == property.categoryId}">
											<span class="category-tag"><i class="meta-icon"
												data-lucide="${cat.categoryIcon}"></i> ${cat.categoryName} </span>
										</c:if>
									</c:forEach></td>
								<td class="table-cell">${property.hostName}</td>
								<td class="table-cell"><span
									class="status-badge ${property.propertyStatus == 'AVAILABLE' ? 'status-available' : 
                                'NOT AVAILABLE'}">
										${property.propertyStatus} </span></td>
								<td class="table-cell"><fmt:formatNumber
										value="${property.pricePerNight}" type="currency"
										currencySymbol="$" /></td>
								<td class="table-cell">
									<div class="action-buttons">
										<a
											href="${pageContext.request.contextPath}/updatepropertycontroller?propertyId=${property.propertyId}"
											class="details-btn"> Details </a>
										<form
											action="${pageContext.request.contextPath}/DeletePropertyController"
											method="POST"
											onsubmit="return confirm('Are you sure you want to delete this property?');">
											<input type="hidden" name="propertyId"
												value="${property.propertyId}">
											<button type="submit" class="delete-btn">Delete</button>
										</form>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<!-- Pagination -->
			<div class="pagination-container">
				<div class="pagination-info">
					Showing <span id="showingCount">7</span> of
					${fn:length(properties)} properties
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
    const itemsPerPage = 7;
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