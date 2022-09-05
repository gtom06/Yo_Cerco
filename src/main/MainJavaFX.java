package main;

import control.CartElaboration;
import control.OrderHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Order.Order;
import model.Product.ProductShop;
import model.Shop.Shop;
import model.User.Buyer;

import java.io.IOException;

public class MainJavaFX extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("Java version: " + System.getProperty("java.home"));
        Parent root = FXMLLoader.load((getClass().getResource("/view/login.fxml")));
        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);




        //todo: remove lines

        /*
        Order order = new Order(0, 1, "abc", "visa", null,
                100.0, "usd", null, Timestamp.from(Instant.now()),1, null);
        System.out.println(order);
        order = OrderDao.insertOrder(order);
        System.out.println(order);
        OrderDao.insertOrderItems(order.getOrderId(), null);
        CartElaboration.deleteCart();
        */
        CartElaboration.deleteCart();
        //Cart.readOrderItemsFromCart();
        ProductShop productShop = new ProductShop(150.0,10,"eur", 0,1, 100, 1,1, "name1", "brand1", "description1", 1.0,"m", "null", 1);
        CartElaboration.addOrderItemsToCart(productShop, 5);
        ProductShop productShop2 = new ProductShop(100.0,10,"eur", 0,1, 100, 1,2, "name2", "brand2", "description1", 1.0,"m", "null", 1);
        CartElaboration.addOrderItemsToCart(productShop, 5);
        System.out.println(productShop2);
        CartElaboration.addOrderItemsToCart(productShop2, 2);

        //System.out.println(readCart());
        System.out.println(CartElaboration.readOrderItemsFromCart());
        CartElaboration.delete0QuantityItemsFromCart();

        Buyer buyer = new Buyer("gtom", "null", "null", "null", "null", null, "null", "null", "null", "null", "null", "M", "null");
        Shop shop = new Shop("","","","","","",
                "",2, 0,"","",0,0,"","",
                0);
        Order order = OrderHandler.createOrder(buyer, shop, "cash", "ciao","444444411","10","22",
                "124");
        System.out.println(order);

    }
}