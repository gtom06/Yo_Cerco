package model.department;

import constants.Constants;
import model.product.ProductShop;

import java.util.List;

public class Department {
    private final int shopId;
    private final String logoImagepath;
    private final String name;
    private final int departmentId;
    private List<ProductShop> items;


    public Department(int shopId, String logoImagepath, String name, int departmentId) {
        this.shopId = shopId;
        this.logoImagepath = Constants.LOGO_DEPARTMENT_PATH + logoImagepath;
        this.name = name;
        this.departmentId = departmentId;
    }

    public Department(int shopId, String logoImagepath, String name, int departmentId, List<ProductShop> items) {
        this.shopId = shopId;
        this.logoImagepath = Constants.LOGO_DEPARTMENT_PATH + logoImagepath;
        this.name = name;
        this.departmentId = departmentId;
        this.items = items;
    }

    public void setItems(List<ProductShop> items) {
        this.items = items;
    }

    public int getShopId() {
        return shopId;
    }

    public String getLogoImagepath() {
        return logoImagepath;
    }

    public String getName() {
        return name;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public List<ProductShop> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Department{" +
                "shopId=" + shopId +
                ", logoImagepath='" + logoImagepath + '\'' +
                ", name='" + name + '\'' +
                ", departmentId=" + departmentId +
                ", items=" + items +
                '}';
    }
}
