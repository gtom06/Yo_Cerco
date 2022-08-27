package control;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import model.Constants;
import model.Order.OrderItem;
import model.Product.ProductShop;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CartElaboration {
    public static ArrayList<OrderItem> readOrderItemsFromCart() {
        ArrayList<OrderItem> orderItemArrayList = new ArrayList<>();
        try {
            JsonReader reader = new JsonReader(new FileReader(Constants.CART_PATH));
            OrderItem[] output = new Gson().fromJson(reader, OrderItem[].class);
            if (output == null) {
                return null;
            }
            orderItemArrayList = new ArrayList<>(List.of(output));
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return orderItemArrayList;
        }
    }

    public static boolean isValidCart() {
        try {
            JsonReader reader = new JsonReader(new FileReader(Constants.CART_PATH));
            OrderItem[] output = new Gson().fromJson(reader, OrderItem[].class);
            if (output == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void addArrayListOrderItemsToCart(ArrayList<ProductShop> productShopArrayList, ArrayList<Integer> newQuantityArrayList) {
        for (int i = 0; i<productShopArrayList.size(); i++) {
            addOrderItemsToCart(productShopArrayList.get(i), newQuantityArrayList.get(i));
        }
    }

    public static void addOrderItemsToCart(ProductShop productShop, int newQuantity) {
        ArrayList<OrderItem> orderItemArrayList;
        try {
            orderItemArrayList = readOrderItemsFromCart();
            if (orderItemArrayList != null) {
                boolean found = false;
                for (OrderItem orderItem : orderItemArrayList) {
                    if (orderItem.getProductShop().getSku() == productShop.getSku()) {
                        //int actualQuantity = orderItem.getQuantityOrdered();
                        found = true;
                        orderItem.setQuantityOrdered(newQuantity);
                    }
                }
                if (!found) {
                    orderItemArrayList.add(new OrderItem(null, productShop, newQuantity));
                }
            } else {
                orderItemArrayList = new ArrayList<>();
                orderItemArrayList.add(new OrderItem(null, productShop, newQuantity));
            }
            String json = new Gson().toJson(orderItemArrayList);
            BufferedWriter out = new BufferedWriter(new FileWriter(Constants.CART_PATH));
            out.write(json);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteCart() {
        try {
            String emptyString = "";
            BufferedWriter out = new BufferedWriter(new FileWriter(Constants.CART_PATH));
            out.write(emptyString);
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean delete0QuantityItemsFromCart() {
        //initialize vars
        boolean output = false;
        ArrayList<OrderItem> orderItemArrayList;
        ArrayList<OrderItem> orderItemArrayListToDel = new ArrayList<>();
        try {
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
                BufferedWriter out = new BufferedWriter(new FileWriter(Constants.CART_PATH));
                out.write(json);
                out.close();
                output = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return output;
        }
    }

    public static String convertJsonCartToString() {
        return FileElaboration.fileToString(Constants.CART_PATH);
    }
}