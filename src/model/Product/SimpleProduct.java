package model.Product;

public class SimpleProduct implements Product {
    private int sku;
    private String name;
    private String description;
    private int type;
    private String logoImagepath;
    private double weight;

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
