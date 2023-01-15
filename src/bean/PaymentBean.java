package bean;

import java.sql.Timestamp;

public class PaymentBean {
    private int paymentId;
    private String paymentMethod; //card or cod
    private String cardholder; //name + surname
    private double totalPrice;
    private String currency;
    private Timestamp paymentTimestamp;
    private String status;

    public PaymentBean() {
        //only init
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
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
