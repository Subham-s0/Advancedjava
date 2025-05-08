<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/property.css" />
<title>Insert title here</title><link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/searchbar.css" />
</head>
<body>
<header >
<jsp:include page="header.jsp" />

</header>
<div class="search-container">
	            <jsp:include page="searchbar.jsp" />
	         </div>
	         <h2 style="margin-top:180px;">Featured Luxury Properties</h2>
  <div class="hotel-grid">       
   <jsp:include page="/WEB-INF/pages/Property-grid.jsp" />
    </div>
    <script src="${pageContext.request.contextPath}/javascript/Searchbar.js"></script>
<jsp:include page="footer.jsp" />
</body>
</html>