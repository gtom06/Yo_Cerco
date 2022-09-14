package main;

import control.CartElaboration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainJavaFX2 extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("Java version: " + System.getProperty("java.home"));
        Parent root = FXMLLoader.load((getClass().getResource("/view/view2/login.fxml")));
        Scene scene = new Scene(root, 1080, 720);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        CartElaboration.deleteCart();
    }
}