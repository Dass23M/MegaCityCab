<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mega City Cab - Booking</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            padding: 20px 0;
        }
        .booking-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.1);
            padding: 30px;
            margin: auto;
            max-width: 800px;
        }
        .form-control {
            border-radius: 8px;
            padding: 12px 15px;
            border: 1px solid #e0e0e0;
        }
        .form-label {
            font-weight: 600;
            color: #2d3748;
        }
        .input-group-text {
            background: #f8f9fa;
            border: 1px solid #e0e0e0;
        }
        .btn-primary {
            background: #667eea;
            border: none;
            padding: 12px 20px;
            border-radius: 8px;
            font-weight: 600;
        }
        .btn-primary:hover {
            background: #5a67d8;
        }
        .alert-info {
            background: #ebf4ff;
            border-color: #c3dafe;
            color: #2d3748;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="booking-card">
            <h2 class="text-center mb-4"><i class="fas fa-car-side me-2"></i>Book Your Ride</h2>
            
            <div class="alert alert-info mb-4">
                Logged in as: <strong id="loggedInUser">John Doe</strong>
            </div>

            <form id="bookingForm">
                <div class="row g-4">
                    <!-- Pickup & Dropoff Section -->
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="form-label">Pickup Location</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fas fa-map-marker-alt"></i></span>
                                <select id="pickUpStation" class="form-select" required>
                                    <option value="">Select pickup location</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="form-label">Drop-off Location</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fas fa-flag-checkered"></i></span>
                                <select id="dropOffStation" class="form-select" required>
                                    <option value="">Select dropoff location</option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <!-- Date & Time Section -->
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="form-label">Pickup Date & Time</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fas fa-calendar-alt"></i></span>
                                <input type="datetime-local" id="dateTime" class="form-control" required>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="form-label">Distance (km)</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fas fa-road"></i></span>
                                <input type="text" id="distance" class="form-control" readonly>
                            </div>
                        </div>
                    </div>

                    <!-- Passengers & Vehicle Section -->
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="form-label">Passengers</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fas fa-users"></i></span>
                                <input type="number" id="numPassengers" class="form-control" 
                                       min="1" max="8" value="1" required>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="form-label">Select Vehicle</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fas fa-car"></i></span>
                                <select id="carSelect" class="form-select" required>
                                    <option value="">Choose vehicle</option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <!-- Driver Selection -->
                    <div class="col-12">
                        <div class="form-group">
                            <label class="form-label">Select Driver</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fas fa-id-card-alt"></i></span>
                                <select id="driverSelect" class="form-select" required>
                                    <option value="">Available drivers</option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <!-- Submit Button -->
                    <div class="col-12">
                        <button type="submit" class="btn btn-primary w-100 mt-3">
                            <i class="fas fa-check-circle me-2"></i>Confirm Booking
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <!-- Scripts -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        $(document).ready(function () {
            const userId = 1; // Should come from session/auth system

            // Load Stations
            $.ajax({
                url: "http://localhost:8080/CABSERVICE/api/bookingstations",
                success: function (stations) {
                    const uniqueStations = [...new Set(stations.flatMap(s => [s.fromStationName, s.toStationName]))];
                    uniqueStations.forEach(stationName => {
                        $('#pickUpStation, #dropOffStation').append(
                            `<option value="${stationName}">${stationName}</option>`
                        );
                    });
                }
            });

            // Load Available Cars
            $.ajax({
                url: "http://localhost:8080/CABSERVICE/api/cars",
                success: function (cars) {
                    cars.filter(car => car.status === 'Available').forEach(car => {
                        $('#carSelect').append(
                            `<option value="${car.carId}" 
                                data-model="${car.model}"
                                data-capacity="${car.capacity}">
                                ${car.model} (${car.capacity} seats)
                            </option>`
                        );
                    });
                }
            });

            // Load Available Drivers
            $.ajax({
                url: "http://localhost:8080/CABSERVICE/api/drivers",
                success: function (drivers) {
                    drivers.filter(driver => driver.status === 'Available').forEach(driver => {
                        $('#driverSelect').append(
                            `<option value="${driver.driverId}" 
                                data-name="${driver.name}">
                                ${driver.name} (${driver.licenseNumber})
                            </option>`
                        );
                    });
                }
            });

            // Calculate Distance
            $('#pickUpStation, #dropOffStation').change(function () {
                const from = $('#pickUpStation').val();
                const to = $('#dropOffStation').val();

                if (from && to && from !== to) {
                    $.ajax({
                        url: `http://localhost:8080/CABSERVICE/api/bookingstations/${encodeURIComponent(from)}/${encodeURIComponent(to)}`,
                        success: function (distanceData) {
                            if (distanceData?.distanceKm) {
                                $('#distance').val(distanceData.distanceKm);
                            } else {
                                $('#distance').val('');
                                showError('Distance not available for this route');
                            }
                        },
                        error: () => {
                            $('#distance').val('');
                            showError('Error fetching distance information');
                        }
                    });
                }
            });

            // Form Submission
            $('#bookingForm').submit(function (e) {
                e.preventDefault();

                const pickup = $('#pickUpStation').val();
                const dropoff = $('#dropOffStation').val();
                const car = $('#carSelect option:selected');
                const driver = $('#driverSelect option:selected');
                const passengers = parseInt($('#numPassengers').val());

                if (!validateForm(pickup, dropoff, car, driver, passengers)) return;

                const bookingData = {
                    userId: userId,
                    pickUpStation: pickup,
                    dropOffStation: dropoff,
                    distance: parseFloat($('#distance').val()),
                    dateTime: $('#dateTime').val().replace('T', ' ') + ':00',
                    numPassengers: passengers,
                    carModel: car.data('model'),
                    driverName: driver.data('name'),
                    status: 'Confirmed'
                };

                submitBooking(bookingData, car.val(), driver.val());
            });

            function validateForm(pickup, dropoff, car, driver, passengers) {
                if (pickup === dropoff) {
                    showError('Pickup and dropoff locations must be different');
                    return false;
                }

                if (!car.val()) {
                    showError('Please select a vehicle');
                    return false;
                }

                if (passengers > parseInt(car.data('capacity'))) {
                    showError(`Selected vehicle can only accommodate ${car.data('capacity')} passengers`);
                    return false;
                }

                if (!driver.val()) {
                    showError('Please select a driver');
                    return false;
                }

                if (!$('#distance').val()) {
                    showError('Please select valid locations to calculate distance');
                    return false;
                }

                return true;
            }

            function submitBooking(bookingData, carId, driverId) {
                $.ajax({
                    url: "http://localhost:8080/CABSERVICE/api/bookings",
                    method: "POST",
                    contentType: "application/json",
                    data: JSON.stringify(bookingData),
                    success: function (response) {
                        updateVehicleStatus(carId);
                        updateDriverStatus(driverId);
                        showSuccess('Booking confirmed successfully!');
                    },
                    error: handleApiError
                });
            }

            function updateVehicleStatus(carId) {
                $.ajax({
                    url: `http://localhost:8080/CABSERVICE/api/cars/${carId}`,
                    method: "GET",
                    success: function (carData) {
                        carData.status = "In Service";
                        $.ajax({
                            url: "http://localhost:8080/CABSERVICE/api/cars",
                            method: "PUT",
                            contentType: "application/json",
                            data: JSON.stringify(carData)
                        });
                    }
                });
            }

            function updateDriverStatus(driverId) {
                $.ajax({
                    url: `http://localhost:8080/CABSERVICE/api/drivers/${driverId}`,
                    method: "GET",
                    success: function (driverData) {
                        driverData.status = "Busy";
                        $.ajax({
                            url: "http://localhost:8080/CABSERVICE/api/drivers",
                            method: "PUT",
                            contentType: "application/json",
                            data: JSON.stringify(driverData)
                        });
                    }
                });
            }

            function showError(message) {
                alert(`Error: ${message}`);
            }

            function showSuccess(message) {
                alert(message);
                window.location.reload();
            }

            function handleApiError(xhr) {
                let errorMsg = 'Booking failed. Please try again.';
                try {
                    if (xhr.responseJSON?.message) errorMsg = xhr.responseJSON.message;
                } catch (e) {}
                showError(errorMsg);
            }
        });
    </script>
</body>
</html>