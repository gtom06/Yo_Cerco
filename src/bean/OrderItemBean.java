package bean;

public class OrderItemBean extends ProductShopBean {
    int quantityOrderedBean;
    double priceTotalBean;
    ProductShopBean productShopBean;

    public OrderItemBean() {
        super();
    }

    public int getQuantityOrdered() {
        return quantityOrderedBean;
    }

    public void setQuantityOrdered(int quantityOrderedBean) {
        this.quantityOrderedBean = quantityOrderedBean;
    }

    public double getPriceTotal() {
        return priceTotalBean;
    }

    public void setPriceTotal(double priceTotalBean) {
        this.priceTotalBean = priceTotalBean;
    }

    public ProductShopBean getProductShopBean() {
        return productShopBean;
    }

    public void setProductShopBean(ProductShopBean productShopBean) {
        this.productShopBean = productShopBean;
    }
}
