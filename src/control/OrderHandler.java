package control;

import model.Dao.OrderDao;
import model.Order.Order;
import model.Order.OrderItem;
import model.Order.OrderPreview;
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
        //todo: in base agli items presenti dentro il cart, calcolare tutti
        // i vari parametri da inserire nell'ordine


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
                totalAmount += orderItem.getProductShop().getDiscountedAmount() * orderItem.getQuantityOrdered();
            }
            orderPreview = new OrderPreview(shopId, collectionTimestamp, totalAmount,orderTotalQuantity,orderItemArrayList);
        }
        return orderPreview;
    }

    public static boolean createOrder(User user, Shop shop, String paymentMethod, String cardholder, String cardNumber, int mm, int yy, String cvv) throws IOException {
        if (!CartElaboration.isValidCart()) {
            System.out.println("not valid cart");
            return false;
        }

        //Payment payment = new Payment(0, cardNumber., null,null, null, null, null, null, null);

        //Order = new Order(0, shop.getShopId(), user.getUsername(), 0, null, null, null, null, null, null, null);

        int orderId;
        int shopId;
        String username;
        String payment;
        Timestamp orderTimestamp;
        Double totalAmount;
        String currency;
        String status;
        Timestamp collectionTimestamp;
        int orderTotalQuantity;
        ArrayList<OrderItem> orderItemArrayList = null;

        CartElaboration.delete0QuantityItemsFromCart();
        orderItemArrayList = CartElaboration.readOrderItemsFromCart();

        String orderItemsJson = "";
        if (orderItemArrayList == null){
            return false;
        }
        else {
            orderItemsJson = CartElaboration.convertJsonCartToString();
            //calculate all params to create Order()
            for (OrderItem orderItem : orderItemArrayList) {
                orderItem.getProductShop().getAmount();
                orderItem.getProductShop().getDiscountedAmount();
                //todo: only for the first item
                orderItem.getProductShop().getShopId();
            }
        }
        CartElaboration.deleteCart();
        return true;
    }
}
