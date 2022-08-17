package control;

import model.Department.Department;
import model.Dao.DepartmentDao;

import java.util.ArrayList;

public class DepartmentHandler {
    public static ArrayList<Department> findDepartmentByShop(int shopId) {
        ArrayList<Department> output = null;
        output = DepartmentDao.findDepartmentByShop(shopId);
        return output.size() != 0 ? output : null;
    }
    public static ArrayList<Department> findDepartmentByShopForTable(int shopId) {
        //if DepartmentDao.findDepartmentByShop().size()
        return DepartmentDao.findDepartmentByShop(shopId);
    }

}
