<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>NestAway Footer</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css">
  <script src="https://kit.fontawesome.com/3ff6ed8df5.js" crossorigin="anonymous"></script>
  <link href="https://fonts.googleapis.com/css2?family=Dancing+Script:wght@400;700&display=swap" rel="stylesheet">
  <link rel="preconnect" href="https://fonts.googleapis.com">
</head>
<body>
  <footer>
    <div class="row">          
      <div class="column-1">
        <div class="footer-logo">
          <!-- Replace with actual logo -->
          <div class="footer-logo"><img src="${pageContext.request.contextPath}/resources/images/logoReverse.png" /></div>
          <h1>NestAway</h1>
        </div>
        <p class="tagline">Find Your Perfect Stay</p>
      </div>
      
      <div class="column-2">
        <h2>Contact Us</h2>
        <p><a href="https://maps.app.goo.gl/pS1LFvWpcaSTJ5oZ7" target="_blank" rel="noopener">
          <i class="fa-solid fa-location-dot"></i>
          Islington College</a></p>
        <p><i class="fa-solid fa-phone"></i>977887788</p>
        <p><i class="fa-solid fa-envelope"></i> support@nestaway.com</p>
      </div>
      
      <div class="column-3">
        <h2>Explore</h2>
        <ul>
          <li><a href="home">Home</a></li>
          <li><a href="Property">Properties</a></li>
          <li><a href="#">Bookings</a></li>
          <li><a href="Profile"> My Profile</a></li>
          <li><a href="#">About Us</a></li>
        </ul>
      </div>
      
      <div class="column-4">
        <h2>Join Us</h2>
        <p>Subscribe to our newsletter and get <strong>10% off</strong> your first booking!</p>
        <form class="emailbox">
          <label for="email"><i class="fa-regular fa-envelope"></i></label>
          <input id="email" name="email" type="email" placeholder="Enter your Email" required>
          <button type="submit"><i class="fa-solid fa-arrow-right"></i></button>
        </form>
      </div>
      
      <div class="column-5">
        <h2>Connect With Us</h2>
        <div class="socialmedia">
          <a href="https://www.facebook.com" target="_blank" rel="noopener" aria-label="Facebook">
            <i class="fa-brands fa-facebook-f"></i>
          </a>
          <a href="https://www.instagram.com" target="_blank" rel="noopener" aria-label="Instagram">
            <i class="fa-brands fa-instagram"></i>
          </a>
          <a href="https://www.x.com" target="_blank" rel="noopener" aria-label="Twitter/X">
            <i class="fa-brands fa-twitter"></i>
          </a>
        </div>
      </div>
    </div>
    <div class="footer-bottom">
      <p>© 2024 NestAway. All rights reserved. | <a href="#">Terms & Privacy</a> | <a href="#">Partner With Us</a></p>
    </div>
  </footer>
</body>
</html>