package model.Order;

import java.sql.Timestamp;

public class Payment {
    private String last4digits;
    private String mmyy;
    private String method; //visa, mastercard, cashOnDelivery
    private String cardholder; //name + surname
    private double totalAmount;
    private Timestamp paymentTimestamp;
    private String status;
}
