<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Property - Step 3</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/addamenities.css">
    <script src="https://unpkg.com/lucide@latest"></script>
</head>
<body>
    <jsp:include page="/WEB-INF/pages/admin/Adminheader.jsp" />
    <div class="main-content">
	<div class="container">
  <div class="heading">Add Ameniity  </div>
   <%String error = (String) request.getAttribute("error"); %>
     
   <%
    String propertyId = (String) request.getParameter("propertyId");
   if ("null".equalsIgnoreCase(propertyId)) {
       propertyId = null;
   }
%>
    <div class="step-indicator">
			<div class="step-indicator">
				<div class="steps ">
					<div class="step <%= (propertyId != null && !propertyId.isEmpty()) ? "completed" : "not-completed" %>">
						<div class="step-circle">1</div>
						<p class="step-label">Property Details</p>
					</div>
					<div class="step-line active "></div>
					<div class="step active">
						<div class="step-circle">2</div>
						<p class="step-label">Add Images</p>
					</div>
					<div class="step-line active"></div>
					<div class="step active">
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
				    <%String success = (String) session.getAttribute("success");
				if (success != null && !success.isEmpty()) {
				%>
				<p style="color: green;"><%= success %></p>
				 <% session.removeAttribute("success"); %>
				<%
				
				}
				%>
				  <% session.removeAttribute("success"); %>
				</div>
            <h1>Select Amenities</h1>
            
            <form class="property-form" action="addamenities" method="POST">
    <input type="hidden" name="propertyId" value="<%= propertyId %>" />
    <div class="amenities-grid">
      <c:forEach items="${amenities}" var="amenity">
    <c:if test="${!existingAmenityIdsSet.contains(amenity.amenityId)}">
        <label class="amenity-item">
            <input type="checkbox" 
                name="amenityIds" 
                value="${amenity.amenityId}" />
            <i data-lucide="${amenity.amenityIcon}" class="amenity-icon"></i>
            <span class="amenity-name">${amenity.amenityName}</span>
        </label>
    </c:if>
</c:forEach>

    </div>

    <div class="form-buttons">
        <a href="propertydashboard" class="btn-back">← Back</a>
        <button type="submit" onclick="return confirm('Do you want to add amenities to this property?')" class="btn-submit">ADD AMENITIES</button>
    </div>
</form>

        </div>
    </div>
     </div>
     <script>
    
     document.querySelectorAll('.amenity-item').forEach(item => {
       item.addEventListener('click', function(e) {
         const checkbox = this.querySelector('input[type="checkbox"]');
         // Toggle the checkbox state
         checkbox.checked = !checkbox.checked;
         
         // Toggle visual selection state
         this.classList.toggle('selected', checkbox.checked);
         
         // Prevent double-triggering on label click
         e.preventDefault();
       });

       // Add keyboard support for accessibility
       item.addEventListener('keypress', (e) => {
         if (e.key === 'Enter' || e.key === ' ') {
           const checkbox = item.querySelector('input[type="checkbox"]');
           checkbox.checked = !checkbox.checked;
           item.classList.toggle('selected', checkbox.checked);
           e.preventDefault();
         }
       });
     });

     // Initialize Lucide icons
     lucide.createIcons();
     </script>
</body>
</html>