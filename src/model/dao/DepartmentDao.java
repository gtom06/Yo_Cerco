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
    private static final String SELECT_DISTINCT_ALL = "SELECT DISTINCT * ";
    private static final String SELECT_DISTINCT_ALL_FROM_SHOP = "SELECT DISTINCT * FROM shop ";
    private static final String AND_TYPE ="AND type = ?";
    private static final String TWO_VALUES = "VALUES (?, ?)";
    private static final String WHERE_USERNAME = "WHERE username = ?";
    private static final String AND_TIME = "AND CAST(opening_time AS INT) <= ? AND CAST(closing_time AS INT) >= ? ";
    private static final Connection conn = DbHelper.getInstance().getConnection();
    private DepartmentDao(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    static final Logger logger = Logger.getLogger(DepartmentDao.class.getName());
    public static List<Department> findDepartmentByShop(int shopId) {
        ArrayList<Department> arrayDepartment= new ArrayList<>();
        try {
            String sql = "SELECT * FROM department D " +
                    "JOIN shop_department SD " +
                    "ON D.department_id = SD.department_id " +
                    "WHERE SD.shop_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, shopId);
            ResultSet rs = stmt.executeQuery();

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
