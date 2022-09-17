package view.view2;

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
import javafx.stage.Stage;
import model.Constants;
import model.order.Order;
import model.user.User;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomepageAdmin {
    User user = null;
    // Import the application's controls
    @FXML
    protected TableView<Order> orderTableView = new TableView<>();
    protected TableColumn<Order, String> orderNumber;
    protected TableColumn<Order, Integer> orderTotalQuantity;
    protected TableColumn<Order, String> orderTotalPrice;
    protected TableColumn<Order, Timestamp> orderTimeStamp;
    protected TableColumn<Order, String> orderStatus;
    static final Logger logger = Logger.getLogger(HomepageAdmin.class.getName());

    @FXML
    protected void onLogoutButtonClick() throws IOException {
        Stage stage2 = new Stage();
        Parent root = FXMLLoader.load((getClass().getResource("login.fxml")));
        Scene scene = new Scene(root, 1000, 700);
        stage2.setTitle("LoginPage");
        stage2.setScene(scene);
        stage2.show();
        stage2.setResizable(false);
        Stage stage = (Stage) orderTableView.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onOrderClicked() throws IOException {
        Order order = orderTableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("specificOrderAdmin.fxml"));
        Parent root = loader.load();
        SpecificOrderAdmin specificOrderAdmin = loader.getController();
        specificOrderAdmin.passParams(user, order);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) orderTableView.getScene().getWindow();
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

    protected void fillTableView() {
        orderTableView.getItems().clear();
        ObservableList<Order> orderObservableList = FXCollections.observableArrayList();
        List<Order> orderArrayList = OrderHandler.findOrdersByAdmin(user);
        if (orderArrayList != null) {
            for (Order o : orderArrayList) {
                orderObservableList.add(o);
            }
            orderTableView.setItems(orderObservableList);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    public void passParams(User user) {
        this.user = user;
        fillTableView();
    }
}
