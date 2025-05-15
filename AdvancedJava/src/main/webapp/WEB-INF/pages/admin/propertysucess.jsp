<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Property Listed Successfully</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/addproperty.css">
</head>
<body>
    <jsp:include page="/WEB-INF/pages/admin/Adminheader.jsp" />
    
    <div class="main-container">
        <div class="success-wrapper">
            <div class="success-content">
                <div class="success-icon">✓</div>
                <h1>Property Listed Successfully!</h1>
                <p>Your property is now visible to potential tenants</p>
                <div class="success-actions">
                    <a href="property-details.jsp" class="btn-new">List Another Property</a>
                    <a href="dashboard.jsp" class="btn-dashboard">View Dashboard</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>