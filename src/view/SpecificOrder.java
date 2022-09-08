package view;

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
import model.Order.Order;
import model.Order.OrderItem;
import model.User.User;

import java.io.IOException;
import java.sql.Timestamp;

public class SpecificOrder {
    User user = null;
    Order order = null;
    @FXML
    private ImageView homepageImageView, goPreviousPageImageView;
    @FXML
    Text textHi, numberOfOrdersText, totalOrdersText;
    @FXML
    TableView<OrderItem> orderItemTableView = new TableView<>();
    TableColumn<OrderItem, String> orderNumber;
    TableColumn<OrderItem, Integer> orderTotalQuantity;
    TableColumn<OrderItem, String> orderTotalPrice;
    TableColumn<OrderItem, Timestamp> orderTimeStamp;
    TableColumn<OrderItem, Timestamp> orderStatus;


    protected void onOrderClicked() {
        orderItemTableView.getSelectionModel().getSelectedItem();
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
    public void previousPage() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("myOrders.fxml"));
        Parent root = loader.load();
        MyOrders myOrders = loader.getController();
        myOrders.passUser(user);

        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) goPreviousPageImageView.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        orderItemTableView.setEditable(true);

        orderNumber = new TableColumn<>("name");
        orderNumber.setMinWidth(10);
        orderNumber.setCellValueFactory(new PropertyValueFactory<>("name"));

        orderTotalQuantity = new TableColumn<>("quantityOrdered");
        orderTotalQuantity.setMinWidth(10);
        orderTotalQuantity.setCellValueFactory(new PropertyValueFactory<>("quantityOrdered"));

        orderTotalPrice = new TableColumn<>("currency");
        orderTotalPrice.setMinWidth(30);
        orderTotalPrice.setCellValueFactory(new PropertyValueFactory<>("currency"));

        orderTimeStamp = new TableColumn<>("priceTotal");
        orderTimeStamp.setMinWidth(10);
        orderTimeStamp.setCellValueFactory(new PropertyValueFactory<>("priceTotal"));

        orderStatus = new TableColumn<>("brand");
        orderStatus.setMinWidth(10);
        orderStatus.setCellValueFactory(new PropertyValueFactory<>("brand"));

        orderItemTableView.getColumns().addAll(orderNumber, orderTotalQuantity, orderTotalPrice, orderTimeStamp, orderStatus);
    }

    public void fillOrderTableView() {
        orderItemTableView.getItems().clear();
        ObservableList<OrderItem> orderItemObservableList = FXCollections.observableArrayList();
        order = OrderHandler.findOrderItemsFromOrder(order);
        System.out.println(order.getOrderItemArrayList());
        if (order.getOrderItemArrayList() != null) {
            for (OrderItem orderItem : order.getOrderItemArrayList()) {
                orderItemObservableList.add(orderItem);
            }
            orderItemTableView.setItems(orderItemObservableList);
        } else {
            System.out.println("no result");
        }
    }

    public void passParams(User user, Order order) {
        this.user = user;
        this.order = order;
        textHi.setText(user.getUsername());
        fillOrderTableView();
    }
}
