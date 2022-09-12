package view.view1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User.User;

import java.io.IOException;

public class HomepageAdmin {
    User user = null;
    // Import the application's controls
    @FXML
    Button logoutButton;
    @FXML
    AnchorPane anchorPane1;

    @FXML
    protected void onLogoutButtonClick() throws IOException {
        Stage stage2 = new Stage();
        Parent root = FXMLLoader.load((getClass().getResource("login.fxml")));
        Scene scene = new Scene(root, 1000, 700);
        stage2.setTitle("LoginPage");
        stage2.setScene(scene);
        stage2.show();
        stage2.setResizable(false);
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
    }

    public void passUser(User user) {
        this.user = user;
    }

    public void openOrders() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ordersAdmin.fxml"));
        Parent root = loader.load();
        OrdersAdmin ordersAdmin = loader.getController();
        ordersAdmin.passUser(user);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) anchorPane1.getScene().getWindow();
        stage.close();
    }
}
