package model.dao;

import model.ConstantsExceptions;
import model.address.Address;
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
    static Logger logger = Logger.getLogger(ShopDao.class.getName());

    public static List<Shop> findShopByCity(String city, String type, Integer hour) {
        List<Shop> arrayShop= new ArrayList<>();
        try {
            ResultSet rs = Queries.findShopByCityQuery(city, type, hour);
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        }
        return arrayShop;
    }

    public static List<Shop> findShopByName(String name, String type, Integer hour) {
        List<Shop> arrayShop= new ArrayList<>();
        try {
            ResultSet rs = Queries.findShopByNameQuery(name, type, hour);
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        }
        return arrayShop;
    }

    public static List<Shop> findShopNearby(double lat, double lng, String type, Integer hour) {
        List<Shop> arrayShop= new ArrayList<>();
        try {
            ResultSet rs = Queries.findShopNearbyQuery(lat, lng, type, hour);
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        }
        return arrayShop;
    }

    public static List<Shop> findShopByFavoriteUser(String username) {
        List<Shop> arrayShop= new ArrayList<>();
        try {
            ResultSet rs = Queries.findShopByFavoriteUserQuery(username);
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        }
        return arrayShop;
    }

    public static List<Shop> findShopsWithProducts(List<Integer> productSkuArrayList) {
        List<Shop> arrayShop= new ArrayList<>();
        try {
            ResultSet rs = Queries.findShopsWithProductsQuery(productSkuArrayList);
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        }
        return arrayShop;
    }

    public static List<Shop> findShopsByProduct(SimpleProduct simpleProduct) {
        List<Shop> arrayShop= new ArrayList<>();
        try {
            ResultSet rs = Queries.findShopsByProductQuery(simpleProduct.getSku());
            arrayShop = convertRSInArrayShop(rs);

        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        }
        return arrayShop;
    }

    public static void insertFavoriteShopIntoDb(int shopId, String username) {
        try {
            Queries.insertFavoriteShopIntoDbQuery(shopId, username);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        }
    }

    public static void removeFavoriteShopFromDb(int shopId, String username) {
        try {
            Queries.removeFavoriteShopFromDbQuery(shopId, username);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
        }
    }

    public static boolean isFavoriteShop(int shopId, String username) {
        boolean output = false;
        try {
            ResultSet rs = Queries.isFavoriteShopQuery(shopId, username);
            if (rs.next()) {
                output = true;
            }
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.SHOP_DAO_ERROR);
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
}
