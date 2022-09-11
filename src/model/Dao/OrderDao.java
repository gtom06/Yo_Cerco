package model.Dao;

import control.FileElaboration;
import model.Constants;
import model.Db.DbHelper;
import model.Order.Order;
import model.Order.OrderItem;
import model.Order.Payment;

import java.sql.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class OrderDao {
    public static ArrayList<Order> findOrdersFromUser(String username) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        ArrayList<Order> orderArrayList = new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * " +
                    "FROM orders " +
                    "WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Order order;
                Integer orderId = rs.getInt("order_id");
                Integer shopId = rs.getInt("shop_id");
                int payment = rs.getInt("payment_id");
                Timestamp orderTimestamp = rs.getTimestamp("order_timestamp");
                double totalAmount = rs.getDouble("total_price");
                String currency = rs.getString("currency");
                String status = rs.getString("status");
                Timestamp collectionTimestamp = rs.getTimestamp("collection_order_timestamp");
                Integer orderTotalQuantity = rs.getInt("total_quantity");
                order = new Order(orderId, shopId, username, payment, orderTimestamp, totalAmount, currency, status, collectionTimestamp, orderTotalQuantity, null, null);
                orderArrayList.add(order);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return orderArrayList;
    }

    public static Order findOrderItemsFromOrder(Order order) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        ArrayList<OrderItem> orderItemArrayList = new ArrayList<>();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "SELECT DISTINCT * " +
                    "FROM order_items " +
                    "WHERE order_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, order.getOrderId());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            order.setOrderItemString(rs.getString("items"));
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return order;
    }

    public static Order insertOrder(Order order) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        try {


            String sql = "INSERT INTO orders (shop_id, username, payment_id, order_timestamp, total_price, total_quantity, currency, collection_order_timestamp) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                    "RETURNING *";
            conn = dbHelper.openDBConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, order.getShopId());
            stmt.setString(2, order.getUsername());
            stmt.setInt(3, order.getPaymentId());
            stmt.setTimestamp(4, order.getOrderTimestamp());
            stmt.setDouble(5, order.getTotalPrice());
            stmt.setInt(6,order.getOrderTotalQuantity());
            stmt.setString(7,order.getCurrency());
            stmt.setTimestamp(8, Timestamp.from(order.getOrderTimestamp().toInstant().plus(1, ChronoUnit.DAYS)));

            stmt.executeQuery();
            ResultSet rs = stmt.getResultSet();
            rs.next();
            order.setOrderId(rs.getInt("order_id"));
            order.setOrderTimestamp(rs.getTimestamp("order_timestamp"));
            order.setStatus(rs.getString("status"));
            order.setCollectionTimestamp(null);
        } catch (SQLException se) {
            se.printStackTrace();
            dbHelper.closeDBConnection(stmt, conn);
            return null;
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return order;
    }

    public static Payment insertPayment(Payment payment){
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        try {
            String sql = "INSERT INTO payment (last_4_digits, mm, yy, payment_method, cardholder, total_price, currency, payment_timestamp) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                    "RETURNING *";
            conn = dbHelper.openDBConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, payment.getLast4digits());
            stmt.setString(2, payment.getMm());
            stmt.setString(3, payment.getYy());
            stmt.setString(4, payment.getpaymentMethod());
            stmt.setString(5, payment.getCardholder());
            stmt.setDouble(6, payment.getTotalPrice());
            stmt.setString(7, payment.getCurrency());
            stmt.setTimestamp(8, Timestamp.from(Instant.now()));
            stmt.executeQuery();
            ResultSet rs = stmt.getResultSet();
            rs.next();
            payment.setPaymentId(rs.getInt("payment_id"));
            payment.setPaymentTimestamp(rs.getTimestamp("payment_timestamp"));
            payment.setStatus(rs.getString("status"));
        } catch (SQLException se) {
            se.printStackTrace();
            dbHelper.closeDBConnection(stmt, conn);
            return null;
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return payment;
    }


    public static boolean insertOrderItems(int orderId, String jsonOrderItems) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        //boolean output = false;
        try {
            String sql = "INSERT INTO order_items (order_id, items) " +
                    "VALUES (?, ?)";
            conn = dbHelper.openDBConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            String jsonString = FileElaboration.fileToString(Constants.CART_PATH);
            stmt.setString(2, jsonString);
            stmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
            dbHelper.closeDBConnection(stmt, conn);
            return false;
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return true;
    }
}
