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
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Constants;
import model.order.Order;
import model.order.OrderItem;
import model.user.User;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpecificOrderAdmin {
    User user = null;
    Order order = null;
    @FXML
    protected ImageView homepageImageView, goPreviousPageImageView, cartImageView;
    @FXML
    protected Text totalPriceText, timestampText, orderNumberText, completedText;
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
        homepage.passParams(user);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) homepageImageView.getScene().getWindow();
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

    protected void fillOrder() {
        orderItemTableView.getItems().clear();
        ObservableList<OrderItem> orderItemObservableList = FXCollections.observableArrayList();
        order = OrderHandler.populateOrderWithOrderItems(order);
        if (order != null) {
            totalPriceText.setText(String.valueOf(order.getTotalPrice()));
            timestampText.setText(String.valueOf(order.getOrderTimestamp()));
            orderNumberText.setText(String.valueOf(order.getOrderId()));
            if (order.getOrderItemArrayList() != null) {
                for (OrderItem orderItem : order.getOrderItemArrayList()) {
                    orderItemObservableList.add(orderItem);
                }
                orderItemTableView.setItems(orderItemObservableList);
            } else {
                logger.log(Level.INFO, Constants.NO_RESULT);
            }
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    public void passParams(User user, Order order){
        this.user = user;
        this.order = order;
        fillOrder();
    }
    @FXML
    protected void completeOrder() {
        if (OrderHandler.setStatusOrder(order, Constants.COMPLETED)) {
            completedText.setVisible(true);
        }
    }
}


