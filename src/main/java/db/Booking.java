/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

/**
 *
 * @author DELL
 */


public class Booking {
    private int bookingId;
    private int userId;
    private String pickUpStation;
    private String dropOffStation;
    private double distance;
    private String dateTime;
    private int numPassengers;
    private int carId;
    private int driverId; // New field
    private int categoryId;
    private String status;
    private boolean alertSent;
    private String adminComment;
    private String createdAt;
    private String updatedAt;

    public Booking() {}
    // Constructor updated to include driverId
    public Booking(int bookingId, int userId, String pickUpStation, String dropOffStation, double distance,
                   String dateTime, int numPassengers, int carId, int driverId, int categoryId, String status,
                   boolean alertSent, String adminComment, String createdAt, String updatedAt) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.pickUpStation = pickUpStation;
        this.dropOffStation = dropOffStation;
        this.distance = distance;
        this.dateTime = dateTime;
        this.numPassengers = numPassengers;
        this.carId = carId;
        this.driverId = driverId; // Added
        this.categoryId = categoryId;
        this.status = status;
        this.alertSent = alertSent;
        this.adminComment = adminComment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and setters
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getPickUpStation() { return pickUpStation; }
    public void setPickUpStation(String pickUpStation) { this.pickUpStation = pickUpStation; }
    public String getDropOffStation() { return dropOffStation; }
    public void setDropOffStation(String dropOffStation) { this.dropOffStation = dropOffStation; }
    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }
    public String getDateTime() { return dateTime; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }
    public int getNumPassengers() { return numPassengers; }
    public void setNumPassengers(int numPassengers) { this.numPassengers = numPassengers; }
    public int getCarId() { return carId; }
    public void setCarId(int carId) { this.carId = carId; }
    public int getDriverId() { return driverId; } // Added getter
    public void setDriverId(int driverId) { this.driverId = driverId; } // Added setter
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public boolean isAlertSent() { return alertSent; }
    public void setAlertSent(boolean alertSent) { this.alertSent = alertSent; }
    public String getAdminComment() { return adminComment; }
    public void setAdminComment(String adminComment) { this.adminComment = adminComment; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}