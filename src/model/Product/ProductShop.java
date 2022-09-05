package model.Product;

public class ProductShop extends SimpleProduct {
    private final double price;
    private double discountedPrice;
    private final String currency;
    private double percentOfDiscount;
    private int availableQuantity;
    private int numberOfPurchase;
    private int shopId;
    private int departmentId;

    public ProductShop(double price, double discountedPrice, String currency, double percentOfDiscount, int availableQuantity, int numberOfPurchase, int shopId,
                       int sku, String name, String brand,String description, double size, String unitOfMeasure, String logoImagepath, int departmentId) {
        super(sku,
                name,
                brand,
                description,
                size,
                unitOfMeasure,
                logoImagepath);
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.currency = currency;
        this.percentOfDiscount = percentOfDiscount;
        this.availableQuantity = availableQuantity;
        this.numberOfPurchase = numberOfPurchase;
        this.shopId = shopId;
        this.departmentId = departmentId;
    }


    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public double getPercentOfDiscount() {
        return percentOfDiscount;
    }

    public void setPercentOfDiscount(double percentOfDiscount) {
        this.percentOfDiscount = percentOfDiscount;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public int getNumberOfPurchase() {
        return numberOfPurchase;
    }

    public void setNumberOfPurchase(int numberOfPurchase) {
        this.numberOfPurchase = numberOfPurchase;
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

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
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
                ", discountedPrice=" + discountedPrice +
                ", currency='" + currency + '\'' +
                ", percentOfDiscount=" + percentOfDiscount +
                ", availableQuantity=" + availableQuantity +
                ", numberOfPurchase=" + numberOfPurchase +
                ", shopId=" + shopId +
                ", departmentId=" + departmentId +
                "test=" + super.getBrand() +
                '}';
    }
}