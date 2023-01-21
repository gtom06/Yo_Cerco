package dao;

import bean.OrderBean;
import bean.PaymentBean;
import constants.ConstantsExceptions;
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
    private static final Connection conn = DbHelper.getInstance().getConnection();
    private OrderDao(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }
    static final Logger logger = Logger.getLogger(OrderDao.class.getName());

    public static List<Order> findOrdersFromUser(String username) {
        PreparedStatement stmt = null;
        List<Order> orderArrayList = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT * FROM orders WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            setUsername(stmt, username);
            ResultSet rs = stmt.executeQuery();
            orderArrayList = convertRSInArrayOrder(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.ORDER_DAO_ERROR);
        } finally {
            DbHelper.closeStatement(stmt);
        }
        return orderArrayList;
    }

    public static String findOrderItemsFromOrder(Integer orderId) {
        PreparedStatement stmt = null;
        String result = "";
        try {
            String sql = "SELECT DISTINCT * FROM order_items WHERE order_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            result = rs.getString("items");
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.ORDER_DAO_ERROR);
        } finally {
            DbHelper.closeStatement(stmt);
        }
        return result;
    }

    public static Order insertOrder2(Integer shopId, String username, Integer paymentId, Timestamp orderTimestamp, Double totalPrice,
                                     Integer totalQuantity, String currency) {
        Order order = null;
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO orders (shop_id, username, payment_id, order_timestamp, total_price, total_quantity, currency, collection_order_timestamp) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                    "RETURNING *";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, shopId);
            stmt.setString(2, username);
            stmt.setInt(3, paymentId);
            stmt.setTimestamp(4, orderTimestamp);
            stmt.setDouble(5, totalPrice);
            stmt.setInt(6, totalQuantity);
            stmt.setString(7, currency);
            stmt.setTimestamp(8, Timestamp.from(orderTimestamp.toInstant().plus(1, ChronoUnit.DAYS)));
            ResultSet rs = stmt.executeQuery();
            rs.next();

            order = new Order(rs.getInt("order_id"), shopId, username,
                    null, totalQuantity, null, "");
            order.setOrderTimestamp(rs.getTimestamp("order_timestamp"));
            order.setStatus(rs.getString("status"));
            order.setCollectionTimestamp(rs.getTimestamp("collection_order_timestamp"));
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.ORDER_DAO_ERROR);
        } finally {
            DbHelper.closeStatement(stmt);
        }
        return order;
    }

    public static Payment insertPayment2(String paymentMethod, String cardholder, Double totalPrice, String currency){
        Payment payment = null;
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO payment (payment_method, cardholder, total_price, currency, payment_timestamp) VALUES (?, ?, ?, ?, ?) RETURNING *";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, paymentMethod);
            stmt.setString(2, cardholder);
            stmt.setDouble(3, totalPrice);
            stmt.setString(4, currency);
            stmt.setTimestamp(5, Timestamp.from(Instant.now()));
            ResultSet rs = stmt.executeQuery();
            rs.next();
            payment = new Payment(rs.getInt("payment_id"), paymentMethod, cardholder, totalPrice, currency, rs.getTimestamp("payment_timestamp"), rs.getString("status"));
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.ORDER_DAO_ERROR);
        } finally {
            DbHelper.closeStatement(stmt);
        }
        return payment;
    }

    public static void insertOrderItems(int orderId, String jsonOrderItems) {
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO order_items (order_id, items) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            stmt.setString(2, jsonOrderItems);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, ConstantsExceptions.ORDER_DAO_ERROR);
        } finally {
            DbHelper.closeStatement(stmt);
        }
    }

    public static List<Order> findOrdersByAdmin(String username) {
        PreparedStatement stmt = null;
        List<Order> orderArrayList = new ArrayList<>();
        try {
            String sql = "SELECT O.order_id, O.shop_id, O.payment_id, O.order_timestamp, O.total_price, O.currency, O.status, O.collection_order_timestamp, O.total_quantity " +
                    "FROM shop S join shopholder_shop SHS ON S.shop_id = SHS.shop_id JOIN orders O ON O.shop_id = SHS.shop_id WHERE SHS.username = ?";
            stmt = conn.prepareStatement(sql);
            setUsername(stmt, username);
            ResultSet rs = stmt.executeQuery();
            orderArrayList = convertRSInArrayOrder(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.ORDER_DAO_ERROR);
        } finally {
            DbHelper.closeStatement(stmt);
        }
        return orderArrayList;
    }

    public static boolean setStatusOrder(int orderId, String status) {
        PreparedStatement stmt = null;
        try {
            String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.ORDER_DAO_ERROR);
        } finally {
            DbHelper.closeStatement(stmt);
        }
        return true;
    }

    public static List<Order> convertRSInArrayOrder(ResultSet rs) throws SQLException {
        Order order;
        ArrayList<Order> arrayOrder= new ArrayList<>();
        while (rs.next()) {
            int orderId = rs.getInt("order_id");
            int shopId = rs.getInt("shop_id");
            int paymentId = rs.getInt("payment_id");
            Timestamp orderTimestamp = rs.getTimestamp("order_timestamp");
            double totalPrice = rs.getDouble("total_price");
            String currency = rs.getString("currency");
            int orderTotalQuantity = rs.getInt("total_quantity");
            String username = rs.getString("username");
            order = new Order(orderId, shopId, username, new Payment(paymentId, null, null, totalPrice, currency, orderTimestamp, null), orderTotalQuantity, null, null);
            arrayOrder.add(order);
        }
        return arrayOrder;
    }

    protected static PreparedStatement setUsername(PreparedStatement stmt, String username) throws SQLException {
        stmt.setString(1, username);
        return stmt;
    }
}
