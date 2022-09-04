package model.Product;

public class SimpleProduct implements Product {
    private int sku;
    private String name;
    private String description;
    private int type;
    private String logoImagepath;
    private double size;
    private String unitOfMeasure;


    public SimpleProduct(int sku, String name, String description, double size, String unitOfMeasure, String logoImagepath) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.size = size;
        this.unitOfMeasure = unitOfMeasure;
        this.logoImagepath = logoImagepath;
    }

    public void setSku(int sku) {
        this.sku = sku;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLogoImagepath(String logoImagepath) {
        this.logoImagepath = logoImagepath;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    @Override
    public int getSku() {
        return 0;
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
    public String getDescription() {
        return description;
    }

    @Override
    public double getSize() {
        return size;
    }

    @Override
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }
}
