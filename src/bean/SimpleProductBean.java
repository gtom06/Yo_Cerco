package bean;

public class SimpleProductBean {
    private int skuBean;
    private String nameBean;
    private String brandBean;
    private String descriptionBean;
    private String logoImagepathBean;
    private double sizeBean;
    private String unitOfMeasureBean;

    public SimpleProductBean() {
        //only init
    }

    public int getSku() {
        return skuBean;
    }

    public void setSku(int skuBean) {
        this.skuBean = skuBean;
    }

    public String getName() {
        return nameBean;
    }

    public void setName(String nameBean) {
        this.nameBean = nameBean;
    }

    public String getBrand() {
        return brandBean;
    }

    public void setBrand(String brandBean) {
        this.brandBean = brandBean;
    }

    public String getDescription() {
        return descriptionBean;
    }

    public void setDescription(String descriptionBean) {
        this.descriptionBean = descriptionBean;
    }

    public String getLogoImagepath() {
        return logoImagepathBean;
    }

    public void setLogoImagepath(String logoImagepathBean) {
        this.logoImagepathBean = logoImagepathBean;
    }

    public double getSize() {
        return sizeBean;
    }

    public void setSize(double sizeBean) {
        this.sizeBean = sizeBean;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasureBean;
    }

    public void setUnitOfMeasure(String unitOfMeasureBean) {
        this.unitOfMeasureBean = unitOfMeasureBean;
    }
}
