/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

/**
 *
 * @author DELL
 */
import java.sql.Timestamp;

public class Booking {
    private int bookingId;
    private int userId;
    private String pickUpStation;
    private String dropOffStation;
    private double distance;
    private String dateTime;  // storing the DATETIME as a String (e.g., "2025-02-24 06:24:55")
    private int numPassengers;
    private String carModel;
    private String driverName;
    private String categoryName; // category name as text
    private String status;
    private boolean alertSent;
    private String adminComment;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Booking() {}

    public Booking(int bookingId, int userId, String pickUpStation, String dropOffStation, double distance,
                   String dateTime, int numPassengers, String carModel, String driverName, String categoryName,
                   String status, boolean alertSent, String adminComment, Timestamp createdAt, Timestamp updatedAt) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.pickUpStation = pickUpStation;
        this.dropOffStation = dropOffStation;
        this.distance = distance;
        this.dateTime = dateTime;
        this.numPassengers = numPassengers;
        this.carModel = carModel;
        this.driverName = driverName;
        this.categoryName = categoryName;
        this.status = status;
        this.alertSent = alertSent;
        this.adminComment = adminComment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and setters
    public int getBookingId() {
        return bookingId;
    }
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getPickUpStation() {
        return pickUpStation;
    }
    public void setPickUpStation(String pickUpStation) {
        this.pickUpStation = pickUpStation;
    }
    public String getDropOffStation() {
        return dropOffStation;
    }
    public void setDropOffStation(String dropOffStation) {
        this.dropOffStation = dropOffStation;
    }
    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public String getDateTime() {
        return dateTime;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    public int getNumPassengers() {
        return numPassengers;
    }
    public void setNumPassengers(int numPassengers) {
        this.numPassengers = numPassengers;
    }
    public String getCarModel() {
        return carModel;
    }
    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }
    public String getDriverName() {
        return driverName;
    }
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public boolean isAlertSent() {
        return alertSent;
    }
    public void setAlertSent(boolean alertSent) {
        this.alertSent = alertSent;
    }
    public String getAdminComment() {
        return adminComment;
    }
    public void setAdminComment(String adminComment) {
        this.adminComment = adminComment;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
