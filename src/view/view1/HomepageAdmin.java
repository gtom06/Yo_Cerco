package view.view1;

import bean.UserBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.user.User;

import java.io.IOException;

public class HomepageAdmin {
    UserBean user1 = null;
    @FXML
    protected Button logoutButton1;
    @FXML
    protected AnchorPane anchorPane11;

    @FXML
    protected void onLogoutButtonClick() throws IOException {
        Stage stage21 = new Stage();
        Parent root1 = FXMLLoader.load((getClass().getResource("login.fxml")));
        Scene scene1 = new Scene(root1, 1000, 700);
        stage21.setTitle("LoginPage");
        stage21.setScene(scene1);
        stage21.show();
        stage21.setResizable(false);
        Stage stage1 = (Stage) logoutButton1.getScene().getWindow();
        stage1.close();
    }

    public void passUser(UserBean user) {
        this.user1 = user;
    }

    @FXML
    protected void openOrders() throws IOException {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("ordersAdmin.fxml"));
        Parent root = loader1.load();
        OrdersAdmin ordersAdmin1 = loader1.getController();
        //ordersAdmin1.passUser(user1);
        Stage newStage1 = new Stage();
        newStage1.setScene(new Scene(root));
        newStage1.show();
        newStage1.setResizable(false);
        Stage stage1 = (Stage) anchorPane11.getScene().getWindow();
        stage1.close();
    }
}
