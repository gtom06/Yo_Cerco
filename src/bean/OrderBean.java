package bean;

import java.sql.Timestamp;
import java.util.List;

public class OrderBean {
    private int orderIdBean = 0;
    private int shopIdBean = 0;
    private String usernameBean;
    private PaymentBean paymentBean;
    private int paymentIdBean;
    private Timestamp orderTimestampBean;
    private double totalPriceBean;
    private String currencyBean;
    private String statusBean;
    private Timestamp collectionTimestampBean;
    private int orderTotalQuantityBean;
    private List<OrderItemBean> orderItemArrayListBean;
    private String orderItemStringBean;

    public OrderBean() {
        //only init
    }

    public int getOrderId() {
        return orderIdBean;
    }

    public void setOrderId(int orderIdBean) {
        this.orderIdBean = orderIdBean;
    }

    public int getShopId() {
        return shopIdBean;
    }

    public void setShopId(int shopIdBean) {
        this.shopIdBean = shopIdBean;
    }

    public String getUsername() {
        return usernameBean;
    }

    public void setUsername(String usernameBean) {
        this.usernameBean = usernameBean;
    }

    public PaymentBean getPayment() {
        return paymentBean;
    }

    public void setPayment(PaymentBean paymentBean) {
        this.paymentBean = paymentBean;
    }

    public int getPaymentId() {
        return paymentIdBean;
    }

    public void setPaymentId(int paymentIdBean) {
        this.paymentIdBean = paymentIdBean;
    }

    public Timestamp getOrderTimestamp() {
        return orderTimestampBean;
    }

    public void setOrderTimestamp(Timestamp orderTimestampBean) {
        this.orderTimestampBean = orderTimestampBean;
    }

    public double getTotalPrice() {
        return totalPriceBean;
    }

    public void setTotalPrice(double totalPriceBean) {
        this.totalPriceBean = totalPriceBean;
    }

    public String getCurrency() {
        return currencyBean;
    }

    public void setCurrency(String currencyBean) {
        this.currencyBean = currencyBean;
    }

    public String getStatus() {
        return statusBean;
    }

    public void setStatus(String statusBean) {
        this.statusBean = statusBean;
    }

    public Timestamp getCollectionTimestamp() {
        return collectionTimestampBean;
    }

    public void setCollectionTimestamp(Timestamp collectionTimestampBean) {
        this.collectionTimestampBean = collectionTimestampBean;
    }

    public int getOrderTotalQuantity() {
        return orderTotalQuantityBean;
    }

    public void setOrderTotalQuantity(int orderTotalQuantityBean) {
        this.orderTotalQuantityBean = orderTotalQuantityBean;
    }

    public List<OrderItemBean> getOrderItemArrayList() {
        return orderItemArrayListBean;
    }

    public void setOrderItemArrayList(List<OrderItemBean> orderItemArrayListBean) {
        this.orderItemArrayListBean = orderItemArrayListBean;
    }

    public String getOrderItemString() {
        return orderItemStringBean;
    }

    public void setOrderItemString(String orderItemStringBean) {
        this.orderItemStringBean = orderItemStringBean;
    }
}
