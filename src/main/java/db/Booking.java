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
    private int pickUpStationId;
    private int dropOffStationId;
    private double distance;
    private String dateTime;
    private int numPassengers;
    private int carId;
    private int driverId;
    private String status;

    // Constructors
    public Booking() {}

    public Booking(int bookingId, int userId, int pickUpStationId, int dropOffStationId, double distance, 
                   String dateTime, int numPassengers, int carId, int driverId, String status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.pickUpStationId = pickUpStationId;
        this.dropOffStationId = dropOffStationId;
        this.distance = distance;
        this.dateTime = dateTime;
        this.numPassengers = numPassengers;
        this.carId = carId;
        this.driverId = driverId;
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

    public int getPickUpStationId() {
        return pickUpStationId;
    }

    public void setPickUpStationId(int pickUpStationId) {
        this.pickUpStationId = pickUpStationId;
    }

    public int getDropOffStationId() {
        return dropOffStationId;
    }

    public void setDropOffStationId(int dropOffStationId) {
        this.dropOffStationId = dropOffStationId;
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

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}