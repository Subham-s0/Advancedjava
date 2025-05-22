<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.Advancedjava.util.Sessionutil" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nestaway1</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin/Admindashboard.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>

<jsp:include page="/WEB-INF/pages/admin/Adminheader.jsp" />
<div class ="main-content">
 <div class="container">
  <% 
String error = (String) Sessionutil.getAttribute(request, "error");
if (error != null && !error.isEmpty()) { 
%>
    <div class="error-catch-message" id="errorMessage">
        <div><%= error %></div>
       <div class="close-icon"><i data-lucide="x" class="" onclick="closeErrorMessage()"></i></div> 
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

<div class="dashboard">
        <div class="dashboard-header">
            <h1 class="dashboard-title">Dashboard</h1>
        </div>

         
      

        <div class="stats-container">
            <div class="stat-card">
                <i class="fas fa-calendar-check icon"></i>
                <h3>Total Bookings</h3>
                <div class="value">${totalBookings}</div>
            </div>
            <div class="stat-card">
                <i class="fas fa-shopping-cart icon"></i>
                <h3>Total Purchases</h3>
                <div class="value">$${totalPayments}</div>
            </div>
            <div class="stat-card">
                <i class="fas fa-home icon"></i>
                <h3>Total Properties</h3>
                <div class="value">${totalProperties}</div>
            </div>
            <div class="stat-card">
                <i class="fas fa-users icon"></i>
                <h3>Total Users</h3>
                <div class="value">${totalUsers }</div>
            </div>
        </div>

<div class="sales-category-card">
<div class= "sales-by-category">
    <h2 class="sales-title">Sales by Category</h2>
    <div class="sales-content">
        <canvas id="salesPieChart" width="160" height="160"></canvas>

        <!-- Legend -->
        <div class="sales-legend">
            <ul>
                <c:set var="colors" value="#f87171,#60a5fa,#fbbf24,#34d399,#a78bfa,#f472b6,#818cf8,#facc15,#10b981" />
                <c:set var="colorList" value="${fn:split(colors, ',')}" />
                <c:forEach var="cat" items="${categoryCounts}" varStatus="loop">
                    <c:if test="${loop.index < 5}">
                        <li>
                            <span class="legend-dot" style="background:${colorList[loop.index]}"></span>
                            ${cat.categoryName}: ${cat.totalBookings} bookings
                        </li>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
    </div>

    <!-- Summary -->
    <div class="sales-summary">
        <div class="summary-box">
            <div class="summary-label">Top Category</div>
            <div class="summary-value">${topCategory}</div>
        </div>
        <div class="summary-box">
            <div class="summary-label">Total Bookings</div>
            <div class="summary-value">${totalCategoryBookings}</div>
        </div>
    </div>
    </div>
    <div class="table-container">
    <h2 style= "text-align:center; margin-bottom:7px">Recent 5 Booking</h2>
        <table class="user-booking-table">
            <thead>
                <tr>
                    <th>User</th>
                    <th>Check-In</th>
                    <th>Check-Out</th>
                    <th>Price</th>
                    <th>Status</th>
                </tr>
            </thead>
           <tbody>
  <c:forEach items="${recentBookings}" var="booking" varStatus="loop">
    <tr class=property-row>
      <td>
        <div class="user-info">
       <c:choose>
                                <c:when test="${not empty users[loop.index].userProfilePicture}">
                                    <img src="${pageContext.request.contextPath}/resources/images/profile/${users[loop.index].userProfilePicture}" alt="${users[loop.index].userFirstName}" class="user-image" style="width:35px; height:35px;"/>
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/resources/images/profile/default.png" alt="Default User Photo" class="user-image" />
                                </c:otherwise>
                            </c:choose>
                               </div>
        <span class="user-name">${users[loop.index].userFirstName} ${users[loop.index].userLastName}</span>
      </td>
      <td class="date-value"><fmt:formatDate value="${booking.checkInDate}" pattern="MMM dd, yyyy" /></td>
      <td class="date-value"><fmt:formatDate value="${booking.checkOutDate}" pattern="MMM dd, yyyy" /></td>
      <td class="booking-amount"><fmt:formatNumber value="${booking.totalPrice}" type="currency" currencySymbol="$" /></td>
      <td><span class="status-badge ${booking.bookingStatus}">${booking.bookingStatus}</span></td>
    </tr>
  </c:forEach>
</tbody>

        </table>
       
    </div>
 </div>
<div class="table-container">
<h2 style= "text-align:center; margin-bottom:7px">Top  5 Properties</h2>
				<table class="property-table">
					<thead>
						<tr>
							<th class="table-header">Photo</th>
							<th class="table-header">Property Info</th>
							<th class="table-header">Category</th>
							<th class="table-header">Host</th>
							<th class="table-header">Status</th>
							<th class="table-header">Price/Night</th>
							
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${topProperties}" var="property" varStatus="loop">
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
								<td class="table-cell"><c:forEach items="${categories}"
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
									
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

 </div>
 
  </div>
  
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    const pieLabels = [
        <c:forEach var="cat" items="${categoryCounts}" varStatus="loop">
            <c:if test="${loop.index < 5}">
                "${cat.categoryName}"<c:if test="${!loop.last && loop.index < 4}">,</c:if>
            </c:if>
        </c:forEach>
    ];

    const pieData = [
        <c:forEach var="cat" items="${categoryCounts}" varStatus="loop">
            <c:if test="${loop.index < 5}">
                ${cat.totalBookings}<c:if test="${!loop.last && loop.index < 4}">,</c:if>
            </c:if>
        </c:forEach>
    ];

    const pieColors = ['#f87171', '#60a5fa', '#fbbf24', '#34d399', '#a78bfa', '#f472b6'];

    const ctx = document.getElementById('salesPieChart').getContext('2d');
    new Chart(ctx, {
        type: 'pie',
        data: {
            labels: pieLabels,
            datasets: [{
                data: pieData,
                backgroundColor: pieColors.slice(0, pieLabels.length),
                borderWidth: 0
            }]
        },
        options: {
            plugins: {
                legend: { display: false }
            }
        }
    });
</script>
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
  
</body>
<jsp:include page="/WEB-INF/pages/admin/Adminfooter.jsp" />

</html>