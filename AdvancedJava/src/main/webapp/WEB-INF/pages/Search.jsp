<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title><link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/Search.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/Searchbar.css" />
</head>
<body>
<header>
<jsp:include page="header.jsp" />
</header>
 <div class="search-container">
	            <jsp:include page="searchbar.jsp" />
	        </div>

    <div class="outer-container">
        <div class="products">
            <div class="hotel1">
                <div class="image">
                    <img src="https://images.unsplash.com/photo-1596394516093-501ba68a0ba6?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80" alt="Hotel">
                </div>
            
                <div class="components">
                    <div class="hotel-header">
                        <h4>Landmark Hotel</h4>
                        <span><strong>$20 / Night</strong></span>
                    </div>
                    <p>⭐⭐⭐⭐ Hotel</p>
                    <p>Tour desk, Airport shuttle</p>
                    <p>1.7 miles to City center</p>
                    <p><strong>7.4</strong> (96 ratings)</p>
                </div>
                </div>
            <div class="hotel1">
                    <div class="image">
                        <img src="https://images.unsplash.com/photo-1600585154340-be6161a56a0c"" alt="Hotel">
                    </div>
                
                    <div class="components">
                        <div class="hotel-header">
                            <h4>Landmark Hotel</h4>
                            <span><strong>$20 / Night</strong></span>
                        </div>
                        <p>⭐⭐⭐⭐ Hotel</p>
                        <p>Tour desk, Airport shuttle</p>
                        <p>1.7 miles to City center</p>
                        <p><strong>7.4</strong> (96 ratings)</p>
                    </div>
                </div>  
            <div class="hotel1">
                    <div class="image">
                        <img src="https://images.unsplash.com/photo-1596394516093-501ba68a0ba6?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80" alt="Hotel">
                    </div>
                
                    <div class="components">
                        <div class="hotel-header">
                            <h4>Landmark Hotel</h4>
                            <span><strong>$20 / Night</strong></span>
                        </div>
                        <p>⭐⭐⭐⭐ Hotel</p>
                        <p>Tour desk, Airport shuttle</p>
                        <p>1.7 miles to City center</p>
                        <p><strong>7.4</strong> (96 ratings)</p>
                    </div>
                    </div>
            
            <div class="hotel1">
            <div class="image">
                <img src="https://images.unsplash.com/photo-1600585154340-be6161a56a0c" alt="Hotel">
            </div>
        
            <div class="components">
                <div class="hotel-header">
                    <h4>Landmark Hotel</h4>
                    <span><strong>$20 / Night</strong></span>
                </div>
                <p>⭐⭐⭐⭐ Hotel</p>
                <p>Tour desk, Airport shuttle</p>
                <p>1.7 miles to City center</p>
                <p><strong>7.4</strong> (96 ratings)</p>
            </div>
            </div>
        </div>
        <div class="mapdiv">
            <iframe
            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3532.0550626806677!2d85.31453581506143!3d27.708955282791088!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x39eb190f63ed7b0d%3A0x7e7e60e6f8677c1!2sRatna%20Park!5e0!3m2!1sen!2snp!4v1618146502634!5m2!1sen!2snp"
            width="100%" height="100%" style="border:0;" allowfullscreen="" loading="lazy">
            </iframe>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/javascript/Searchbar.js"></script>
    
</body>
</html>