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
	href="${pageContext.request.contextPath}/css/admin/Userdashboard.css">
<script src="https://unpkg.com/lucide@latest"></script>


</head>
<body>
	<jsp:include page="/WEB-INF/pages/admin/Adminheader.jsp" />
	<div class="main-content">
		<div class="container">
		<div class="header-section">
				<h1>Users</h1>
				
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

	<!-- Filters -->
<div class="filter-section">
    <form id="filterForm" action="${pageContext.request.contextPath}/usersdashboard" method="get">
      
        
        <select class="filter-control" name="status">
            <option value="" ${empty param.status ? 'selected' : ''}>All Statuses</option>
            <option value="active" ${param.status == 'active' ? 'selected' : ''}>Active</option>
            <option value="inactive" ${param.status == 'inactive' ? 'selected' : ''}>Inactive</option>
            <option value="blocked" ${param.status == 'blocked' ? 'selected' : ''}>Blocked</option>
        </select>

        <button type="submit" class="filter-button">Filter</button>
    </form>
</div>

<!-- Users Table -->
<div class="table-container">
    <table class="property-table">
        <thead>
            <tr>
                <th class="table-header">Photo</th>
                <th class="table-header">User ID</th>
                <th class="table-header">Full Name</th>
                <th class="table-header">Username</th>
                <th class="table-header">Email</th>
                <th class="table-header">Total Orders</th>
                <th class="table-header">User Role</th>
                <th class="table-header">Status</th>
                <th class="table-header">Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}" var="user" varStatus="status">
                <tr class="property-row">
                   
                    <td class="table-cell">${user.userId}</td>
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
                    <td class="table-cell">${user.userFirstName} ${user.userLastName}</td>
                    <td class="table-cell">${user.userName}</td>
                    <td class="table-cell">${user.userEmail}</td>
                    <td class="table-cell">${bookingCounts[status.index]}</td>
                    <td class="table-cell">${user.userRole}</td>
                    <td class="table-cell">
                        <span class="status-badge 
                            ${user.userStatus == 'active' ? 'status-available' : 
                            (user.userStatus == 'inactive' ? 'status-inactive' : 'status-not-available')}">
                            ${user.userStatus}
                        </span>
                    </td>
                    <td class="table-cell">
                      <c:if test="${user.userRole ne 'admin'}">
					    <div class="action-buttons">
					        <a href="${pageContext.request.contextPath}/usersdetails?userId=${user.userId}" class="details-btn">Details</a>
					        <form id="toggleForm${user.userId}" onsubmit="return(are you sure you want to block this user?)" action="${pageContext.request.contextPath}/usersdashboard" method="post" style="display: inline;">
					            <input type="hidden" name="action" value="delete">
					            <input type="hidden" name="userId" value="${user.userId}">
					            <input type="hidden" name="currentStatus" value="${user.userStatus}">
					            <button type="submit"  class="block-btn">
					                ${user.userStatus == 'active' ? 'Block' : 'Unblock'}
					            </button>
					        </form>
					    </div>
					</c:if>
                    </td>
                </tr>
            </c:forEach>

            <c:if test="${empty users}">
                <tr>
                    <td colspan="9" class="table-cell" style="text-align: center;">No users found.</td>
                </tr>
            </c:if>
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