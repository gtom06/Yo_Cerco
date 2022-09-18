package model.dao;

import model.Constants;
import model.ConstantsExceptions;
import model.db.DbHelper;
import model.order.Order;
import model.shop.Shop;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Queries {
    private static final String SELECT_DISTINCT_ALL = "SELECT DISTINCT * ";
    private static final String FROM_SHOP = "FROM SHOP ";
    private static final String WHERE_USERNAME = "WHERE username = '";
    private static final String WHERE_STATUS = " AND status != " + Constants.NOT_AVAILABLE;

    public static ResultSet validateLoginQuery(Statement stmt, String username, String password) throws SQLException {
        String sql =    "SELECT username, pass FROM userx " +
                        WHERE_USERNAME + username +
                        "' AND pass = '" + password +
                        "'";
        return stmt.executeQuery(sql);
    }
    public static ResultSet retrieveUserFromQuery(Statement stmt, String username) throws SQLException {
        String sql =    "SELECT * " +
                        "FROM userx " +
                        WHERE_USERNAME + username + "'";
        return stmt.executeQuery(sql);
    }

    public static ResultSet findShopNearbyQuery(Statement stmt, double lat, double lng, String type, Integer time) throws SQLException {
        String sql = SELECT_DISTINCT_ALL +
                FROM_SHOP +
                addLatLng(lat,lng) +
                addTimeShop(time)+
                addTypeShop(type)+
                WHERE_STATUS;
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }

    public static ResultSet findShopByNameQuery(Statement stmt, String name, String type, Integer time) throws SQLException {
        String sql = SELECT_DISTINCT_ALL +
                FROM_SHOP +
                " WHERE LOWER(name) LIKE " + "'%"+name.toLowerCase()+"%'" +
                WHERE_STATUS +
                addTypeShop(type) +
                addTimeShop(time);
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }

    public static ResultSet findShopByCityQuery(Statement stmt, String city, String type, Integer time) throws SQLException {
        String sql = SELECT_DISTINCT_ALL +
                FROM_SHOP +
                " WHERE LOWER(city) LIKE " + "'%"+city.toLowerCase()+"%'" +
                WHERE_STATUS +
                addTypeShop(type) +
                addTimeShop(time);
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }

    public static ResultSet findShopByFavoriteUserQuery(Statement stmt, String username) throws SQLException {
        String sql = SELECT_DISTINCT_ALL +
                "FROM shop s JOIN user_favoriteshop ufs " +
                "ON s.shop_id = ufs.shop_id " +
                "WHERE LOWER(username) = '" + username.toLowerCase() + "'";
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }

    public static ResultSet findShopsByProductQuery(Statement stmt, int sku) throws SQLException {
        String sql = SELECT_DISTINCT_ALL +
                "FROM shop S " +
                "JOIN product_shop PS " +
                "ON S.shop_id = PS.shop_id "+
                "JOIN product p " +
                "ON PS.sku = p.sku " +
                "WHERE p.sku = " + sku;
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }

    public static ResultSet findShopsWithProductsQuery(Statement stmt, List<Integer> productSkuArrayList) throws SQLException {
        String sql = SELECT_DISTINCT_ALL +
                "FROM product_shop PS " +
                "JOIN shop S on S.shop_id = PS.shop_id " +
                "WHERE sku IN " +
                QueriesHelper.buildSqlStringFromArrayOfIntegers(productSkuArrayList);
        System.out.println(sql);
        return stmt.executeQuery(sql);
    }

    public static void insertFavoriteShopIntoDbQuery(Statement stmt, Integer shopId, String username) throws SQLException {
        String sql = "INSERT INTO user_favoriteshop (username, shop_id) " +
                "VALUES (" +"'" + username + "'," + shopId +")";
        System.out.println(sql);
        stmt.executeUpdate(sql);
    }
    public static void removeFavoriteShopFromDbQuery(Statement stmt, Integer shopId, String username) throws SQLException {
        String sql = "DELETE FROM user_favoriteshop " +
                "WHERE username =" +"'" + username + "' AND shop_id = " + shopId;
        System.out.println(sql);
        stmt.executeUpdate(sql);
    }

    public static ResultSet isFavoriteShopQuery(Statement stmt, Integer shopId, String username) throws SQLException {
        String sql = SELECT_DISTINCT_ALL +
                " FROM user_favoriteshop " +
                " WHERE username =" +"'" + username + "' AND shop_id = " + shopId;
        return stmt.executeQuery(sql);
    }

    public static ResultSet findOrderItemsFromOrderQuery(Statement stmt, Order order){
        return null;


    }

    private static String addLatLng(Double lat, double lng){
        double latInf = lat - 0.5;
        double latSup = lat + 0.5;
        double lngInf = lng - 0.5;
        double lngSup = lng + 0.5;
        return  " WHERE "+ latInf + "< latitude " +
                " AND latitude < " + latSup +
                " AND " + lngInf + " < longitude " +
                " AND longitude < " + lngSup;
    }

    private static String addTimeShop(Integer time) {
        if (time != null){
            return  " AND CAST(opening_time AS INT) <= " + time +
                    " AND CAST(closing_time AS INT) > " + time;
        }
        else {
            return "";
        }
    }

    private static String addTypeShop(String type) {
        if (!type.equals(Constants.SHOP_TYPE.get(0))){
            return " AND type = " + type;
        }
        else {
            return "";
        }
    }
}
