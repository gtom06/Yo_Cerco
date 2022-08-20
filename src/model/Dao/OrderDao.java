package model.Dao;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import control.JsonParserCustom;
import model.Db.DbHelper;
import model.Order.Order;
import model.Order.OrderItem;
import model.Product.ProductShop;

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
                String payment = rs.getString("payment");
                Timestamp orderTimestamp = rs.getTimestamp("order_timestamp");
                double totalAmount = rs.getDouble("total_amount");
                String currency = rs.getString("currency");
                String status = rs.getString("status");
                Timestamp collectionTimestamp = rs.getTimestamp("collection_order_timestamp");
                Integer orderTotalQuantity = rs.getInt("total_quantity");
                order = new Order(orderId, username, payment, orderTimestamp, totalAmount, currency, status, collectionTimestamp, orderTotalQuantity, null);
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
}
