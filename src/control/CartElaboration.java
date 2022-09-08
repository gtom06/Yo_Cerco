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

    public static boolean addOrderItemsToCart(ProductShop productShop, int quantityToAdd) {
        ArrayList<OrderItem> orderItemArrayList;
        double priceTotal = 0;
        try {
            orderItemArrayList = readOrderItemsFromCart();
            if (orderItemArrayList != null) {
                boolean found = false;
                if (orderItemArrayList.get(0).getShopId() != productShop.getShopId()){
                    return false;
                }
                for (OrderItem orderItem : orderItemArrayList) {
                    if (orderItem.getSku() == productShop.getSku()) {
                        int actualQuantity = orderItem.getQuantityOrdered();
                        found = true;
                        orderItem.setQuantityOrdered(actualQuantity + quantityToAdd);
                        orderItem.setPriceTotal(orderItem.getPrice() * orderItem.getQuantityOrdered());
                    }
                }
                if (!found) {
                    orderItemArrayList.add(
                        new OrderItem(
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
                            productShop.getPrice() * quantityToAdd
                        )
                    );

                }
            } else {
                orderItemArrayList = new ArrayList<>();
                orderItemArrayList.add(
                    new OrderItem(
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
                        productShop.getPrice() * quantityToAdd
                    )
                );
            }
            String json = new Gson().toJson(orderItemArrayList);
            BufferedWriter out = new BufferedWriter(new FileWriter(Constants.CART_PATH));
            out.write(json);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return true;
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

    public static boolean isEmptyCart() throws IOException {
        BufferedReader reader = null;
        boolean bool = false;
        try {
            reader = new BufferedReader(new FileReader(Constants.CART_PATH));
            if (reader.read() == -1){
                bool = true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return bool;
        } finally {
            reader.close();
            return bool;
        }
    }

    public static boolean delete0QuantityItemsFromCart() {
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