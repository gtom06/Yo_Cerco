package model.product;

import constants.Constants;

public class SimpleProduct implements Product {
    private int sku;
    private String name;
    private String brand;
    private String description;
    private String logoImagepath;
    private double size;
    private String unitOfMeasure;


    public SimpleProduct(int sku, String name, String brand, String description, double size, String unitOfMeasure, String logoImagepath) {
        this.sku = sku;
        this.name = name;
        this.brand = brand;
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
        this.logoImagepath = /*Constants.LOGO_PRODUCTS_PATH +*/ logoImagepath;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public int getSku() {
        return sku;
    }

    @Override
    public String getLogoImagepath() {

        String output = Constants.LOGO_PRODUCTS_PATH;
        if (logoImagepath.isBlank()) {
            output += Constants.LOGO_SHOP_NA;
        } else {
            output += logoImagepath;
        }
        return output;
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