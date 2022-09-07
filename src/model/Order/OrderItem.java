package model.Order;

import model.Product.ProductShop;

public class OrderItem extends ProductShop {
    int quantityOrdered;
    double priceTotal;

    public OrderItem(double price, double discountedPrice, String currency, double percentOfDiscount,
                     int availableQuantity, int numberOfPurchase, int shopId, int sku, String name, String brand,
                     String description, double size, String unitOfMeasure, String logoImagepath, int departmentId,
                     int quantityOrdered, Double priceTotal) {
        super(price,
                discountedPrice,
                currency,
                percentOfDiscount,
                availableQuantity,
                numberOfPurchase,
                shopId,
                sku,
                name,
                brand,
                description,
                size,
                unitOfMeasure,
                logoImagepath,
                departmentId
                );
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
