package model.Order;

import model.Product.ProductShop;

public class OrderItem {
    int orderId;
    ProductShop productShop;
    int quantityOrdered;

    public OrderItem(int orderId, ProductShop productShop, int quantityOrdered) {
        this.orderId = orderId;
        this.productShop = productShop;
        this.quantityOrdered = quantityOrdered;
    }
}
