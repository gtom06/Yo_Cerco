package main;

import constants.Constants;
import control.CartElaboration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainJavaFX2 extends Application {
    public static void main(String[] args) {
        launch();
    }
    static final Logger logger = Logger.getLogger(MainJavaFX2.class.getName());

    @Override
    public void start(Stage stage2) throws IOException {
        logger.log(Level.INFO, Constants.RUN_OK_2);
        Parent root2 = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/view/view2/login.fxml"))));
        Scene scene2 = new Scene(root2, 1080, 720);
        stage2.setTitle("Login");
        stage2.setScene(scene2);
        stage2.show();
        stage2.setResizable(false);
        CartElaboration.deleteCart();
    }
}