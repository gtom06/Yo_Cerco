package control;

import model.Dao.DepartmentDao;
import model.Department.Department;
import model.Shop.Shop;

import java.util.ArrayList;

public class DepartmentHandler {
    public static ArrayList<Department> findDepartmentByShop(Shop shop) {
        ArrayList<Department> output = null;
        output = DepartmentDao.findDepartmentByShop(shop.getShopId());
        if (output.size() > 14) {
            output = new ArrayList<>(output.subList(0, 14));
        }
        System.out.println(output);
        return output.size() != 0 ? output : null;
    }
    public static void findProductFromDepartmentAndShop(Shop shop,String param) {

    }
}
