package view.view2;

import control.OrderHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Constants;
import model.Order.Order;
import model.User.User;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class HomepageAdmin {
    User user = null;
    // Import the application's controls
    @FXML
    TableView<Order> orderTableView = new TableView<>();
    TableColumn<Order, String> orderNumber;
    TableColumn<Order, Integer> orderTotalQuantity;
    TableColumn<Order, String> orderTotalPrice;
    TableColumn<Order, Timestamp> orderTimeStamp;
    TableColumn<Order, String> orderStatus;

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

    public void fillTableView() {
        orderTableView.getItems().clear();
        ObservableList<Order> orderObservableList = FXCollections.observableArrayList();
        ArrayList<Order> orderArrayList = OrderHandler.findOrdersByAdmin(user);
        System.out.println(orderArrayList);
        if (orderArrayList != null) {
            for (Order o : orderArrayList) {
                orderObservableList.add(o);
            }
            orderTableView.setItems(orderObservableList);
        }
        else {
            System.out.println("no result");
        }
    }

    public void passParams(User user) {
        this.user = user;
        System.out.println(user);
        fillTableView();
    }
}
