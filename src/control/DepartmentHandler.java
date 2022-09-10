package control;

import model.Dao.DepartmentDao;
import model.Department.Department;
import model.Product.ProductShop;
import model.Shop.Shop;

import java.util.ArrayList;

public class DepartmentHandler {
    public static ArrayList<Department> findDepartmentByShop(Shop shop) {
        ArrayList<Department> output = null;
        output = DepartmentDao.findDepartmentByShop(shop.getShopId());
        if (output.size() > 14) {
            output = new ArrayList<>(output.subList(0, 14));
        }
        return output.size() != 0 ? output : null;
    }

    public static ArrayList<ProductShop> findProductByDepartmentAndShop(Shop shop, Department department){
        ArrayList<ProductShop> output = null;
        output = DepartmentDao.findProductByDepartmentAndShop(shop.getShopId(), department.getDepartmentId());
        if (output.size() > 14) {
            output = new ArrayList<>(output.subList(0, 14));
        }
        return output.size() != 0 ? output : null;
    }
}
