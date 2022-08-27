package model.Order;

import model.Product.ProductShop;

public class OrderItem {
    ProductShop productShop;
    int quantityOrdered;

    public OrderItem(Integer orderId, ProductShop productShop, int quantityOrdered) {
        this.productShop = productShop;
        this.quantityOrdered = quantityOrdered;
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
                "productShop=" + productShop +
                ", quantityOrdered=" + quantityOrdered +
                '}';
    }
}
