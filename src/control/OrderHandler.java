package control;

import bean.OrderBean;
import bean.OrderItemBean;
import bean.PaymentBean;
import bean.UserBean;
import com.google.gson.Gson;
import exceptions.ExceptionCart;
import exceptions.FileElaborationException;
import constants.Constants;
import constants.ConstantsExceptions;
import dao.OrderDao;
import model.order.Order;
import model.order.OrderItem;
import model.order.Payment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderHandler {
    static final Logger logger = Logger.getLogger(OrderHandler.class.getName());
    private OrderHandler(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }



    public static List<OrderBean> findOrdersInfoFromUser(UserBean user) {
        ArrayList<Order> orderArrayList = (ArrayList<Order>) OrderDao.findOrdersFromUser(user.getUsername());
        return OrderHandler.toListOrderBean(orderArrayList);
    }

    public static List<OrderBean> findOrdersByAdmin(UserBean user){
        ArrayList<Order> orderArrayList = (ArrayList<Order>) OrderDao.findOrdersByAdmin(user.getUsername());
        return OrderHandler.toListOrderBean(orderArrayList);
    }
    private static List<OrderBean> toListOrderBean(List<Order> orderList){
        ArrayList<OrderBean> orderBeanArrayList = new ArrayList<>();
        if (orderList.isEmpty()){
            return Collections.emptyList();
        }
        for (Order order : orderList){
            OrderBean ob = new OrderBean();
            ob.setOrderId(order.getOrderId());
            ob.setShopId(order.getShopId());
            ob.setPaymentId(order.getPaymentId());
            ob.setOrderTimestamp(order.getOrderTimestamp());
            ob.setTotalPrice(order.getTotalPrice());
            ob.setCurrency(order.getCurrency());
            ob.setOrderTotalQuantity(order.getOrderTotalQuantity());
            ob.setUsername(order.getUsername());
            orderBeanArrayList.add(ob);
        }
        return orderBeanArrayList;
    }

    public static OrderBean populateOrderWithOrderItems(OrderBean order){
        ArrayList<OrderItem> orderItemArrayList;
        try {
            String orderItemsJson = OrderDao.findOrderItemsFromOrder(order.getOrderId());
            if (orderItemsJson.equals("")){
                return null;
            }
            OrderItem[] output = new Gson().fromJson(orderItemsJson, OrderItem[].class);
            if (output == null) {
                return null;
            }
            orderItemArrayList = new ArrayList<>(List.of(output));
            order.setOrderItemArrayList(OrderHandler.toListOrderItemBean(orderItemArrayList));
        } catch (Exception e) {
            logger.log(Level.WARNING, ConstantsExceptions.ORDER_HANDLER_ERROR);
        }
        return order;
    }

    private static List<OrderItemBean> toListOrderItemBean(ArrayList<OrderItem> orderItemArrayList) {
        List<OrderItemBean> orderItemBeanList = new ArrayList<>();
        for (OrderItem oi : orderItemArrayList) {
            OrderItemBean orderItemBean = new OrderItemBean();
            orderItemBean.setDiscountedPrice(oi.getDiscountedPrice());
            orderItemBean.setPrice(oi.getPrice());
            orderItemBean.setDepartmentId(oi.getDepartmentId());
            orderItemBean.setPriceTotal(oi.getPriceTotal());
            orderItemBean.setSize(oi.getSize());
            orderItemBean.setLogoImagepath(oi.getLogoImagepath());
            orderItemBean.setCurrency(oi.getCurrency());
            orderItemBean.setSku(oi.getSku());
            orderItemBean.setShopId(oi.getShopId());
            orderItemBean.setUnitOfMeasure(oi.getUnitOfMeasure());
            orderItemBean.setQuantityOrdered(oi.getQuantityOrdered());
            orderItemBean.setDescription(oi.getDescription());
            orderItemBean.setBrand(oi.getBrand());
            orderItemBean.setName(oi.getName());
            orderItemBeanList.add(orderItemBean);
        }
        return orderItemBeanList;
    }

    public static OrderBean previewOrder() throws ExceptionCart {
        List<OrderItemBean> orderItemArrayList;
        try {
            CartElaboration.delete0QuantityItemsFromCart();
        } catch (IOException e) {
            logger.log(Level.WARNING, ConstantsExceptions.ORDER_HANDLER_ERROR);
        }
        orderItemArrayList = CartElaboration.readOrderItemsFromCart();
        double orderTotalPrice = 0;
        int orderTotalQuantity = 0;
        String orderCurrency;
        int shopId = 0;
        if (orderItemArrayList == null || orderItemArrayList.isEmpty()){
            return null;
        }
        else {
            for (OrderItemBean orderItem : orderItemArrayList) {
                orderTotalQuantity += orderItem.getQuantityOrdered();
                orderTotalPrice += orderItem.getPriceTotal();
            }
            orderCurrency = orderItemArrayList.get(0).getCurrency();
        }
        OrderBean orderBean = new OrderBean();
        PaymentBean paymentBean = new PaymentBean();
        orderBean.setShopId(shopId);
        orderBean.setTotalPrice(orderTotalPrice);
        orderBean.setCurrency(orderCurrency);
        orderBean.setOrderItemArrayList(orderItemArrayList);
        orderBean.setOrderTotalQuantity(orderTotalQuantity);
        paymentBean.setCurrency(orderCurrency);
        paymentBean.setTotalPrice(orderTotalPrice);
        orderBean.setPayment(paymentBean);
        return orderBean;
    }

    public static OrderBean createOrder(UserBean user, String paymentMethod, String cardholder) throws IOException, FileElaborationException {
        Payment payment = null;
        Order order = null;
        OrderBean order2 = null;
        PaymentBean payment2 = null;
        int shopId = 0;
        List<OrderItemBean> orderItemArrayList;
        CartElaboration.delete0QuantityItemsFromCart();
        orderItemArrayList = CartElaboration.readOrderItemsFromCart();
        String orderItemsJson = "";
        double orderTotalPrice = 0;
        int orderTotalQuantity = 0;
        String orderCurrency;

        if (CartElaboration.isEmptyCart() || orderItemArrayList == null
                || orderItemArrayList.isEmpty() || !CartElaboration.isValidCart()){
            logger.log(Level.INFO, "not valid cart or empty cart");
            return null;
        }
        else {
            orderItemsJson = CartElaboration.convertJsonCartToString();
            for (OrderItemBean orderItem : orderItemArrayList) {
                orderTotalQuantity += orderItem.getQuantityOrdered();
                orderTotalPrice += orderItem.getPriceTotal();
            }
            shopId = orderItemArrayList.get(0).getShopId();
            orderCurrency = orderItemArrayList.get(0).getCurrency();
        }


        //check params passed from UX/UI
        if (user != null && !paymentMethod.isBlank()) {
            //insert payment
            payment = OrderDao.insertPayment2(paymentMethod, cardholder, orderTotalPrice, orderCurrency);
            //check on payment
            if (payment == null) {
                logger.log(Level.SEVERE, "payment not executed");
                return null;
            }

            //create PaymentBean
            payment2 = new PaymentBean();
            payment2.setPaymentMethod(paymentMethod);
            payment2.setTotalPrice(orderTotalPrice);
            payment2.setCurrency(orderCurrency);
            payment2.setCardholder(cardholder);
            payment2.setPaymentId(payment.getPaymentId());
            payment2.setPaymentTimestamp(payment.getPaymentTimestamp());
            payment2.setStatus(payment.getStatus());

            //insert order
            order = OrderDao.insertOrder2(shopId, user.getUsername(), payment.getPaymentId(), payment.getPaymentTimestamp(), orderTotalPrice, orderTotalQuantity, orderCurrency);
            OrderDao.insertOrderItems(order.getOrderId(), FileElaboration.fileToString(Constants.CART_PATH));

            //create OrderBean
            order2 = new OrderBean();
            order2.setShopId(shopId);
            order2.setUsername(user.getUsername());
            order2.setPayment(payment2);
            order2.setOrderTotalQuantity(orderTotalQuantity);
            order2.setOrderItemArrayList(orderItemArrayList);
            order2.setOrderItemString(orderItemsJson);
            order2.setOrderId(order.getOrderId());
            order2.setOrderTimestamp(order.getOrderTimestamp());
            order2.setStatus(order.getStatus());
            order2.setCollectionTimestamp(order.getCollectionTimestamp());

            //deleteCart after order is created
            CartElaboration.deleteCart();
            return order2;
        }
        return null;
    }

    public static boolean setStatusOrder(OrderBean order, String status) {
        return OrderDao.setStatusOrder(order.getOrderId(), status);
    }

    public static boolean someBlankDataUser(String name, String surname, String billingStreet, String billingCity, String billingCountry, String billingZip, String phoneNumber) {
        return name.isBlank() || surname.isBlank() || billingStreet.isBlank() || billingCity.isBlank() ||
                billingCountry.isBlank() || billingZip.isBlank() || phoneNumber.isBlank();
    }
}