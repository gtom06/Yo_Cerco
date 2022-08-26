package model.Product;

public class ProductShop implements Product {
    private final int sku;
    private final double amount;
    private final double discountedAmount;
    private final String currency;
    private double percentOfDiscount;
    private int availableQuantity;
    private int numberOfPurchase;
    private int shopId;
    private final String type;

    public ProductShop(int sku, double amount, double discountedAmount, String currency, double percentOfDiscount, int availableQuantity, int numberOfPurchase, int shopId, String type) {
        this.sku = sku;
        this.amount = amount;
        this.discountedAmount = discountedAmount;
        this.currency = currency;
        this.percentOfDiscount = percentOfDiscount;
        this.availableQuantity = availableQuantity;
        this.numberOfPurchase = numberOfPurchase;
        this.shopId = shopId;
        this.type = type;
    }

    //used in OrderItem
    public ProductShop(int sku, double amount, double discountedAmount, String currency, String type) {
        this.sku = sku;
        this.amount = amount;
        this.discountedAmount = discountedAmount;
        this.currency = currency;
        this.type = type;
    }

    @Override
    public int getSku() {
        return sku;
    }

    @Override
    public String getLogoImagepath() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public String toString() {
        return "ProductShop{" +
                "sku=" + sku +
                ", amount=" + amount +
                ", discountedAmount=" + discountedAmount +
                ", currency='" + currency + '\'' +
                ", percentOfDiscount=" + percentOfDiscount +
                ", availableQuantity=" + availableQuantity +
                ", numberOfPurchase=" + numberOfPurchase +
                ", shopId=" + shopId +
                ", type='" + type + '\'' +
                '}';
    }
}
