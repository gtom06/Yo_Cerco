package model.dao;

import model.ConstantsExceptions;
import model.db.DbHelper;
import model.department.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DepartmentDao {
    private DepartmentDao(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    static final Logger logger = Logger.getLogger(DepartmentDao.class.getName());
    public static List<Department> findDepartmentByShop(int shopId) {
        ArrayList<Department> arrayDepartment= new ArrayList<>();
        try {
            ResultSet rs = Queries.findDepartmentByShopQuery(shopId);
            arrayDepartment = (ArrayList<Department>) convertRSInArrayDepartment(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.DEPARTMENT_DAO_ERROR);
        }
        return arrayDepartment;
    }

    public static List<Department> convertRSInArrayDepartment(ResultSet rs) throws SQLException {
        Department department;
        ArrayList<Department> departmentArrayList = new ArrayList<>();
        while (rs.next()) {
            String logoImagepath = rs.getString("logo_imagepath");
            String name = rs.getString("name");
            int departmentId = rs.getInt("department_id");
            int shopId = rs.getInt("shop_id");
            department = new Department(shopId, logoImagepath, name, departmentId);
            departmentArrayList.add(department);
        }
        return departmentArrayList;
    }
}
