package view.view1;

import control.ShopHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
    }

    @FXML
    protected void onHomepageImageClick() throws IOException {

    }

    @FXML
    protected void onSearchButtonClick() {

    }

    public void passUser(User user) {
        u = user;
        labelHi.setText(user.getUsername());
    }
}