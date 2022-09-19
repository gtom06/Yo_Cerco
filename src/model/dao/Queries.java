package model.dao;

import exceptions.FileElaborationException;
import model.Constants;

import model.ConstantsExceptions;
import model.db.DbHelper;
import model.order.Order;
import model.order.Payment;
import java.sql.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Queries {
    private static final String SELECT_DISTINCT_ALL = "SELECT DISTINCT * ";
    private static final String SELECT_DISTINCT_ALL_FROM_SHOP = "SELECT DISTINCT * FROM shop ";
    private static final String AND_TYPE ="AND type = ?";
    private static final String TWO_VALUES = "VALUES (?, ?)";
    private static final String WHERE_USERNAME = "WHERE username = ?";
    private static final String AND_TIME = "AND CAST(opening_time AS INT) <= ? AND CAST(closing_time AS INT) >= ? ";
    private static final Connection conn = DbHelper.getInstance().getConnection();

    private Queries(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    private static List<Integer> checkHour(Integer hour){
        Integer hour1 = 0;
        Integer hour2;
        if (hour == null) {
            hour2 = 24;
        }
        else {
            hour1 = hour2 = hour;
        }
        return new ArrayList<>(Arrays.asList(hour1, hour2));
    }

    public static ResultSet findShopByCityQuery(String city, String type, Integer hour) throws SQLException {
        ArrayList<Integer> hours = (ArrayList<Integer>) checkHour(hour);
        String sql = SELECT_DISTINCT_ALL_FROM_SHOP +
                "WHERE LOWER(city) " +
                "LIKE ? AND status != ? "+
                AND_TIME;
        if (!type.equals(Constants.SHOP_TYPE.get(0))){
            sql = sql.concat(AND_TYPE);
        }
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + city.toLowerCase() + "%");
        stmt.setInt(2, Constants.NOT_AVAILABLE);
        stmt.setInt(3, hours.get(1));
        stmt.setInt(4, hours.get(0));
        if (!type.equals(Constants.SHOP_TYPE.get(0))){
            stmt.setString(5,type);
        }
        stmt.executeQuery();
        return stmt.getResultSet();
    }

    public static ResultSet findShopByNameQuery(String name, String type, Integer hour) throws SQLException {
        ArrayList<Integer> hours = (ArrayList<Integer>) checkHour(hour);
        String sql = SELECT_DISTINCT_ALL_FROM_SHOP +
                "WHERE LOWER(name) " +
                "LIKE ? AND status != ? " +
                AND_TIME;
        if (!type.equals(Constants.SHOP_TYPE.get(0))){
            sql = sql.concat(AND_TYPE);
        }
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + name.toLowerCase() + "%");
        stmt.setInt(2, Constants.NOT_AVAILABLE);
        stmt.setInt(3, hours.get(1));
        stmt.setInt(4, hours.get(0));
        if (!type.equals(Constants.SHOP_TYPE.get(0))){
            stmt.setString(5,type);
        }
        stmt.executeQuery();
        return stmt.getResultSet();
    }

    public static ResultSet findShopNearbyQuery(Double lat, Double lng, String type, Integer hour) throws SQLException {
        ArrayList<Integer> hours = (ArrayList<Integer>) checkHour(hour);
        String sql = SELECT_DISTINCT_ALL_FROM_SHOP +
                "WHERE  ? < latitude " +
                "AND latitude < ? " +
                "AND ? < longitude " +
                "AND longitude < ? " +
                "AND status != ? "+
                AND_TIME;
        if (!type.equals(Constants.SHOP_TYPE.get(0))){
            sql = sql.concat(AND_TYPE);
        }
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDouble(1, lat - 0.5);
        stmt.setDouble(2, lat + 0.5);
        stmt.setDouble(3, lng - 0.5);
        stmt.setDouble(4, lng + 0.5);
        stmt.setInt(5,Constants.NOT_AVAILABLE);
        stmt.setInt(6, hours.get(1));
        stmt.setInt(7, hours.get(0));
        if (!type.equals(Constants.SHOP_TYPE.get(0))){
            stmt.setString(8,type);
        }
        stmt.executeQuery();
        return stmt.getResultSet();
    }

    public static ResultSet findShopByFavoriteUserQuery(String username) throws SQLException {
        String sql = SELECT_DISTINCT_ALL_FROM_SHOP + " s JOIN user_favoriteshop ufs " +
                "ON s.shop_id = ufs.shop_id " +
                "WHERE LOWER(username) = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username.toLowerCase());
        stmt.executeQuery();
        return stmt.getResultSet();
    }

    public static ResultSet findShopsWithProductsQuery(List<Integer> productSkuArrayList) throws SQLException {
        String sql = SELECT_DISTINCT_ALL +
                "FROM product_shop PS " +
                "JOIN shop S on S.shop_id = PS.shop_id " +
                "WHERE sku IN ";
        PreparedStatement stmt = conn.prepareStatement(sql + QueriesHelper.buildSqlStringFromArrayOfIntegers(productSkuArrayList));
        stmt.executeQuery();
        return stmt.getResultSet();
    }

    public static ResultSet findShopsByProductQuery(Integer sku) throws SQLException {
        String sql = SELECT_DISTINCT_ALL_FROM_SHOP + " S " +
                "JOIN product_shop PS " +
                "ON S.shop_id = PS.shop_id "+
                "JOIN product p " +
                "ON PS.sku = p.sku " +
                "WHERE p.sku = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, sku);
        stmt.executeQuery();
        return stmt.getResultSet();
    }

    public static void insertFavoriteShopIntoDbQuery(int shopId, String username) throws SQLException {
        String sql = "INSERT INTO user_favoriteshop (username, shop_id) " +
                TWO_VALUES;
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setInt(2, shopId);
        stmt.executeUpdate();
    }

    public static void removeFavoriteShopFromDbQuery(int shopId, String username) throws SQLException {
        String sql = "DELETE FROM user_favoriteshop " +
                "WHERE LOWER(username) = ? AND shop_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username.toLowerCase());
        stmt.setInt(2, shopId);
        stmt.executeUpdate();
    }

    public static ResultSet isFavoriteShopQuery(int shopId, String username) throws SQLException {
        String sql = SELECT_DISTINCT_ALL +
                "FROM user_favoriteshop " +
                "WHERE shop_id = ? AND LOWER(username) = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, shopId);
        stmt.setString(2, username.toLowerCase());
        stmt.executeQuery();
        return stmt.getResultSet();
    }

    //departments
    public static ResultSet findDepartmentByShopQuery(int shopId) throws SQLException {
        String sql = "SELECT * FROM department D " +
                "JOIN shop_department SD " +
                "ON D.department_id = SD.department_id " +
                "WHERE SD.shop_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, shopId);
        stmt.executeQuery();
        stmt.executeQuery();
        return stmt.getResultSet();
    }

    //products
    public static ResultSet findProductByNameQuery(String name) throws SQLException {
        String sql = SELECT_DISTINCT_ALL +
                "FROM product " +
                "WHERE LOWER(name) LIKE ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + name.toLowerCase() + "%");
        stmt.executeQuery();
        return stmt.getResultSet();
    }

    public static ResultSet findProductByDepartmentAndShopQuery(Integer shopId, Integer departmentId) throws SQLException {
        String sql =    "SELECT * FROM product_shop PS " +
                        "JOIN product P " +
                        "ON P.sku = PS.sku " +
                        "WHERE PS.shop_id = ? AND PS.department_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, shopId);
        stmt.setInt(2, departmentId);
        stmt.executeQuery();
        return stmt.getResultSet();
    }

    public static ResultSet isFavoriteProductQuery(String username, Integer sku) throws SQLException {
        String sql = SELECT_DISTINCT_ALL +
                "FROM user_favoriteproduct " +
                "WHERE sku = ? AND username = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, sku);
        stmt.setString(2, username);
        stmt.executeQuery();
        return stmt.getResultSet();
    }

    public static void removeFavoriteProductFromDbQuery(String username, Integer sku) throws SQLException {
        String sql = "DELETE FROM user_favoriteproduct " +
                "WHERE username = ? AND sku = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setInt(2, sku);
        stmt.executeUpdate();
    }

    public static void insertFavoriteProductIntoDbQuery(String username, Integer sku) throws SQLException {
        String sql = "INSERT INTO user_favoriteproduct (username, sku) " +
                TWO_VALUES;
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setInt(2, sku);
        stmt.executeUpdate();
    }

    public static ResultSet findSimpleProductFromUserQuery(String username) throws SQLException {
        String sql = SELECT_DISTINCT_ALL +
                "FROM user_favoriteproduct ufp " +
                "JOIN product p " +
                "ON p.sku = ufp.sku " +
                WHERE_USERNAME;
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.executeQuery();
        return stmt.getResultSet();
    }

    public static ResultSet findProductBySkuAndShopIdQuery(Integer shopId, Integer sku) throws SQLException {
        String sql = SELECT_DISTINCT_ALL +
                "FROM product P JOIN product_shop PS " +
                "ON P.sku = PS.sku " +
                "WHERE shop_id = ? AND PS.sku = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, shopId);
        stmt.setInt(2, sku);
        stmt.executeQuery();
        return stmt.executeQuery();
    }


    //orders
    public static ResultSet findOrdersFromUserQuery(String username) throws SQLException {
        String sql = SELECT_DISTINCT_ALL +
                "FROM orders " +
                WHERE_USERNAME;
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.executeQuery();
        return stmt.getResultSet();
    }


    public static ResultSet findOrderItemsFromOrderQuery(Integer orderId) throws SQLException {
        String sql = SELECT_DISTINCT_ALL +
                "FROM order_items " +
                "WHERE order_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, orderId);
        stmt.executeQuery();
        return stmt.getResultSet();
    }

    public static ResultSet insertOrderQuery(Order order) throws SQLException {
        String sql = "INSERT INTO orders (shop_id, username, payment_id, order_timestamp, total_price, total_quantity, currency, collection_order_timestamp) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                "RETURNING *";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, order.getShopId());
        stmt.setString(2, order.getUsername());
        stmt.setInt(3, order.getPaymentId());
        stmt.setTimestamp(4, order.getOrderTimestamp());
        stmt.setDouble(5, order.getTotalPrice());
        stmt.setInt(6, order.getOrderTotalQuantity());
        stmt.setString(7, order.getCurrency());
        stmt.setTimestamp(8, Timestamp.from(order.getOrderTimestamp().toInstant().plus(1, ChronoUnit.DAYS)));

        stmt.executeQuery();
        return stmt.getResultSet();
    }

    public static ResultSet insertPaymentQuery(Payment payment) throws SQLException {
        String sql = "INSERT INTO payment (last_4_digits, mm, yy, payment_method, cardholder, total_price, currency, payment_timestamp) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                "RETURNING *";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, payment.getLast4digits());
        stmt.setString(2, payment.getMm());
        stmt.setString(3, payment.getYy());
        stmt.setString(4, payment.getPaymentMethod());
        stmt.setString(5, payment.getCardholder());
        stmt.setDouble(6, payment.getTotalPrice());
        stmt.setString(7, payment.getCurrency());
        stmt.setTimestamp(8, Timestamp.from(Instant.now()));
        stmt.executeQuery();
        return stmt.getResultSet();
    }
    public static void insertOrderItemsQuery(int orderId, String jsonOrderItems) throws SQLException, FileElaborationException {
        String sql = "INSERT INTO order_items (order_id, items) " +
                TWO_VALUES;
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, orderId);
        stmt.setString(2, jsonOrderItems);
        stmt.executeUpdate();
    }

    public static ResultSet findOrdersByAdminQuery(String username) throws SQLException {
        String sql =    "SELECT o.order_id, o.shop_id, o.payment_id, o.order_timestamp, o.total_price, o.currency, o.status, o.collection_order_timestamp, o.total_quantity " +
                "FROM shop S join shopholder_shop shs " +
                "ON s.shop_id = shs.shop_id " +
                "JOIN orders o " +
                "ON o.shop_id = shs.shop_id " +
                "WHERE shs.username = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.executeQuery();
        return stmt.getResultSet();
    }


    public static void setStatusOrderQuery(int orderId, String status) throws SQLException {

        String sql = "UPDATE orders " +
                "SET status = ? " +
                "WHERE order_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, status);
        stmt.setInt(2, orderId);
        stmt.executeUpdate();
    }

    //user
    public static ResultSet validateLoginQuery(String username, String password) throws SQLException {
        String sql = "SELECT username, pass " +
                "FROM userx " +
                "WHERE username = ? AND pass = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2,password);
        stmt.executeQuery();
        return stmt.getResultSet();
    }

    public static ResultSet retrieveUserFromQuery(String username) throws SQLException {
        String sql = SELECT_DISTINCT_ALL +
                "FROM userx " +
                WHERE_USERNAME;
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.executeQuery();
        return stmt.getResultSet();
    }
}
