package model.dao;

import model.product.SimpleProduct;
import model.shop.Shop;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShopDao {
    static Logger logger = Logger.getLogger(ShopDao.class.getName());

    public static ArrayList<Shop> findShopByCity(String city, String type, Integer hour) {
        ArrayList<Shop> arrayShop= new ArrayList<>();
        try {
            ResultSet rs = Queries.findShopByCityQuery(city, type, hour);
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in findShop");
        }
        return arrayShop;
    }

    public static ArrayList<Shop> findShopByName(String name, String type, Integer hour) {
        ArrayList<Shop> arrayShop= new ArrayList<>();
        try {
            ResultSet rs = Queries.findShopByNameQuery(name, type, hour);
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in findShop");
        }
        return arrayShop;
    }

    public static ArrayList<Shop> findShopNearby(double lat, double lng, String type, Integer hour) {
        ArrayList<Shop> arrayShop= new ArrayList<>();
        try {
            ResultSet rs = Queries.findShopNearbyQuery(lat, lng, type, hour);
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in findShop");
        }
        return arrayShop;
    }

    public static ArrayList<Shop> findShopByFavoriteUser(String username) {
        ArrayList<Shop> arrayShop= new ArrayList<>();
        try {
            ResultSet rs = Queries.findShopByFavoriteUserQuery(username);
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in findShop");
        }
        return arrayShop;
    }

    public static ArrayList<Shop> findShopsWithProducts(ArrayList<Integer> productSkuArrayList) {
        ArrayList<Shop> arrayShop= new ArrayList<>();
        try {
            ResultSet rs = Queries.findShopsWithProductsQuery(productSkuArrayList);
            arrayShop = convertRSInArrayShop(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in findShop");
        }
        return arrayShop;
    }

    public static ArrayList<Shop> findShopsByProduct(SimpleProduct simpleProduct) {
        ArrayList<Shop> arrayShop= new ArrayList<>();
        try {
            Queries.findShopsByProductQuery(simpleProduct.getSku());
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in findShop");
        }
        return arrayShop;
    }

    public static void insertFavoriteShopIntoDb(int shopId, String username) {
        try {
            Queries.insertFavoriteShopIntoDbQuery(shopId, username);
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in findShop");
        }
    }

    public static void removeFavoriteShopFromDb(int shopId, String username) {
        try {
            Queries.removeFavoriteShopFromDbQuery(shopId, username);
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in findShop");
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
            logger.log(Level.WARNING, "error in findShop");
        }
        return output;
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
