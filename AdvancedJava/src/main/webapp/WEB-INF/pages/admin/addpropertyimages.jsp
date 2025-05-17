<%@ page contentType="text/html;charset=UTF-8" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Property - Step 2</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/addproperty.css">
</head>
<body>
    <jsp:include page="/WEB-INF/pages/admin/Adminheader.jsp" />
    
    <div class="main-content">
    <div class="container">
     <%String error = (String) request.getParameter("error"); %>
     
   <%
    String propertyId = (String) request.getParameter("propertyId");
%>
<!--<c:set var="propertyId" value="${param.PropertyId != null ? param.PropertyId : param.propertyId}" /> 
 ${propertyId }-->
   
    <div class="heading">Add Property Images ${propertyId }</div>
    <div class="step-indicator">
			<div class="step-indicator">
				<div class="steps ">
					<div class="step <%= (propertyId != null && !propertyId.isEmpty()) ? "completed" : "not-completed" %>">
						<div class="step-circle">1</div>
						<p class="step-label">Property Details</p>
					</div>
					<div class="step-line active "></div>
					<div class="step <%= (error != null && !error.isEmpty()) ? "not-completed" : "active" %> ">
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
          <form class="property-form" action="addpropertyimages" method="POST" enctype="multipart/form-data">
          <input type="hidden" name="propertyId" value="<%= propertyId %>" />
    <div class="upload-section" id="image-upload-container">
        <div class="image-upload-block">
            <input type="file" name="images" accept="image/*" required>
            <input type="text" name="imageNames" placeholder="Enter image name" required>
            <!-- No remove button for the first block -->
        </div>
    </div>

    <button type="button" class="add-image-btn" onclick="addImageUploadBlock()">+ Add More Images</button>

    <div class="form-buttons">
        <a href="property-details.jsp" class="btn-back">← Back</a>
        <button type="submit" class="btn-next" onclick="return confirm('Do you want to add this property?')">SUBMIT →</button>
    </div>
</form>
      
    </div>
</div>
</div>
<!-- end of all opened divs -->
<script>
function addImageUploadBlock() {
    const container = document.getElementById("image-upload-container");
    const block = document.createElement("div");
    block.className = "image-upload-block";
    block.innerHTML = `
        <input type="file" name="images" accept="image/*" required>
        <input type="text" name="imageNames" placeholder="Enter image name" required>
        <button type="button" class="remove-image-btn" onclick="removeImageUploadBlock(this)">Remove</button>
    `;
    container.appendChild(block);
}

function removeImageUploadBlock(btn) {
    btn.parentElement.remove();
}
</script>
</body>
</html>