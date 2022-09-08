package model.Product;

public class ProductShop extends SimpleProduct {
    private final double price;
    private final String currency;
    private int shopId;
    private int departmentId;

    public ProductShop(double price, String currency, int shopId,
                       int sku, String name, String brand,String description, double size, String unitOfMeasure, String logoImagepath, int departmentId) {
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

    public String getLogoImagepath() {
        return super.getLogoImagepath();
    }

    public String getName() {
        return super.getName();
    }

    public double getSize() {
        return super.getSize();
    }

    public String getDescription() {
        return super.getDescription();
    }

    public String getUnitOfMeasure() {
        return super.getUnitOfMeasure();
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