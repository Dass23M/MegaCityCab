/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author DELL
 */
public class DBUtilsTest {

    public DBUtilsTest() {
    }

    private static DBUtils dbUtils;

    @BeforeAll
    public static void setUpClass() {
        dbUtils = new DBUtils();
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getConnection method, of class DBUtils.
     */
    @Test
    public void testGetConnection() throws Exception {
        System.out.println("Testing getConnection()");
        DBUtils instance = new DBUtils();
        Connection result = instance.getConnection();

        // Assert that the connection is not null
        assertNotNull(result, "Database connection should not be null");

        // Assert that the connection is valid
        assertTrue(result.isValid(2), "Database connection should be valid");

        // Close the connection after testing
        result.close();
    }

    /**
     * Test of emailExists method, of class DBUtils.
     */
    @Test
    public void testEmailExists() {
        System.out.println("emailExists");
        String email = "test@example.com"; // Provide a real email that exists in the database
        DBUtils instance = new DBUtils();
        boolean result = instance.emailExists(email);

        // Assuming the email exists, assertTrue; otherwise, assertFalse
        assertFalse(result, "Email should not exist in the database for this test.");
    }

    /**
     * Test of registerUser method, of class DBUtils.
     */
    @Test
    public void testRegisterUser() {
        System.out.println("registerUser");

        // Create a valid User object
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("Test@123");  // Plain password (will be hashed)
        user.setPhone("0712345678");
        user.setAddress("123 Test Street");
        user.setNic("123456789V");
        user.setRole("customer");

        DBUtils instance = new DBUtils();
        boolean result = instance.registerUser(user);

        // Check if registration was successful
        assertTrue(result, "User registration should be successful");
    }

    /**
     * Test of validateLogin method, of class DBUtils.
     */
    @Test
    public void testValidateLogin() {
        System.out.println("validateLogin Test Running...");

        // Ensure this user exists in the database with the correct hashed password
        String email = "dasunmethmal23@gmail.com";
        String password = "LLdm2323";

        DBUtils instance = new DBUtils();

        User result = instance.validateLogin(email, password);

        assertNotNull(result, "Login validation failed - user should exist");
        assertEquals(email, result.getEmail(), "Email does not match the expected user");

        System.out.println("Test passed!");
    }

    /**
     * Test of getUsers method, of class DBUtils.
     */
    @Test
    public void testGetUsers() {
        System.out.println("getUsers");
        DBUtils instance = new DBUtils();
        List<User> result = instance.getUsers();
        assertNotNull(result, "User list should not be null");
        assertTrue(result.size() >= 0, "User list should have at least zero users");
    }

    @Test
    public void testGetUser() throws Exception {
        System.out.println("getUser");
        DBUtils instance = new DBUtils();
        int userId = 1; // Ensure a user with ID 1 exists in the database for this test
        User result = instance.getUser(userId);
        assertNotNull(result, "User should be returned if ID exists");
        assertEquals(userId, result.getUserId(), "Returned user ID should match the requested ID");
    }

    @Test
    public void testAddUsera() {
        System.out.println("addUser");
        DBUtils instance = new DBUtils();

        // Creating a sample user
        User user = new User(1, "Test User", "test@example.com", "1234567890", "Test Address", "123456789V", "Customer");

        boolean result = instance.addUser(user);
        assertTrue(result, "User should be added successfully");
    }

    @Test
    public void testUpdateUser() {
        System.out.println("updateUser");
        DBUtils instance = new DBUtils();

        // Updating an existing user
        User user = new User(1, "Updated Name", "updated@example.com", "0987654321", "Updated Address", "987654321V", "Admin");

        boolean result = instance.updateUser(user);
        assertTrue(result, "User update should be successful");
    }

    @Test
    public void testDeleteUser() {
        System.out.println("deleteUser");
        DBUtils instance = new DBUtils();

        int userId = 1; // Ensure this user exists before running this test
        boolean result = instance.deleteUser(userId);
        assertTrue(result, "User should be deleted successfully");
    }

    /**
     * Test of getBookings method, of class DBUtils.
     */
    @Test
    public void testGetBookings() {
        System.out.println("getBookings");
        DBUtils instance = new DBUtils();
        List<Booking> result = instance.getBookings();

        // Check if the result is not null
        assertNotNull(result, "The returned list should not be null");

        // Check if the list has at least one booking (if your DB has data)
        assertFalse(result.isEmpty(), "The returned list should not be empty");
    }

    /**
     * Test of getBooking method, of class DBUtils.
     */
    @Test
    public void testGetBooking() throws Exception {
        System.out.println("getBooking");

        DBUtils instance = new DBUtils();

        // Use a valid booking ID from your database for testing
        int validBookingId = 1;  // Change this to an actual booking ID in your database

        Booking result = instance.getBooking(validBookingId);

        // Check if the result is not null
        assertNotNull(result, "The returned booking should not be null");

        // Optional: Check if the booking ID matches
        assertEquals(validBookingId, result.getBookingId(), "Booking ID should match");
    }

    /**
     * Test of addBookingAndGetId method, of class DBUtils.
     */
    @Test
    public void testAddBookingAndGetId() throws Exception {
        System.out.println("addBookingAndGetId");

        // Create a valid Booking object with test data
        Booking booking = new Booking();
        booking.setUserId(1);
        booking.setPickUpStation("Station A");
        booking.setDropOffStation("Station B");
        booking.setDistance(10.5);
        booking.setDateTime("2025-03-12 10:00:00");
        booking.setNumPassengers(2);
        booking.setCarId(3);
        booking.setDriverId(1);
        booking.setCategoryId(1);
        booking.setStatus("Pending");
        booking.setAlertSent(false);
        booking.setAdminComment("Test Booking");

        DBUtils instance = new DBUtils();
        int result = instance.addBookingAndGetId(booking);

        // Assert that the result is a valid booking ID (greater than 0)
        assertTrue(result > 0, "Booking ID should be greater than 0");
    }

    /**
     * Test of updateBooking method, of class DBUtils.
     */
    @Test
    public void testUpdateBooking() {
        System.out.println("updateBooking");

        // Create a valid Booking object with test data
        Booking booking = new Booking();
        booking.setBookingId(1); // Set a valid booking ID (ensure it exists in your database for testing)
        booking.setUserId(2);
        booking.setPickUpStation("Colombo");
        booking.setDropOffStation("Kandy");
        booking.setDistance(120.5);
        booking.setDateTime("2025-03-12 14:30:00");
        booking.setNumPassengers(3);
        booking.setCarId(5);
        booking.setDriverId(8);
        booking.setCategoryId(2);
        booking.setStatus("Confirmed");
        booking.setAlertSent(false);
        booking.setAdminComment("Updated booking test");

        // Create an instance of DBUtils
        DBUtils instance = new DBUtils();

        // Expected result: the update should be successful (assuming booking exists in DB)
        boolean expResult = true;
        boolean result = instance.updateBooking(booking);

        // Assert that the update operation was successful
        assertEquals(expResult, result);
    }

    /**
     * Test of deleteBooking method, of class DBUtils.
     */
    @Test
    public void testDeleteBooking() throws Exception {
        System.out.println("deleteBooking");

        //  Add a test booking
        Booking booking = new Booking();
        booking.setUserId(1);
        booking.setPickUpStation("Test Station A");
        booking.setDropOffStation("Test Station B");
        booking.setDistance(5.0);
        booking.setDateTime("2025-03-12 12:00:00");
        booking.setNumPassengers(1);
        booking.setCarId(2);
        booking.setDriverId(1);
        booking.setCategoryId(1);
        booking.setStatus("Pending");
        booking.setAlertSent(false);
        booking.setAdminComment("Delete Test Booking");

        DBUtils instance = new DBUtils();
        int bookingId = instance.addBookingAndGetId(booking);
        assertTrue(bookingId > 0, "Booking should be added successfully");

        // Delete the booking
        boolean result = instance.deleteBooking(bookingId);
        assertTrue(result, "Booking should be deleted successfully");

        //  Try deleting the same booking again (should return false)
        boolean secondDeleteAttempt = instance.deleteBooking(bookingId);
        assertFalse(secondDeleteAttempt, "Booking should no longer exist");
    }

    /**
     * Test of getCars method, of class DBUtils.
     */
    @Test
    public void testGetCars() {
        System.out.println("getCars");
        DBUtils instance = new DBUtils();

        List<Car> result = instance.getCars();

        // Check if the result is not null and has at least one entry
        assertNotNull(result, "Car list should not be null");
        assertFalse(result.isEmpty(), "Car list should not be empty");
    }

    /**
     * Test of getCar method, of class DBUtils.
     */
    @Test
    public void testGetCar() throws Exception {
        System.out.println("getCar");

        int carId = 1; // Replace with an actual car ID that exists in your database
        DBUtils instance = new DBUtils();

        Car result = instance.getCar(carId);

        assertNotNull(result, "Car should be found in the database.");
    }

    /**
     * Test of addCar method, of class DBUtils.
     */
    @Test
    public void testAddCar() {
        System.out.println("addCar");
        DBUtils instance = new DBUtils();

        // Create a test car
        Car testCar = new Car(0, "Toyota", "Corolla", "ABC-1234", 4, 500.0, 50.0, 1, 1, "Available");

        boolean result = instance.addCar(testCar);

        // Verify if the car was added successfully
        assertTrue(result, "Car should be added successfully");
    }

    /**
     * Test of updateCar method, of class DBUtils.
     */
    @Test
    public void testUpdateCar() {
        System.out.println("updateCar");
        DBUtils instance = new DBUtils();

        // Create a test car with valid data
        Car testCar = new Car(1, "Toyota", "Corolla", "XYZ-1234", 4, 600.0, 55.0, 1, 1, "Available");

        // First, add the car to ensure it exists in the database
        instance.addCar(testCar);

        // Modify some fields
        testCar.setModel("Camry");
        testCar.setBaseFare(700.0);

        // Now update the car in the database
        boolean result = instance.updateCar(testCar);

        // Assert that the update was successful
        assertTrue(result, "Car should be updated successfully");
    }

    /**
     * Test of deleteCar method, of class DBUtils.
     */
    @Test
    public void testDeleteCar() {
        System.out.println("deleteCar");
        DBUtils instance = new DBUtils();

        // Step 1: Add a test car first
        Car testCar = new Car(0, "Honda", "Civic", "XYZ-5678", 4, 600.0, 55.0, 1, 1, "Available");
        boolean carAdded = instance.addCar(testCar);
        assertTrue(carAdded, "Test car should be added successfully before deletion");

        // Step 2: Retrieve the car ID
        List<Car> cars = instance.getCars();
        int carIdToDelete = -1;
        for (Car car : cars) {
            if (car.getLicensePlate().equals("XYZ-5678")) {
                carIdToDelete = car.getCarId();
                break;
            }
        }
        assertNotEquals(-1, carIdToDelete, "Car ID should be found after adding");

        // Step 3: Delete the car
        boolean result = instance.deleteCar(carIdToDelete);
        assertTrue(result, "Car should be deleted successfully");

    }

    /**
     * Test of getDrivers method, of class DBUtils.
     */
    @Test
    public void testGetDrivers() {
        System.out.println("getDrivers");
        DBUtils instance = new DBUtils();

        List<Driver> result = instance.getDrivers();

        // Ensure that the list is not null
        assertNotNull(result, "The driver list should not be null");

        // Optional: Check if there are drivers in the database
        assertTrue(result.size() >= 0, "Driver list should contain zero or more drivers");
    }

    /**
     * Test of getDriver method, of class DBUtils.
     */
    @Test
    public void testGetDriver() throws Exception {
        System.out.println("getDriver");
        DBUtils instance = new DBUtils();

        // Assume a valid driver ID exists in the database
        int driverId = 1;

        Driver result = instance.getDriver(driverId);

        // If the driver exists, check that it is not null
        assertNotNull(result, "The driver should not be null if it exists in the database");
    }

    /**
     * Test of addDriver method, of class DBUtils.
     */
    @Test
    public void testAddDriver() {
        System.out.println("addDriver");
        DBUtils instance = new DBUtils();

        // Create a new driver object with test data
        Driver driver = new Driver(0, "John Doe", "ABC12345", "0712345678", "Active");

        boolean result = instance.addDriver(driver);

        assertTrue(result, "Driver should be successfully added to the database");
    }

    /**
     * Test of updateDriver method, of class DBUtils.
     */
    @Test
    public void testUpdateDriver() {
        System.out.println("updateDriver");
        DBUtils instance = new DBUtils();

        // Assume an existing driver ID (Update with a real one)
        Driver driver = new Driver(1, "Updated Name", "XYZ98765", "0771234567", "Available");

        boolean result = instance.updateDriver(driver);

        assertTrue(result, "Driver details should be successfully updated");
    }

    /**
     * Test of deleteDriver method, of class DBUtils.
     */
    @Test
    public void testDeleteDriver() {
        System.out.println("deleteDriver");
        DBUtils instance = new DBUtils();

        // Assume an existing driver ID (Replace with a real one)
        int driverId = 1;

        boolean result = instance.deleteDriver(driverId);

        assertTrue(result, "Driver should be successfully deleted from the database");
    }

    /**
     * Test of getBillings method, of class DBUtils.
     */
    @Test
    public void testGetBillings() {
        System.out.println("getBillings");
        DBUtils instance = new DBUtils();
        List<Billing> result = instance.getBillings();

        // Ensure that the result is not null
        assertNotNull(result, "The result list should not be null.");

        // Check if the result list contains at least one entry (if expected)
        assertFalse(result.isEmpty(), "The result list should not be empty.");
    }

    /**
     * Test of getBillingById method, of class DBUtils.
     */
    @Test
    public void testGetBillingById() {
        System.out.println("getBillingById");
        DBUtils instance = new DBUtils();

        // Insert a test billing record first
        Billing billing = new Billing(0, 1, new BigDecimal("50.00"), new BigDecimal("20.00"),
                new BigDecimal("10.00"), new BigDecimal("5.00"),
                new BigDecimal("3.00"), new BigDecimal("78.00"), new Timestamp(System.currentTimeMillis()));

        boolean isAdded = instance.addBilling(billing);
        assertTrue(isAdded, "Failed to add test billing record");

        // Fetch the latest inserted billing record
        List<Billing> billings = instance.getBillings();
        assertFalse(billings.isEmpty(), "Billing list should not be empty after insertion");

        Billing lastBilling = billings.get(billings.size() - 1);
        int billingId = lastBilling.getBillingId(); // Get the actual ID

        // Test fetching the record by ID
        Billing result = instance.getBillingById(billingId);

        // Ensure the result is not null
        assertNotNull(result, "Billing record should not be null.");
        assertEquals(billingId, result.getBillingId(), "Billing ID should match.");
    }

    /**
     * Test of addBilling method, of class DBUtils.
     */
    @Test
    public void testAddBilling() {
        System.out.println("addBilling");
        DBUtils instance = new DBUtils();

        Billing billing = new Billing(0, 1, new BigDecimal("100.00"), new BigDecimal("50.00"),
                new BigDecimal("20.00"), new BigDecimal("10.00"), new BigDecimal("5.00"),
                new BigDecimal("155.00"), new Timestamp(System.currentTimeMillis()));

        boolean result = instance.addBilling(billing);
        assertTrue(result, "Billing record should be added successfully.");
    }

    /**
     * Test of updateBilling method, of class DBUtils.
     */
    @Test
    public void testUpdateBilling() {
        System.out.println("updateBilling");
        DBUtils instance = new DBUtils();

        // Create a new billing record to update
        Billing billing = new Billing(0, 1, new BigDecimal("50.00"), new BigDecimal("20.00"),
                new BigDecimal("10.00"), new BigDecimal("5.00"),
                new BigDecimal("3.00"), new BigDecimal("78.00"), new Timestamp(System.currentTimeMillis()));

        // Add the record first
        boolean isAdded = instance.addBilling(billing);
        assertTrue(isAdded, "Failed to add billing before updating");

        // Retrieve the latest added billing ID
        List<Billing> billings = instance.getBillings();
        Billing lastBilling = billings.get(billings.size() - 1);
        int billingId = lastBilling.getBillingId();

        // Modify billing details
        lastBilling.setBaseFare(new BigDecimal("60.00"));
        lastBilling.setDistanceFare(new BigDecimal("25.00"));
        lastBilling.setTotalFare(new BigDecimal("90.00"));

        // Perform update
        boolean isUpdated = instance.updateBilling(lastBilling);
        assertTrue(isUpdated, "Billing update failed");

        // Retrieve updated billing
        Billing updatedBilling = instance.getBillingById(billingId);
        assertNotNull(updatedBilling, "Updated billing should not be null");
        assertEquals(new BigDecimal("60.00"), updatedBilling.getBaseFare(), "Base fare update failed");
        assertEquals(new BigDecimal("25.00"), updatedBilling.getDistanceFare(), "Distance fare update failed");
        assertEquals(new BigDecimal("90.00"), updatedBilling.getTotalFare(), "Total fare update failed");
    }

    /**
     * Test of deleteBilling method, of class DBUtils.
     */
    /**
     * Test of getBookingStations method, of class DBUtils.
     */
    @Test
    public void testGetBookingStations() {
        System.out.println("Testing getBookingStations");
        List<BookingStations> result = dbUtils.getBookingStations();
        assertNotNull(result, "The result should not be null");
        assertTrue(result.size() >= 0, "The list size should be valid");
    }

    @Test
    public void testAddBookingStation() {
        System.out.println("Testing addBookingStation");
        BookingStations newStation = new BookingStations(0, "Colombo", "Kandy", 115.0);
        boolean result = dbUtils.addBookingStation(newStation);
        assertTrue(result, "New booking station should be added successfully");
    }

    @Test
    public void testGetDistanceBetweenStations() {
        System.out.println("Testing getDistanceBetweenStations");
        BookingStations result = dbUtils.getDistanceBetweenStations("Colombo Fort", "Bambalapitiya");
        assertNotNull(result, "Should return a valid station if exists");
        assertEquals(4.50, result.getDistanceKm(), "Distance should match");
    }

    @Test
    public void testUpdateBookingStation() {
        System.out.println("Testing updateBookingStation");
        BookingStations updatedStation = new BookingStations(0, "Colombo", "Kandy", 120.0);
        boolean result = dbUtils.updateBookingStation(updatedStation);
        assertTrue(result, "Update should be successful");
    }

    @Test
    public void testDeleteBookingStation() {
        System.out.println("Testing deleteBookingStation");
        boolean result = dbUtils.deleteBookingStation("Colombo", "Kandy");
        assertTrue(result, "Delete should be successful");
    }

    @Test
    public void testGetCategories() {
        System.out.println("getCategories");
        DBUtils instance = new DBUtils();
        List<Categories> result = instance.getCategories();

        assertNotNull(result);  // Ensure the result is not null
        assertFalse(result.isEmpty());  // Ensure the list is not empty
    }

    /**
     * Test of getCategory method, of class DBUtils.
     */
    @Test
    public void testGetCategory() {
        DBUtils instance = new DBUtils();
        Categories category = instance.getCategory(1); // Test with a valid category_id
        assertNotNull(category, "Category should not be null for a valid ID");
    }

    /**
     * Test of addCategory method, of class DBUtils.
     */
    @Test
    public void testAddCategory() {
        System.out.println("addCategory");

        // Create a valid category object
        Categories category = new Categories(1, "Test Category", "Test Description", "test_image.jpg");

        DBUtils instance = new DBUtils();

        // Expected result should be true since we expect a successful insert
        boolean result = instance.addCategory(category);

        assertTrue(result, "Category insertion failed when it should succeed");
    }

    /**
     * Test of updateCategory method, of class DBUtils.
     */
    @Test
    public void testUpdateCategory() {
        System.out.println("updateCategory");

        // Creating a valid category object
        Categories category = new Categories(1, "Updated Category", "Updated Description", "updated_image.jpg");

        DBUtils instance = new DBUtils();

        boolean expResult = true; // Expecting a successful update
        boolean result = instance.updateCategory(category);

        assertEquals(expResult, result);
    }

    /**
     * Test of deleteCategory method, of class DBUtils.
     */
    @Test
    public void testDeleteCategory() {
        DBUtils instance = new DBUtils();
        boolean result = instance.deleteCategory(1); // Ensure ID 1 exists before running the test
        assertTrue(result, "Category should be deleted successfully");
        boolean expResult = true;
        assertEquals(expResult, result);
    }

    /**
     * Test of addUser method, of class DBUtils.
     */
//    @org.junit.Test
//    public void testAddUser() {
//        System.out.println("addUser");
//        User user = null;
//        DBUtils instance = new DBUtils();
//        boolean expResult = false;
//        boolean result = instance.addUser(user);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deleteBilling method, of class DBUtils.
//     */
//    @org.junit.Test
//    public void testDeleteBilling() {
//        System.out.println("deleteBilling");
//        int id = 0;
//        DBUtils instance = new DBUtils();
//        boolean expResult = false;
//        boolean result = instance.deleteBilling(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getCategories method, of class DBUtils.
//     */
//    @org.junit.Test
//    public void testGetCategories() {
//        System.out.println("getCategories");
//        DBUtils instance = new DBUtils();
//        List<Categories> expResult = null;
//        List<Categories> result = instance.getCategories();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of getNotifications method, of class DBUtils.
     */
    @org.junit.Test
    public void testGetNotifications() {
        System.out.println("getNotifications");
        DBUtils instance = new DBUtils();
        List<Notifications> expResult = null;
        List<Notifications> result = instance.getNotifications();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNotificationsByUserId method, of class DBUtils.
     */
    @org.junit.Test
    public void testGetNotificationsByUserId() {
        System.out.println("getNotificationsByUserId");
        int userId = 0;
        DBUtils instance = new DBUtils();
        List<Notifications> expResult = null;
        List<Notifications> result = instance.getNotificationsByUserId(userId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNotification method, of class DBUtils.
     */
    @org.junit.Test
    public void testGetNotification() {
        System.out.println("getNotification");
        int notificationId = 0;
        DBUtils instance = new DBUtils();
        Notifications expResult = null;
        Notifications result = instance.getNotification(notificationId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addNotification method, of class DBUtils.
     */
    @org.junit.Test
    public void testAddNotification() {
        System.out.println("addNotification");
        Notifications notification = null;
        DBUtils instance = new DBUtils();
        boolean expResult = false;
        boolean result = instance.addNotification(notification);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateNotification method, of class DBUtils.
     */
    @org.junit.Test
    public void testUpdateNotification() {
        System.out.println("updateNotification");
        Notifications notification = null;
        DBUtils instance = new DBUtils();
        boolean expResult = false;
        boolean result = instance.updateNotification(notification);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteNotification method, of class DBUtils.
     */
    @org.junit.Test
    public void testDeleteNotification() {
        System.out.println("deleteNotification");
        int notificationId = 0;
        DBUtils instance = new DBUtils();
        boolean expResult = false;
        boolean result = instance.deleteNotification(notificationId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
