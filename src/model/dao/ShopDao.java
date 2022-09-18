package model.dao;

import model.ConstantsExceptions;
import model.db.DbHelper;
import model.product.SimpleProduct;
import model.shop.Shop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShopDao {
    private ShopDao(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }
    static ArrayList<Shop> arrayShop = new ArrayList<>();
    static Statement stmt;

    static {
        try {
            stmt = DbHelper.getInstance().getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static final Logger logger = Logger.getLogger(ShopDao.class.getName());

    public static List<Shop> findShopByCity(String city, String type, Integer time) {
        try {
            ResultSet rs = Queries.findShopByCityQuery(stmt, city, type, time);
            arrayShop = (ArrayList<Shop>) convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        }
        return arrayShop;
    }

    public static List<Shop> findShopByName(String name, String type, Integer time) {
        try {
            ResultSet rs = Queries.findShopByNameQuery(stmt, name, type, time);
            arrayShop = (ArrayList<Shop>) convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        }
        return arrayShop;
    }

    public static List<Shop> findShopNearby(Double lat, Double lng, String type, Integer time) {
        try {
            ResultSet rs = Queries.findShopNearbyQuery(stmt, lat, lng, type, time);
            arrayShop = (ArrayList<Shop>) convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        }
        return arrayShop;
    }

    public static List<Shop> findShopByFavoriteUser(String username) {
        try {
            ResultSet rs = Queries.findShopByFavoriteUserQuery(stmt, username);
            arrayShop = (ArrayList<Shop>) convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        }
        return arrayShop;
    }

    public static List<Shop> findShopsWithProducts(List<Integer> productSkuArrayList) {
        try {
            ResultSet rs = Queries.findShopsWithProductsQuery(stmt, productSkuArrayList);
            arrayShop = (ArrayList<Shop>) convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        }
        return arrayShop;
    }

    public static List<Shop> findShopsByProduct(SimpleProduct simpleProduct) {
        try {
            ResultSet rs = Queries.findShopsByProductQuery(stmt, simpleProduct.getSku());
            arrayShop = (ArrayList<Shop>) convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        }
        return arrayShop;
    }

    public static boolean isFavoriteShop(int shopId, String username) {
        boolean output = false;
        try {
            ResultSet rs = Queries.isFavoriteShopQuery(stmt, shopId, username);
            if (rs.next()) {
                output = true;
            }
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        }
        return output;
    }

    public static void insertFavoriteShopIntoDb(int shopId, String username) {
        try {
            Queries.insertFavoriteShopIntoDbQuery(stmt, shopId, username);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        }
    }

    public static void removeFavoriteShopFromDb(int shopId, String username) {
        try {
            Queries.removeFavoriteShopFromDbQuery(stmt, shopId, username);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        }
    }

    public static List<Shop> convertRSInArrayShop(ResultSet rs) throws SQLException {
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
