<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mega City Cab - Authentication</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome Icons -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <!-- Custom Styles -->
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0;
            padding: 0;
            overflow: hidden;
        }
        .auth-container {
            background: rgba(255, 255, 255, 0.95);
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
            overflow: hidden;
            max-width: 800px; /* Increased width for better spacing */
            width: 100%;
            animation: fadeIn 0.5s ease-in-out;
        }
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-20px); }
            to { opacity: 1; transform: translateY(0); }
        }
        .nav-pills .nav-link {
            border-radius: 25px;
            margin: 5px;
            font-weight: 500;
            transition: all 0.3s ease;
        }
        .nav-pills .nav-link.active {
            background-color: #667eea;
            color: white;
        }
        .form-control {
            border-radius: 25px;
            padding: 10px 15px;
            border: 1px solid #ddd;
            transition: border-color 0.3s ease;
        }
        .form-control:focus {
            border-color: #667eea;
            box-shadow: 0 0 5px rgba(102, 126, 234, 0.5);
        }
        .btn-primary {
            background-color: #667eea;
            border: none;
            border-radius: 25px;
            padding: 10px 20px;
            font-weight: 500;
            transition: background-color 0.3s ease;
        }
        .btn-primary:hover {
            background-color: #5a4bdf;
        }
        .input-group-text {
            background: transparent;
            border: none;
            color: #667eea;
        }
        .tab-content {
            padding: 20px;
        }
        .auth-container h3 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
            font-weight: 600;
        }
        .auth-container p {
            text-align: center;
            color: #666;
            margin-bottom: 30px;
        }
        /* Background Animation */
        body::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(135deg, rgba(102, 126, 234, 0.1), rgba(118, 75, 162, 0.1));
            z-index: -1;
            animation: animateBackground 10s infinite alternate;
        }
        @keyframes animateBackground {
            0% { transform: scale(1); }
            100% { transform: scale(1.1); }
        }
        /* Two-column layout for register form */
        .register-form {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
        }
        .register-form .input-group {
            margin-bottom: 15px;
        }
        @media (max-width: 768px) {
            .register-form {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="auth-container">
            <ul class="nav nav-pills nav-fill mb-3" id="authTabs">
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

            <div class="tab-content">
                <!-- Login Form -->
                <div class="tab-pane fade show active" id="login">
                    <h3>Welcome Back!</h3>
                    <p>Login to continue to Mega City Cab.</p>
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
                    <h3>Create an Account</h3>
                    <p>Join Mega City Cab to book your rides.</p>
                    <form id="registerForm" class="register-form">
                        <!-- Column 1 -->
                        <div>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fas fa-user"></i></span>
                                <input type="text" id="regUsername" class="form-control" placeholder="Full Name" required>
                            </div>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fas fa-envelope"></i></span>
                                <input type="email" id="regEmail" class="form-control" placeholder="Email" required>
                            </div>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fas fa-phone"></i></span>
                                <input type="tel" id="regPhone" class="form-control" placeholder="Phone Number" required>
                            </div>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fas fa-map-marker-alt"></i></span>
                                <input type="text" id="regAddress" class="form-control" placeholder="Address" required>
                            </div>
                        </div>
                        <!-- Column 2 -->
                        <div>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fas fa-id-card"></i></span>
                                <input type="text" id="regNIC" class="form-control" placeholder="NIC/Identification Number" required>
                            </div>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fas fa-user-shield"></i></span>
                                <select id="regRole" class="form-control" required>
                                    <option value="">Select Role</option>
                                    <option value="customer">Customer</option>
                                    <option value="admin">Admin</option>
                                </select>
                            </div>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fas fa-lock"></i></span>
                                <input type="password" id="regPassword" class="form-control" placeholder="Password" required>
                            </div>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fas fa-lock"></i></span>
                                <input type="password" id="regConfirmPassword" class="form-control" placeholder="Confirm Password" required>
                            </div>
                        </div>
                        <div class="d-grid col-span-2">
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-user-plus"></i> Register
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS and Dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <!-- jQuery -->
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
                        window.location.href = "index.html";
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
                        window.location.href = "register-login.jsp";
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