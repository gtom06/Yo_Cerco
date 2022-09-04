package model.Product;

public class ProductShop extends SimpleProduct {
    private final double price;
    private double discountedAmount;
    private final String currency;
    private double percentOfDiscount;
    private int availableQuantity;
    private int numberOfPurchase;
    private int shopId;
    private int departmentId;

    public ProductShop(double price, double discountedAmount, String currency, double percentOfDiscount, int availableQuantity, int numberOfPurchase, int shopId,
                       int sku, String name, String description, double size, String unitOfMeasure, String logoImagepath, int departmentId) {
        super(sku,
                name,
                description,
                size,
                unitOfMeasure,
                logoImagepath);
        this.price = price;
        this.discountedAmount = discountedAmount;
        this.currency = currency;
        this.percentOfDiscount = percentOfDiscount;
        this.availableQuantity = availableQuantity;
        this.numberOfPurchase = numberOfPurchase;
        this.shopId = shopId;
        this.departmentId = departmentId;
    }

    public double getAmount() {
        return price;
    }

    public double getDiscountedAmount() {
        return discountedAmount;
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

    @Override
    public String getLogoImagepath() {
        return super.getLogoImagepath();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public double getSize() {
        return 0;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String toString() {
        return "ProductShop{" +
                "amount=" + price +
                ", discountedAmount=" + discountedAmount +
                ", currency='" + currency + '\'' +
                ", percentOfDiscount=" + percentOfDiscount +
                ", availableQuantity=" + availableQuantity +
                ", numberOfPurchase=" + numberOfPurchase +
                ", shopId=" + shopId +
                '}';
    }
}
