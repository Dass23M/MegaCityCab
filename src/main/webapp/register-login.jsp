<%-- 
    Document   : register-login
    Created on : Feb 10, 2025, 8:57:58 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mega City Cab - Authentication</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
        <style>
            body {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                height: 100vh;
                display: flex;
                align-items: center;
                justify-content: center;
            }
            .auth-container {
                background: white;
                border-radius: 15px;
                box-shadow: 0 10px 25px rgba(0,0,0,0.1);
                overflow: hidden;
                max-width: 500px;
                width: 100%;
            }
            .nav-pills .nav-link.active {
                background-color: #667eea;
            }
            .form-control {
                border-radius: 25px;
                padding: 10px 15px;
            }
            .btn-primary {
                background-color: #667eea;
                border: none;
                border-radius: 25px;
                padding: 10px 20px;
            }
            .btn-primary:hover {
                background-color: #5a4bdf;
            }
            .input-group-text {
                background: transparent;
                border: none;
                color: #667eea;
            }
        </style>
</head>
<body>
    <div class="container">
        <div class="auth-container">
            <ul class="nav nav-pills nav-fill" id="authTabs">
                <li class="nav-item">
                    <a class="nav-link active" id="login-tab" data-bs-toggle="pill" href="#login">
                        <i class="fas fa-sign-in-alt"></i> Login
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="register-tab" data-bs-toggle="pill" href="#register">
                        <i class="fas fa-user-plus"></i> Register
                    </a>
                </li>
            </ul>

            <div class="tab-content p-4">
                <!-- Login Form -->
                <div class="tab-pane fade show active" id="login">
                    <form id="loginForm">
                        <div class="input-group mb-3">
                            <span class="input-group-text"><i class="fas fa-envelope"></i></span>
                            <input type="email" id="loginEmail" class="form-control" placeholder="Email" required>
                        </div>
                        <div class="input-group mb-3">
                            <span class="input-group-text"><i class="fas fa-lock"></i></span>
                            <input type="password" id="loginPassword" class="form-control" placeholder="Password" required>
                        </div>
                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-sign-in-alt"></i> Login
                            </button>
                        </div>
                    </form>
                </div>

                <!-- Registration Form -->
                <div class="tab-pane fade" id="register">
                    <form id="registerForm">
                        <div class="input-group mb-3">
                            <span class="input-group-text"><i class="fas fa-user"></i></span>
                            <input type="text" id="regUsername" class="form-control" placeholder="Full Name" required>
                        </div>
                        <div class="input-group mb-3">
                            <span class="input-group-text"><i class="fas fa-envelope"></i></span>
                            <input type="email" id="regEmail" class="form-control" placeholder="Email" required>
                        </div>
                        <div class="input-group mb-3">
                            <span class="input-group-text"><i class="fas fa-phone"></i></span>
                            <input type="tel" id="regPhone" class="form-control" placeholder="Phone Number" required>
                        </div>
                        <div class="input-group mb-3">
                            <span class="input-group-text"><i class="fas fa-map-marker-alt"></i></span>
                            <input type="text" id="regAddress" class="form-control" placeholder="Address" required>
                        </div>
                        <div class="input-group mb-3">
                            <span class="input-group-text"><i class="fas fa-id-card"></i></span>
                            <input type="text" id="regNIC" class="form-control" placeholder="NIC/Identification Number" required>
                        </div>
                        <div class="input-group mb-3">
                            <span class="input-group-text"><i class="fas fa-user-shield"></i></span>
                            <select id="regRole" class="form-control" required>
                                <option value="">Select Role</option>
                                <option value="customer">Customer</option>
                                <option value="admin">Admin</option>
                            </select>
                        </div>
                        <div class="input-group mb-3">
                            <span class="input-group-text"><i class="fas fa-lock"></i></span>
                            <input type="password" id="regPassword" class="form-control" placeholder="Password" required>
                        </div>
                        <div class="input-group mb-3">
                            <span class="input-group-text"><i class="fas fa-lock"></i></span>
                            <input type="password" id="regConfirmPassword" class="form-control" placeholder="Confirm Password" required>
                        </div>
                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-user-plus"></i> Register
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <script>
        $(document).ready(function() {
            // Login Handler
            $("#loginForm").submit(function(event) {
                event.preventDefault();
                const loginData = {
                    email: $("#loginEmail").val(),
                    password: $("#loginPassword").val()
                };

                $.ajax({
                    url: "http://localhost:8080/CABSERVICE/api/auth/login",
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify(loginData),
                    success: function(response) {
                        sessionStorage.setItem('loggedInUser', JSON.stringify({
                            user_id: response.userId,
                            username: response.username,
                            role: response.role
                        }));
                        window.location.href = "booking.html";
                    },
                    error: function(xhr) {
                        alert("Login failed: " + xhr.responseJSON.message);
                    }
                });
            });

            // Registration Handler
            $("#registerForm").submit(function(event) {
                event.preventDefault();

                if ($("#regPassword").val() !== $("#regConfirmPassword").val()) {
                    alert("Passwords do not match!");
                    return;
                }

                const userData = {
                    username: $("#regUsername").val(),
                    email: $("#regEmail").val(),
                    phone: $("#regPhone").val(),
                    address: $("#regAddress").val(),
                    nic: $("#regNIC").val(),
                    role: $("#regRole").val(),
                    password: $("#regPassword").val()
                };

                $.ajax({
                    url: "http://localhost:8080/CABSERVICE/api/auth/register",
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify(userData),
                    success: function(response) {
                        sessionStorage.setItem('loggedInUser', JSON.stringify({
                            user_id: response.userId,
                            username: response.username,
                            role: response.role
                        }));
                        window.location.href = "booking.html";
                    },
                    error: function(xhr) {
                        alert("Registration failed: " + xhr.responseJSON.message);
                    }
                });
            });
        });
    </script>
</body>
</html>











