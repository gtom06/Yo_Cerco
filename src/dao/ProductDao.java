package dao;

import constants.ConstantsExceptions;
import model.db.DbHelper;
import model.product.ProductShop;
import model.product.SimpleProduct;
import model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDao {
    private static final Connection conn = DbHelper.getInstance().getConnection();
    private ProductDao(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    static final Logger logger = Logger.getLogger(ProductDao.class.getName());

    public static List<SimpleProduct> findProductByName(String name) {
        PreparedStatement stmt = null;
        ArrayList<SimpleProduct> productArrayList = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT * FROM product WHERE LOWER(name) LIKE ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + name.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();
            productArrayList = (ArrayList<SimpleProduct>) convertRSInArraySimpleProduct(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.PRODUCT_DAO_ERROR);
        } finally {
            DbHelper.closeStatement(stmt);
        }
        return productArrayList;
    }

    public static List<ProductShop> findProductByDepartmentAndShop(int shopId, int departmentId) {
        PreparedStatement stmt = null;
        ArrayList<ProductShop> arrayProductShop = new ArrayList<>();
        try {
            String sql = "SELECT * FROM product_shop PS JOIN product P ON P.sku = PS.sku WHERE PS.shop_id = ? AND PS.department_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, shopId);
            stmt.setInt(2, departmentId);
            ResultSet rs = stmt.executeQuery();
            arrayProductShop = (ArrayList<ProductShop>) convertRSInArrayProductShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.DEPARTMENT_DAO_ERROR);
        } finally {
            DbHelper.closeStatement(stmt);
        }
        return arrayProductShop;
    }

    public static boolean isFavoriteProduct(String username, int sku) {
        PreparedStatement stmt = null;
        boolean output = false;
        try {
            String sql = "SELECT DISTINCT * FROM user_favoriteproduct WHERE sku = ? AND username = ?";
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
            DbHelper.closeStatement(stmt);
        }
        return output;
    }

    public static void removeFavoriteProductFromDb(String username, int sku) {
        PreparedStatement stmt1 = null;
        try {
            String sql1 = "DELETE FROM user_favoriteproduct WHERE username = ? AND sku = ?";
            stmt1 = conn.prepareStatement(sql1);
            setUsernameAndSku(stmt1, username, sku);
            stmt1.executeUpdate();
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.PRODUCT_DAO_ERROR);
        } finally {
            DbHelper.closeStatement(stmt1);
        }
    }

    public static void insertFavoriteProductIntoDb(String username, int sku ) {
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO user_favoriteproduct (username, sku) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            setUsernameAndSku(stmt, username, sku);
            stmt.executeUpdate();
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.PRODUCT_DAO_ERROR);
        } finally {
            DbHelper.closeStatement(stmt);
        }
    }

    public static List<SimpleProduct> findSimpleProductFromUser(String username) {
        PreparedStatement stmt = null;
        ArrayList<SimpleProduct> simpleProductArrayList = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT * FROM user_favoriteproduct UFP JOIN product P ON P.sku = UFP.sku WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            simpleProductArrayList = (ArrayList<SimpleProduct>) convertRSInArraySimpleProduct(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.PRODUCT_DAO_ERROR);
        } finally {
            DbHelper.closeStatement(stmt);
        }
        return simpleProductArrayList;
    }

    public static List<ProductShop> findProductBySkuAndShopId(int shopId, int sku) {
        PreparedStatement stmt = null;
        ArrayList<ProductShop> productArrayList = new ArrayList<>();
        try {
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
            DbHelper.closeStatement(stmt);
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
            int sku = rs.getInt("sku");
            String name = rs.getString("name");
            String brand = rs.getString("brand");
            String description = rs.getString("description");
            double size = rs.getDouble("size");
            String unitOfMeasure = rs.getString("unit_of_measure");
            String logoImagepath = rs.getString("logo_imagepath");
            simpleProduct = new SimpleProduct(sku, name,brand, description, size, unitOfMeasure, logoImagepath);
            arraySimpleProduct.add(simpleProduct);
        }
        return arraySimpleProduct;
    }

    protected static PreparedStatement setUsernameAndSku(PreparedStatement stmt, String username, int sku) throws SQLException {
        stmt.setString(1, username);
        stmt.setInt(2, sku);
        return stmt;
    }
}
