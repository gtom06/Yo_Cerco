package model.Order;

import java.sql.Date;

public class Order {
    private int orderId;
    private String username;
    private String payment;
    private Date orderDate;
    private Double totalAmount;
    private String currency;
    private String status;
    private Date collectionDate;

    public Order(int orderId, String username, String payment, Date orderDate, Double totalAmount, String currency, String status, Date collectionDate) {
        this.orderId = orderId;
        this.username = username;
        this.payment = payment;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.currency = currency;
        this.status = status;
        this.collectionDate = collectionDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
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

    public Date getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(Date collectionDate) {
        this.collectionDate = collectionDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", username='" + username + '\'' +
                ", payment='" + payment + '\'' +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", currency='" + currency + '\'' +
                ", status='" + status + '\'' +
                ", collectionDate=" + collectionDate +
                '}';
    }
}
