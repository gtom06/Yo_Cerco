package model.Order;

import model.Product.ProductShop;

public class OrderItem {
    Integer orderId;
    ProductShop productShop;
    int quantityOrdered;

    public OrderItem(Integer orderId, ProductShop productShop, int quantityOrdered) {
        this.orderId = orderId;
        this.productShop = productShop;
        this.quantityOrdered = quantityOrdered;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public ProductShop getProductShop() {
        return productShop;
    }

    public void setProductShop(ProductShop productShop) {
        this.productShop = productShop;
    }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(int quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderId=" + orderId +
                ", productShop=" + productShop +
                ", quantityOrdered=" + quantityOrdered +
                '}';
    }
}
