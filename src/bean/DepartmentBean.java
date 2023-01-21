package bean;

import java.util.List;

public class DepartmentBean {
    private int shopIdBean;
    private String logoImagepathBean;
    private String nameBean;
    private int departmentIdBean;
    private List<ProductShopBean> itemsBean;

    public DepartmentBean() {
        //only init
    }

    public int getShopId() {
        return shopIdBean;
    }

    public void setShopId(int shopIdBean) {
        this.shopIdBean = shopIdBean;
    }

    public String getLogoImagepath() {
        return logoImagepathBean;
    }

    public void setLogoImagepath(String logoImagepathBean) {
        this.logoImagepathBean = logoImagepathBean;
    }

    public String getName() {
        return nameBean;
    }

    public void setName(String nameBean) {
        this.nameBean = nameBean;
    }

    public int getDepartmentId() {
        return departmentIdBean;
    }

    public void setDepartmentId(int departmentIdBean) {
        this.departmentIdBean = departmentIdBean;
    }

    public List<ProductShopBean> getItems() {
        return itemsBean;
    }

    public void setItems(List<ProductShopBean> itemsBean) {
        this.itemsBean = itemsBean;
    }
}
