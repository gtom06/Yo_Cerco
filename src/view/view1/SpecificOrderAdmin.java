package view.view1;

import bean.OrderBean;
import bean.OrderItemBean;
import bean.UserBean;
import control.OrderHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import constants.Constants;
import model.order.Order;
import model.order.OrderItem;
import model.user.User;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpecificOrderAdmin {
    UserBean user1SOA = null;
    OrderBean order1SOA = null;
    @FXML
    protected ImageView homepageImageView1SOA;
    @FXML
    protected ImageView goPreviousPageImageView1SOA;
    @FXML
    protected Text textHi1SOA;
    @FXML
    protected Label completedLabel1SOA;
    @FXML
    protected TableView<OrderItemBean> orderItemTableView1SOA = new TableView<>();
    protected TableColumn<OrderItemBean, String> nameColumn1SOA;
    protected TableColumn<OrderItemBean, String> brandColumn1SOA;
    protected TableColumn<OrderItemBean, Integer> quantityOrderedColumn1SOA;
    protected TableColumn<OrderItemBean, Double> priceTotalColumn1SOA;
    protected TableColumn<OrderItemBean, String> currencyColumn1SOA;

    static final Logger logger = Logger.getLogger(SpecificOrderAdmin.class.getName());

    @FXML
    protected void onHomepageImageClick1SOA() throws IOException {
        FXMLLoader loader1SOA = new FXMLLoader(getClass().getResource("homepageAdmin.fxml"));
        Parent root1SOA = loader1SOA.load();
        HomepageAdmin homepage1SOA = loader1SOA.getController();
        //homepage1SOA.passUser(user1SOA);
        Stage newStage1SOA = new Stage();
        newStage1SOA.setScene(new Scene(root1SOA));
        newStage1SOA.show();
        newStage1SOA.setResizable(false);
        Stage stage1SOA = (Stage) homepageImageView1SOA.getScene().getWindow();
        stage1SOA.close();
    }

    @FXML
    protected void previousPage1SOA() throws IOException {
        FXMLLoader loader1SOA = new FXMLLoader(getClass().getResource("ordersAdmin.fxml"));
        Parent root1SOA = loader1SOA.load();
        OrdersAdmin ordersAdmin1SOA = loader1SOA.getController();
        ordersAdmin1SOA.passUser(user1SOA);
        Stage newStage1SOA = new Stage();
        newStage1SOA.setScene(new Scene(root1SOA));
        newStage1SOA.show();
        newStage1SOA.setResizable(false);
        Stage stage1SOA = (Stage) goPreviousPageImageView1SOA.getScene().getWindow();
        stage1SOA.close();
    }

    @FXML
    public void initialize() {
        orderItemTableView1SOA.setEditable(true);

        nameColumn1SOA = new TableColumn<>("Name");
        brandColumn1SOA = new TableColumn<>("Brand");
        quantityOrderedColumn1SOA = new TableColumn<>("Quantity ordered");
        priceTotalColumn1SOA = new TableColumn<>("Price total");
        currencyColumn1SOA = new TableColumn<>("Currency");

        nameColumn1SOA.setMinWidth(10);
        brandColumn1SOA.setMinWidth(10);
        quantityOrderedColumn1SOA.setMinWidth(10);
        priceTotalColumn1SOA.setMinWidth(10);
        currencyColumn1SOA.setMinWidth(10);

        nameColumn1SOA.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandColumn1SOA.setCellValueFactory(new PropertyValueFactory<>("brand"));
        quantityOrderedColumn1SOA.setCellValueFactory(new PropertyValueFactory<>("quantityOrdered"));
        priceTotalColumn1SOA.setCellValueFactory(new PropertyValueFactory<>("priceTotal"));
        currencyColumn1SOA.setCellValueFactory(new PropertyValueFactory<>("currency"));

        orderItemTableView1SOA.getColumns().addAll(
                nameColumn1SOA, brandColumn1SOA, quantityOrderedColumn1SOA, priceTotalColumn1SOA, currencyColumn1SOA);
    }

    protected void fillOrderTableView1SOA() {
        orderItemTableView1SOA.getItems().clear();
        ObservableList<OrderItemBean> orderItemObservableList1SOA = FXCollections.observableArrayList();
        order1SOA = OrderHandler.populateOrderWithOrderItems(order1SOA);
        if (order1SOA.getOrderItemArrayList() != null) {
            orderItemObservableList1SOA.addAll(order1SOA.getOrderItemArrayList());
            orderItemTableView1SOA.setItems(orderItemObservableList1SOA);
        } else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    protected void passParams(UserBean user1SOA, OrderBean order1SOA) {
        this.user1SOA = user1SOA;
        this.order1SOA = order1SOA;
        textHi1SOA.setText(user1SOA.getUsername());
        fillOrderTableView1SOA();
    }

    @FXML
    protected void completeOrder1SOA() {
        if (OrderHandler.setStatusOrder(order1SOA, Constants.COMPLETED)) {
            completedLabel1SOA.setVisible(true);
        }
    }
}
