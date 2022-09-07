package view;

import control.OrderHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Constants;
import model.Order.Order;
import model.User.User;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MyOrders {
    User u = null;
    @FXML
    private ImageView homepageImageView;
    @FXML
    Label labelHi, numberOfOrdersLabel, totalOrdersLabel;
    @FXML
    TableView<Order> orderTableView = new TableView<>();
    TableColumn<Order, String> orderNumber;
    TableColumn<Order, Integer> orderTotalQuantity;
    TableColumn<Order, String> orderTotalPrice;
    TableColumn<Order, Timestamp> orderTimeStamp;
    TableColumn<Order, Timestamp> orderStatus;

    @FXML
    protected void onHomepageImageClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root = loader.load();
        Homepage homepage = loader.getController();
        homepage.passUser(u);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) homepageImageView.getScene().getWindow();
        stage.close();
    }


    @FXML
    public void initialize() {
        orderTableView.setEditable(true);

        orderNumber = new TableColumn<>("Order Number");
        orderNumber.setMinWidth(10);
        orderNumber.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        orderTotalQuantity = new TableColumn<>("orderTotalQuantity");
        orderTotalQuantity.setMinWidth(10);
        orderTotalQuantity.setCellValueFactory(new PropertyValueFactory<>("orderTotalQuantity"));

        orderTotalPrice = new TableColumn<>("totalPrice");
        orderTotalPrice.setMinWidth(30);
        orderTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        orderTimeStamp = new TableColumn<>("orderTimestamp");
        orderTimeStamp.setMinWidth(10);
        orderTimeStamp.setCellValueFactory(new PropertyValueFactory<>("orderTimestamp"));

        orderStatus = new TableColumn<>("Status");
        orderStatus.setMinWidth(10);
        orderStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        orderTableView.getColumns().addAll(orderNumber, orderTotalQuantity, orderTotalPrice, orderTimeStamp, orderStatus);

    }

    public void fillShopTableView() {
        int numberOfOrders = 0;
        orderTableView.getItems().clear();
        ObservableList<Order> orderObservableList = FXCollections.observableArrayList();
        ArrayList<Order> orderArrayList = OrderHandler.findOrdersInfoFromUser(u);
        if (orderArrayList != null) {
            for (Order o : orderArrayList) {
                orderObservableList.add(o);
                numberOfOrders++;
            }
            orderTableView.setItems(orderObservableList);
        }
        else {
            System.out.println("no result");
        }
        totalOrdersLabel.setText(Constants.TOTAL_ORDERS_STRING);
        numberOfOrdersLabel.setText(String.valueOf(numberOfOrders));
    }


    public void passUser(User user) {
        u = user;
        labelHi.setText(user.getUsername());
        fillShopTableView();
    }
}