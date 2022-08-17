package model.Product;

public class SimpleProduct implements Product {
    private final int sku;
    private final String name;
    private final String description;
    private final int type;
    private final String logoImagepath;
    private final double weight;

    public SimpleProduct(int sku, String name, String description, int type, double weight, String logoImagepath) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.type = type;
        this.weight = weight;
        this.logoImagepath = logoImagepath;
    }
    @Override
    public int getSku() {
        return sku;
    }

    @Override
    public String getLogoImagepath() {
        return logoImagepath;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "SimpleProduct{" +
                "sku=" + sku +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", logoImagepath='" + logoImagepath + '\'' +
                ", weight=" + weight +
                '}';
    }
}
