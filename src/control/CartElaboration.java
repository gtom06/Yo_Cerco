package control;

import bean.OrderItemBean;
import bean.ProductShopBean;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import exceptions.ExceptionCart;
import exceptions.FileElaborationException;
import constants.Constants;
import constants.ConstantsExceptions;
import model.order.OrderItem;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CartElaboration {
    static final Logger logger = Logger.getLogger(CartElaboration.class.getName());
    private CartElaboration(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    public static List<OrderItemBean> readOrderItemsFromCart() throws ExceptionCart {
        ArrayList<OrderItemBean> orderItemArrayList = new ArrayList<>();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(Constants.CART_PATH));
            OrderItemBean[] output = new Gson().fromJson(reader, OrderItemBean[].class);
            if (output == null) {
                return Collections.emptyList();
            }
            orderItemArrayList = new ArrayList<>(List.of(output));
        } catch (IOException e) {
            logger.log(Level.WARNING, ConstantsExceptions.CART_ELABORATION_FAILURE_INFO);
            throw new ExceptionCart(ConstantsExceptions.CART_ELABORATION_FAILURE_CLOSING_READING_FILE);
        }
        return orderItemArrayList;
    }

    public static boolean isValidCart() {
        try {
            JsonReader reader = new JsonReader(new FileReader(Constants.CART_PATH));
            OrderItem[] output = new Gson().fromJson(reader, OrderItem[].class);
            if (output == null) {
                return false;
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, ConstantsExceptions.CART_ELABORATION_FAILURE_INFO);
            return false;
        }
        return true;
    }

    public static boolean addOrderItemsToCart(ProductShopBean productShop, int quantityToAdd) throws ExceptionCart {
        List<OrderItemBean> orderItemArrayList;
        try {
            orderItemArrayList = readOrderItemsFromCart();
            if (!orderItemArrayList.isEmpty() && orderItemArrayList.get(0).getShopId() != productShop.getShopId()){
                return false; //prevent adding products from different shops
            }
            int position = -1;
            OrderItemBean orderItemToAdd = new OrderItemBean();
            orderItemToAdd.setPrice(productShop.getPrice());
            orderItemToAdd.setCurrency(productShop.getCurrency());
            orderItemToAdd.setShopId(productShop.getShopId());
            orderItemToAdd.setName(productShop.getName());
            orderItemToAdd.setSku(productShop.getSku());
            orderItemToAdd.setBrand(productShop.getBrand());
            orderItemToAdd.setDescription(productShop.getDescription());
            orderItemToAdd.setSize(productShop.getSize());
            orderItemToAdd.setUnitOfMeasure(productShop.getUnitOfMeasure());
            orderItemToAdd.setLogoImagepath(productShop.getLogoImagepath());
            orderItemToAdd.setDepartmentId(productShop.getDepartmentId());
            orderItemToAdd.setQuantityOrdered(quantityToAdd);
            orderItemToAdd.setPriceTotal(productShop.getPrice() * quantityToAdd);
            orderItemToAdd.setDiscountedPrice(productShop.getDiscountedPrice());

            if (!orderItemArrayList.isEmpty()) {
                position = positionOfExistingProductInCart(productShop, orderItemArrayList);
                if (position != -1) {
                    setNewQuantityOnOrderItem(orderItemArrayList, position, quantityToAdd);
                } else {
                    orderItemToAdd = addNewProductToCart(productShop, orderItemToAdd, quantityToAdd);
                    orderItemArrayList.add(orderItemToAdd);
                }
            } else {
                orderItemToAdd = addNewProductToCart(productShop, orderItemToAdd, quantityToAdd);
                orderItemArrayList = new ArrayList<>();
                orderItemArrayList.add(orderItemToAdd);
            }
            writeOrderItemOnCart(orderItemArrayList);
        } catch (Exception e) {
            logger.log(Level.WARNING, ConstantsExceptions.CART_ELABORATION_FAILURE_INFO);
            throw new ExceptionCart(ConstantsExceptions.CART_ELABORATION_FAILURE_CLOSING_WRITING_FILE);
        }
        return true;
    }

    protected static void setNewQuantityOnOrderItem(List<OrderItemBean> orderItemList, int position, int quantityToAdd) {
        OrderItemBean orderItem = orderItemList.get(position);
        int actualQuantity = orderItem.getQuantityOrdered();
        orderItem.setQuantityOrdered(actualQuantity + quantityToAdd);
        if (orderItem.getDiscountedPrice() == 0) {
            orderItem.setPriceTotal(orderItem.getPrice() * orderItem.getQuantityOrdered());
        }
        else {
            orderItem.setPriceTotal(orderItem.getDiscountedPrice() * orderItem.getQuantityOrdered());
        }
    }

    protected static int positionOfExistingProductInCart(ProductShopBean productShop, List<OrderItemBean> orderItemList){
        for (OrderItemBean orderItem : orderItemList) {
            if (orderItem.getSku() == productShop.getSku()) {
                return orderItemList.indexOf(orderItem);
            }
        }
        return -1;
    }

    protected static OrderItemBean addNewProductToCart(ProductShopBean productShop, OrderItemBean orderItem, int quantityToAdd) {
        //not discounted
        if (productShop.getDiscountedPrice() == 0) {
            orderItem.setPriceTotal(productShop.getPrice() * quantityToAdd);
        }
        //discounted
        else {
            orderItem.setPriceTotal(productShop.getDiscountedPrice() * quantityToAdd);
        }
        return orderItem;
    }

    protected static boolean writeOrderItemOnCart(List<OrderItemBean> orderItemList) throws ExceptionCart {
        String json = new Gson().toJson(orderItemList);
        try (BufferedWriter out = new BufferedWriter(new FileWriter(Constants.CART_PATH))){
            out.write(json);
        } catch (Exception e){
            logger.log(Level.WARNING, ConstantsExceptions.CART_ELABORATION_FAILURE_INFO);
            throw new ExceptionCart(ConstantsExceptions.CART_ELABORATION_FAILURE_CLOSING_WRITING_FILE);
        }
        return true;
    }

    public static void deleteCart() throws ExceptionCart {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(Constants.CART_PATH))){
            String emptyString = "";
            out.write(emptyString);
        }
        catch (Exception e) {
            logger.log(Level.WARNING, ConstantsExceptions.CART_ELABORATION_FAILURE_INFO);
            throw new ExceptionCart(ConstantsExceptions.CART_ELABORATION_FAILURE_CLOSING_WRITING_FILE);
        }
    }

    public static boolean isEmptyCart() {
        boolean bool = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.CART_PATH))){
            if (reader.read() == -1) {
                bool = true;
            }
        }
        catch (Exception e) {
            logger.log(Level.WARNING, ConstantsExceptions.CART_ELABORATION_FAILURE_INFO);
            return false;
        }
        return bool;
    }

    public static boolean delete0QuantityItemsFromCart() throws IOException {
        //initialize vars
        boolean output = false;
        ArrayList<OrderItemBean> orderItemArrayList;
        ArrayList<OrderItemBean> orderItemArrayListToDel = new ArrayList<>();
        try {
            if (!isEmptyCart()) {
                //read json && convert to arrayList
                orderItemArrayList = new ArrayList<>(List.of(new Gson().fromJson(new JsonReader(new FileReader(Constants.CART_PATH)), OrderItem[].class)));
                for (OrderItemBean orderItem : orderItemArrayList) {
                    if (orderItem.getQuantityOrdered() == 0) {
                        orderItemArrayListToDel.add(orderItem);
                    }
                }
                if (orderItemArrayListToDel.isEmpty()) {
                    output = true;
                } else {
                    orderItemArrayList.removeAll(orderItemArrayListToDel);
                    String json = new Gson().toJson(orderItemArrayList);
                    try (BufferedWriter out = new BufferedWriter(new FileWriter(Constants.CART_PATH))) {
                        out.write(json);
                        output = true;
                    }
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, ConstantsExceptions.CART_ELABORATION_FAILURE_INFO);
        }
        return output;
    }

    public static String convertJsonCartToString() throws FileElaborationException {
        return FileElaboration.fileToString(Constants.CART_PATH);
    }
}