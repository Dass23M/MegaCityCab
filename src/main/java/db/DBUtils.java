/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

/**
 *
 * @author DELL
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.sql.Types;

/**
 *
 * @author icbt
 */
public class DBUtils {

    static final String DB_URL = "jdbc:mysql://localhost:3306/megacitycab?useSSL=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "LLdm2323$DM$";

    public DBUtils() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {

        }
    }

    // Method to get a database connection
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public Student getStudent(int id) throws SQLException {
        Student st = null;
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());

            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM students WHERE id=" + id);) {
                while (rs.next()) {
                    st = new Student();
                    st.setId(rs.getInt("id"));
                    st.setName(rs.getString("name"));
                    break;
                }
            } catch (SQLException e) {
                System.err.print(e);
                throw e;
            }

        } catch (SQLException e) {
            System.err.print(e);
            throw e;
        }

        return st;
    }

    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());

            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM students");) {
                while (rs.next()) {
                    Student st = new Student();
                    st.setId(rs.getInt("id"));
                    st.setName(rs.getString("name"));
                    students.add(st);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {

        }

        return students;
    }

    public boolean addStudent(Student st) {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());

            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement();) {
                stmt.executeUpdate("INSERT INTO students (id, name) "
                        + "VALUES ('" + st.getId() + "', '" + st.getName() + "');");
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {

        }
        return false;
    }

    public boolean updateStudent(Student st) {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());

            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement();) {
                stmt.executeUpdate("UPDATE students SET name = '" + st.getName() + "' WHERE (id = '" + st.getId() + "');");
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {

        }
        return false;
    }

    public boolean deleteStudent(int id) {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());

            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement();) {
                stmt.executeUpdate("DELETE FROM students WHERE (id = '" + id + "');");
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {

        }
        return false;
    }

    public boolean emailExists(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerUser(User user) {
        String query = "INSERT INTO users (username, email, password, phone, address, nic, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt())); // Using BCrypt
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getNic());
            stmt.setString(7, user.getRole());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User validateLogin(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password");
                if (BCrypt.checkpw(password, storedHash)) {
                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getString("nic"),
                            rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    ////////////////////////////////////////////////////
    //////////////////////// USERS ////////////////////
    ////////////////////////////////////////////////////
    // Retrieve all users
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("nic"),
                        rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    // Retrieve a single user by ID
    public User getUser(int userId) throws SQLException {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("nic"),
                        rs.getString("role")
                );
            }
        }
        return null;
    }

    // Add a new user
    public boolean addUser(User user) {
        String query = "INSERT INTO users (username, email, phone, address, nic, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPhone());
            pstmt.setString(4, user.getAddress());
            pstmt.setString(5, user.getNic());
            pstmt.setString(6, user.getRole());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Update an existing user
    public boolean updateUser(User user) {
        String query = "UPDATE users SET username = ?, email = ?, phone = ?, address = ?, nic = ?, role = ? WHERE user_id = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPhone());
            pstmt.setString(4, user.getAddress());
            pstmt.setString(5, user.getNic());
            pstmt.setString(6, user.getRole());
            pstmt.setInt(7, user.getUserId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Delete a user by ID
    public boolean deleteUser(int userId) {
        String query = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    ////////////////////////////////////////////////////
    // BOOKING //
    /////////////////////////////////////////////////////
     // Retrieve all bookings
  // =================== Booking Methods ===================

    // Retrieve all bookings
    public List<Booking> getBookings() {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("user_id"),
                        rs.getString("pick_up_station"),
                        rs.getString("drop_off_station"),
                        rs.getDouble("distance"),
                        rs.getString("date_time"),
                        rs.getInt("num_passengers"),
                        rs.getString("car_model"),
                        rs.getString("driver_name"),
                        rs.getString("category_name"),
                        rs.getString("status"),
                        rs.getBoolean("alert_sent"),
                        rs.getString("admin_comment"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    // Retrieve a booking by ID
    public Booking getBooking(int bookingId) {
        String query = "SELECT * FROM bookings WHERE booking_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, bookingId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("user_id"),
                        rs.getString("pick_up_station"),
                        rs.getString("drop_off_station"),
                        rs.getDouble("distance"),
                        rs.getString("date_time"),
                        rs.getInt("num_passengers"),
                        rs.getString("car_model"),
                        rs.getString("driver_name"),
                        rs.getString("category_name"),
                        rs.getString("status"),
                        rs.getBoolean("alert_sent"),
                        rs.getString("admin_comment"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Add a new booking
    public boolean addBooking(Booking booking) {
        String query = "INSERT INTO bookings (user_id, pick_up_station, drop_off_station, distance, date_time, num_passengers, car_model, driver_name, category_name, status, alert_sent, admin_comment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, booking.getUserId());
            pstmt.setString(2, booking.getPickUpStation());
            pstmt.setString(3, booking.getDropOffStation());
            pstmt.setDouble(4, booking.getDistance());
            pstmt.setString(5, booking.getDateTime());
            pstmt.setInt(6, booking.getNumPassengers());
            pstmt.setString(7, booking.getCarModel());
            pstmt.setString(8, booking.getDriverName());
            pstmt.setString(9, booking.getCategoryName());
            pstmt.setString(10, booking.getStatus());
            pstmt.setBoolean(11, booking.isAlertSent());
            pstmt.setString(12, booking.getAdminComment());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update an existing booking
    public boolean updateBooking(Booking booking) {
        String query = "UPDATE bookings SET user_id = ?, pick_up_station = ?, drop_off_station = ?, distance = ?, date_time = ?, num_passengers = ?, car_model = ?, driver_name = ?, category_name = ?, status = ?, alert_sent = ?, admin_comment = ? WHERE booking_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, booking.getUserId());
            pstmt.setString(2, booking.getPickUpStation());
            pstmt.setString(3, booking.getDropOffStation());
            pstmt.setDouble(4, booking.getDistance());
            pstmt.setString(5, booking.getDateTime());
            pstmt.setInt(6, booking.getNumPassengers());
            pstmt.setString(7, booking.getCarModel());
            pstmt.setString(8, booking.getDriverName());
            pstmt.setString(9, booking.getCategoryName());
            pstmt.setString(10, booking.getStatus());
            pstmt.setBoolean(11, booking.isAlertSent());
            pstmt.setString(12, booking.getAdminComment());
            pstmt.setInt(13, booking.getBookingId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a booking
    public boolean deleteBooking(int bookingId) {
        String query = "DELETE FROM bookings WHERE booking_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, bookingId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    ///////////////////////////////////////////////
    //////////////// CAR ///////////////////////
    ////////////////////////////////////////////////
    
    // Retrieve all cars
    public List<Car> getCars() {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM cars";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Car car = new Car();
                car.setCarId(rs.getInt("car_id"));
                car.setMake(rs.getString("make"));
                car.setModel(rs.getString("model"));
                car.setLicensePlate(rs.getString("license_plate"));
                car.setCapacity(rs.getInt("capacity"));
                car.setBaseFare(rs.getDouble("base_fare"));
                car.setPricePerKm(rs.getDouble("price_per_km"));
                car.setCategoryId(rs.getInt("category_id"));
                car.setStatus(rs.getString("status"));
                car.setCreatedAt(rs.getTimestamp("created_at"));
                car.setUpdatedAt(rs.getTimestamp("updated_at"));
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    // Retrieve a single car by ID
    public Car getCar(int carId) throws SQLException {
        String query = "SELECT * FROM cars WHERE car_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, carId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Car car = new Car();
                car.setCarId(rs.getInt("car_id"));
                car.setMake(rs.getString("make"));
                car.setModel(rs.getString("model"));
                car.setLicensePlate(rs.getString("license_plate"));
                car.setCapacity(rs.getInt("capacity"));
                car.setBaseFare(rs.getDouble("base_fare"));
                car.setPricePerKm(rs.getDouble("price_per_km"));
                car.setCategoryId(rs.getInt("category_id"));
                car.setStatus(rs.getString("status"));
                car.setCreatedAt(rs.getTimestamp("created_at"));
                car.setUpdatedAt(rs.getTimestamp("updated_at"));
                return car;
            }
        }
        return null;
    }

    // Add a new car
    public boolean addCar(Car car) {
        String query = "INSERT INTO cars (make, model, license_plate, capacity, base_fare, price_per_km, category_id, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, car.getMake());
            pstmt.setString(2, car.getModel());
            pstmt.setString(3, car.getLicensePlate());
            pstmt.setInt(4, car.getCapacity());
            pstmt.setDouble(5, car.getBaseFare());
            pstmt.setDouble(6, car.getPricePerKm());
            pstmt.setInt(7, car.getCategoryId());
            pstmt.setString(8, car.getStatus());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update an existing car
    public boolean updateCar(Car car) {
        String query = "UPDATE cars SET make = ?, model = ?, license_plate = ?, capacity = ?, base_fare = ?, price_per_km = ?, category_id = ?, status = ? WHERE car_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, car.getMake());
            pstmt.setString(2, car.getModel());
            pstmt.setString(3, car.getLicensePlate());
            pstmt.setInt(4, car.getCapacity());
            pstmt.setDouble(5, car.getBaseFare());
            pstmt.setDouble(6, car.getPricePerKm());
            pstmt.setInt(7, car.getCategoryId());
            pstmt.setString(8, car.getStatus());
            pstmt.setInt(9, car.getCarId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a car
    public boolean deleteCar(int carId) {
        String query = "DELETE FROM cars WHERE car_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, carId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    ////////////////////////////////////////////////////////////////////
    /////////////////// DRIVER /////////////////////////////////
    ///////////////////////////////////////////////////////////////////
    public List<Driver> getDrivers() {
        List<Driver> drivers = new ArrayList<>();
        String query = "SELECT * FROM drivers";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                drivers.add(new Driver(
                        rs.getInt("driver_id"),
                        rs.getString("name"),
                        rs.getString("license_number"),
                        rs.getString("phone"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return drivers;
    }

    // Retrieve a single driver by ID
    public Driver getDriver(int driverId) throws SQLException {
        String query = "SELECT * FROM drivers WHERE driver_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, driverId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Driver(
                        rs.getInt("driver_id"),
                        rs.getString("name"),
                        rs.getString("license_number"),
                        rs.getString("phone"),
                        rs.getString("status")
                );
            }
        }
        return null;
    }

    // Add a new driver
    public boolean addDriver(Driver driver) {
        String query = "INSERT INTO drivers (name, license_number, phone, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, driver.getName());
            pstmt.setString(2, driver.getLicenseNumber());
            pstmt.setString(3, driver.getPhone());
            pstmt.setString(4, driver.getStatus());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Update an existing driver
    public boolean updateDriver(Driver driver) {
        String query = "UPDATE drivers SET name = ?, license_number = ?, phone = ?, status = ? WHERE driver_id = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, driver.getName());
            pstmt.setString(2, driver.getLicenseNumber());
            pstmt.setString(3, driver.getPhone());
            pstmt.setString(4, driver.getStatus());
            pstmt.setInt(5, driver.getDriverId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Delete a driver
    public boolean deleteDriver(int driverId) {
        String query = "DELETE FROM drivers WHERE driver_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, driverId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    ///////////////// BILLING /////////////////////
    ////////////////////////////////////////////////
    public List<Billing> getBillings() {
        List<Billing> billings = new ArrayList<>();
        String query = "SELECT * FROM billing";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                billings.add(new Billing(
                        rs.getInt("billing_id"),
                        rs.getInt("booking_id"),
                        rs.getBigDecimal("base_fare"),
                        rs.getBigDecimal("distance_fare"),
                        rs.getBigDecimal("passenger_fare"),
                        rs.getBigDecimal("discount"),
                        rs.getBigDecimal("tax"),
                        rs.getBigDecimal("total_fare"),
                        rs.getTimestamp("date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billings;
    }

    public Billing getBillingById(int id) {
        Billing billing = null;
        String query = "SELECT * FROM billing WHERE billing_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                billing = new Billing(
                        rs.getInt("billing_id"),
                        rs.getInt("booking_id"),
                        rs.getBigDecimal("base_fare"),
                        rs.getBigDecimal("distance_fare"),
                        rs.getBigDecimal("passenger_fare"),
                        rs.getBigDecimal("discount"),
                        rs.getBigDecimal("tax"),
                        rs.getBigDecimal("total_fare"),
                        rs.getTimestamp("date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billing;
    }

    public boolean addBilling(Billing billing) {
        String query = "INSERT INTO billing (booking_id, base_fare, distance_fare, passenger_fare, discount, tax, total_fare) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, billing.getBookingId());
            stmt.setBigDecimal(2, billing.getBaseFare());
            stmt.setBigDecimal(3, billing.getDistanceFare());
            stmt.setBigDecimal(4, billing.getPassengerFare());
            stmt.setBigDecimal(5, billing.getDiscount());
            stmt.setBigDecimal(6, billing.getTax());
            stmt.setBigDecimal(7, billing.getTotalFare());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBilling(Billing billing) {
        String query = "UPDATE billing SET booking_id = ?, base_fare = ?, distance_fare = ?, passenger_fare = ?, discount = ?, tax = ?, total_fare = ? WHERE billing_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, billing.getBookingId());
            stmt.setBigDecimal(2, billing.getBaseFare());
            stmt.setBigDecimal(3, billing.getDistanceFare());
            stmt.setBigDecimal(4, billing.getPassengerFare());
            stmt.setBigDecimal(5, billing.getDiscount());
            stmt.setBigDecimal(6, billing.getTax());
            stmt.setBigDecimal(7, billing.getTotalFare());
            stmt.setInt(8, billing.getBillingId());  // Set the billing ID to identify the record
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBilling(int id) {
        String query = "DELETE FROM billing WHERE billing_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve all booking stations
    public List<BookingStations> getBookingStations() {
        List<BookingStations> stations = new ArrayList<>();
        String query = "SELECT * FROM booking_stations";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                stations.add(new BookingStations(
                        rs.getInt("id"),
                        rs.getString("from_station_name"),
                        rs.getString("to_station_name"),
                        rs.getDouble("distance_km")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stations;
    }

    // Retrieve a specific distance between two stations
    public BookingStations getDistanceBetweenStations(String fromStationName, String toStationName) {
        String query = "SELECT * FROM booking_stations WHERE from_station_name = ? AND to_station_name = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, fromStationName);
            pstmt.setString(2, toStationName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new BookingStations(
                        rs.getInt("id"),
                        rs.getString("from_station_name"),
                        rs.getString("to_station_name"),
                        rs.getDouble("distance_km")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Add a new booking station distance
    public boolean addBookingStation(BookingStations bookingStation) {
        String query = "INSERT INTO booking_stations (from_station_name, to_station_name, distance_km) VALUES (?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, bookingStation.getFromStationName());
            pstmt.setString(2, bookingStation.getToStationName());
            pstmt.setDouble(3, bookingStation.getDistanceKm());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Update an existing booking station distance
    public boolean updateBookingStation(BookingStations bookingStation) {
        String query = "UPDATE booking_stations SET distance_km = ? WHERE from_station_name = ? AND to_station_name = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDouble(1, bookingStation.getDistanceKm());
            pstmt.setString(2, bookingStation.getFromStationName());
            pstmt.setString(3, bookingStation.getToStationName());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Delete a booking station distance
    public boolean deleteBookingStation(String fromStationName, String toStationName) {
        String query = "DELETE FROM booking_stations WHERE from_station_name = ? AND to_station_name = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, fromStationName);
            pstmt.setString(2, toStationName);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    
    //////////////////////////////////////////////
    
    //////////////               /////////////////
    
    //////////////////////////////////////////////
    
          // =================== Category Methods ===================

    // Get all categories
    public List<Categories> getCategories() {
        List<Categories> categoriesList = new ArrayList<>();
        String query = "SELECT * FROM categories";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Categories category = new Categories(
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getString("description"),
                        rs.getString("image_path")
                );
                categoriesList.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoriesList;
    }

    // Get a single category by ID
    public Categories getCategory(int categoryId) {
        String query = "SELECT * FROM categories WHERE category_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, categoryId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Categories(
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getString("description"),
                        rs.getString("image_path")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Add a new category
    public boolean addCategory(Categories category) {
        String query = "INSERT INTO categories (category_name, description, image_path) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, category.getCategoryName());
            pstmt.setString(2, category.getDescription());
            pstmt.setString(3, category.getImagePath());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update an existing category
    public boolean updateCategory(Categories category) {
        String query = "UPDATE categories SET category_name = ?, description = ?, image_path = ? WHERE category_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, category.getCategoryName());
            pstmt.setString(2, category.getDescription());
            pstmt.setString(3, category.getImagePath());
            pstmt.setInt(4, category.getCategoryId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a category
    public boolean deleteCategory(int categoryId) {
        String query = "DELETE FROM categories WHERE category_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, categoryId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

  // ================= Notification Methods =================
    
    // Retrieve all notifications
    public List<Notifications> getNotifications() {
        List<Notifications> notifications = new ArrayList<>();
        String query = "SELECT * FROM notifications";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Notifications notification = new Notifications(
                        rs.getInt("notification_id"),
                        rs.getInt("user_id"),
                        rs.getInt("booking_id"),
                        rs.getString("message"),
                        rs.getString("type"),
                        rs.getBoolean("is_read"),
                        rs.getTimestamp("created_at")
                );
                notifications.add(notification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    // Retrieve notifications by user ID
    public List<Notifications> getNotificationsByUserId(int userId) {
        List<Notifications> notifications = new ArrayList<>();
        String query = "SELECT * FROM notifications WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Notifications notification = new Notifications(
                        rs.getInt("notification_id"),
                        rs.getInt("user_id"),
                        rs.getInt("booking_id"),
                        rs.getString("message"),
                        rs.getString("type"),
                        rs.getBoolean("is_read"),
                        rs.getTimestamp("created_at")
                );
                notifications.add(notification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    // Get a single notification by ID
    public Notifications getNotification(int notificationId) {
        String query = "SELECT * FROM notifications WHERE notification_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, notificationId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Notifications(
                        rs.getInt("notification_id"),
                        rs.getInt("user_id"),
                        rs.getInt("booking_id"),
                        rs.getString("message"),
                        rs.getString("type"),
                        rs.getBoolean("is_read"),
                        rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Add a new notification
    public boolean addNotification(Notifications notification) {
        String query = "INSERT INTO notifications (user_id, booking_id, message, type, is_read) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, notification.getUserId());
            pstmt.setInt(2, notification.getBookingId());
            pstmt.setString(3, notification.getMessage());
            pstmt.setString(4, notification.getType());
            pstmt.setBoolean(5, notification.isIsRead());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Update an existing notification (e.g., mark as read)
    public boolean updateNotification(Notifications notification) {
        String query = "UPDATE notifications SET user_id = ?, booking_id = ?, message = ?, type = ?, is_read = ? WHERE notification_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, notification.getUserId());
            pstmt.setInt(2, notification.getBookingId());
            pstmt.setString(3, notification.getMessage());
            pstmt.setString(4, notification.getType());
            pstmt.setBoolean(5, notification.isIsRead());
            pstmt.setInt(6, notification.getNotificationId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a notification
    public boolean deleteNotification(int notificationId) {
        String query = "DELETE FROM notifications WHERE notification_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, notificationId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    

}