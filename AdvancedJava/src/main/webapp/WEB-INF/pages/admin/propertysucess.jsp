<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Property Listed Successfully</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admin/addproperty.css">
</head>
<body>
	<jsp:include page="/WEB-INF/pages/admin/Adminheader.jsp" />

 <%
    String propertyId = (String) request.getParameter("propertyId");
   if ("null".equalsIgnoreCase(propertyId)) {
       propertyId = null;
   }
%>
	<div class="main-content">
		<div class="container ">
			<div class="add-property" style="margin-top: 50px;">
				
					<div class="success-content">
						<div class="success-icon">✓</div>
						<div class="added-msg">Successfully Added</div>
						<p>Your task was executed sucessfully.</p>
						<div class="success-actions">
							   <a href="AddPropertyController" class="btn-new">List Another Property</a>
                        <a href="admindashboard" class="btn-dashboard">View Dashboard</a>
                        <a href="addpropertyimages?propertyId=<%= propertyId %>" class="btn-new">Add Images</a>
                        <a href="addamenities?propertyId=<%= propertyId %>" class="btn-new">Add Amenities</a>
						</div>
					
				</div>
			</div>
		</div>
	</div>
</body>
</html>