package model.Product;

public class ProductRefrigerated implements Product {
    private final String name;
    private final int sku;
    private String description;
    private String imagePath;

    public ProductRefrigerated(String name, int sku, String description, String imagePath) {
        this.name = name;
        this.sku = sku;
        this.description = description;
        this.imagePath = imagePath;
    }

    @Override
    public int getSku() {
        return 0;
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
}
