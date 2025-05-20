<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.Advancedjava.util.Sessionutil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nestaway1</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin/Admindashboard.css" />
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
 </div>
  </div>
</body>

</html>