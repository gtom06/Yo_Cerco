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
    User user1H = null;
    @FXML
    protected Button logoutButton1H;
    @FXML
    protected AnchorPane anchorPane11H;
    @FXML
    protected AnchorPane anchorPane21H;
    @FXML
    protected AnchorPane anchorPane31H;
    @FXML
    protected AnchorPane anchorPane41H;
    @FXML
    protected Label labelHi1H;
    @FXML
    protected void onClickAnchorPane1H() throws IOException {
        FXMLLoader loader1H = new FXMLLoader(getClass().getResource("searchShop.fxml"));
        Parent root1H = loader1H.load();
        SearchShop searchShop1H = loader1H.getController();
        searchShop1H.passUser(user1H);
        Stage newStage1H = new Stage();
        newStage1H.setScene(new Scene(root1H));
        newStage1H.show();
        newStage1H.setResizable(false);
        Stage stage1H = (Stage) anchorPane11H.getScene().getWindow();
        stage1H.close();
    }

    @FXML
    protected void onClickAnchorPane2H() throws IOException {
        FXMLLoader loader1H = new FXMLLoader(getClass().getResource("searchProduct.fxml"));
        Parent root1H = loader1H.load();
        SearchProduct searchProduct1H = loader1H.getController();
        searchProduct1H.passUser(user1H);
        Stage newStage1H = new Stage();
        newStage1H.setScene(new Scene(root1H));
        newStage1H.show();
        newStage1H.setResizable(false);
        Stage stage1H = (Stage) anchorPane21H.getScene().getWindow();
        stage1H.close();
    }

    @FXML
    protected void onClickAnchorPane3H() throws IOException {
        FXMLLoader loader1H = new FXMLLoader(getClass().getResource("favorite.fxml"));
        Parent root1H = loader1H.load();
        Favorite favorite1H = loader1H.getController();
        favorite1H.passUser(user1H);
        Stage newStage1H = new Stage();
        newStage1H.setScene(new Scene(root1H));
        newStage1H.show();
        newStage1H.setResizable(false);
        Stage stage1H = (Stage) anchorPane31H.getScene().getWindow();
        stage1H.close();
    }

    @FXML
    protected void onClickAnchorPane4H() throws IOException{
        FXMLLoader loader1H = new FXMLLoader(getClass().getResource("myProfile.fxml"));
        Parent root1H = loader1H.load();
        MyProfile myProfile1H = loader1H.getController();
        myProfile1H.passUser(user1H);
        Stage newStage1H = new Stage();
        newStage1H.setScene(new Scene(root1H));
        newStage1H.show();
        newStage1H.setResizable(false);
        Stage stage1H = (Stage) anchorPane41H.getScene().getWindow();
        stage1H.close();
    }

    @FXML
    protected void onLogoutButtonClick1H() throws IOException {
        Stage stage21H = new Stage();
        Parent root1H = FXMLLoader.load((getClass().getResource("login.fxml")));
        Scene scene1H = new Scene(root1H, 1000, 700);
        stage21H.setTitle("LoginPage");
        stage21H.setScene(scene1H);
        stage21H.show();
        stage21H.setResizable(false);
        Stage stage1 = (Stage) logoutButton1H.getScene().getWindow();
        stage1.close();
    }

    @FXML
    protected void openCartAndPayment1H() throws IOException {
        FXMLLoader loader1H = new FXMLLoader(getClass().getResource("cartAndPayment.fxml"));
        Parent root1H = loader1H.load();
        CartAndPayment cartAndPayment1H = loader1H.getController();
        cartAndPayment1H.passParam(null, user1H);
        Stage newStage1H = new Stage();
        newStage1H.setScene(new Scene(root1H));
        newStage1H.show();
        newStage1H.setResizable(false);
        Stage stage1H = (Stage) anchorPane31H.getScene().getWindow();
        stage1H.close();
    }

    public void passUser(User user) {
        this.user1H = user;
        labelHi1H.setText(user.getUsername());
    }
}