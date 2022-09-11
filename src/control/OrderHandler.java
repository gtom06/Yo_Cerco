package control;

import com.google.gson.Gson;
import model.Constants;
import model.Dao.OrderDao;
import model.Order.Order;
import model.Order.OrderItem;
import model.Order.Payment;
import model.Shop.Shop;
import model.User.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderHandler {
    private OrderHandler(){
        throw new IllegalStateException("Utility class");
    }

    public static ArrayList<Order> findOrdersInfoFromUser(User user) {
        ArrayList<Order> orderArrayList = OrderDao.findOrdersFromUser(user.getUsername());
        return orderArrayList.size() != 0 ? orderArrayList : null;
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
            e.printStackTrace();
        }
        return order;
    }

    public static Order previewOrder() {
        ArrayList<OrderItem> orderItemArrayList;
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

    public static Order createOrder(User user, Shop shop, String paymentMethod, String cardholder, String cardNumber, String mm, String yy, String cvv) throws IOException {
        Order order = null;
        Payment payment = null;
        int shopId = 0;
        ArrayList<OrderItem> orderItemArrayList;
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
            System.out.println("not valid cart");
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
                System.out.println("payment not executed");
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
                System.out.println("insertOrder failed");
                return null;
            }
            OrderDao.insertOrderItems(order.getOrderId(), orderItemsJson);
            //deleteCart after order is created
            CartElaboration.deleteCart();
            return order;
        }
        return null;
    }
}