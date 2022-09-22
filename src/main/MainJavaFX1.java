package main;

import control.CartElaboration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class MainJavaFX1 extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage1) throws IOException {
        Parent root1 = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/view/view1/login.fxml"))));
        Scene scene1 = new Scene(root1, 1000, 700);
        stage1.setTitle("Login1");
        stage1.setScene(scene1);
        stage1.show();
        stage1.setResizable(false);
        CartElaboration.deleteCart();
    }
}