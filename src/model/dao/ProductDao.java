package model.dao;

import model.ConstantsExceptions;
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

    private ProductDao(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    static final Logger logger = Logger.getLogger(ProductDao.class.getName());

    public static List<SimpleProduct> findProductByName(String name) {
        ArrayList<SimpleProduct> productArrayList = new ArrayList<>();
        try {
            ResultSet rs = Queries.findProductByNameQuery(name);
            productArrayList = (ArrayList<SimpleProduct>) convertRSInArraySimpleProduct(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.PRODUCT_DAO_ERROR);
        }
        return productArrayList;
    }

    public static List<ProductShop> findProductByDepartmentAndShop(int shopId, int departmentId) {
        ArrayList<ProductShop> arrayProductShop = new ArrayList<>();
        try {
            ResultSet rs = Queries.findProductByDepartmentAndShopQuery(shopId, departmentId);
            arrayProductShop = (ArrayList<ProductShop>) convertRSInArrayProductShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.DEPARTMENT_DAO_ERROR);
        }
        return arrayProductShop;
    }

    public static boolean isFavoriteProduct(String username, int sku) {
        boolean output = false;
        try {
            ResultSet rs = Queries.isFavoriteProductQuery(username, sku);
            if (rs.next()) {
                output = true;
            }
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.PRODUCT_DAO_ERROR);
        }
        return output;
    }

    public static void removeFavoriteProductFromDb(String username, int sku) {
        try {
            Queries.removeFavoriteProductFromDbQuery(username,sku);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.PRODUCT_DAO_ERROR);
        }
    }

    public static void insertFavoriteProductIntoDb(String username, int sku ) {
        try {
            Queries.insertFavoriteProductIntoDbQuery(username, sku);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.PRODUCT_DAO_ERROR);
        }
    }

    public static List<SimpleProduct> findSimpleProductFromUser(User user) {
        ArrayList<SimpleProduct> simpleProductArrayList = new ArrayList<>();
        try {
            ResultSet rs = Queries.findSimpleProductFromUserQuery(user.getUsername());
            simpleProductArrayList = (ArrayList<SimpleProduct>) convertRSInArraySimpleProduct(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.PRODUCT_DAO_ERROR);
        }
        return simpleProductArrayList;
    }

    public static List<ProductShop> findProductBySkuAndShopId(int shopId, int sku) {
        ArrayList<ProductShop> productArrayList = new ArrayList<>();
        try {
            ResultSet rs = Queries.findProductBySkuAndShopIdQuery(shopId, sku);
            productArrayList = (ArrayList<ProductShop>) convertRSInArrayProductShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.PRODUCT_DAO_ERROR);
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
