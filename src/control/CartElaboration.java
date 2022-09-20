package control;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import exceptions.ExceptionCart;
import exceptions.FileElaborationException;
import model.Constants;
import model.ConstantsExceptions;
import model.order.OrderItem;
import model.product.ProductShop;

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

    public static List<OrderItem> readOrderItemsFromCart() throws ExceptionCart {
        ArrayList<OrderItem> orderItemArrayList = new ArrayList<>();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(Constants.CART_PATH));
            OrderItem[] output = new Gson().fromJson(reader, OrderItem[].class);
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

    public static boolean addArrayListOrderItemsToCart(List<ProductShop> productShopList, List<Integer> newQuantityArrayList) throws IOException, FileElaborationException {
        List<OrderItem> orderItemArrayList = readOrderItemsFromCart();
        //backup file
        if (orderItemArrayList != null && !orderItemArrayList.isEmpty()) {
            FileElaboration.writeOnFile(Constants.CART_PATH2, FileElaboration.fileToString(Constants.CART_PATH));
        }
        for (int i = 0; i<productShopList.size(); i++) {
            if (!addOrderItemsToCart(productShopList.get(i), newQuantityArrayList.get(i))) {
                FileElaboration.writeOnFile(Constants.CART_PATH, FileElaboration.fileToString(Constants.CART_PATH2));
                FileElaboration.writeOnFile(Constants.CART_PATH2, "");
                return false;
            }
        }
        return true;
    }

    public static boolean addOrderItemsToCart(ProductShop productShop, int quantityToAdd) throws ExceptionCart {
        List<OrderItem> orderItemArrayList;
        try {
            orderItemArrayList = readOrderItemsFromCart();
            if (!orderItemArrayList.isEmpty() && orderItemArrayList.get(0).getShopId() != productShop.getShopId()){
                return false; //prevent adding products from different shops
            }
            int position = -1;
            OrderItem orderItemToAdd = new OrderItem(
                    productShop.getPrice(),
                    productShop.getCurrency(),
                    productShop.getShopId(),
                    productShop.getSku(),
                    productShop.getName(),
                    productShop.getBrand(),
                    productShop.getDescription(),
                    productShop.getSize(),
                    productShop.getUnitOfMeasure(),
                    productShop.getLogoImagepath(),
                    productShop.getDepartmentId(),
                    quantityToAdd,
                    productShop.getPrice() * quantityToAdd,
                    productShop.getDiscountedPrice()
            );
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

    protected static void setNewQuantityOnOrderItem(List<OrderItem> orderItemList, int position, int quantityToAdd) {
        OrderItem orderItem = orderItemList.get(position);
        int actualQuantity = orderItem.getQuantityOrdered();
        orderItem.setQuantityOrdered(actualQuantity + quantityToAdd);
        if (orderItem.getDiscountedPrice() == 0) {
            orderItem.setPriceTotal(orderItem.getPrice() * orderItem.getQuantityOrdered());
        }
        else {
            orderItem.setPriceTotal(orderItem.getDiscountedPrice() * orderItem.getQuantityOrdered());
        }
    }

    protected static int positionOfExistingProductInCart(ProductShop productShop, List<OrderItem> orderItemList){
        for (OrderItem orderItem : orderItemList) {
            if (orderItem.getSku() == productShop.getSku()) {
                return orderItemList.indexOf(orderItem);
            }
        }
        return -1;
    }

    protected static OrderItem addNewProductToCart(ProductShop productShop, OrderItem orderItem, int quantityToAdd) {
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

    protected static boolean writeOrderItemOnCart(List<OrderItem> orderItemList) throws ExceptionCart {
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
        ArrayList<OrderItem> orderItemArrayList;
        ArrayList<OrderItem> orderItemArrayListToDel = new ArrayList<>();
        try {
            if (!isEmptyCart()) {
                //read json && convert to arrayList
                orderItemArrayList = new ArrayList<>(List.of(new Gson().fromJson(new JsonReader(new FileReader(Constants.CART_PATH)), OrderItem[].class)));
                for (OrderItem orderItem : orderItemArrayList) {
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