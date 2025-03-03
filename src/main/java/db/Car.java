/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

public class Car {
    private int carId;
    private String make;
    private String model;
    private String licensePlate;
    private int capacity;
    private double baseFare;
    private double pricePerKm;
    private int categoryId;
    private int driverId;
    private String status;

    public Car() {}

    public Car(int carId, String make, String model, String licensePlate, int capacity, double baseFare, double pricePerKm, int categoryId, int driverId, String status) {
        this.carId = carId;
        this.make = make;
        this.model = model;
        this.licensePlate = licensePlate;
        this.capacity = capacity;
        this.baseFare = baseFare;
        this.pricePerKm = pricePerKm;
        this.categoryId = categoryId;
        this.driverId = driverId;
        this.status = status;
    }

    public int getCarId() { return carId; }
    public void setCarId(int carId) { this.carId = carId; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public double getBaseFare() { return baseFare; }
    public void setBaseFare(double baseFare) { this.baseFare = baseFare; }

    public double getPricePerKm() { return pricePerKm; }
    public void setPricePerKm(double pricePerKm) { this.pricePerKm = pricePerKm; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public int getDriverId() { return driverId; }
    public void setDriverId(int driverId) { this.driverId = driverId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}