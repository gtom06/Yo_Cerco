package model.Dao;

import control.FileElaboration;
import control.JsonParserCustom;
import model.Constants;
import model.Db.DbHelper;
import model.Order.Order;
import model.Order.OrderItem;

import java.sql.*;
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
                int payment = rs.getInt("payment");
                Timestamp orderTimestamp = rs.getTimestamp("order_timestamp");
                double totalAmount = rs.getDouble("total_amount");
                String currency = rs.getString("currency");
                String status = rs.getString("status");
                Timestamp collectionTimestamp = rs.getTimestamp("collection_order_timestamp");
                Integer orderTotalQuantity = rs.getInt("total_quantity");
                order = new Order(orderId, shopId, username, payment, orderTimestamp, totalAmount, currency, status, collectionTimestamp, orderTotalQuantity, null);
                orderArrayList.add(order);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return orderArrayList;
        }
    }

    public static ArrayList<OrderItem> findOrderItemsFromOrder(int orderId) {
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
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String json = rs.getString("items");
                orderItemArrayList = JsonParserCustom.convertJsonIntoOrderItem(json);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return orderItemArrayList;
        }
    }

    public static Order insertOrder(Order order) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        try {
            String sql = "INSERT INTO orders (shop_id, username, payment, total_amount, total_quantity, currency, collection_order_timestamp) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                    "RETURNING *";
            conn = dbHelper.openDBConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, order.getShopId());
            stmt.setString(2, order.getUsername());
            stmt.setInt(3, order.getPayment());
            stmt.setDouble(4, order.getTotalAmount());
            stmt.setInt(5,order.getOrderTotalQuantity());
            stmt.setString(6,order.getCurrency());
            stmt.setTimestamp(7, order.getCollectionTimestamp());

            stmt.executeQuery();
            ResultSet rs = stmt.getResultSet();
            rs.next();
            order.setOrderId(rs.getInt("order_id"));
            order.setOrderTimestamp(rs.getTimestamp("order_timestamp"));
            order.setStatus(rs.getString("status"));
        } catch (SQLException se) {
            se.printStackTrace();
            dbHelper.closeDBConnection(stmt, conn);
            return null;
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return order;
        }
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
            return true;
        }
    }
}
