package view.view1;

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

public class MyOrders {
    UserBean user1MO = null;
    @FXML
    protected ImageView homepageImageView1MO;
    @FXML
    protected Text textHi1MO;
    @FXML
    protected Text numberOfOrdersText1MO;
    @FXML
    protected Text totalOrdersText1MO;
    @FXML
    protected TableView<OrderBean> orderTableView1MO = new TableView<>();
    protected TableColumn<OrderBean, String> orderNumber1MO;
    protected TableColumn<OrderBean, Integer> orderTotalQuantity1MO;
    protected TableColumn<OrderBean, String> orderTotalPrice1MO;
    protected TableColumn<OrderBean, Timestamp> orderTimeStamp1MO;
    protected TableColumn<OrderBean, Timestamp> orderStatus1MO;
    static final Logger logger = Logger.getLogger(MyOrders.class.getName());

    @FXML
    protected void onOrderClicked1MO() throws IOException {
        OrderBean order1MO = orderTableView1MO.getSelectionModel().getSelectedItem();
        FXMLLoader loader1MO = new FXMLLoader(getClass().getResource("specificOrder.fxml"));
        Parent root1MO = loader1MO.load();
        SpecificOrder specificOrder1MO = loader1MO.getController();
        specificOrder1MO.passParams(user1MO, order1MO);
        Stage newStage1MO = new Stage();
        newStage1MO.setScene(new Scene(root1MO));
        newStage1MO.show();
        newStage1MO.setResizable(false);
        Stage stage1MO = (Stage) homepageImageView1MO.getScene().getWindow();
        stage1MO.close();
    }

    @FXML
    protected void onHomepageImageClick() throws IOException {
        FXMLLoader loader1MO = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root1MO = loader1MO.load();
        Homepage homepage1MO = loader1MO.getController();
        homepage1MO.passUser(user1MO);
        Stage newStage1MO = new Stage();
        newStage1MO.setScene(new Scene(root1MO));
        newStage1MO.show();
        newStage1MO.setResizable(false);
        Stage stage1 = (Stage) homepageImageView1MO.getScene().getWindow();
        stage1.close();
    }


    @FXML
    public void initialize() {
        orderTableView1MO.setEditable(true);

        orderNumber1MO = new TableColumn<>("Order Number");
        orderNumber1MO.setMinWidth(10);
        orderNumber1MO.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        orderTotalQuantity1MO = new TableColumn<>("orderTotalQuantity");
        orderTotalQuantity1MO.setMinWidth(10);
        orderTotalQuantity1MO.setCellValueFactory(new PropertyValueFactory<>("orderTotalQuantity"));

        orderTotalPrice1MO = new TableColumn<>("totalPrice");
        orderTotalPrice1MO.setMinWidth(30);
        orderTotalPrice1MO.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        orderTimeStamp1MO = new TableColumn<>("orderTimestamp");
        orderTimeStamp1MO.setMinWidth(10);
        orderTimeStamp1MO.setCellValueFactory(new PropertyValueFactory<>("orderTimestamp"));

        orderStatus1MO = new TableColumn<>("Status");
        orderStatus1MO.setMinWidth(10);
        orderStatus1MO.setCellValueFactory(new PropertyValueFactory<>("status"));

        orderTableView1MO.getColumns().addAll(orderNumber1MO, orderTotalQuantity1MO, orderTotalPrice1MO, orderTimeStamp1MO, orderStatus1MO);

    }

    protected void fillShopTableView1MO() {
        int numberOfOrders1MO = 0;
        orderTableView1MO.getItems().clear();
        ObservableList<OrderBean> orderObservableList1MO = FXCollections.observableArrayList();
        List<OrderBean> orderArrayList1MO = OrderHandler.findOrdersInfoFromUser(user1MO);
        if (orderArrayList1MO != null) {
            for (OrderBean o1 : orderArrayList1MO) {
                orderObservableList1MO.add(o1);
                numberOfOrders1MO++;
            }
            orderTableView1MO.setItems(orderObservableList1MO);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
        totalOrdersText1MO.setText(Constants.TOTAL_ORDERS_STRING);
        numberOfOrdersText1MO.setText(String.valueOf(numberOfOrders1MO));
    }


    public void passUser(UserBean user1MO) {
        this.user1MO = user1MO;
        textHi1MO.setText(user1MO.getUsername());
        fillShopTableView1MO();
    }
}