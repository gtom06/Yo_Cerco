package model.Order;

import model.Product.ProductShop;

public class OrderItem {
    ProductShop productShop;
    int quantityOrdered;

    public OrderItem(ProductShop productShop, int quantityOrdered) {
        this.productShop = productShop;
        this.quantityOrdered = quantityOrdered;
    }
}
