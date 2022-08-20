package model.Order;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Order {
    private int orderId;
    private String username;
    private String payment;
    private Timestamp orderTimestamp;
    private Double totalAmount;
    private String currency;
    private String status;
    private Timestamp collectionTimestamp;
    private int orderTotalQuantity;
    private ArrayList<OrderItem> orderItemArrayList;

    //with OrderItems
    public Order(int orderId, String username, String payment, Timestamp orderTimestamp, Double totalAmount, String currency, String status, Timestamp collectionTimestamp, int orderTotalQuantity, ArrayList<OrderItem> orderItemArrayList) {
        this.orderId = orderId;
        this.username = username;
        this.payment = payment;
        this.orderTimestamp = orderTimestamp;
        this.totalAmount = totalAmount;
        this.currency = currency;
        this.status = status;
        this.collectionTimestamp = collectionTimestamp;
        this.orderTotalQuantity = orderTotalQuantity;
        this.orderItemArrayList = orderItemArrayList;
    }

    //without OrderItems
    public Order(int orderId, String username, String payment, Timestamp orderTimestamp, Double totalAmount, String currency, String status, Timestamp collectionTimestamp) {
        this.orderId = orderId;
        this.username = username;
        this.payment = payment;
        this.orderTimestamp = orderTimestamp;
        this.totalAmount = totalAmount;
        this.currency = currency;
        this.status = status;
        this.collectionTimestamp = collectionTimestamp;
        this.orderTotalQuantity = orderTotalQuantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getUsername() {
        return username;
    }

    public String getPayment() {
        return payment;
    }

    public Timestamp getOrderTimestamp() {
        return orderTimestamp;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getCollectionTimestamp() {
        return collectionTimestamp;
    }

    public ArrayList<OrderItem> getOrderItemArrayList() {
        return orderItemArrayList;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCollectionTimestamp(Timestamp collectionTimestamp) {
        this.collectionTimestamp = collectionTimestamp;
    }

    public void setOrderItemArrayList(ArrayList<OrderItem> orderItemArrayList) {
        this.orderItemArrayList = orderItemArrayList;
    }
}
