package control;

import constants.ConstantsExceptions;
import dao.DepartmentDao;
import dao.ProductDao;
import model.department.Department;
import model.product.ProductShop;
import model.shop.Shop;

import java.util.ArrayList;
import java.util.List;

public class DepartmentHandler {
    private DepartmentHandler(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    public static List<Department> findDepartmentByShop(Shop shop) {
        ArrayList<Department> output = (ArrayList<Department>) DepartmentDao.findDepartmentByShop(shop.getShopId());
        if (output.size() > 14) {
            output = new ArrayList<>(output.subList(0, 14));
        }
        return !output.isEmpty() ? output : null;
    }

    public static List<ProductShop> findProductByDepartmentAndShop(Shop shop, Department department){
        ArrayList<ProductShop> output = (ArrayList<ProductShop>) ProductDao.findProductByDepartmentAndShop(shop.getShopId(), department.getDepartmentId());
        if (output.size() > 14) {
            output = new ArrayList<>(output.subList(0, 14));
        }
        return !output.isEmpty() ? output : null;
    }
}
