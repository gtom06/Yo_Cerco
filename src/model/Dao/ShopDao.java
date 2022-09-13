package model.Dao;

import model.Constants;
import model.Db.DbHelper;
import model.Department.Department;
import model.Product.ProductShop;
import model.Product.SimpleProduct;
import model.Shop.Shop;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShopDao {
    static Logger logger = Logger.getLogger(ShopDao.class.getName());

    public static void insertShopDb(Shop shop) {
    }

    public static void deleteShopDb(Shop shop) {
    }

    public static ArrayList<Shop> findShopById(int shopId) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        ArrayList<Shop> arrayShop= new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();

            String sql = "SELECT DISTINCT * " +
                    "FROM shop " +
                    "WHERE shop_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, shopId);

            ResultSet rs = stmt.executeQuery();
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in findShop");
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return arrayShop;
    }

    public static ArrayList<Shop> findShopByCity(String city, String type) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        ArrayList<Shop> arrayShop= new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * " +
                    "FROM shop " +
                    "WHERE LOWER(city) " +
                    "LIKE ? AND status != ? ";
            if (!type.equals(Constants.SHOP_TYPE.get(0))){
                sql += "AND type = ?";
            }
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + city.toLowerCase() + "%");
            stmt.setInt(2, Constants.NOT_AVAILABLE);
            if (!type.equals(Constants.SHOP_TYPE.get(0))){
                stmt.setString(3,type);
            }
            ResultSet rs = stmt.executeQuery();
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in findShop");
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return arrayShop;
    }

    public static ArrayList<Shop> findShopByName(String name, String type) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();

        ArrayList<Shop> arrayShop= new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();

            String sql = "SELECT DISTINCT * " +
                    "FROM shop " +
                    "WHERE LOWER(name) LIKE ? AND status != ? ";
            if (!type.equals(Constants.SHOP_TYPE.get(0))){
                sql += "AND type = ?";
            }
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + name.toLowerCase() + "%");
            stmt.setInt(2,Constants.NOT_AVAILABLE);
            if (!type.equals(Constants.SHOP_TYPE.get(0))){
                stmt.setString(3,type);
            }
            ResultSet rs = stmt.executeQuery();
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in findShop");
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return arrayShop;
    }

    public static ArrayList<Shop> findShopNearby(double lat, double lng, String type) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();

        ArrayList<Shop> arrayShop= new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * " +
                    "FROM shop " +
                    "WHERE  ? < latitude " +
                    "AND latitude < ? " +
                    "AND ? < longitude " +
                    "AND longitude < ? " +
                    "AND status != ? ";
            if (!type.equals(Constants.SHOP_TYPE.get(0))){
                sql += "AND type = ?";
            }
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, lat - 0.5);
            stmt.setDouble(2, lat + 0.5);
            stmt.setDouble(3, lng - 0.5);
            stmt.setDouble(4, lng + 0.5);
            stmt.setInt(5,Constants.NOT_AVAILABLE);
            if (!type.equals(Constants.SHOP_TYPE.get(0))){
                stmt.setString(6,type);
            }
            ResultSet rs = stmt.executeQuery();
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in findShop");
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return arrayShop;
    }

    public static ArrayList<Shop> findShoNearbyAndTime(Double lat, Double lng, Integer time, String type) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();

        Shop shop = null;
        ArrayList<Shop> arrayShop= new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * " +
                    "FROM shop " +
                    "WHERE  ? < latitude " +
                    "AND latitude < ? " +
                    "AND ? < longitude " +
                    "AND longitude < ? " +
                    "AND status != ?" +
                    "AND CAST(opening_time AS INT) <= ? AND CAST(closing_time AS INT) > ? ";
            if (!type.equals(Constants.SHOP_TYPE.get(0))){
                sql += "AND type = ?";
            }
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, lat - 0.5);
            stmt.setDouble(2, lat + 0.5);
            stmt.setDouble(3, lng - 0.5);
            stmt.setDouble(4, lng + 0.5);
            stmt.setInt(5,Constants.NOT_AVAILABLE);
            stmt.setInt(6,time);
            stmt.setInt(7,time);
            if (!type.equals(Constants.SHOP_TYPE.get(0))){
                stmt.setString(8,type);
            }
            ResultSet rs = stmt.executeQuery();
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in findShop");
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return arrayShop;
    }

    public static ArrayList<Shop> findShopByCityAndTime(String city, Integer time, String type) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();

        Shop shop = null;
        ArrayList<Shop> arrayShop= new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * " +
                    "FROM shop " +
                    "WHERE LOWER(city) LIKE ? " +
                    "AND status != ? " +
                    "AND CAST(opening_time AS INT) <= ? " +
                    "AND CAST(closing_time AS INT) > ? ";
            if (!type.equals(Constants.SHOP_TYPE.get(0))){
                sql += "AND type = ?";
            }
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + city.toLowerCase() + "%");
            stmt.setInt(2,Constants.NOT_AVAILABLE);
            stmt.setInt(3,time);
            stmt.setInt(4,time);
            if (!type.equals(Constants.SHOP_TYPE.get(0))){
                stmt.setString(5,type);
            }
            ResultSet rs = stmt.executeQuery();
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in findShop");
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return arrayShop;
    }

    public static ArrayList<Shop> findShopByFavouriteUser(String username) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();

        //Shop shop = null;
        ArrayList<Shop> arrayShop= new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * " +
                    "FROM shop s JOIN user_favoriteshop ufs " +
                    "ON s.shop_id = ufs.shop_id " +
                    "WHERE LOWER(username) = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username.toLowerCase());
            ResultSet rs = stmt.executeQuery();
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in findShop");
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return arrayShop;
    }

    public static ArrayList<Shop> findShopByNameAndTime(String name, Integer time, String type) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        Shop shop = null;
        ArrayList<Shop> arrayShop= new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * FROM shop " +
                    "WHERE LOWER(name) LIKE ? " +
                    "AND status != ? " +
                    "AND CAST(opening_time AS INT) <= ? " +
                    "AND CAST(closing_time AS INT) > ? ";
            if (!type.equals(Constants.SHOP_TYPE.get(0))){
                sql += "AND type = ?";
            }
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + name.toLowerCase() + "%");
            stmt.setInt(2,Constants.NOT_AVAILABLE);
            stmt.setInt(3,time);
            stmt.setInt(4,time);
            if (!type.equals(Constants.SHOP_TYPE.get(0))){
                stmt.setString(5,type);
            }

            ResultSet rs = stmt.executeQuery();
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in findShop");
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
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
            String sql = "SELECT DISTINCT * " +
                    "FROM product_shop PS " +
                    "JOIN shop S on S.shop_id = PS.shop_id " +
                    "WHERE sku IN ";
            stmt = conn.prepareStatement(sql + DaoHelper.buildSqlStringFromArrayOfIntegers(productSkuArrayList));

            ResultSet rs = stmt.executeQuery();
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in findShop");
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return arrayShop;
    }

    public static ArrayList<Shop> findShopsByProduct(SimpleProduct simpleProduct) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        Shop shop = null;
        ArrayList<Shop> arrayShop= new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * " +
                    "FROM shop S " +
                    "JOIN product_shop PS " +
                    "ON S.shop_id = PS.shop_id "+
                    "JOIN product p " +
                    "ON PS.sku = p.sku " +
                    "WHERE p.sku = ?";
            stmt = conn.prepareStatement(sql );
            stmt.setInt(1, simpleProduct.getSku());
            ResultSet rs = stmt.executeQuery();
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in findShop");
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return arrayShop;
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
            logger.log(Level.WARNING, "error in findShop");
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
    }

    public static void removeFavoriteShopFromDb(int shopId, String username) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "DELETE FROM user_favoriteshop " +
                    "WHERE LOWER(username) = ? AND shop_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username.toLowerCase());
            stmt.setInt(2, shopId);
            stmt.executeUpdate();
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in findShop");
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
    }

    public static boolean isFavoriteShop(int shopId, String username) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        boolean output = false;
        ArrayList<Shop> arrayShop= new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * " +
                    "FROM user_favoriteshop " +
                    "WHERE shop_id = ? AND LOWER(username) = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, shopId);
            stmt.setString(2, username.toLowerCase());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                output = true;
            }
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in findShop");
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return output;
    }

    public static ArrayList<String> findTypeShop() {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        ArrayList<String> arrayTypeShop= new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT type " +
                    "FROM shop " +
                    "WHERE type IS NOT NULL ";
            stmt = conn.prepareStatement(sql );
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                arrayTypeShop.add(rs.getString("type"));
            }
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in findShop");
        } finally {

            dbHelper.closeDBConnection(stmt, conn);
        }
        return arrayTypeShop;
    }

    public static ArrayList<Shop> convertRSInArrayShop(ResultSet rs) throws SQLException {
        Shop shop;
        ArrayList<Shop> arrayShop= new ArrayList<>();
        while (rs.next()) {
            String shopName = rs.getString("name").toUpperCase();
            String address = rs.getString("address").toUpperCase();
            String city = rs.getString("city").toUpperCase();
            String logoImagepath = rs.getString("logo_imagepath");
            String interiorPhotosImagepath = rs.getString("interior_photos_imagepath");
            String planimetryImagePath = rs.getString("planimetry_imagepath");
            int shopId = rs.getInt("shop_id");
            ArrayList<Department> department = null;
            ArrayList<ProductShop> items = null;
            int status = rs.getInt("status");
            String openingTime = rs.getString("opening_time");
            String closingTime = rs.getString("closing_time");
            String franchising = rs.getString("franchising");
            double lat = rs.getDouble("latitude");
            double lng = rs.getDouble("longitude");
            String phone = rs.getString("phone");
            String gmapsLink = rs.getString("gmaps_string");
            String offersFlyerPath = rs.getString("offers_flyer_link");
            int distance = 0;
            shop = new Shop(phone, address, city, shopName, logoImagepath, interiorPhotosImagepath, planimetryImagePath, shopId,  status,
                    openingTime, closingTime, lat, lng, gmapsLink, franchising, offersFlyerPath, distance);
            arrayShop.add(shop);
        }
        return arrayShop;
    }
}
