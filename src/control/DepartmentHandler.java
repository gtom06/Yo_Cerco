package control;

import model.ConstantsExceptions;
import model.Dao.DepartmentDao;
import model.Department.Department;
import model.Product.ProductShop;
import model.Shop.Shop;

import java.util.ArrayList;
import java.util.List;

public class DepartmentHandler {
    private DepartmentHandler(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    public static List<Department> findDepartmentByShop(Shop shop) {
        ArrayList<Department> output = null;
        output = DepartmentDao.findDepartmentByShop(shop.getShopId());
        if (output.size() > 14) {
            output = new ArrayList<>(output.subList(0, 14));
        }
        return !output.isEmpty() ? output : null;
    }

    public static List<ProductShop> findProductByDepartmentAndShop(Shop shop, Department department){
        ArrayList<ProductShop> output = null;
        output = DepartmentDao.findProductByDepartmentAndShop(shop.getShopId(), department.getDepartmentId());
        if (output.size() > 14) {
            output = new ArrayList<>(output.subList(0, 14));
        }
        return !output.isEmpty() ? output : null;
    }
}
