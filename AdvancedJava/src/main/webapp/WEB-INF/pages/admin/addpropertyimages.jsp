<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Property - Step 2</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/addproperty.css">
</head>
<body>
    <jsp:include page="/WEB-INF/pages/admin/Adminheader.jsp" />
    
    <div class="main-content">
        <div class="form-wrapper">
            <div class="step-indicator ">
                <div class="step completed">1</div>
                <div class="step-divider"></div>
                <div class="step active">2</div>
                <div class="step-divider"></div>
                <div class="step">3</div>
            </div>

            <h1>Add Property Images</h1>
            
            <form class="property-form" action="addpropertyimages" method="POST" enctype="multipart/form-data">
                <div class="upload-section">
                    <label class="file-upload">
                        <input type="file" name="images" multiple accept="image/*">
                        <div class="upload-content">
                            <span class="upload-icon">📷</span>
                            <p>Click to upload or drag and drop</p>
                            <small>Maximum 10 images (JPEG, PNG)</small>
                        </div>
                    </label>
                </div>

                <div class="form-buttons">
                    <a href="property-details.jsp" class="btn-back">← Back</a>
                    <button type="submit" class="btn-next">Next →</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>