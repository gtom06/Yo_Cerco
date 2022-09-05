package model.Order;

import java.sql.Timestamp;

public class Payment {
    private int paymentId;
    private String last4digits;
    private String mm;
    private String yy;
    private String method; //card or cod
    private String cardholder; //name + surname
    private double totalAmount;
    private Timestamp paymentTimestamp;
    private String status;

    public Payment(int paymentId, String last4digits, String mm, String yy, String method, String cardholder, double totalAmount, Timestamp paymentTimestamp, String status) {
        this.paymentId = paymentId;
        this.last4digits = last4digits;
        this.mm = mm;
        this.yy = yy;
        this.method = method;
        this.cardholder = cardholder;
        this.totalAmount = totalAmount;
        this.paymentTimestamp = paymentTimestamp;
        this.status = status;
    }
}
