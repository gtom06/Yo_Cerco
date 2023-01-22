package main;

import constants.Constants;
import constants.ConstantsExceptions;
import control.CartElaboration;
import dao.DepartmentDao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainJavaFX1 extends Application {
    static final Logger logger = Logger.getLogger(MainJavaFX1.class.getName());
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage1) throws IOException {
        logger.log(Level.INFO, Constants.RUN_OK);
        Parent root1 = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/view/view1/login.fxml"))));
        Scene scene1 = new Scene(root1, 1000, 700);
        stage1.setTitle("Login1");
        stage1.setScene(scene1);
        stage1.show();
        stage1.setResizable(false);
        CartElaboration.deleteCart();
    }
}