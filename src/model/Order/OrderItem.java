package model.order;

import model.product.ProductShop;

public class OrderItem extends ProductShop {
    int quantityOrdered;
    double priceTotal;

    public OrderItem(double price, String currency, int shopId, int sku, String name, String brand,
                     String description, double size, String unitOfMeasure, String logoImagepath, int departmentId,
                     int quantityOrdered, Double priceTotal, double discountedPrice) {
        super(price,
                currency,
                shopId,
                sku,
                name,
                brand,
                description,
                size,
                unitOfMeasure,
                logoImagepath,
                departmentId,
                discountedPrice);
        this.quantityOrdered = quantityOrdered;
        this.priceTotal = priceTotal;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "quantityOrdered=" + quantityOrdered +
                ", priceTotal=" + priceTotal +
                '}';
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
}
