package bean;

public class SimpleProductBean {
    private int sku;
    private String name;
    private String brand;
    private String description;
    private String logoImagepath;
    private double size;
    private String unitOfMeasure;

    public SimpleProductBean() {
        //only init
    }

    public int getSku() {
        return sku;
    }

    public void setSku(int sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoImagepath() {
        return logoImagepath;
    }

    public void setLogoImagepath(String logoImagepath) {
        this.logoImagepath = logoImagepath;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }
}
