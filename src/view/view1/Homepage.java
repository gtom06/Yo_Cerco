package view.view1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.user.User;

import java.io.IOException;

public class Homepage {
    User user1 = null;
    @FXML
    protected Button logoutButton1;
    @FXML
    protected AnchorPane anchorPane11;
    @FXML
    protected AnchorPane anchorPane21;
    @FXML
    protected AnchorPane anchorPane31;
    @FXML
    protected AnchorPane anchorPane41;
    @FXML
    protected Label labelHi1;
    @FXML
    protected void onClickAnchorPane1() throws IOException {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("searchShop.fxml"));
        Parent root1 = loader1.load();
        SearchShop searchShop1 = loader1.getController();
        searchShop1.passUser(user1);
        Stage newStage1 = new Stage();
        newStage1.setScene(new Scene(root1));
        newStage1.show();
        newStage1.setResizable(false);
        Stage stage1 = (Stage) anchorPane11.getScene().getWindow();
        stage1.close();
    }

    @FXML
    protected void onClickAnchorPane2() throws IOException {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("searchProduct.fxml"));
        Parent root1 = loader1.load();
        SearchProduct searchProduct1 = loader1.getController();
        searchProduct1.passUser(user1);
        Stage newStage1 = new Stage();
        newStage1.setScene(new Scene(root1));
        newStage1.show();
        newStage1.setResizable(false);
        Stage stage1 = (Stage) anchorPane21.getScene().getWindow();
        stage1.close();
    }

    @FXML
    protected void onClickAnchorPane3() throws IOException {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("favorite.fxml"));
        Parent root1 = loader1.load();
        Favorite favorite1 = loader1.getController();
        favorite1.passUser(user1);
        Stage newStage1 = new Stage();
        newStage1.setScene(new Scene(root1));
        newStage1.show();
        newStage1.setResizable(false);
        Stage stage1 = (Stage) anchorPane31.getScene().getWindow();
        stage1.close();
    }

    @FXML
    protected void onClickAnchorPane4() throws IOException{
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("myProfile.fxml"));
        Parent root1 = loader1.load();
        MyProfile myProfile1 = loader1.getController();
        myProfile1.passUser(user1);
        Stage newStage1 = new Stage();
        newStage1.setScene(new Scene(root1));
        newStage1.show();
        newStage1.setResizable(false);
        Stage stage1 = (Stage) anchorPane41.getScene().getWindow();
        stage1.close();
    }

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

    @FXML
    protected void openCartAndPayment() throws IOException {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("cartAndPayment.fxml"));
        Parent root1 = loader1.load();
        CartAndPayment cartAndPayment1 = loader1.getController();
        cartAndPayment1.passParam(null, user1);
        Stage newStage1 = new Stage();
        newStage1.setScene(new Scene(root1));
        newStage1.show();
        newStage1.setResizable(false);
        Stage stage1 = (Stage) anchorPane31.getScene().getWindow();
        stage1.close();
    }

    public void passUser(User user) {
        this.user1 = user;
        labelHi1.setText(user.getUsername());
    }
}