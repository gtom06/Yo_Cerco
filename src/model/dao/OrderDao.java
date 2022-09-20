package model.dao;

import exceptions.FileElaborationException;
import model.ConstantsExceptions;
import model.order.Order;
import model.order.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDao {
    public static final String STATUS = "status";
    private OrderDao(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }
    static final Logger logger = Logger.getLogger(OrderDao.class.getName());

    public static List<Order> findOrdersFromUser(String username) {
        List<Order> orderArrayList = new ArrayList<>();
        try {
            ResultSet rs = Queries.findOrdersFromUserQuery(username);
            orderArrayList = convertRSInArrayOrder(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.ORDER_DAO_ERROR);
        }
        return orderArrayList;
    }

    public static Order findOrderItemsFromOrder(Order order) {
        try {
            ResultSet rs = Queries.findOrderItemsFromOrderQuery(order.getOrderId());
            rs.next();
            order.setOrderItemString(rs.getString("items"));
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.ORDER_DAO_ERROR);
        }
        return order;
    }

    public static Order insertOrder(Order order) {
        try {
            ResultSet rs = Queries.insertOrderQuery(order);
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
            ResultSet rs = Queries.insertPaymentQuery(payment);
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
            Queries.insertOrderItemsQuery(orderId, jsonOrderItems);
        } catch (SQLException | FileElaborationException e) {
            logger.log(Level.WARNING, ConstantsExceptions.ORDER_DAO_ERROR);
        }
        return true;
    }

    public static List<Order> findOrdersByAdmin(String username) {
        List<Order> orderArrayList = new ArrayList<>();
        try {
            ResultSet rs = Queries.findOrdersByAdminQuery(username);
            orderArrayList = convertRSInArrayOrder(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.ORDER_DAO_ERROR);
        }
        return orderArrayList;
    }

    public static boolean setStatusOrder(int orderId, String status) {
        try {
            Queries.setStatusOrderQuery(orderId, status);
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
