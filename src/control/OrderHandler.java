package control;

import model.Dao.OrderDao;
import model.Order.Order;
import model.Order.OrderItem;
import model.Order.OrderPreview;
import model.Order.Payment;
import model.Shop.Shop;
import model.User.User;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class OrderHandler {
    public static ArrayList<Order> findOrdersInfoFromUser(User user) {
        ArrayList<Order> orderArrayList = OrderDao.findOrdersFromUser(user.getUsername());
        return orderArrayList.size() != 0 ? orderArrayList : null;
    }
    public static Order populateOrderWithOrderItems(Order order){
        /*
        ArrayList<OrderItem> orderItemArrayList = OrderDao.findOrderItemsFromOrder(order.getOrderId());
        if (orderItemArrayList.size() != 0) {
            order.setOrderItemArrayList(orderItemArrayList);
        }
        */
        return order;
    }

    public static OrderPreview previewOrder() throws IOException {
        CartElaboration.delete0QuantityItemsFromCart();
        ArrayList<OrderItem> orderItemArrayList = CartElaboration.readOrderItemsFromCart();
        OrderPreview orderPreview = null;
        if (orderItemArrayList == null){
            return null;
        }
        else {
            double totalAmount = 0;
            int orderTotalQuantity = 0;
            int shopId = orderItemArrayList.get(0).getProductShop().getShopId();
            Timestamp collectionTimestamp = Timestamp.from(Instant.now().plus(1, ChronoUnit.DAYS));
            for (OrderItem orderItem : orderItemArrayList) {
                orderTotalQuantity += orderItem.getQuantityOrdered();
                totalAmount += orderItem.getProductShop().getDiscountedPrice() * orderItem.getQuantityOrdered();
            }
            orderPreview = new OrderPreview(shopId, collectionTimestamp, totalAmount,orderTotalQuantity,orderItemArrayList);
        }
        return orderPreview;
    }

    public static Order createOrder(User user, Shop shop, String paymentMethod, String cardholder, String cardNumber, String mm, String yy, String cvv) throws IOException {
        Order order = null;

        ArrayList<OrderItem> orderItemArrayList;
        CartElaboration.delete0QuantityItemsFromCart();
        orderItemArrayList = CartElaboration.readOrderItemsFromCart();
        String orderItemsJson = "";
        double orderTotalPrice = 0;
        int orderTotalQuantity = 0;
        String orderCurrency;

        if (orderItemArrayList == null){
            return null;
        }
        else {
            orderItemsJson = CartElaboration.convertJsonCartToString();
            orderCurrency = orderItemArrayList.get(0).getProductShop().getCurrency();
            for (OrderItem orderItem : orderItemArrayList) {
                orderTotalQuantity += orderItem.getQuantityOrdered();
                orderTotalPrice += orderItem.getQuantityOrdered() * orderItem.getProductShop().getPrice();
            }
        }

        if (!CartElaboration.isValidCart()) {
            System.out.println("not valid cart");
            return null;
        }


        Payment payment = new Payment(
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

        payment = OrderDao.insertPayment(payment);
        if (payment == null) {
            System.out.println("payment not executed");
            return null;
        }

        order = new Order(
                0,
                shop.getShopId(),
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

        order = OrderDao.insertOrder(order);
        if (order == null) {
            System.out.println("insertOrder failed");
            return null;
        }

        OrderDao.insertOrderItems(order.getOrderId(), orderItemsJson);
        //deleteCart after order is created
        CartElaboration.deleteCart();
        return order;
    }
}