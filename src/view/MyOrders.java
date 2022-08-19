package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import model.Order.Order;
import model.User.User;

public class MyOrders {
    User u = null;
    @FXML
    private ImageView homepageImageView;
    @FXML
    TextField requestTextField;
    @FXML
    Label labelHi;
    @FXML
    TableView<Order> orderTableView = new TableView<>();
    TableColumn<Order, String> descriptionColumn;
    TableColumn<Order, Integer> typeColumn;
    TableColumn<Order, String> nameColumn;
    TableColumn<Order, Double> weightColumn;

    public void passUser(User user) {
        u = user;
        labelHi.setText(user.getUsername());
    }
}