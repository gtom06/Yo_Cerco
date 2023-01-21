package bean;

import java.sql.Timestamp;

public class PaymentBean {
    private int paymentIdBean = 0;
    private String paymentMethodBean; //card or cod
    private String cardholderBean; //name + surname
    private double totalPriceBean;
    private String currencyBean;
    private Timestamp paymentTimestampBean;
    private String statusBean;

    public PaymentBean() {
        //only init
    }

    public int getPaymentId() {
        return paymentIdBean;
    }

    public void setPaymentId(int paymentIdBean) {
        this.paymentIdBean = paymentIdBean;
    }

    public String getPaymentMethod() {
        return paymentMethodBean;
    }

    public void setPaymentMethod(String paymentMethodBean) {
        this.paymentMethodBean = paymentMethodBean;
    }

    public String getCardholder() {
        return cardholderBean;
    }

    public void setCardholder(String cardholderBean) {
        this.cardholderBean = cardholderBean;
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

    public Timestamp getPaymentTimestamp() {
        return paymentTimestampBean;
    }

    public void setPaymentTimestamp(Timestamp paymentTimestampBean) {
        this.paymentTimestampBean = paymentTimestampBean;
    }

    public String getStatus() {
        return statusBean;
    }

    public void setStatus(String statusBean) {
        this.statusBean = statusBean;
    }
}
