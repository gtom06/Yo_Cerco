package model.dao;

import model.Constants;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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

    public static ResultSet findShopByFavoriteUser(Statement stmt, String username) throws SQLException {
        String sql = "SELECT DISTINCT * " +
                "FROM shop s JOIN user_favoriteshop ufs " +
                "ON s.shop_id = ufs.shop_id " +
                "WHERE LOWER(username) = '" + username.toLowerCase() + "'";
        System.out.println(sql);
        return stmt.executeQuery(sql);
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
