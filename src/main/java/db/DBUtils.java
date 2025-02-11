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

/**
 *
 * @author icbt
 */
public class DBUtils {

    static final String DB_URL = "jdbc:mysql://localhost:3306/megacitycab";
    static final String USER = "root";
    static final String PASS = "";
    
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
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
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
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
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
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
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

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE is_active = TRUE"; // Added condition to return only active users
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
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

    public User getUserById(int id) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUser(User user) {
        String query = "UPDATE users SET username=?, email=?, phone=?, address=?, nic=?, role=? WHERE user_id=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getNic());
            stmt.setString(6, user.getRole());
            stmt.setInt(7, user.getUserId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int id) {
        String query = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    ////////////////////////////////////////////////////
             // BOOKING //
    /////////////////////////////////////////////////////
    
    
      public List<Booking> getBookings() {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                bookings.add(new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("user_id"),
                        rs.getInt("pick_up_station_id"),
                        rs.getInt("drop_off_station_id"),
                        rs.getDouble("distance"),
                        rs.getString("date_time"),
                        rs.getInt("num_passengers"),
                        rs.getInt("car_id"),
                        rs.getInt("driver_id"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    // Method to get a single booking by ID
    public Booking getBooking(int id) {
        Booking booking = null;
        String query = "SELECT * FROM bookings WHERE booking_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                booking = new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("user_id"),
                        rs.getInt("pick_up_station_id"),
                        rs.getInt("drop_off_station_id"),
                        rs.getDouble("distance"),
                        rs.getString("date_time"),
                        rs.getInt("num_passengers"),
                        rs.getInt("car_id"),
                        rs.getInt("driver_id"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }

    // Method to add a booking
    public boolean addBooking(Booking booking) {
        String query = "INSERT INTO bookings (user_id, pick_up_station_id, drop_off_station_id, distance, date_time, num_passengers, car_id, driver_id, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getPickUpStationId());
            stmt.setInt(3, booking.getDropOffStationId());
            stmt.setDouble(4, booking.getDistance());
            stmt.setString(5, booking.getDateTime());
            stmt.setInt(6, booking.getNumPassengers());
            stmt.setInt(7, booking.getCarId());
            stmt.setInt(8, booking.getDriverId());
            stmt.setString(9, booking.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to update a booking
    public boolean updateBooking(Booking booking) {
        String query = "UPDATE bookings SET user_id=?, pick_up_station_id=?, drop_off_station_id=?, distance=?, date_time=?, num_passengers=?, car_id=?, driver_id=?, status=? WHERE booking_id=?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getPickUpStationId());
            stmt.setInt(3, booking.getDropOffStationId());
            stmt.setDouble(4, booking.getDistance());
            stmt.setString(5, booking.getDateTime());
            stmt.setInt(6, booking.getNumPassengers());
            stmt.setInt(7, booking.getCarId());
            stmt.setInt(8, booking.getDriverId());
            stmt.setString(9, booking.getStatus());
            stmt.setInt(10, booking.getBookingId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to delete a booking
    public boolean deleteBooking(int id) {
        String query = "DELETE FROM bookings WHERE booking_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    
    ///////////////////////////////////////////////
    
    //////////////// CAR ///////////////////////
    
    ////////////////////////////////////////////////
    
    
     public List<Car> getCars() {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM cars";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                cars.add(new Car(
                    rs.getInt("car_id"),
                    rs.getString("make"),
                    rs.getString("model"),
                    rs.getString("license_plate"),
                    rs.getInt("capacity"),
                    rs.getDouble("base_fare"),
                    rs.getDouble("price_per_km"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

    public Car getCar(int carId) throws SQLException {
        String query = "SELECT * FROM cars WHERE car_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, carId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Car(
                    rs.getInt("car_id"),
                    rs.getString("make"),
                    rs.getString("model"),
                    rs.getString("license_plate"),
                    rs.getInt("capacity"),
                    rs.getDouble("base_fare"),
                    rs.getDouble("price_per_km"),
                    rs.getString("status")
                );
            }
        }
        return null;
    }

    public boolean addCar(Car car) {
        String query = "INSERT INTO cars (make, model, license_plate, capacity, base_fare, price_per_km, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, car.getMake());
            pstmt.setString(2, car.getModel());
            pstmt.setString(3, car.getLicensePlate());
            pstmt.setInt(4, car.getCapacity());
            pstmt.setDouble(5, car.getBaseFare());
            pstmt.setDouble(6, car.getPricePerKm());
            pstmt.setString(7, car.getStatus());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean updateCar(Car car) {
    String query = "UPDATE cars SET make = ?, model = ?, license_plate = ?, capacity = ?, base_fare = ?, price_per_km = ?, status = ? WHERE car_id = ?";

    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, car.getMake());
        pstmt.setString(2, car.getModel());
        pstmt.setString(3, car.getLicensePlate());
        pstmt.setInt(4, car.getCapacity());
        pstmt.setDouble(5, car.getBaseFare());
        pstmt.setDouble(6, car.getPricePerKm());
        pstmt.setString(7, car.getStatus());
        pstmt.setInt(8, car.getCarId());

        return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}

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

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

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
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
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

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

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

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

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
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, driverId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}




