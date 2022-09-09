package view.view1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User.User;

import java.io.IOException;

public class HomepageAdmin {
    User u = null;
    // Import the application's controls
    @FXML
    Button logoutButton;
    @FXML
    AnchorPane anchorPane1;
    @FXML
    AnchorPane anchorPane2;
    @FXML
    Label labelHi;
    @FXML
    protected void onClickAnchorPane1() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchShop.fxml"));
        Parent root = loader.load();
        SearchShop searchShop = loader.getController();
        searchShop.passUser(u);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) anchorPane1.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onClickAnchorPane2() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("createUserAS.fxml"));
        Parent root = loader.load();
        CreateUserAS createUserAS = loader.getController();
        createUserAS.passUser(u);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) anchorPane1.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onClickAnchorPane3() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchShop.fxml"));
        Parent root = loader.load();
        SearchShop searchShop = loader.getController();
        searchShop.passUser(u);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) anchorPane1.getScene().getWindow();
        stage.close();
    }

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
        labelHi.setText(user.getUsername());
    }
}
