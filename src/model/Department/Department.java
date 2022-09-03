package model.Department;

import model.Constants;
import model.Product.ProductShop;

import java.util.ArrayList;

public class Department {
    private final int shopId;
    private final String logoImagepath;
    private final String name;
    private final int departmentId;
    private ArrayList<ProductShop> items;

    public Department(int shopId, String logoImagepath, String name, int departmentId) {
        this.shopId = shopId;
        this.logoImagepath = Constants.LOGO_DEPARTMENT_PATH + logoImagepath;
        this.name = name;
        this.departmentId = departmentId;
    }

    public Department(int shopId, String logoImagepath, String name, int departmentId, ArrayList<ProductShop> items) {
        this.shopId = shopId;
        this.logoImagepath = logoImagepath;
        this.name = name;
        this.departmentId = departmentId;
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

    public ArrayList<ProductShop> getItems() {
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
