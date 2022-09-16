package control;

import com.google.gson.Gson;
import exceptions.ExceptionCart;
import exceptions.FileElaborationException;
import model.Constants;
import model.ConstantsExceptions;
import model.Dao.OrderDao;
import model.Order.Order;
import model.Order.OrderItem;
import model.Order.Payment;
import model.User.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderHandler {
    static final Logger logger = Logger.getLogger(OrderHandler.class.getName());
    private OrderHandler(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    public static ArrayList<Order> findOrdersInfoFromUser(User user) {
        ArrayList<Order> orderArrayList = (ArrayList<Order>) OrderDao.findOrdersFromUser(user.getUsername());
        return !orderArrayList.isEmpty() ? orderArrayList : null;
    }

    public static List<Order> findOrdersByAdmin(User user){
        ArrayList<Order> orderArrayList = (ArrayList<Order>) OrderDao.findOrdersByAdmin(user.getUsername());
        return !orderArrayList.isEmpty() ? orderArrayList : null;
    }

    public static Order populateOrderWithOrderItems(Order order){
        ArrayList<OrderItem> orderItemArrayList;
        try {
            order = OrderDao.findOrderItemsFromOrder(order);
            OrderItem[] output = new Gson().fromJson(order.getOrderItemString(), OrderItem[].class);
            if (output == null) {
                return null;
            }
            orderItemArrayList = new ArrayList<>(List.of(output));
            order.setOrderItemArrayList(orderItemArrayList);
        } catch (Exception e) {
            logger.log(Level.WARNING, "error in OrderHandler");
        }
        return order;
    }

    public static Order previewOrder() throws ExceptionCart {
        List<OrderItem> orderItemArrayList;
        CartElaboration.delete0QuantityItemsFromCart();
        orderItemArrayList = CartElaboration.readOrderItemsFromCart();
        double orderTotalPrice = 0;
        int orderTotalQuantity = 0;
        String orderCurrency;
        int shopId = 0;
        if (orderItemArrayList == null || orderItemArrayList.size() == 0){
            return null;
        }
        else {
            for (OrderItem orderItem : orderItemArrayList) {
                orderTotalQuantity += orderItem.getQuantityOrdered();
                orderTotalPrice += orderItem.getPriceTotal();
            }
            orderCurrency = orderItemArrayList.get(0).getCurrency();
        }
        return new Order(0, shopId, null, 0, null,
                orderTotalPrice, orderCurrency, null, null, orderTotalQuantity,
                null, null);
    }

    public static Order createOrder(User user, String paymentMethod, String cardholder, String cardNumber, String mm, String yy, String cvv) throws IOException, FileElaborationException {
        Order order = null;
        Payment payment = null;
        int shopId = 0;
        List<OrderItem> orderItemArrayList;
        CartElaboration.delete0QuantityItemsFromCart();
        orderItemArrayList = CartElaboration.readOrderItemsFromCart();
        String orderItemsJson = "";
        double orderTotalPrice = 0;
        int orderTotalQuantity = 0;
        String orderCurrency;

        if (CartElaboration.isEmptyCart()) {
            return null;
        }

        if (orderItemArrayList == null){
            return null;
        }
        else {
            orderItemsJson = CartElaboration.convertJsonCartToString();
            for (OrderItem orderItem : orderItemArrayList) {
                orderTotalQuantity += orderItem.getQuantityOrdered();
                orderTotalPrice += orderItem.getPriceTotal();
            }
            shopId = orderItemArrayList.get(0).getShopId();
            orderCurrency = orderItemArrayList.get(0).getCurrency();
        }

        if (!CartElaboration.isValidCart()) {
            logger.log(Level.SEVERE, "not valid cart");
            return null;
        }

        //check params passed from UX/UI
        if (user != null && !paymentMethod.isBlank()) {
            if (paymentMethod == Constants.CASH_ON_DELIVERY_PAYMENT && cardNumber.isBlank() && cardholder.isBlank() && mm.isBlank() && yy.isBlank() && cvv.isBlank()) {
                payment = new Payment(
                        0,
                        null,
                        null,
                        null,
                        paymentMethod,
                        null,
                        orderTotalPrice,
                        orderCurrency,
                        null,
                        null
                );
            } else {
                payment = new Payment(
                        0,
                        cardNumber.substring(cardNumber.length() - 4),
                        mm,
                        yy,
                        paymentMethod,
                        cardholder,
                        orderTotalPrice,
                        orderCurrency,
                        null,
                        null
                );
            }
            //insert payment
            payment = OrderDao.insertPayment(payment);
            //check on payment
            if (payment == null) {
                logger.log(Level.SEVERE, "payment not executed");
                return null;
            }

            order = new Order(
                    0,
                    shopId,
                    user.getUsername(),
                    payment.getPaymentId(),
                    payment.getPaymentTimestamp(),
                    orderTotalPrice,
                    payment.getCurrency(),
                    null,
                    null,
                    orderTotalQuantity,
                    orderItemArrayList,
                    orderItemsJson
            );
            //insert order
            order = OrderDao.insertOrder(order);
            //check on order
            if (order == null) {
                logger.log(Level.SEVERE, "insertOrder failed");
                return null;
            }
            OrderDao.insertOrderItems(order.getOrderId(), FileElaboration.fileToString(Constants.CART_PATH));
            //deleteCart after order is created
            CartElaboration.deleteCart();
            return order;
        }
        return null;
    }

    public static boolean setStatusOrder(Order order, String status) {
        return OrderDao.setStatusOrder(order.getOrderId(), status);
    }
}