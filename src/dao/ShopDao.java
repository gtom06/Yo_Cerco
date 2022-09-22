package dao;

import constants.Constants;
import constants.ConstantsExceptions;
import model.address.Address;
import model.db.DbHelper;
import model.product.SimpleProduct;
import model.shop.Shop;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShopDao {
    private static final Connection conn = DbHelper.getInstance().getConnection();

    private ShopDao(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }
    static Logger logger = Logger.getLogger(ShopDao.class.getName());

    public static List<Shop> findShopByCity(String city, String type, Integer hour) {
        PreparedStatement stmt = null;
        List<Shop> arrayShop= new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT * FROM shop WHERE status != ? AND CAST(opening_time AS INT) <= ? AND CAST(closing_time AS INT) >= ? AND LOWER(city) LIKE ?";
            if (!type.equals(Constants.SHOP_TYPE.get(0))){
                sql = sql.concat(" AND type = ?");
            }
            stmt = conn.prepareStatement(sql);
            setShopArgs(stmt, hour);
            stmt.setString(4, "%" + city.toLowerCase() + "%");
            if (!type.equals(Constants.SHOP_TYPE.get(0))){
                stmt.setString(5,type);
            }
            ResultSet rs = stmt.executeQuery();
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        } finally {
            DbHelper.closeStatement(stmt);
        }
        return arrayShop;
    }

    public static List<Shop> findShopByName(String name, String type, Integer hour) {
        PreparedStatement stmt = null;
        List<Shop> arrayShop= new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT * FROM shop WHERE status != ? AND CAST(opening_time AS INT) <= ? AND CAST(closing_time AS INT) >= ? AND LOWER(name) LIKE ? ";
            if (!type.equals(Constants.SHOP_TYPE.get(0))){
                sql = sql.concat("AND type = ?");
            }
            stmt = conn.prepareStatement(sql);
            setShopArgs(stmt, hour);
            stmt.setString(4, "%" + name.toLowerCase() + "%");
            if (!type.equals(Constants.SHOP_TYPE.get(0))){
                stmt.setString(5,type);
            }
            ResultSet rs = stmt.executeQuery();
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        } finally {
            DbHelper.closeStatement(stmt);
        }
        return arrayShop;
    }

    public static List<Shop> findShopNearby(double lat, double lng, String type, Integer hour) {
        PreparedStatement stmt = null;
        List<Shop> arrayShop= new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT * FROM shop WHERE status != ? AND CAST(opening_time AS INT) <= ? AND CAST(closing_time AS INT) >= ? AND ? < latitude AND latitude < ? AND ? < longitude AND longitude < ? ";
            if (!type.equals(Constants.SHOP_TYPE.get(0))){
                sql = sql.concat("AND type = ?");
            }
            stmt = conn.prepareStatement(sql);
            setShopArgs(stmt, hour);
            stmt.setDouble(4, lat - 0.5);
            stmt.setDouble(5, lat + 0.5);
            stmt.setDouble(6, lng - 0.5);
            stmt.setDouble(7, lng + 0.5);
            if (!type.equals(Constants.SHOP_TYPE.get(0))){
                stmt.setString(8,type);
            }
            ResultSet rs = stmt.executeQuery();
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        } finally {
            DbHelper.closeStatement(stmt);
        }
        return arrayShop;
    }

    public static List<Shop> findShopByFavoriteUser(String username) {
        PreparedStatement stmt = null;
        List<Shop> arrayShop= new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT * FROM shop s JOIN user_favoriteshop ufs ON s.shop_id = ufs.shop_id WHERE LOWER(username) = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username.toLowerCase());
            ResultSet rs = stmt.executeQuery();
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        } finally {
            DbHelper.closeStatement(stmt);
        }
        return arrayShop;
    }

    public static List<Shop> findShopsByProduct(SimpleProduct simpleProduct) {
        PreparedStatement stmt = null;
        List<Shop> arrayShop= new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT * FROM shop S JOIN product_shop PS ON S.shop_id = PS.shop_id JOIN product p ON PS.sku = p.sku WHERE p.sku = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, simpleProduct.getSku());
            ResultSet rs = stmt.executeQuery();
            arrayShop = convertRSInArrayShop(rs);

        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        } finally {
            DbHelper.closeStatement(stmt);
        }
        return arrayShop;
    }

    public static void insertFavoriteShopIntoDb(int shopId, String username) {
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO user_favoriteshop (username, shop_id) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setInt(2, shopId);
            stmt.executeUpdate();
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        } finally {
            DbHelper.closeStatement(stmt);
        }
    }

    public static void removeFavoriteShopFromDb(int shopId, String username) {
        PreparedStatement stmt = null;
        try {
            String sql = "DELETE FROM user_favoriteshop WHERE LOWER(username) = ? AND shop_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username.toLowerCase());
            stmt.setInt(2, shopId);
            stmt.executeUpdate();
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        } finally {
            DbHelper.closeStatement(stmt);
        }
    }

    public static boolean isFavoriteShop(int shopId, String username) {
        PreparedStatement stmt = null;
        boolean output = false;
        try {
            String sql = "SELECT DISTINCT * FROM user_favoriteshop WHERE shop_id = ? AND LOWER(username) = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, shopId);
            stmt.setString(2, username.toLowerCase());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                output = true;
            }
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        } finally {
            DbHelper.closeStatement(stmt);
        }
        return output;
    }

    public static List<Shop> convertRSInArrayShop(ResultSet rs) throws SQLException {
        Shop shop;
        ArrayList<Shop> arrayShop= new ArrayList<>();
        while (rs.next()) {
            List<String> openingTimes = new ArrayList<>();
            Integer shopId = rs.getInt("shop_id");
            String shopName = rs.getString("name").toUpperCase();
            String address = rs.getString("address").toUpperCase();
            String city = rs.getString("city").toUpperCase();
            String logoImagepath = rs.getString("logo_imagepath");
            openingTimes.add(rs.getString("opening_time"));
            openingTimes.add(rs.getString("closing_time"));
            String franchising = rs.getString("franchising");
            double lat = rs.getDouble("latitude");
            double lng = rs.getDouble("longitude");
            String phone = rs.getString("phone");
            String gmapsLink = rs.getString("gmaps_string");
            String offersFlyerPath = rs.getString("offers_flyer_link");
            shop = new Shop(phone, new Address(lat, lng, gmapsLink, city, address), shopName, logoImagepath, openingTimes, franchising, offersFlyerPath);
            shop.setShopId(shopId);
            arrayShop.add(shop);
        }
        return arrayShop;
    }

    private static List<Integer> checkHour(Integer hour){
        int hour1 = 0;
        int hour2;
        if (hour == null) {
            hour2 = 24;
        }
        else {
            hour1 = hour2 = hour;
        }
        return new ArrayList<>(Arrays.asList(hour1, hour2));
    }

    protected static PreparedStatement setShopArgs(PreparedStatement stmt, Integer hour) throws SQLException {
        ArrayList<Integer> hours = (ArrayList<Integer>) checkHour(hour);
        stmt.setInt(1, Constants.NOT_AVAILABLE);
        stmt.setInt(2, hours.get(1));
        stmt.setInt(3, hours.get(0));
        return stmt;
    }
}
