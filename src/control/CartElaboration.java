package control;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import exceptions.ExceptionCart;
import exceptions.FileElaborationException;
import model.Constants;
import model.ConstantsExceptions;
import model.Order.OrderItem;
import model.Product.ProductShop;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CartElaboration {
    static Logger logger = Logger.getLogger(CartElaboration.class.getName());
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
                return null;
            }
            orderItemArrayList = new ArrayList<>(List.of(output));
            reader.close();
        } catch (IOException e) {
            logger.log(Level.WARNING, ConstantsExceptions.CART_ELABORATION_FAILURE_INFO);
            throw new ExceptionCart(ConstantsExceptions.CART_ELABORATION_FAILURE_CLOSING_READING_FILE);
        } finally {
            try{
                if (reader != null){
                    reader.close();
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, ConstantsExceptions.CART_ELABORATION_FAILURE_INFO);
            }
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
        if (orderItemArrayList != null && orderItemArrayList.size() != 0) {
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
        BufferedWriter out = null;
        try {
            orderItemArrayList = readOrderItemsFromCart();
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

            if (orderItemArrayList != null && orderItemArrayList.size() != 0) {
                boolean found = false;
                if (orderItemArrayList.get(0).getShopId() != productShop.getShopId()){
                    return false;
                }
                for (OrderItem orderItem : orderItemArrayList) {
                    if (orderItem.getSku() == productShop.getSku()) {
                        int actualQuantity = orderItem.getQuantityOrdered();
                        found = true;
                        orderItem.setQuantityOrdered(actualQuantity + quantityToAdd);
                        if (orderItem.getDiscountedPrice() == 0) {
                            orderItem.setPriceTotal(orderItem.getPrice() * orderItem.getQuantityOrdered());
                        }
                        else {
                            orderItem.setPriceTotal(orderItem.getDiscountedPrice() * orderItem.getQuantityOrdered());
                        }
                    }
                }
                if (!found) {
                    //not discounted
                    if (productShop.getDiscountedPrice() == 0) {
                        orderItemToAdd.setPriceTotal(productShop.getPrice() * quantityToAdd);
                    }
                    //discounted
                    else {
                        orderItemToAdd.setPriceTotal(productShop.getDiscountedPrice() * quantityToAdd);
                    }
                    orderItemArrayList.add(orderItemToAdd);
                }
            } else {
                //not discounted
                if (productShop.getDiscountedPrice() == 0) {
                    orderItemToAdd.setPriceTotal(productShop.getPrice() * quantityToAdd);
                }
                //discounted
                else {
                    orderItemToAdd.setPriceTotal(productShop.getDiscountedPrice() * quantityToAdd);
                }
                orderItemArrayList = new ArrayList<>();
                orderItemArrayList.add(orderItemToAdd);
            }
            String json = new Gson().toJson(orderItemArrayList);
            out = new BufferedWriter(new FileWriter(Constants.CART_PATH));
            out.write(json);
            out.close();
        } catch (Exception e) {
            logger.log(Level.WARNING, ConstantsExceptions.CART_ELABORATION_FAILURE_INFO);
            throw new ExceptionCart(ConstantsExceptions.CART_ELABORATION_FAILURE_CLOSING_WRITING_FILE);
        } finally{
            try{
                if (out != null){
                    out.close();
                }
            }
            catch (Exception e ) {
                logger.log(Level.WARNING, ConstantsExceptions.CART_ELABORATION_FAILURE_INFO);
            }
        }
        return true;
    }

    public static void deleteCart() throws ExceptionCart {
        BufferedWriter out = null;
        try {
            String emptyString = "";
            out = new BufferedWriter(new FileWriter(Constants.CART_PATH));
            out.write(emptyString);
        }
        catch (Exception e) {
            logger.log(Level.WARNING, ConstantsExceptions.CART_ELABORATION_FAILURE_INFO);
            throw new ExceptionCart(ConstantsExceptions.CART_ELABORATION_FAILURE_CLOSING_WRITING_FILE);
        }
        finally {
            try{
                if (out != null){
                    out.close();
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, ConstantsExceptions.CART_ELABORATION_FAILURE_INFO);
            }
        }
    }

    public static boolean isEmptyCart() throws IOException {
        boolean bool = false;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(Constants.CART_PATH));
            if (reader.read() == -1){
                bool = true;
            }
        }
        catch (Exception e) {
            logger.log(Level.WARNING, ConstantsExceptions.CART_ELABORATION_FAILURE_INFO);
            return false;
        }
        finally{
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, ConstantsExceptions.CART_ELABORATION_FAILURE_INFO);
            }
        }
        return bool;
    }

    public static boolean delete0QuantityItemsFromCart() {
        //initialize vars
        boolean output = false;
        BufferedWriter out = null;
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
                if (orderItemArrayListToDel.size() == 0) {
                    output = true;
                } else {
                    orderItemArrayList.removeAll(orderItemArrayListToDel);
                    String json = new Gson().toJson(orderItemArrayList);
                    out = new BufferedWriter(new FileWriter(Constants.CART_PATH));
                    out.write(json);
                    output = true;
                }
            }

        } catch (Exception e) {
            logger.log(Level.WARNING, ConstantsExceptions.CART_ELABORATION_FAILURE_INFO);
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e){
                logger.log(Level.WARNING, ConstantsExceptions.CART_ELABORATION_FAILURE_INFO);
            }
        }
        return output;
    }

    public static String convertJsonCartToString() throws FileElaborationException {
        return FileElaboration.fileToString(Constants.CART_PATH);
    }
}