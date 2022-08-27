package model.Product;

public class ProductShop extends SimpleProduct {
    private final double amount;
    private final double discountedAmount;
    private final String currency;
    private double percentOfDiscount;
    private int availableQuantity;
    private int numberOfPurchase;
    private int shopId;

    public ProductShop(double amount, double discountedAmount, String currency, double percentOfDiscount, int availableQuantity, int numberOfPurchase, int shopId,
                       int sku, String name, String description, int type, double weight, String logoImagepath) {
        super(sku,
                name,
                description,
                type,
                weight,
                logoImagepath);
        this.amount = amount;
        this.discountedAmount = discountedAmount;
        this.currency = currency;
        this.percentOfDiscount = percentOfDiscount;
        this.availableQuantity = availableQuantity;
        this.numberOfPurchase = numberOfPurchase;
        this.shopId = shopId;
    }

    public double getAmount() {
        return amount;
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
    public double getWeight() {
        return 0;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String toString() {
        return "ProductShop{" +
                "amount=" + amount +
                ", discountedAmount=" + discountedAmount +
                ", currency='" + currency + '\'' +
                ", percentOfDiscount=" + percentOfDiscount +
                ", availableQuantity=" + availableQuantity +
                ", numberOfPurchase=" + numberOfPurchase +
                ", shopId=" + shopId +
                '}';
    }
}
