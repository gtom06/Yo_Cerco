package model.Dao;

import model.ConstantsExceptions;
import model.Db.DbHelper;
import model.Department.Department;
import model.Product.ProductShop;
import model.Shop.Shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DepartmentDao {
    private DepartmentDao(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    static final Logger logger = Logger.getLogger(DepartmentDao.class.getName());
    public static ArrayList<Department> findDepartmentByShop(int shopId) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
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
            logger.log(Level.WARNING, "error in department");
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return arrayDepartment;
    }

    public static ArrayList<ProductShop> convertRSInArrayProductShop(ResultSet rs) throws SQLException {
        ProductShop productShop;
        ArrayList<ProductShop> arrayProductShop= new ArrayList<>();
        while (rs.next()) {

            Double price = rs.getDouble("price");
            Integer sku = rs.getInt("sku");
            String currency = rs.getString("currency");
            Integer shopId = rs.getInt("shop_id");
            String name = rs.getString("name");
            String brand = rs.getString("brand");
            String description = rs.getString("description");
            Double size = rs.getDouble("size");
            String unitOfMeasure = rs.getString("unit_of_measure");
            String logoImagepath = rs.getString("logo_imagepath");
            Integer departmentId = rs.getInt("department_id");
            Double discountedPrice = rs.getDouble("discounted_price");
            productShop = new ProductShop(
                price,
                currency,
                shopId,
                sku,
                name,
                brand,
                description,
                size,
                unitOfMeasure,
                logoImagepath,
                departmentId,
                    discountedPrice);
            arrayProductShop.add(productShop);
        }
        return arrayProductShop;
    }

    public static ArrayList<ProductShop> findProductByDepartmentAndShop(int shopId, int departmentId) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        ArrayList<ProductShop> arrayProductShop = new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();

            String sql =    "SELECT * FROM product_shop PS " +
                            "JOIN product P " +
                            "ON P.sku = PS.sku " +
                            "WHERE PS.shop_id = ? AND PS.department_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, shopId);
            stmt.setInt(2, departmentId);
            ResultSet rs = stmt.executeQuery();
            arrayProductShop = convertRSInArrayProductShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in department");
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return arrayProductShop;
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
}
