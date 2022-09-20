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
import model.Constants;
import model.order.Order2;
import model.order.OrderItem;
import model.user.User;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpecificOrderAdmin {
    User user = null;
    Order2 order = null;
    @FXML
    protected ImageView homepageImageView;
    @FXML
    protected ImageView goPreviousPageImageView;
    @FXML
    protected Text textHi;
    @FXML
    protected Label completedLabel;
    @FXML
    protected TableView<OrderItem> orderItemTableView = new TableView<>();
    protected TableColumn<OrderItem, String> nameColumn;
    protected TableColumn<OrderItem, String> brandColumn;
    protected TableColumn<OrderItem, Integer> quantityOrderedColumn;
    protected TableColumn<OrderItem, Double> priceTotalColumn;
    protected TableColumn<OrderItem, String> currencyColumn;

    static final Logger logger = Logger.getLogger(SpecificOrderAdmin.class.getName());

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
    protected void previousPage() throws IOException {
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

    protected void fillOrderTableView() {
        orderItemTableView.getItems().clear();
        ObservableList<OrderItem> orderItemObservableList = FXCollections.observableArrayList();
        order = OrderHandler.populateOrderWithOrderItems(order);
        if (order.getOrderItemArrayList() != null) {
            orderItemObservableList.addAll(order.getOrderItemArrayList());
            orderItemTableView.setItems(orderItemObservableList);
        } else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    protected void passParams(User user, Order2 order) {
        this.user = user;
        this.order = order;
        textHi.setText(user.getUsername());
        fillOrderTableView();
    }

    @FXML
    protected void completeOrder() {
        if (OrderHandler.setStatusOrder(order, Constants.COMPLETED)) {
            completedLabel.setVisible(true);
        }
    }
}
