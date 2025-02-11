/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

/**
 *
 * @author DELL
 */
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Billing {
    private int billingId;
    private int bookingId;
    private BigDecimal baseFare;
    private BigDecimal distanceFare;
    private BigDecimal passengerFare;
    private BigDecimal discount;
    private BigDecimal tax;
    private BigDecimal totalFare;
    private Timestamp date;

    public Billing() {}

    public Billing(int billingId, int bookingId, BigDecimal baseFare, BigDecimal distanceFare, 
                   BigDecimal passengerFare, BigDecimal discount, BigDecimal tax, 
                   BigDecimal totalFare, Timestamp date) {
        this.billingId = billingId;
        this.bookingId = bookingId;
        this.baseFare = baseFare;
        this.distanceFare = distanceFare;
        this.passengerFare = passengerFare;
        this.discount = discount;
        this.tax = tax;
        this.totalFare = totalFare;
        this.date = date;
    }

    public int getBillingId() {
        return billingId;
    }

    public void setBillingId(int billingId) {
        this.billingId = billingId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public BigDecimal getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(BigDecimal baseFare) {
        this.baseFare = baseFare;
    }

    public BigDecimal getDistanceFare() {
        return distanceFare;
    }

    public void setDistanceFare(BigDecimal distanceFare) {
        this.distanceFare = distanceFare;
    }

    public BigDecimal getPassengerFare() {
        return passengerFare;
    }

    public void setPassengerFare(BigDecimal passengerFare) {
        this.passengerFare = passengerFare;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(BigDecimal totalFare) {
        this.totalFare = totalFare;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}