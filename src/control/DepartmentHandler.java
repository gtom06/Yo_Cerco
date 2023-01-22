package control;

import bean.DepartmentBean;
import bean.ShopBean;
import constants.ConstantsExceptions;
import dao.DepartmentDao;
import model.department.Department;
import java.util.ArrayList;
import java.util.List;

public class DepartmentHandler {
    private DepartmentHandler(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    public static List<DepartmentBean> findDepartmentByShop(ShopBean shop) {
        ArrayList<Department> output = (ArrayList<Department>) DepartmentDao.findDepartmentByShop(shop.getShopId());
        ArrayList<DepartmentBean> outputBean = new ArrayList<>();
        if (output.size() > 14) {
            output = new ArrayList<>(output.subList(0, 14));
        }
        for (Department d : output) {
            DepartmentBean departmentBean = new DepartmentBean();
            departmentBean.setDepartmentId(d.getDepartmentId());
            departmentBean.setLogoImagepath(d.getLogoImagepath());
            departmentBean.setName(d.getName());
            departmentBean.setShopId(d.getShopId());
            outputBean.add(departmentBean);
        }
        return !outputBean.isEmpty() ? outputBean : null;
    }
}
