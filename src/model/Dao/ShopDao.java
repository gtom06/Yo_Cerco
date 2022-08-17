package model.Dao;

import model.Constants;
import model.Db.DbHelper;
import model.Department.Department;
import model.Product.ProductShop;
import model.Shop.Shop;

import java.sql.*;
import java.util.ArrayList;

public class ShopDao {

    public static void insertShopDb(Shop shop) {

    }

    public static void deleteShopDb(Shop shop) {

        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        try {
            conn = dbHelper.openDBConnection();

            String sql = "DELETE FROM shop " +
                    "WHERE shop_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, shop.getShopId());
            stmt.executeUpdate();
            sql = "DELETE FROM shop_department " +
                    "WHERE shop_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, shop.getShopId());
            stmt.executeUpdate();
            sql = "DELETE FROM shop_item " +
                    "WHERE shop_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, shop.getShopId());
            stmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
    }

    public static Shop findShopById(int shopId) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();

        Shop shop = null;
        try {
            conn = dbHelper.openDBConnection();

            String sql = "SELECT DISTINCT * " +
                    "FROM shop " +
                    "WHERE shop_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, shopId);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            String address = rs.getString("address").toUpperCase();
            String shopName = rs.getString("name").toUpperCase();
            String logoImagepath = rs.getString("logo_imagepath");
            String interiorPhotosImagepath = rs.getString("interior_photos_imagepath");
            String planimetryImagePath = rs.getString("planimetry_imagepath");
            String city = rs.getString("city").toUpperCase();
            ArrayList<Department> department = null;
            ArrayList<ProductShop> items = null;
            int status = rs.getInt("status");
            String openingTime = rs.getString("opening_time");
            String closingTime = rs.getString("closing_time");

            shop = new Shop(address, city, shopName, logoImagepath, interiorPhotosImagepath, planimetryImagePath, shopId,  status, openingTime, closingTime);
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return shop;
        }
    }

    public static ArrayList<Shop> findShopByCity(String city) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        ArrayList<Shop> arrayShop= new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * " +
                    "FROM shop " +
                    "WHERE city " +
                    "LIKE ? AND status != ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + city.toLowerCase() + "%");
            stmt.setInt(2, Constants.NOT_AVAILABLE);
            ResultSet rs = stmt.executeQuery();
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return arrayShop;
        }
    }

    public static ArrayList<Shop> findShopByName(String name) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();

        Shop shop = null;
        ArrayList<Shop> arrayShop= new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();

            String sql = "SELECT DISTINCT * " +
                    "FROM shop " +
                    "WHERE name LIKE ? AND status != ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + name.toLowerCase() + "%");
            stmt.setInt(2,Constants.NOT_AVAILABLE);

            ResultSet rs = stmt.executeQuery();
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return arrayShop;
        }
    }

    public static ArrayList<Shop> findShopByAddress(String address) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();

        Shop shop = null;
        ArrayList<Shop> arrayShop= new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * " +
                    "FROM shop " +
                    "WHERE address LIKE ? AND status != ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + address.toLowerCase() + "%");
            stmt.setInt(2,Constants.NOT_AVAILABLE);
            ResultSet rs = stmt.executeQuery();
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return arrayShop;
        }
    }

    public static void insertFavoriteShopIntoDb(int shopId, String username) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "INSERT INTO user_favoriteshop (username, shop_id) " +
                    "VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setInt(2, shopId);
            stmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
    }

    public static ArrayList<Shop> findShopByAddressAndTime(String address, Integer time) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();

        Shop shop = null;
        ArrayList<Shop> arrayShop= new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * " +
                    "FROM shop " +
                    "WHERE address LIKE ? AND status != ? AND opening_time <= ? AND closing_time > ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + address.toLowerCase() + "%");
            stmt.setInt(2,Constants.NOT_AVAILABLE);
            stmt.setInt(3,time);
            stmt.setInt(4,time);
            ResultSet rs = stmt.executeQuery();
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return arrayShop;
        }
    }

    public static ArrayList<Shop> findShopByCityAndTime(String city, Integer time) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();

        Shop shop = null;
        ArrayList<Shop> arrayShop= new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * FROM shop WHERE city LIKE ? AND status != ? AND opening_time <= ? AND closing_time > ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + city.toLowerCase() + "%");
            stmt.setInt(2,Constants.NOT_AVAILABLE);
            stmt.setInt(3,time);
            stmt.setInt(4,time);
            ResultSet rs = stmt.executeQuery();
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return arrayShop;
        }
    }

    public static ArrayList<Shop> findShopByNameAndTime(String name, Integer time) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        Shop shop = null;
        ArrayList<Shop> arrayShop= new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * FROM shop WHERE name LIKE ? AND status != ? AND opening_time <= ? AND closing_time > ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + name.toLowerCase() + "%");
            stmt.setInt(2,Constants.NOT_AVAILABLE);
            stmt.setInt(3,time);
            stmt.setInt(4,time);

            ResultSet rs = stmt.executeQuery();
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return arrayShop;
        }
    }

    public static ArrayList<Shop> convertRSInArrayShop(ResultSet rs) throws SQLException {
        Shop shop;
        ArrayList<Shop> arrayShop= new ArrayList<>();
        while (rs.next()) {
            String address = rs.getString("address").toUpperCase();
            String city = rs.getString("city").toUpperCase();
            String shopName = rs.getString("name").toUpperCase();
            String logoImagepath = rs.getString("logo_imagepath");
            String interiorPhotosImagepath = rs.getString("interior_photos_imagepath");
            String planimetryImagePath = rs.getString("planimetry_imagepath");
            int shopId = rs.getInt("shop_id");
            ArrayList<Department> department = null;
            ArrayList<ProductShop> items = null;
            int status = rs.getInt("status");
            String openingTime = rs.getString("opening_time");
            String closingTime = rs.getString("closing_time");
            shop = new Shop(address, city, shopName, logoImagepath, interiorPhotosImagepath, planimetryImagePath, shopId,  status, openingTime, closingTime);
            System.out.println(shop);
            arrayShop.add(shop);
        }
        return arrayShop;
    }

    public static ArrayList<Shop> findShopsWithProducts(ArrayList<Integer> productSkuArrayList) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        Shop shop = null;
        ArrayList<Shop> arrayShop= new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * FROM product_shop PS JOIN shop S on S.shop_id = PS.shop_id WHERE sku IN ";
            System.out.println(sql + DaoHelper.buildSqlStringFromArrayOfIntegers(productSkuArrayList));
            stmt = conn.prepareStatement(sql + DaoHelper.buildSqlStringFromArrayOfIntegers(productSkuArrayList));

            ResultSet rs = stmt.executeQuery();
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return arrayShop;
        }
    }
}
