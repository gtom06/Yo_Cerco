package view;

import control.ShopHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.User.User;

import java.io.IOException;
import java.util.ArrayList;


public class SearchUser {
    User u = null;
    @FXML
    private ImageView homepageImageView;
    @FXML
    private ListView<String> userListView;
    @FXML
    TextField requestTextField;
    @FXML
    Label selected;
    @FXML
    Label labelHi;
    @FXML
    Button searchButton;

    @FXML
    protected void onListViewItemClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("shopView.fxml"));
        Parent root = loader.load();
        ShopView shopView = loader.getController();
        shopView.passUser(u);
        int shopId = ShopHandler.getShopFromString(userListView.getSelectionModel().getSelectedItem());
        ArrayList<String> shopInfo = ShopHandler.findShopForViewShop(shopId);
        //ArrayList<String> departments = DepartmentHandler.findDepartmentByShop(shopId);
        //shopView.passShop(shopInfo);
        //shopView.passDepartments(departments);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) userListView.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onHomepageImageClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homepageAdmin.fxml"));
        Parent root = loader.load();
        HomepageAdmin homepageAdmin = loader.getController();
        homepageAdmin.passUser(u);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) homepageImageView.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onSearchButtonClick() {
        userListView.getItems().clear();
        /////// to be changed
        /*
        for (String s : ShopHandler.findShopByAddress(requestTextField.getText())) {
            userListView.getItems().add(s);
        }
        */
    }

    public void passUser(User user) {
        u = user;
        labelHi.setText(user.getUsername());
    }
}