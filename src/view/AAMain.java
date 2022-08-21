package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AAMain extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("login.fxml")));
        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }
}