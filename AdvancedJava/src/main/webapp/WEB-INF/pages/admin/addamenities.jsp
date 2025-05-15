<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Property - Step 3</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/addproperty.css">
</head>
<body>
    <jsp:include page="/WEB-INF/pages/admin/Adminheader.jsp" />
    
    <div class="main-content">
        <div class="form-wrapper">
            <div class="step-indicator">
                <div class="step completed">1</div>
                <div class="step-divider"></div>
                <div class="step completed">2</div>
                <div class="step-divider"></div>
                <div class="step active">3</div>
            </div>

            <h1>Select Amenities</h1>
            
            <form class="property-form" action="PropertyAmenitiesServlet" method="POST">
                <div class="amenities-grid">
                    <c:forEach items="${amenities}" var="amenity">
                        <label class="amenity-item">
                            <input type="checkbox" name="amenities" value="${amenity.id}">
                            <span class="amenity-icon">${amenity.icon}</span>
                            <span class="amenity-name">${amenity.name}</span>
                        </label>
                    </c:forEach>
                </div>

                <div class="form-buttons">
                    <a href="property-images.jsp" class="btn-back">← Back</a>
                    <button type="submit" class="btn-submit">Complete Listing</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>