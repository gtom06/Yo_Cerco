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
    User user1 = null;
    @FXML
    protected ImageView homepageImageView1;
    @FXML
    protected Text textHi1;
    @FXML
    protected Text numberOfOrdersText1;
    @FXML
    protected Text totalOrdersText1;
    @FXML
    protected TableView<Order> orderTableView1 = new TableView<>();
    protected TableColumn<Order, String> orderNumber1;
    protected TableColumn<Order, Integer> orderTotalQuantity1;
    protected TableColumn<Order, String> orderTotalPrice1;
    protected TableColumn<Order, Timestamp> orderTimeStamp1;
    protected TableColumn<Order, Timestamp> orderStatus1;
    static final Logger logger = Logger.getLogger(OrdersAdmin.class.getName());

    @FXML
    protected void onOrderClicked() throws IOException {
        Order order1 = orderTableView1.getSelectionModel().getSelectedItem();
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("specificOrderAdmin.fxml"));
        Parent root1 = loader1.load();
        SpecificOrderAdmin specificOrder1 = loader1.getController();
        specificOrder1.passParams(user1, order1);
        Stage newStage1 = new Stage();
        newStage1.setScene(new Scene(root1));
        newStage1.show();
        newStage1.setResizable(false);
        Stage stage1 = (Stage) homepageImageView1.getScene().getWindow();
        stage1.close();
    }

    @FXML
    protected void onHomepageImageClick() throws IOException {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("homepageAdmin.fxml"));
        Parent root1 = loader1.load();
        HomepageAdmin homepage1 = loader1.getController();
        homepage1.passUser(user1);
        Stage newStage1 = new Stage();
        newStage1.setScene(new Scene(root1));
        newStage1.show();
        newStage1.setResizable(false);
        Stage stage1 = (Stage) homepageImageView1.getScene().getWindow();
        stage1.close();
    }


    @FXML
    public void initialize() {
        orderTableView1.setEditable(true);

        orderNumber1 = new TableColumn<>("Order Number");
        orderNumber1.setMinWidth(10);
        orderNumber1.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        orderTotalQuantity1 = new TableColumn<>("orderTotalQuantity");
        orderTotalQuantity1.setMinWidth(10);
        orderTotalQuantity1.setCellValueFactory(new PropertyValueFactory<>("orderTotalQuantity"));

        orderTotalPrice1 = new TableColumn<>("totalPrice");
        orderTotalPrice1.setMinWidth(30);
        orderTotalPrice1.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        orderTimeStamp1 = new TableColumn<>("orderTimestamp");
        orderTimeStamp1.setMinWidth(10);
        orderTimeStamp1.setCellValueFactory(new PropertyValueFactory<>("orderTimestamp"));

        orderStatus1 = new TableColumn<>("Status");
        orderStatus1.setMinWidth(10);
        orderStatus1.setCellValueFactory(new PropertyValueFactory<>("status"));

        orderTableView1.getColumns().addAll(orderNumber1, orderTotalQuantity1, orderTotalPrice1, orderTimeStamp1, orderStatus1);

    }

    protected void fillShopTableView() {
        int numberOfOrders1 = 0;
        orderTableView1.getItems().clear();
        ObservableList<Order> orderObservableList1 = FXCollections.observableArrayList();
        List<Order> orderArrayList1 = OrderHandler.findOrdersByAdmin(user1);
        if (orderArrayList1 != null) {
            for (Order o1 : orderArrayList1) {
                orderObservableList1.add(o1);
                numberOfOrders1++;
            }
            orderTableView1.setItems(orderObservableList1);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
        totalOrdersText1.setText(Constants.TOTAL_ORDERS_STRING);
        numberOfOrdersText1.setText(String.valueOf(numberOfOrders1));
    }


    public void passUser(User user1) {
        this.user1 = user1;
        textHi1.setText(user1.getUsername());
        fillShopTableView();
    }
}