package control;

import model.Dao.OrderDao;
import model.Order.Order;
import model.Order.OrderItem;
import model.User.User;

import java.util.ArrayList;

public class OrderHandler {
    public static ArrayList<Order> findOrdersWithoutItemsFromUser(User user) {
        ArrayList<Order> orderArrayList = OrderDao.findOrdersFromUser(user.getUsername());
        return orderArrayList.size() != 0 ? orderArrayList : null;
    }
    public static Order populateOrderWithOrderItems(Order order){
        ArrayList<OrderItem> orderItemArrayList = OrderDao.findOrderItemsFromOrder(order.getOrderId());
        if (orderItemArrayList.size() != 0) {
            order.setOrderItemArrayList(orderItemArrayList);
        }
        return order;
    }
}
