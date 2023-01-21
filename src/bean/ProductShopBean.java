package bean;

public class ProductShopBean extends SimpleProductBean {
    private double priceBean;
    private String currencyBean;
    private int shopIdBean;
    private int departmentIdBean;
    private double discountedPriceBean;

    public ProductShopBean() {
        super();
    }

    public double getPrice() {
        return priceBean;
    }

    public void setPrice(double priceBean) {
        this.priceBean = priceBean;
    }

    public String getCurrency() {
        return currencyBean;
    }

    public void setCurrency(String currencyBean) {
        this.currencyBean = currencyBean;
    }

    public int getShopId() {
        return shopIdBean;
    }

    public void setShopId(int shopIdBean) {
        this.shopIdBean = shopIdBean;
    }

    public int getDepartmentId() {
        return departmentIdBean;
    }

    public void setDepartmentId(int departmentIdBean) {
        this.departmentIdBean = departmentIdBean;
    }

    public double getDiscountedPrice() {
        return discountedPriceBean;
    }

    public void setDiscountedPrice(double discountedPriceBean) {
        this.discountedPriceBean = discountedPriceBean;
    }
}
