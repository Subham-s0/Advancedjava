<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NestAway</title>
    <script src="https://kit.fontawesome.com/3ff6ed8df5.js" crossorigin="anonymous"></script>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        footer {
            background: #1c2834;
            padding: 10px 0 0;
            margin: 10px -20px -20px;
            padding-bottom: 10px;
        }

        .footer-content {
            max-width: 1200px;
            margin: 0 auto;
            padding: 10px;
            display: flex;
            flex-wrap: wrap;
            gap: 30px;
            justify-content: center;
        }

        .footer-section {
            flex: 1;
            min-width: 250px;
            text-align: center;
        }
.footer-contact{
font-size:18px;
font-weight:750;
color:white;}
     
        .contact-info{
            display: flex;
            gap: 20px;
            justify-content: center;
        }

        .contact-info p {
            color: #fff;
            margin: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
        }

        .social-icons {
            display: flex;
            justify-content: center;
            gap: 25px;
            margin-top: 7px;
        }

        .social-icons a {
            width: 35px;
            height: 35px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            text-decoration: none;
        }

        .facebook { background: #3b5998; }
        .instagram { background: linear-gradient(45deg, #f09433, #dc2743, #cc2366); }
        .twitter { background: #1DA1F2; }

        .footer-bottom {
            background: #34495e;
            padding: 10px;
            text-align: center;
            margin-top: 10px;
        }

        .footer-bottom p {
            color: white;
            font-size: 0.9rem;
        }

        .footer-bottom a {
            color: #dbe4f1;
            text-decoration: none;
            margin: 0 10px;
        }

        @media (max-width: 768px) {
            .footer-content {
                flex-direction: column;
                align-items: center;
            }
        }
    </style>
</head>
<body>
    <footer>
     
            

            <div class="footer-section">
                <div class="social-icons">
                    <a href="https://www.facebook.com" class="facebook"><i class="fa-brands fa-facebook-f"></i></a>
                    <a href="https://www.instagram.com" class="instagram"><i class="fa-brands fa-instagram"></i></a>
                    <a href="https://www.x.com" class="twitter"><i class="fa-brands fa-twitter"></i></a>
                </div>
            </div>
    <div class="footer-section">
                <span class ="footer-contact">Contact Us</span>
                <div class="contact-info">
                    <p><i class="fa-solid fa-location-dot"></i>Islington College</p>
                    <p><i class="fa-solid fa-phone"></i>+977 9876543210</p>
                    <p><i class="fa-solid fa-envelope"></i>support@nestaway.com</p>
                </div>
            </div>

        <div class="footer-bottom">
            <p>
                © 2024 NestAway. All rights reserved.
                <a href="#">Terms & Privacy</a> | 
                <a href="#">Partner With Us</a>
            </p>
        </div>
    </footer>
</body>
</html>