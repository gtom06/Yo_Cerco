package control;

import model.Dao.OrderDao;
import model.Order.Order;
import model.Order.OrderItem;
import model.Order.OrderPreview;
import model.User.User;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class OrderHandler {
    public static ArrayList<Order> findOrdersWithoutItemsFromUser(User user) {
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
                totalAmount += orderItem.getProductShop().getDiscountedPrice() * orderItem.getQuantityOrdered();
            }
            orderPreview = new OrderPreview(shopId, collectionTimestamp, totalAmount,orderTotalQuantity,orderItemArrayList);
        }
        return orderPreview;

    }

    public static boolean createOrder() throws IOException {
        /*
        if (!CartElaboration.isValidCart()) {
            System.out.println("not valid cart");
            return false;
        }
        */
        /*
        private int orderId;
        private int shopId;
        private String username;
        private String payment;
        private Timestamp orderTimestamp;
        private Double totalAmount;
        private String currency;
        private String status;
        private Timestamp collectionTimestamp;
        private int orderTotalQuantity;
        private ArrayList<OrderItem> orderItemArrayList;
        */

        CartElaboration.delete0QuantityItemsFromCart();
        ArrayList<OrderItem> orderItemArrayList = CartElaboration.readOrderItemsFromCart();
        //todo: pass this string to populate json in DB Order
        String orderItemsJson = "";
        //if insert ok, return orderId. if orderId != 0, return true;
        if (orderItemArrayList == null){
            return false;
        }
        else {
            orderItemsJson = CartElaboration.convertJsonCartToString();
            //calculate all params to create Order()
            for (OrderItem orderItem : orderItemArrayList) {
                orderItem.getProductShop().getPrice();
                orderItem.getProductShop().getDiscountedPrice();
                //todo: only for the first item
                orderItem.getProductShop().getShopId();

            }
        }




        return true;
    }
}
