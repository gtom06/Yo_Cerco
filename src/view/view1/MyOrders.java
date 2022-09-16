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
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyOrders {
    User user = null;
    @FXML
    protected ImageView homepageImageView;
    @FXML
    protected Text textHi;
    @FXML
    protected Text numberOfOrdersText;
    @FXML
    protected Text totalOrdersText;
    @FXML
    protected TableView<Order> orderTableView = new TableView<>();
    protected TableColumn<Order, String> orderNumber;
    protected TableColumn<Order, Integer> orderTotalQuantity;
    protected TableColumn<Order, String> orderTotalPrice;
    protected TableColumn<Order, Timestamp> orderTimeStamp;
    protected TableColumn<Order, Timestamp> orderStatus;
    static final Logger logger = Logger.getLogger(MyOrders.class.getName());

    @FXML
    protected void onOrderClicked() throws IOException {
        Order order = orderTableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("specificOrder.fxml"));
        Parent root = loader.load();
        SpecificOrder specificOrder = loader.getController();
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root = loader.load();
        Homepage homepage = loader.getController();
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
        ArrayList<Order> orderArrayList = OrderHandler.findOrdersInfoFromUser(user);
        if (orderArrayList != null) {
            for (Order o : orderArrayList) {
                orderObservableList.add(o);
                numberOfOrders++;
            }
            orderTableView.setItems(orderObservableList);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
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