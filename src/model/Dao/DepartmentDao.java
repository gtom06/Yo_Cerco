package model.Dao;

import model.Db.DbHelper;
import model.Department.Department;
import model.Shop.Shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DepartmentDao {
    public static ArrayList<Department> findDepartmentByShop(int shopId) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        Shop shop = null;
        ArrayList<Department> arrayDepartment= new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();

            String sql = "SELECT * FROM department\n" +
                    "WHERE department_id in(\n" +
                    "SELECT department_id FROM shop_department WHERE shop_id = ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, shopId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String logoImagepath = rs.getString("logo_imagepath");
                String name = rs.getString("name");
                int departmentId = rs.getInt("department_id");
                shopId = rs.getInt("shop_id");
                Department department = new Department(shopId, logoImagepath, name, departmentId);
                arrayDepartment.add(department);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return arrayDepartment;
        }
    }
}
