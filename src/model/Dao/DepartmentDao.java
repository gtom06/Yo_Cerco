package model.Dao;

import model.Db.DbHelper;
import model.Department.Department;
import model.Product.ProductShop;
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

            String sql = "SELECT * FROM department D " +
                    "JOIN shop_department SD " +
                    "ON D.department_id = SD.department_id " +
                    "WHERE SD.shop_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, shopId);

            ResultSet rs = stmt.executeQuery();
            arrayDepartment = convertRSInArrayDepartment(rs);
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return arrayDepartment;
        }
    }

    public static ArrayList<Department> convertRSInArrayDepartment(ResultSet rs) throws SQLException {
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

    public static ArrayList<ProductShop> convertRSInArrayProductShop(ResultSet rs) throws SQLException {
        ProductShop productShop;
        ArrayList<ProductShop> arrayProductShop= new ArrayList<>();
        while (rs.next()) {

            Double price = rs.getDouble("price");
            Integer sku = rs.getInt("sku");
            Double discountedAmount = rs.getDouble("discounted_amount");
            String currency = rs.getString("currency");
            Integer availableQuantity = rs.getInt("available_quantity");
            Integer numberOfPurchase = rs.getInt("number_of_purchase");
            Integer shopId = rs.getInt("shop_id");
            String name = rs.getString("name");
            String description = null;
            Double size = rs.getDouble("size");
            String unitOfMeasure = rs.getString("unit_of_measure");
            String logoImagepath = rs.getString("logo_imagepath");
            Integer departmentId = rs.getInt("department_id");
            productShop = new ProductShop( price , discountedAmount, currency,0, availableQuantity, numberOfPurchase,shopId,
                    sku, name, description, size, unitOfMeasure, logoImagepath, departmentId);
            arrayProductShop.add(productShop);
        }
        return arrayProductShop;
    }

    public static ArrayList<ProductShop> findProductByDepartmentAndShop(int shopId, String s) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        Shop shop = null;
        ArrayList<ProductShop> arrayProductShop = new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();

            String sql = "SELECT * FROM product_shop PS " +
                    "JOIN department D " +
                    "ON D.department_id = PS.department_id " +
                    "WHERE PS.shop_id = ? " +
                    "AND D.name = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, shopId);
            stmt.setString(2, s);

            ResultSet rs = stmt.executeQuery();
            arrayProductShop = convertRSInArrayProductShop(rs);

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return arrayProductShop;
        }
    }
}
