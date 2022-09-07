package view;

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

public class Homepage {
    User u = null;
    @FXML
    Button logoutButton;
    @FXML
    AnchorPane anchorPane1, anchorPane2, anchorPane3, anchorPane4;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchProduct.fxml"));
        Parent root = loader.load();
        SearchProduct searchProduct = loader.getController();
        searchProduct.passUser(u);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) anchorPane1.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onClickAnchorPane3() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("cartAndPayment.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("favorite.fxml"));
        Parent root = loader.load();
        CartAndPayment cartAndPayment = loader.getController();
        //Favorite favorite = loader.getController();
        cartAndPayment.passParam(null, u);
        //favorite.passUser(u);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) anchorPane3.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onClickAnchorPane4() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("myProfile.fxml"));
        Parent root = loader.load();
        MyProfile myProfile = loader.getController();
        myProfile.passUser(u);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) anchorPane4.getScene().getWindow();
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
        u = user;
        labelHi.setText(user.getUsername());
        u.toString();
    }
}