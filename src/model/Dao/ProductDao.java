package model.Dao;

import model.Db.DbHelper;
import model.Department.Department;
import model.Product.ProductShop;
import model.Product.SimpleProduct;
import model.Shop.Shop;
import model.User.User;

import java.sql.*;
import java.util.ArrayList;

public class ProductDao {
    public static ArrayList<ProductShop> findProductShopByName(String name) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        ArrayList<ProductShop> productArrayList = new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * " +
                    "FROM product_shop " +
                    "WHERE name LIKE ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + name.toLowerCase() + "%");

            ResultSet rs = stmt.executeQuery();
            productArrayList = convertRSInArrayProductShop(rs);
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return productArrayList;
        }
    }

    public static ArrayList<SimpleProduct> findProductByName(String name) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        ArrayList<SimpleProduct> productArrayList = new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * " +
                    "FROM product " +
                    "WHERE name LIKE ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + name.toLowerCase() + "%");

            ResultSet rs = stmt.executeQuery();
            productArrayList = convertRSInArraySimpleProduct(rs);
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return productArrayList;
        }
    }

    public static ArrayList<SimpleProduct> findProductByShopId(int shopId) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        ArrayList<SimpleProduct> productArrayList = new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * " +
                    "FROM product P JOIN product_shop PS " +
                    "ON P.sku = PS.sku " +
                    "WHERE shop_id = ? AND qty_on_stock > 0";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, shopId);
            ResultSet rs = stmt.executeQuery();
            productArrayList = convertRSInArraySimpleProduct(rs);
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return productArrayList;
        }
    }

    public static ArrayList<ProductShop> findProductInShop(int shopId) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        ArrayList<ProductShop> productArrayList = new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * " +
                    "FROM product P JOIN product_shop PS " +
                    "ON P.sku = PS.sku " +
                    "WHERE shop_id = ? AND qty_on_stock > 0";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, shopId);
            ResultSet rs = stmt.executeQuery();
            productArrayList = convertRSInArrayProductShop(rs);
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return productArrayList;
        }
    }

    public static ArrayList<SimpleProduct> findFavoriteShopsFromUser(String username) {
        //todo:
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        ArrayList<SimpleProduct> productArrayList = new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * " +
                    "FROM  s JOIN user_favoriteshop ufs " +
                    "ON s.shop_id = ufs.shop_id " +
                    "WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            productArrayList = convertRSInArraySimpleProduct(rs);
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return productArrayList;
        }
    }

    public static ArrayList<SimpleProduct> convertRSInArraySimpleProduct(ResultSet rs) throws SQLException {
        SimpleProduct simpleProduct;
        ArrayList<SimpleProduct> arraySimpleProduct= new ArrayList<>();
        while (rs.next()) {
            Integer sku = rs.getInt("sku");
            String name = rs.getString("name");
            String description = rs.getString("description");
            Double size = rs.getDouble("size");
            String unitOfMeasure = rs.getString("unit_of_measure");
            String logoImagepath = rs.getString("logo_imagepath");
            simpleProduct = new SimpleProduct(sku, name, description, size, unitOfMeasure, logoImagepath);
            arraySimpleProduct.add(simpleProduct);
        }
        return arraySimpleProduct;
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
            String description = rs.getString("description");
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

}
