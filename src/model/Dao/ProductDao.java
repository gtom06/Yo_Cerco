package model.Dao;

import model.ConstantsExceptions;
import model.Db.DbHelper;
import model.Product.ProductShop;
import model.Product.SimpleProduct;
import model.User.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDao {

    private ProductDao(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    static final Logger logger = Logger.getLogger(ProductDao.class.getName());

    public static List<ProductShop> findProductShopByName(String name) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        ArrayList<ProductShop> productArrayList = new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * " +
                    "FROM product_shop " +
                    "WHERE LOWER(name) LIKE ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + name.toLowerCase() + "%");

            ResultSet rs = stmt.executeQuery();
            productArrayList = (ArrayList<ProductShop>) convertRSInArrayProductShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.PRODUCT_DAO_ERROR);
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return productArrayList;
    }

    public static List<SimpleProduct> findProductByName(String name) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        ArrayList<SimpleProduct> productArrayList = new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * " +
                    "FROM product " +
                    "WHERE LOWER(name) LIKE ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + name.toLowerCase() + "%");

            ResultSet rs = stmt.executeQuery();
            productArrayList = (ArrayList<SimpleProduct>) convertRSInArraySimpleProduct(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.PRODUCT_DAO_ERROR);
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return productArrayList;
    }

    public static List<SimpleProduct> findProductByShopId(int shopId) {
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
            productArrayList = (ArrayList<SimpleProduct>) convertRSInArraySimpleProduct(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.PRODUCT_DAO_ERROR);
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return productArrayList;
    }

    public static List<ProductShop> findProductInShop(int shopId) {
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
            productArrayList = (ArrayList<ProductShop>) convertRSInArrayProductShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.PRODUCT_DAO_ERROR);
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return productArrayList;
    }

    public static List<SimpleProduct> findFavoriteShopsFromUser(String username) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        ArrayList<SimpleProduct> productArrayList = new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * " +
                    "FROM shop s JOIN user_favoriteshop ufs " +
                    "ON s.shop_id = ufs.shop_id " +
                    "WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            productArrayList = (ArrayList<SimpleProduct>) convertRSInArraySimpleProduct(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.PRODUCT_DAO_ERROR);
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return productArrayList;
    }

    public static boolean isFavoriteProduct(String username, int sku) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        boolean output = false;
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
            logger.log(Level.WARNING, ConstantsExceptions.PRODUCT_DAO_ERROR);
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return output;
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
            logger.log(Level.WARNING, ConstantsExceptions.PRODUCT_DAO_ERROR);
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
            logger.log(Level.WARNING, ConstantsExceptions.PRODUCT_DAO_ERROR);
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
    }

    public static List<SimpleProduct> findSimpleProductFromUser(User user) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        ArrayList<SimpleProduct> simpleProductArrayList = new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();



            String sql = "SELECT DISTINCT * " +
                    "FROM user_favoriteproduct ufp " +
                    "JOIN product p " +
                    "ON p.sku = ufp.sku " +
                    "WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            ResultSet rs = stmt.executeQuery();
            simpleProductArrayList = (ArrayList<SimpleProduct>) convertRSInArraySimpleProduct(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.PRODUCT_DAO_ERROR);
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return simpleProductArrayList;
    }

    public static List<ProductShop> findProductBySkuAndShopId(int shopId, int sku) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        ArrayList<ProductShop> productArrayList = new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * " +
                    "FROM product P JOIN product_shop PS " +
                    "ON P.sku = PS.sku " +
                    "WHERE shop_id = ? AND PS.sku = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, shopId);
            stmt.setInt(2, sku);
            ResultSet rs = stmt.executeQuery();
            productArrayList = (ArrayList<ProductShop>) convertRSInArrayProductShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.PRODUCT_DAO_ERROR);
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return productArrayList;
    }

    public static List<ProductShop> convertRSInArrayProductShop(ResultSet rs) throws SQLException {
        ProductShop productShop;
        ArrayList<ProductShop> arrayProductShop= new ArrayList<>();
        while (rs.next()) {
            int sku = rs.getInt("sku");
            String name = rs.getString("name");
            int shopId = rs.getInt("shop_id");
            int departmentId = rs.getInt("department_id");
            String location = rs.getString("location");
            double price = rs.getDouble("price");
            String currency = rs.getString("currency");
            String brand = rs.getString("brand");
            String description = rs.getString("description");
            double size = rs.getDouble("size");
            String unitOfMeasure = rs.getString("unit_of_measure");
            String logoImagepath = rs.getString("logo_imagepath");
            double discountedPrice = rs.getDouble("discounted_price");

            productShop = new ProductShop(price,currency, shopId,sku,name,brand,description,
                    size,unitOfMeasure,logoImagepath,departmentId, discountedPrice);
            arrayProductShop.add(productShop);
        }
        return arrayProductShop;
    }

    public static List<SimpleProduct> convertRSInArraySimpleProduct(ResultSet rs) throws SQLException {
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
}
