package view.view1;

import control.OrderHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Constants;
import model.Order.Order;
import model.User.User;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class OrdersAdmin {
    User user = null;
    @FXML
    protected ImageView homepageImageView;
    @FXML
    protected Text textHi, numberOfOrdersText, totalOrdersText;
    @FXML
    protected TableView<Order> orderTableView = new TableView<>();
    protected TableColumn<Order, String> orderNumber;
    protected TableColumn<Order, Integer> orderTotalQuantity;
    protected TableColumn<Order, String> orderTotalPrice;
    protected TableColumn<Order, Timestamp> orderTimeStamp;
    protected TableColumn<Order, Timestamp> orderStatus;

    @FXML
    protected void onOrderClicked() throws IOException {
        Order order = orderTableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("specificOrderAdmin.fxml"));
        Parent root = loader.load();
        SpecificOrderAdmin specificOrder = loader.getController();
        specificOrder.passParams(user, order);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) homepageImageView.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onHomepageImageClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homepageAdmin.fxml"));
        Parent root = loader.load();
        HomepageAdmin homepage = loader.getController();
        homepage.passUser(user);
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

    protected void fillShopTableView() {
        int numberOfOrders = 0;
        orderTableView.getItems().clear();
        ObservableList<Order> orderObservableList = FXCollections.observableArrayList();
        ArrayList<Order> orderArrayList = OrderHandler.findOrdersByAdmin(user);
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
        totalOrdersText.setText(Constants.TOTAL_ORDERS_STRING);
        numberOfOrdersText.setText(String.valueOf(numberOfOrders));
    }


    public void passUser(User user) {
        this.user = user;
        textHi.setText(user.getUsername());
        fillShopTableView();
    }
}