package model.Order;

import java.sql.Timestamp;

public class Payment {
    private int paymentId;
    private String last4digits;
    private String mm;
    private String yy;
    private String paymentMethod; //card or cod
    private String cardholder; //name + surname
    private double totalPrice;
    private String currency;
    private Timestamp paymentTimestamp;
    private String status;

    public Payment(int paymentId, String last4digits, String mm, String yy, String paymentMethod, String cardholder, double totalPrice, String currency, Timestamp paymentTimestamp, String status) {
        this.paymentId = paymentId;
        this.last4digits = last4digits;
        this.mm = mm;
        this.yy = yy;
        this.paymentMethod = paymentMethod;
        this.cardholder = cardholder;
        this.totalPrice = totalPrice;
        this.currency = currency;
        this.paymentTimestamp = paymentTimestamp;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", last4digits='" + last4digits + '\'' +
                ", mm='" + mm + '\'' +
                ", yy='" + yy + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", cardholder='" + cardholder + '\'' +
                ", totalPrice=" + totalPrice +
                ", currency='" + currency + '\'' +
                ", paymentTimestamp=" + paymentTimestamp +
                ", status='" + status + '\'' +
                '}';
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getLast4digits() {
        return last4digits;
    }

    public void setLast4digits(String last4digits) {
        this.last4digits = last4digits;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public String getYy() {
        return yy;
    }

    public void setYy(String yy) {
        this.yy = yy;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCardholder() {
        return cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Timestamp getPaymentTimestamp() {
        return paymentTimestamp;
    }

    public void setPaymentTimestamp(Timestamp paymentTimestamp) {
        this.paymentTimestamp = paymentTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
