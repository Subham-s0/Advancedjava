<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration Form</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css" />
</head>
<body>
    <div class="login-box">
        <h2>Registration Form</h2>
        <form action="#" >
            <!-- First Name and Last Name inline -->
            <div class="row">
                <div class="col">
                    <label for="firstname">First Name:</label>
                    <input type="text" id="firstname" name="firstname" required>
                </div>
                <div class="col">
                    <label for="lastname">Last Name:</label>
                    <input type="text" id="lastname" name="lastname" required>
                </div>
            </div>

            <!-- Username and Birthday inline -->
            <div class="row">
                <div class="col">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="col">
                    <label for="birthday">Birthday:</label>
                    <input type="date" id="birthday" name="birthday" required>
                </div>
            </div>

            <!-- Gender and Email inline -->
            <div class="row">
                <div class="col">
                    <label for="gender">Gender:</label>
                    <select id="gender" name="gender" required>
                        <option value="male">Male</option>
                        <option value="female">Female</option>
                        <option value="other">Other</option>
                    </select>
                </div>
                <div class="col">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required>
                </div>
            </div>

            <!-- Phone and Subject inline -->
            <div class="row">
                <div class="col">
                    <label for="phone">Phone Number:</label>
                    <input type="tel" id="phone" name="phone" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" required>
                    <small>Format: 123-456-7890</small>
                </div>
                <div class="col">
                    <label for="subject">Subject:</label>
                    <select id="subject" name="subject" required>
                        <option value="math">Math</option>
                        <option value="science">Science</option>
                        <option value="english">English</option>
                        <option value="history">History</option>
                    </select>
                </div>
            </div>

            <!-- Password and Retype Password inline -->
            <div class="row">
                <div class="col">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="col">
                    <label for="confirm-password">Retype Password:</label>
                    <input type="password" id="confirm-password" name="confirm-password" required>
                </div>
            </div>

            <!-- Error message for password mismatch -->
            <div id="error-message" style="color: red; font-size: 14px;"></div>

            <!-- Submit Button -->
            <button type="submit" class="login-button">Register</button>
        </form>
    </div>
</body>
</html>
