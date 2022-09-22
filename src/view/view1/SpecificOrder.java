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
import model.order.OrderItem;
import model.user.User;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpecificOrder {
    User user1SO = null;
    Order order1SO = null;
    @FXML
    protected ImageView homepageImageView1SO;
    @FXML
    protected ImageView goPreviousPageImageView1SO;
    @FXML
    protected Text textHi1SO;
    @FXML
    protected TableView<OrderItem> orderItemTableView1SO = new TableView<>();
    protected TableColumn<OrderItem, String> nameColumn1SO;
    protected TableColumn<OrderItem, String> brandColumn1SO;
    protected TableColumn<OrderItem, Integer> quantityOrderedColumn1SO;
    protected TableColumn<OrderItem, Double> priceTotalColumn1SO;
    protected TableColumn<OrderItem, String> currencyColumn1SO;

    static final Logger logger = Logger.getLogger(SpecificOrder.class.getName());


    @FXML
    protected void onHomepageImageClick1SO() throws IOException {
        FXMLLoader loader1SO = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root1SO = loader1SO.load();
        Homepage homepage1SO = loader1SO.getController();
        homepage1SO.passUser(user1SO);
        Stage newStage1SO = new Stage();
        newStage1SO.setScene(new Scene(root1SO));
        newStage1SO.show();
        newStage1SO.setResizable(false);
        Stage stage1SO = (Stage) homepageImageView1SO.getScene().getWindow();
        stage1SO.close();
    }

    @FXML
    protected void previousPage1SO() throws IOException {

        FXMLLoader loader1SO = new FXMLLoader(getClass().getResource("myOrders.fxml"));
        Parent root1SO = loader1SO.load();
        MyOrders myOrders1SO = loader1SO.getController();
        myOrders1SO.passUser(user1SO);

        Stage newStage1SO = new Stage();
        newStage1SO.setScene(new Scene(root1SO));
        newStage1SO.show();
        newStage1SO.setResizable(false);
        Stage stage1SO = (Stage) goPreviousPageImageView1SO.getScene().getWindow();
        stage1SO.close();
    }

    @FXML
    public void initialize() {
        orderItemTableView1SO.setEditable(true);

        nameColumn1SO = new TableColumn<>("Name");
        brandColumn1SO = new TableColumn<>("Brand");
        quantityOrderedColumn1SO = new TableColumn<>("Quantity ordered");
        priceTotalColumn1SO = new TableColumn<>("Price total");
        currencyColumn1SO = new TableColumn<>("Currency");

        nameColumn1SO.setMinWidth(10);
        brandColumn1SO.setMinWidth(10);
        quantityOrderedColumn1SO.setMinWidth(10);
        priceTotalColumn1SO.setMinWidth(10);
        currencyColumn1SO.setMinWidth(10);

        nameColumn1SO.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandColumn1SO.setCellValueFactory(new PropertyValueFactory<>("brand"));
        quantityOrderedColumn1SO.setCellValueFactory(new PropertyValueFactory<>("quantityOrdered"));
        priceTotalColumn1SO.setCellValueFactory(new PropertyValueFactory<>("priceTotal"));
        currencyColumn1SO.setCellValueFactory(new PropertyValueFactory<>("currency"));

        orderItemTableView1SO.getColumns().addAll(
                nameColumn1SO, brandColumn1SO, quantityOrderedColumn1SO, priceTotalColumn1SO, currencyColumn1SO);
    }

    protected void fillOrderTableView1SO() {
        orderItemTableView1SO.getItems().clear();
        ObservableList<OrderItem> orderItemObservableList1SO = FXCollections.observableArrayList();
        order1SO = OrderHandler.populateOrderWithOrderItems(order1SO);
        if (order1SO.getOrderItemArrayList() != null) {
            for (OrderItem orderItem1SO : order1SO.getOrderItemArrayList()) {
                orderItemObservableList1SO.add(orderItem1SO);
            }
            orderItemTableView1SO.setItems(orderItemObservableList1SO);
        } else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    public void passParams(User user1SO, Order order1SO) {
        this.user1SO = user1SO;
        this.order1SO = order1SO;
        textHi1SO.setText(user1SO.getUsername());
        fillOrderTableView1SO();
    }
}
