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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Order.Order;
import model.Order.OrderItem;
import model.User.User;

import java.io.IOException;

public class SpecificOrderAdmin {
    User user = null;
    Order order = null;
    @FXML
    private ImageView homepageImageView, goPreviousPageImageView;
    @FXML
    Text textHi;
    @FXML
    Label completedLabel;
    @FXML
    TableView<OrderItem> orderItemTableView = new TableView<>();
    TableColumn<OrderItem, String> nameColumn;
    TableColumn<OrderItem, String> brandColumn;
    TableColumn<OrderItem, Integer> quantityOrderedColumn;
    TableColumn<OrderItem, Double> priceTotalColumn;
    TableColumn<OrderItem, String> currencyColumn;


    @FXML
    protected void onHomepageImageClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homepageAdmin.fxml"));
        Parent root = loader.load();
        HomepageAdmin homepage = loader.getController();
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ordersAdmin.fxml"));
        Parent root = loader.load();
        OrdersAdmin ordersAdmin = loader.getController();
        ordersAdmin.passUser(user);

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

        nameColumn = new TableColumn<>("Name");
        brandColumn = new TableColumn<>("Brand");
        quantityOrderedColumn = new TableColumn<>("Quantity ordered");
        priceTotalColumn = new TableColumn<>("Price total");
        currencyColumn = new TableColumn<>("Currency");

        nameColumn.setMinWidth(10);
        brandColumn.setMinWidth(10);
        quantityOrderedColumn.setMinWidth(10);
        priceTotalColumn.setMinWidth(10);
        currencyColumn.setMinWidth(10);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        quantityOrderedColumn.setCellValueFactory(new PropertyValueFactory<>("quantityOrdered"));
        priceTotalColumn.setCellValueFactory(new PropertyValueFactory<>("priceTotal"));
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));

        orderItemTableView.getColumns().addAll(nameColumn, brandColumn, quantityOrderedColumn, priceTotalColumn, currencyColumn);
    }

    public void fillOrderTableView() {
        orderItemTableView.getItems().clear();
        ObservableList<OrderItem> orderItemObservableList = FXCollections.observableArrayList();
        order = OrderHandler.populateOrderWithOrderItems(order);
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

    public void completeOrder() {
        if (OrderHandler.setStatusOrder(order, "Completed")) {
            completedLabel.setVisible(true);
        }
    }
}
