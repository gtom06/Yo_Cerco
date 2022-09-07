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
            String brand = rs.getString("brand");
            String description = rs.getString("description");
            Double size = rs.getDouble("size");
            String unitOfMeasure = rs.getString("unit_of_measure");
            String logoImagepath = rs.getString("logo_imagepath");
            simpleProduct = new SimpleProduct(sku, name,brand, description, size, unitOfMeasure, logoImagepath);
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
            Double discountedPrice = rs.getDouble("discounted_price");
            String currency = rs.getString("currency");
            Integer availableQuantity = rs.getInt("available_quantity");
            Integer numberOfPurchase = rs.getInt("number_of_purchase");
            Integer shopId = rs.getInt("shop_id");
            String name = rs.getString("name");
            String brand = rs.getString("brand");
            String description = rs.getString("description");
            Double size = rs.getDouble("size");
            String unitOfMeasure = rs.getString("unit_of_measure");
            String logoImagepath = rs.getString("logo_imagepath");
            Integer departmentId = rs.getInt("department_id");
            productShop = new ProductShop( price , discountedPrice, currency,0, availableQuantity, numberOfPurchase,shopId,
                    sku, name, brand, description, size, unitOfMeasure, logoImagepath, departmentId);
            arrayProductShop.add(productShop);
        }
        return arrayProductShop;
    }

    public static boolean isFavoriteProduct(String username, int sku) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        boolean output = false;
        ArrayList<ProductShop> arrayProduct= new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * " +
                    "FROM user_favoriteproduct " +
                    "WHERE sku = ? AND username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, sku);
            stmt.setString(2, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                output = true;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return output;
        }
    }

    public static void removeFavoriteProductFromDb(String username, int sku) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "DELETE FROM user_favoriteproduct " +
                    "WHERE username = ? AND sku = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setInt(2, sku);
            stmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
    }

    public static void insertFavoriteProductIntoDb(String username, int sku ) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "INSERT INTO user_favoriteproduct (username, sku) " +
                    "VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setInt(2, sku);
            stmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
    }

}
