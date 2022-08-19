package model.Dao;

import model.Db.DbHelper;
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
            while (rs.next()) {
                ProductShop product;

                Integer sku = rs.getInt("sku");
                double amount = rs.getDouble("price");
                double discountedAmount = rs.getDouble("discounted_amount");
                String currency = rs.getString("currency");
                double percentOfDiscount = rs.getDouble("percent_of_discount");
                Integer availableQuantity = rs.getInt("available_quantity");
                Integer numberOfPurchase = rs.getInt("number_of_purchase");
                Integer shopId = rs.getInt("shop_id");

                product = new ProductShop(sku, amount, discountedAmount, currency, percentOfDiscount, availableQuantity, numberOfPurchase, shopId, null);
                productArrayList.add(product);
            }
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
            while (rs.next()) {
                SimpleProduct simpleProduct;

                Integer sku = rs.getInt("sku");
                name = rs.getString("name");
                String description = rs.getString("description");
                Integer type = rs.getInt("type");
                Double weight = rs.getDouble("weight");
                String logoImagepath = rs.getString("logo_imagepath");

                simpleProduct = new SimpleProduct(sku, name, description, type, weight, logoImagepath);
                System.out.println(simpleProduct);
                productArrayList.add(simpleProduct);
            }
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
            while (rs.next()) {
                SimpleProduct simpleProduct;
                Integer sku = rs.getInt("sku");
                String name = rs.getString("name");
                String description = rs.getString("description");
                Integer type = rs.getInt("type");
                Double weight = rs.getDouble("weight");
                String logoImagepath = rs.getString("logo_imagepath");
                simpleProduct = new SimpleProduct(sku, name, description, type, weight, logoImagepath);
                productArrayList.add(simpleProduct);
            }
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
            while (rs.next()) {
                ProductShop productShop;
                int sku = rs.getInt("sku");
                String currency = rs.getString("currency");
                Double amount = rs.getDouble("amount");
                String type = rs.getString("type");
                Double discountedAmount = rs.getDouble("discounted_amount");
                Double percentOfDiscount = 100*((amount-discountedAmount)/amount);
                int availableQuantity = rs.getInt("available_quantity");
                int numberOfPurchase = rs.getInt("number_of_purchase");

                productShop = new ProductShop(sku, amount, discountedAmount, currency, percentOfDiscount, availableQuantity, numberOfPurchase,shopId,type);
                productArrayList.add(productShop);
            }
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
            while (rs.next()) {
                SimpleProduct simpleProduct;
                Integer sku = rs.getInt("sku");
                String name = rs.getString("name");
                String description = rs.getString("description");
                Integer type = rs.getInt("type");
                Double weight = rs.getDouble("weight");
                String logoImagepath = rs.getString("logo_imagepath");
                simpleProduct = new SimpleProduct(sku, name, description, type, weight, logoImagepath);
                productArrayList.add(simpleProduct);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return productArrayList;
        }
    }
}
