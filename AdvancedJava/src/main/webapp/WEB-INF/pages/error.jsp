<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="refresh" content="5;url=${pageContext.request.contextPath}/" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>404 - Page Not Found</title>
   <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/Errorpage.css" />
</head>
<body>
 <video autoplay loop muted class="back-video">
    <source src="${pageContext.request.contextPath}/resources/images/banner/eror.mp4" type= "video/mp4">
    </video>
  <div class="text-content">
    <div class="image-content">
    <img src="${pageContext.request.contextPath}/resources/images/banner/error.svg" alt="error-image">
  </div>
    <h2>PAGE NOT FOUND</h2>
    <p>Your search has ventured beyond the known universe.</p>
    <a href="${pageContext.request.contextPath}/">Back To Home</a>
  </div> 
</body>
</html>