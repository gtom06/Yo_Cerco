package model.Order;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private int shopId;
    private String username;
    private int paymentId;
    private Timestamp orderTimestamp;
    private double totalPrice;
    private String currency;
    private String status;
    private Timestamp collectionTimestamp;
    private int orderTotalQuantity;
    private List<OrderItem> orderItemArrayList;
    private String orderItemString;

    public Order(int orderId, int shopId, String username, int paymentId, Timestamp orderTimestamp, double totalPrice, String currency, String status, Timestamp collectionTimestamp, int orderTotalQuantity, List<OrderItem> orderItemArrayList, String orderItemString) {
        this.orderId = orderId;
        this.shopId = shopId;
        this.username = username;
        this.paymentId = paymentId;
        this.orderTimestamp = orderTimestamp;
        this.totalPrice = totalPrice;
        this.currency = currency;
        this.status = status;
        this.collectionTimestamp = collectionTimestamp;
        this.orderTotalQuantity = orderTotalQuantity;
        this.orderItemArrayList = orderItemArrayList;
        this.orderItemString = orderItemString;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", shopId=" + shopId +
                ", username='" + username + '\'' +
                ", paymentId=" + paymentId +
                ", orderTimestamp=" + orderTimestamp +
                ", totalPrice=" + totalPrice +
                ", currency='" + currency + '\'' +
                ", status='" + status + '\'' +
                ", collectionTimestamp=" + collectionTimestamp +
                ", orderTotalQuantity=" + orderTotalQuantity +
                ", orderItemArrayList=" + orderItemArrayList +
                ", orderItemString='" + orderItemString + '\'' +
                '}';
    }

    public String getOrderItemString() {
        return orderItemString;
    }

    public void setOrderItemString(String orderItemString) {
        this.orderItemString = orderItemString;
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

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Timestamp getOrderTimestamp() {
        return orderTimestamp;
    }

    public void setOrderTimestamp(Timestamp orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
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

    public List<OrderItem> getOrderItemArrayList() {
        return orderItemArrayList;
    }

    public void setOrderItemArrayList(ArrayList<OrderItem> orderItemArrayList) {
        this.orderItemArrayList = orderItemArrayList;
    }
}
