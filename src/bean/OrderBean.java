package bean;

import java.sql.Timestamp;
import java.util.List;

public class OrderBean {
    private int orderId = 0;
    private int shopId = 0;
    private String username;
    private PaymentBean payment;
    private int paymentId;
    private Timestamp orderTimestamp;
    private double totalPrice;
    private String currency;
    private String status;
    private Timestamp collectionTimestamp;
    private int orderTotalQuantity;
    private List<OrderItemBean> orderItemArrayList;
    private String orderItemString;

    public OrderBean() {
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

    public PaymentBean getPayment() {
        return payment;
    }

    public void setPayment(PaymentBean payment) {
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

    public List<OrderItemBean> getOrderItemArrayList() {
        return orderItemArrayList;
    }

    public void setOrderItemArrayList(List<OrderItemBean> orderItemArrayList) {
        this.orderItemArrayList = orderItemArrayList;
    }

    public String getOrderItemString() {
        return orderItemString;
    }

    public void setOrderItemString(String orderItemString) {
        this.orderItemString = orderItemString;
    }
}
