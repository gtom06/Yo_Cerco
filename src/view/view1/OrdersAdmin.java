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
import constants.Constants;
import model.order.Order;
import model.user.User;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrdersAdmin {
    User user1OA = null;
    @FXML
    protected ImageView homepageImageView1OA;
    @FXML
    protected Text textHi1OA;
    @FXML
    protected Text numberOfOrdersText1OA;
    @FXML
    protected Text totalOrdersText1OA;
    @FXML
    protected TableView<Order> orderTableView1OA = new TableView<>();
    protected TableColumn<Order, String> orderNumber1OA;
    protected TableColumn<Order, Integer> orderTotalQuantity1OA;
    protected TableColumn<Order, String> orderTotalPrice1OA;
    protected TableColumn<Order, Timestamp> orderTimeStamp1OA;
    protected TableColumn<Order, Timestamp> orderStatus1OA;
    static final Logger logger = Logger.getLogger(OrdersAdmin.class.getName());

    @FXML
    protected void onOrderClicked1OA() throws IOException {
        Order order1OA = orderTableView1OA.getSelectionModel().getSelectedItem();
        FXMLLoader loader1OA = new FXMLLoader(getClass().getResource("specificOrderAdmin.fxml"));
        Parent root1OA = loader1OA.load();
        SpecificOrderAdmin specificOrder1OA = loader1OA.getController();
        specificOrder1OA.passParams(user1OA, order1OA);
        Stage newStage1OA = new Stage();
        newStage1OA.setScene(new Scene(root1OA));
        newStage1OA.show();
        newStage1OA.setResizable(false);
        Stage stage1OA = (Stage) homepageImageView1OA.getScene().getWindow();
        stage1OA.close();
    }

    @FXML
    protected void onHomepageImageClick1OA() throws IOException {
        FXMLLoader loader1OA = new FXMLLoader(getClass().getResource("homepageAdmin.fxml"));
        Parent root1OA = loader1OA.load();
        HomepageAdmin homepage1OA = loader1OA.getController();
        homepage1OA.passUser(user1OA);
        Stage newStage1OA = new Stage();
        newStage1OA.setScene(new Scene(root1OA));
        newStage1OA.show();
        newStage1OA.setResizable(false);
        Stage stage1OA = (Stage) homepageImageView1OA.getScene().getWindow();
        stage1OA.close();
    }


    @FXML
    public void initialize() {
        orderTableView1OA.setEditable(true);

        orderNumber1OA = new TableColumn<>("Order Number");
        orderNumber1OA.setMinWidth(10);
        orderNumber1OA.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        orderTotalQuantity1OA = new TableColumn<>("orderTotalQuantity");
        orderTotalQuantity1OA.setMinWidth(10);
        orderTotalQuantity1OA.setCellValueFactory(new PropertyValueFactory<>("orderTotalQuantity"));

        orderTotalPrice1OA = new TableColumn<>("totalPrice");
        orderTotalPrice1OA.setMinWidth(30);
        orderTotalPrice1OA.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        orderTimeStamp1OA = new TableColumn<>("orderTimestamp");
        orderTimeStamp1OA.setMinWidth(10);
        orderTimeStamp1OA.setCellValueFactory(new PropertyValueFactory<>("orderTimestamp"));

        orderStatus1OA = new TableColumn<>("Status");
        orderStatus1OA.setMinWidth(10);
        orderStatus1OA.setCellValueFactory(new PropertyValueFactory<>("status"));

        orderTableView1OA.getColumns().addAll(orderNumber1OA, orderTotalQuantity1OA, orderTotalPrice1OA, orderTimeStamp1OA, orderStatus1OA);

    }

    protected void fillShopTableView1OA() {
        int numberOfOrders1OA = 0;
        orderTableView1OA.getItems().clear();
        ObservableList<Order> orderObservableList1OA = FXCollections.observableArrayList();
        List<Order> orderArrayList1OA = OrderHandler.findOrdersByAdmin(user1OA);
        if (orderArrayList1OA != null) {
            for (Order o1OA : orderArrayList1OA) {
                orderObservableList1OA.add(o1OA);
                numberOfOrders1OA++;
            }
            orderTableView1OA.setItems(orderObservableList1OA);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
        totalOrdersText1OA.setText(Constants.TOTAL_ORDERS_STRING);
        numberOfOrdersText1OA.setText(String.valueOf(numberOfOrders1OA));
    }


    public void passUser(User user1OA) {
        this.user1OA = user1OA;
        textHi1OA.setText(user1OA.getUsername());
        fillShopTableView1OA();
    }
}