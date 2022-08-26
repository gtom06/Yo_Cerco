package main;

import control.Cart;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Product.ProductShop;

import java.io.IOException;

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
        Cart.readOrderItemsFromCart();
        ProductShop productShop = new ProductShop(1,1.0,10,"eur", 1.0, 100, 100,1,"bb");
        Cart.addOrderItemsToCart(productShop, 5);
        ProductShop productShop2 = new ProductShop(12,1.0,10,"eur", 1.0, 100, 100,1,"bb");
        Cart.addOrderItemsToCart(productShop2, 2);
    }
}