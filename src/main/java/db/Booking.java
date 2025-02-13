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
    private String carModel;
    private String driverName;
    private String status;

    // Constructors
    public Booking() {}

    public Booking(int bookingId, int userId, String pickUpStation, String dropOffStation, double distance, String dateTime, int numPassengers, String carModel, String driverName, String status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.pickUpStation = pickUpStation;
        this.dropOffStation = dropOffStation;
        this.distance = distance;
        this.dateTime = dateTime;
        this.numPassengers = numPassengers;
        this.carModel = carModel;
        this.driverName = driverName;
        this.status = status;
    }

    // Getters and Setters
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}