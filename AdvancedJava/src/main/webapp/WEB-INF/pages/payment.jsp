<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
  <%@ page import="com.Advancedjava.util.Sessionutil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/payment.css" />
</head>
<header><jsp:include page="header.jsp" /></header>
<body>


 <div class="container">
        <div class="payment-section">
         <%	String error = (String) session.getAttribute("error");	%>

 <div class="error-message <%=(error != null && !error.isEmpty()) ? "visible" : ""%>">
					<c:if test="${not empty error}">
						<p style="color: red;">
							<c:out value="${error}" />
						</p>
					</c:if>

				</div>
				 <% String success = (String) session.getAttribute("success"); %>
    <div class="success-message <%= (success != null && !success.isEmpty()) ? "visible" : "" %>">
        <%
            if (success != null && !success.isEmpty()) {
        %>
            <p><%= success %></p>
        <%
            }
            Sessionutil.removeAttribute(request, "success");
            Sessionutil.removeAttribute(request, "error");
        %>
    </div>
        
            <h1>Complete your payment</h1>
            <p>Please select your preferred payment method below</p>
            
          <form id="paymentForm" action="${pageContext.request.contextPath}/payment" method="post">
                <input type="hidden" name="bookingId" value="${booking.bookingId}">
                <input type="hidden" id="selectedPaymentMethod" name="paymentMethod" value="card">
                
                <div class="payment-methods">
                    <div class="payment-method active" data-method="card">
                        <div class="payment-icon">
                            <i class="fas fa-credit-card"></i>
                        </div>
                        <div>
                            <h2>Credit/Debit Card</h2>
                            <p>Pay securely using your card</p>
                        </div>
                    </div>
                    
                    <div class="payment-method" data-method="bank">
                        <div class="payment-icon">
                            <i class="fas fa-university"></i>
                        </div>
                        <div>
                            <h2>Bank Transfer</h2>
                            <p>Pay directly from your bank account</p>
                        </div>
                    </div>
                    
                    <div class="payment-method" data-method="wallet">
                        <div class="payment-icon">
                            <i class="fas fa-wallet"></i>
                        </div>
                        <div>
                            <h2>Digital Wallet</h2>
                            <p>Pay using your digital wallet</p>
                        </div>
                    </div>
                    
                    <div class="payment-method" data-method="payLater">
                        <div class="payment-icon">
                            <i class="fas fa-clock"></i>
                        </div>
                        <div>
                            <h2>Pay Later</h2>
                            <p>Pay in installments with no interest</p>
                        </div>
                    </div>
                </div>
                
                <button type="submit" class="btn">Complete Payment</button>
            </form>
        </div>
        
        <div class="booking-summary">
            <h1>Booking Summary</h1>
            
            <div class="booking-details">
                <div class="booking-item">
                    <span class="booking-label">Booking ID</span>
                    <span class="booking-value">BK-${booking.bookingId}</span>
                </div>
                
                <div class="booking-item">
                    <span class="booking-label">Check-in</span>
                    <span class="booking-value">
                    <fmt:formatDate value="${booking.checkInDate}" pattern="MMM dd, yyyy" /></span>
                </div>
                
                <div class="booking-item">
                    <span class="booking-label">Check-out</span>
                    <span class="booking-value"><fmt:formatDate value="${booking.checkOutDate}" pattern="MMM dd, yyyy" /></span>
                </div>
                
                <div class="booking-item">
                    <span class="booking-label">Guests</span>
                    <span class="booking-value">${booking.numberOfGuests} Guests</span>
                </div>
                
                <div class="booking-item">
                    <span class="booking-label">Property</span>
                    <span class="booking-value">${property.propertyName}</span>
                </div>
            </div>
            
            <div class="price-details">
                <div class="booking-item">
                    <span class="booking-label">Room charge (${nightCount} nights)</span>
                    <span class="booking-value">
                    $<fmt:formatNumber value="${basePrice * nightCount}" pattern="#,##0.00" /></span>
                </div>
                
                <div class="booking-item">
                    <span class="booking-label">Cleaning fee</span>
                    <span class="booking-value">$<fmt:formatNumber value="${cleaningFee}" pattern="#,##0" /></span>
                </div>
                
                <div class="booking-item">
                    <span class="booking-label">Service fee</span>
                    <span class="booking-value">$<fmt:formatNumber value="${serviceFee}" pattern="#,##0" /></span>
                </div>
                
                <div class="booking-item">
                    <span class="booking-label">Taxes</span>
                    <span class="booking-value">$<fmt:formatNumber value="${tax}" pattern="#,##0" /></span>
                </div>
            </div>
            
            <div class="price-total">
                <span>Total</span>
                <span>$<fmt:formatNumber value="${totalPrice}" pattern="#,##0.00" /></span>
            </div>
        </div>
    </div>

   <script>
        // Script to handle payment method selection
        document.addEventListener('DOMContentLoaded', function() {
            const paymentMethods = document.querySelectorAll('.payment-method');
            const selectedPaymentMethodInput = document.getElementById('selectedPaymentMethod');
            
            paymentMethods.forEach(method => {
                method.addEventListener('click', function() {
                    // Remove active class from all methods
                    paymentMethods.forEach(m => m.classList.remove('active'));
                    
                    // Add active class to clicked method
                    this.classList.add('active');
                    
                    // Update hidden input value
                    selectedPaymentMethodInput.value = this.getAttribute('data-method');
                });
            });
        });
    </script>
    <jsp:include page="footer.jsp" />
</body>
</html>