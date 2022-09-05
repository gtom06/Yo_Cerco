package model.Order;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Order {
    private int orderId;
    private int shopId; //todo: forse meglio inserire private Shop shop???
    private String username;
    private int payment;
    private Timestamp orderTimestamp;
    private Double totalAmount;
    private String currency;
    private String status;
    private Timestamp collectionTimestamp;
    private int orderTotalQuantity;
    private ArrayList<OrderItem> orderItemArrayList;

    public Order(int orderId, int shopId, String username, int payment, Timestamp orderTimestamp, Double totalAmount, String currency, String status, Timestamp collectionTimestamp, int orderTotalQuantity, ArrayList<OrderItem> orderItemArrayList) {
        this.orderId = orderId;
        this.shopId = shopId;
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

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", shopId=" + shopId +
                ", username='" + username + '\'' +
                ", payment='" + payment + '\'' +
                ", orderTimestamp=" + orderTimestamp +
                ", totalAmount=" + totalAmount +
                ", currency='" + currency + '\'' +
                ", status='" + status + '\'' +
                ", collectionTimestamp=" + collectionTimestamp +
                ", orderTotalQuantity=" + orderTotalQuantity +
                ", orderItemArrayList=" + orderItemArrayList +
                '}';
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public Timestamp getOrderTimestamp() {
        return orderTimestamp;
    }

    public void setOrderTimestamp(Timestamp orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCollectionTimestamp() {
        return collectionTimestamp;
    }

    public void setCollectionTimestamp(Timestamp collectionTimestamp) {
        this.collectionTimestamp = collectionTimestamp;
    }

    public int getOrderTotalQuantity() {
        return orderTotalQuantity;
    }

    public void setOrderTotalQuantity(int orderTotalQuantity) {
        this.orderTotalQuantity = orderTotalQuantity;
    }

    public ArrayList<OrderItem> getOrderItemArrayList() {
        return orderItemArrayList;
    }

    public void setOrderItemArrayList(ArrayList<OrderItem> orderItemArrayList) {
        this.orderItemArrayList = orderItemArrayList;
    }
}
