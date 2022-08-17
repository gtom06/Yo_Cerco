package model.Department;

public class DepartmentShop {
    private final int shopId;
    private final String logoImagepath;
    private final String name;
    private final int departmentId;

    public DepartmentShop(int shopId, String logoImagepath, String name, int departmentId) {
        this.shopId = shopId;
        this.logoImagepath = logoImagepath;
        this.name = name;
        this.departmentId = departmentId;
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
}
