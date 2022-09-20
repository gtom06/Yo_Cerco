package model.order;

import java.sql.Timestamp;
import java.util.List;

public class Order2 {
    private int orderId;
    private int shopId;
    private String username;
    private Payment payment;
    private int paymentId;
    private Timestamp orderTimestamp;
    private double totalPrice;
    private String currency;
    private String status; //not important during creation
    private Timestamp collectionTimestamp; //not important during creation of order
    private int orderTotalQuantity;
    private List<OrderItem> orderItemArrayList;
    private String orderItemString;

    public Order2(int orderId, int shopId, String username, Payment payment, int orderTotalQuantity, List<OrderItem> orderItemArrayList, String orderItemString) {
        this.orderId = orderId;
        this.shopId = shopId;
        this.username = username;
        this.payment = payment;
        this.orderTotalQuantity = orderTotalQuantity;
        this.orderItemArrayList = orderItemArrayList;
        this.orderItemString = orderItemString;
        this.paymentId = payment.getPaymentId();
        this.orderTimestamp = payment.getPaymentTimestamp();
        this.totalPrice = payment.getTotalPrice();
        this.currency = payment.getCurrency();
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

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
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

    public void setOrderItemArrayList(List<OrderItem> orderItemArrayList) {
        this.orderItemArrayList = orderItemArrayList;
    }

    public String getOrderItemString() {
        return orderItemString;
    }

    public void setOrderItemString(String orderItemString) {
        this.orderItemString = orderItemString;
    }

    @Override
    public String toString() {
        return "Order2{" +
                "orderId=" + orderId +
                ", shopId=" + shopId +
                ", username='" + username + '\'' +
                ", payment=" + payment +
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
}
