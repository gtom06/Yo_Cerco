package model.product;

public interface Product {
    int getSku();
    String getLogoImagepath();
    String getName();
    String getDescription();
    double getSize();
    String getUnitOfMeasure();
}