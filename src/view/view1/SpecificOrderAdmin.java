package view.view1;

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
    User user1 = null;
    Order order1 = null;
    @FXML
    protected ImageView homepageImageView1SOA;
    @FXML
    protected ImageView goPreviousPageImageView1;
    @FXML
    protected Text textHi1;
    @FXML
    protected Label completedLabel1;
    @FXML
    protected TableView<OrderItem> orderItemTableView1 = new TableView<>();
    protected TableColumn<OrderItem, String> nameColumn1;
    protected TableColumn<OrderItem, String> brandColumn1;
    protected TableColumn<OrderItem, Integer> quantityOrderedColumn1;
    protected TableColumn<OrderItem, Double> priceTotalColumn1;
    protected TableColumn<OrderItem, String> currencyColumn1;

    static final Logger logger = Logger.getLogger(SpecificOrderAdmin.class.getName());

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
        Stage stage1 = (Stage) homepageImageView1SOA.getScene().getWindow();
        stage1.close();
    }

    @FXML
    protected void previousPage() throws IOException {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("ordersAdmin.fxml"));
        Parent root1 = loader1.load();
        OrdersAdmin ordersAdmin1 = loader1.getController();
        ordersAdmin1.passUser(user1);
        Stage newStage1 = new Stage();
        newStage1.setScene(new Scene(root1));
        newStage1.show();
        newStage1.setResizable(false);
        Stage stage1 = (Stage) goPreviousPageImageView1.getScene().getWindow();
        stage1.close();
    }

    @FXML
    public void initialize() {
        orderItemTableView1.setEditable(true);

        nameColumn1 = new TableColumn<>("Name");
        brandColumn1 = new TableColumn<>("Brand");
        quantityOrderedColumn1 = new TableColumn<>("Quantity ordered");
        priceTotalColumn1 = new TableColumn<>("Price total");
        currencyColumn1 = new TableColumn<>("Currency");

        nameColumn1.setMinWidth(10);
        brandColumn1.setMinWidth(10);
        quantityOrderedColumn1.setMinWidth(10);
        priceTotalColumn1.setMinWidth(10);
        currencyColumn1.setMinWidth(10);

        nameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandColumn1.setCellValueFactory(new PropertyValueFactory<>("brand"));
        quantityOrderedColumn1.setCellValueFactory(new PropertyValueFactory<>("quantityOrdered"));
        priceTotalColumn1.setCellValueFactory(new PropertyValueFactory<>("priceTotal"));
        currencyColumn1.setCellValueFactory(new PropertyValueFactory<>("currency"));

        orderItemTableView1.getColumns().addAll(nameColumn1, brandColumn1, quantityOrderedColumn1, priceTotalColumn1, currencyColumn1);
    }

    protected void fillOrderTableView() {
        orderItemTableView1.getItems().clear();
        ObservableList<OrderItem> orderItemObservableList1 = FXCollections.observableArrayList();
        order1 = OrderHandler.populateOrderWithOrderItems(order1);
        if (order1.getOrderItemArrayList() != null) {
            orderItemObservableList1.addAll(order1.getOrderItemArrayList());
            orderItemTableView1.setItems(orderItemObservableList1);
        } else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    protected void passParams(User user1, Order order1) {
        this.user1 = user1;
        this.order1 = order1;
        textHi1.setText(user1.getUsername());
        fillOrderTableView();
    }

    @FXML
    protected void completeOrder() {
        if (OrderHandler.setStatusOrder(order1, Constants.COMPLETED)) {
            completedLabel1.setVisible(true);
        }
    }
}
