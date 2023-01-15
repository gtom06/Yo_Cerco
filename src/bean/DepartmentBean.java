package bean;

import java.util.List;

public class DepartmentBean {
    private int shopId;
    private String logoImagepath;
    private String name;
    private int departmentId;
    private List<ProductShopBean> items;

    public DepartmentBean() {
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getLogoImagepath() {
        return logoImagepath;
    }

    public void setLogoImagepath(String logoImagepath) {
        this.logoImagepath = logoImagepath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public List<ProductShopBean> getItems() {
        return items;
    }

    public void setItems(List<ProductShopBean> items) {
        this.items = items;
    }
}
