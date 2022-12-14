package model.product;

public class ProductShop extends SimpleProduct {
    private final double price;
    private final String currency;
    private int shopId;
    private int departmentId;


    private double discountedPrice;


    public ProductShop(double price, String currency, int shopId,
                       int sku, String name, String brand, String description, double size, String unitOfMeasure, String logoImagepath, int departmentId, double discountedPrice) {
        super(sku,
                name,
                brand,
                description,
                size,
                unitOfMeasure,
                logoImagepath);
        this.price = price;
        this.currency = currency;
        this.shopId = shopId;
        this.departmentId = departmentId;
        this.discountedPrice = discountedPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
    
    public double getPrice() {
        return price;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    @Override
    public String toString() {
        return "ProductShop{" +
                "price=" + price +
                ", currency='" + currency + '\'' +
                ", shopId=" + shopId +
                ", departmentId=" + departmentId +
                '}';
    }
}