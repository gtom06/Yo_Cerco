package main;

import control.CartElaboration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Dao.OrderDao;
import model.Order.Order;
import model.Product.ProductShop;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class MainJavaFX extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("/view/login.fxml")));
        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        //todo: remove this lines

        Order order = new Order(0, 1, "abc", "visa", null,
                100.0, "usd", null, Timestamp.from(Instant.now()),1, null);
        System.out.println(order);
        order = OrderDao.insertOrder(order);
        System.out.println(order);
        OrderDao.insertOrderItems(order.getOrderId(), CartElaboration.convertJsonCartToString());

        CartElaboration.deleteCart();
        //Cart.readOrderItemsFromCart();
        ProductShop productShop = new ProductShop(1.0,10,"eur", 1.0, 100, 100,1, 1,"ciao1", null, 0, 1.0,null);
        CartElaboration.addOrderItemsToCart(productShop, 5);
        ProductShop productShop2 = new ProductShop(1.0,10,"eur", 1.0, 100, 100,1,5,"ciao2", null, 0, 1.1,null);
        System.out.println(productShop2);
        CartElaboration.addOrderItemsToCart(productShop2, 2);


        ProductShop productShop3 = new ProductShop(1.0,10,"eur", 1.0, 100, 100,1,10,"ciao3", null, 0, 1.1,null);
        System.out.println(productShop3);
        CartElaboration.addOrderItemsToCart(productShop2, 0);

        //System.out.println(readCart());
        CartElaboration.delete0QuantityItemsFromCart();

    }
}