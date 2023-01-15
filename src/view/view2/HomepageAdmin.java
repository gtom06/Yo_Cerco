package view.view2;

import bean.OrderBean;
import bean.UserBean;
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
import constants.Constants;
import model.order.Order;
import model.user.User;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomepageAdmin {
    UserBean user2HA = null;
    // Import the application's controls
    @FXML
    protected TableView<OrderBean> orderTableView2HA = new TableView<>();
    protected TableColumn<OrderBean, String> orderNumber2HA;
    protected TableColumn<OrderBean, Integer> orderTotalQuantity2HA;
    protected TableColumn<OrderBean, String> orderTotalPrice2HA;
    protected TableColumn<OrderBean, Timestamp> orderTimeStamp2HA;
    protected TableColumn<OrderBean, String> orderStatus2HA;
    static final Logger logger = Logger.getLogger(HomepageAdmin.class.getName());

    @FXML
    protected void onLogoutButtonClick2HA() throws IOException {
        Stage stage22HA = new Stage();
        Parent root2HA = FXMLLoader.load((getClass().getResource("login.fxml")));
        Scene scene2HA = new Scene(root2HA, 1000, 700);
        stage22HA.setTitle("LoginPage");
        stage22HA.setScene(scene2HA);
        stage22HA.show();
        stage22HA.setResizable(false);
        Stage stage2HA = (Stage) orderTableView2HA.getScene().getWindow();
        stage2HA.close();
    }

    @FXML
    protected void onOrderClicked2HA() throws IOException {
        OrderBean order2HA = orderTableView2HA.getSelectionModel().getSelectedItem();
        FXMLLoader loader2HA = new FXMLLoader(getClass().getResource("specificOrderAdmin.fxml"));
        Parent root2HA = loader2HA.load();
        SpecificOrderAdmin specificOrderAdmin2HA = loader2HA.getController();
        specificOrderAdmin2HA.passParams(user2HA, order2HA);
        Stage newStage2HA = new Stage();
        newStage2HA.setScene(new Scene(root2HA));
        newStage2HA.show();
        newStage2HA.setResizable(false);
        Stage stage2HA = (Stage) orderTableView2HA.getScene().getWindow();
        stage2HA.close();
    }

    @FXML
    public void initialize() {
        orderTableView2HA.setEditable(true);

        orderNumber2HA = new TableColumn<>("Order Number");
        orderNumber2HA.setMinWidth(10);
        orderNumber2HA.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        orderTotalQuantity2HA = new TableColumn<>("orderTotalQuantity");
        orderTotalQuantity2HA.setMinWidth(10);
        orderTotalQuantity2HA.setCellValueFactory(new PropertyValueFactory<>("orderTotalQuantity"));

        orderTotalPrice2HA = new TableColumn<>("totalPrice");
        orderTotalPrice2HA.setMinWidth(30);
        orderTotalPrice2HA.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        orderTimeStamp2HA = new TableColumn<>("orderTimestamp");
        orderTimeStamp2HA.setMinWidth(10);
        orderTimeStamp2HA.setCellValueFactory(new PropertyValueFactory<>("orderTimestamp"));

        orderStatus2HA = new TableColumn<>("Status");
        orderStatus2HA.setMinWidth(10);
        orderStatus2HA.setCellValueFactory(new PropertyValueFactory<>("status"));

        orderTableView2HA.getColumns().addAll(
                orderNumber2HA, orderTotalQuantity2HA, orderTotalPrice2HA, orderTimeStamp2HA, orderStatus2HA);
    }

    protected void fillTableView2HA() {
        orderTableView2HA.getItems().clear();
        ObservableList<OrderBean> orderObservableList2HA = FXCollections.observableArrayList();
        List<OrderBean> orderArrayList2HA = OrderHandler.findOrdersByAdmin(user2HA);
        if (orderArrayList2HA != null) {
            for (OrderBean o : orderArrayList2HA) {
                orderObservableList2HA.add(o);
            }
            orderTableView2HA.setItems(orderObservableList2HA);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    public void passParams(UserBean user2HA) {
        this.user2HA = user2HA;
        fillTableView2HA();
    }
}
