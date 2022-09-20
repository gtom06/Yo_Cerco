package model.dao;

import exceptions.FileElaborationException;
import model.ConstantsExceptions;
import model.db.DbHelper;
import model.order.Order;
import model.order.Payment;

import java.sql.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDao {
    private static final String SELECT_DISTINCT_ALL = "SELECT DISTINCT * ";
    private static final String SELECT_DISTINCT_ALL_FROM_SHOP = "SELECT DISTINCT * FROM shop ";
    private static final String AND_TYPE ="AND type = ?";
    private static final String TWO_VALUES = "VALUES (?, ?)";
    private static final String WHERE_USERNAME = "WHERE username = ?";
    private static final String AND_TIME = "AND CAST(opening_time AS INT) <= ? AND CAST(closing_time AS INT) >= ? ";
    private static final Connection conn = DbHelper.getInstance().getConnection();
    public static final String STATUS = "status";
    private OrderDao(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }
    static final Logger logger = Logger.getLogger(OrderDao.class.getName());

    public static List<Order> findOrdersFromUser(String username) {
        List<Order> orderArrayList = new ArrayList<>();
        try {
            String sql = SELECT_DISTINCT_ALL +
                    "FROM orders " +
                    WHERE_USERNAME;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            orderArrayList = convertRSInArrayOrder(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.ORDER_DAO_ERROR);
        }
        return orderArrayList;
    }

    public static Order findOrderItemsFromOrder(Order order) {
        try {
            String sql = SELECT_DISTINCT_ALL +
                    "FROM order_items " +
                    "WHERE order_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, order.getOrderId());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            order.setOrderItemString(rs.getString("items"));
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.ORDER_DAO_ERROR);
        }
        return order;
    }

    public static Order insertOrder(Order order) {
        try {
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
            ResultSet rs = stmt.executeQuery();
            rs.next();
            order.setOrderId(rs.getInt("order_id"));
            order.setOrderTimestamp(rs.getTimestamp("order_timestamp"));
            order.setStatus(rs.getString(STATUS));
            order.setCollectionTimestamp(rs.getTimestamp("collection_order_timestamp"));
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.ORDER_DAO_ERROR);
        }
        return order;
    }

    public static Payment insertPayment(Payment payment){
        try {
            String sql = "INSERT INTO payment (payment_method, cardholder, total_price, currency, payment_timestamp) " +
                    "VALUES (?, ?, ?, ?, ?) " +
                    "RETURNING *";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, payment.getPaymentMethod());
            stmt.setString(2, payment.getCardholder());
            stmt.setDouble(3, payment.getTotalPrice());
            stmt.setString(4, payment.getCurrency());
            stmt.setTimestamp(5, Timestamp.from(Instant.now()));
            ResultSet rs = stmt.executeQuery();
            rs.next();
            payment.setPaymentId(rs.getInt("payment_id"));
            payment.setPaymentTimestamp(rs.getTimestamp("payment_timestamp"));
            payment.setStatus(rs.getString(STATUS));
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.ORDER_DAO_ERROR);
        }
        return payment;
    }


    public static boolean insertOrderItems(int orderId, String jsonOrderItems) {
        try {
            String sql = "INSERT INTO order_items (order_id, items) " +
                    TWO_VALUES;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            stmt.setString(2, jsonOrderItems);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, ConstantsExceptions.ORDER_DAO_ERROR);
        }
        return true;
    }

    public static List<Order> findOrdersByAdmin(String username) {
        List<Order> orderArrayList = new ArrayList<>();
        try {
            String sql =    "SELECT o.order_id, o.shop_id, o.payment_id, o.order_timestamp, o.total_price, o.currency, o.status, o.collection_order_timestamp, o.total_quantity " +
                    "FROM shop S join shopholder_shop shs " +
                    "ON s.shop_id = shs.shop_id " +
                    "JOIN orders o " +
                    "ON o.shop_id = shs.shop_id " +
                    "WHERE shs.username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            orderArrayList = convertRSInArrayOrder(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.ORDER_DAO_ERROR);
        }
        return orderArrayList;
    }

    public static boolean setStatusOrder(int orderId, String status) {
        try {
            String sql = "UPDATE orders " +
                    "SET status = ? " +
                    "WHERE order_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.ORDER_DAO_ERROR);
        }
        return true;
    }

    public static List<Order> convertRSInArrayOrder(ResultSet rs) throws SQLException {
        Order order;
        ArrayList<Order> arrayOrder= new ArrayList<>();
        while (rs.next()) {
            Integer orderId = rs.getInt("order_id");
            Integer shopId = rs.getInt("shop_id");
            int paymentId = rs.getInt("payment_id");
            Timestamp orderTimestamp = rs.getTimestamp("order_timestamp");
            double totalPrice = rs.getDouble("total_price");
            String currency = rs.getString("currency");
            Integer orderTotalQuantity = rs.getInt("total_quantity");
            String username = rs.getString("username");
            order = new Order(orderId, shopId, username, new Payment(paymentId, null, null, totalPrice, currency, orderTimestamp, null), orderTotalQuantity, null, null);
            arrayOrder.add(order);
        }
        return arrayOrder;
    }
}
