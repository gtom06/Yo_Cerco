package bean;

public class OrderItemBean extends ProductShopBean {
    int quantityOrdered;
    double priceTotal;
    ProductShopBean productShopBean;

    public OrderItemBean() {
        super();
    }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(int quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public ProductShopBean getProductShopBean() {
        return productShopBean;
    }

    public void setProductShopBean(ProductShopBean productShopBean) {
        this.productShopBean = productShopBean;
    }
}
