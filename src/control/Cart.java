package control;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import model.Constants;
import model.Order.OrderItem;
import model.Product.ProductShop;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    //Constants.CART_PATH
    //FileElaboration.writeOnFile(Constants., stringToWrite);
    //creazione ordine
    public static ArrayList<OrderItem> readOrderItemsFromCart() throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader(Constants.CART_PATH));
        OrderItem[] output = new Gson().fromJson(reader, OrderItem[].class);
        ArrayList<OrderItem> orderItemArrayList = new ArrayList<>(List.of(output));
        //ArrayList<OrderItem> orderItemArrayList = new ArrayList<>(List.of(new Gson().fromJson(reader, OrderItem[].class)));
        return orderItemArrayList;
    }

    public static void addOrderItemsToCart(ProductShop productShop, int newQuantity) throws IOException {
        ArrayList<OrderItem> orderItemArrayList = readOrderItemsFromCart();
        //System.out.println(productShop);
        boolean found = false;
        for (OrderItem orderItem : orderItemArrayList) {
            System.out.println(orderItem.getProductShop().getSku());
            System.out.println(productShop.getSku());
            if (orderItem.getProductShop().getSku() == productShop.getSku()) {
                int actualQuantity = orderItem.getQuantityOrdered();
                found = true;
                System.out.println(actualQuantity);
                System.out.println(newQuantity);
                //todo: remove items with 0 quantity from arrayList
                orderItem.setQuantityOrdered(newQuantity);
            }
        }
        if (!found){
            System.out.println("ciao");
            orderItemArrayList.add(new OrderItem(null, productShop, newQuantity));
        }
        String json = new Gson().toJson(orderItemArrayList);
        BufferedWriter out = new BufferedWriter(new FileWriter(Constants.CART_PATH));
        out.write(json);
        out.close();
    }
}