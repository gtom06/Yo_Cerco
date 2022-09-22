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
import constants.Constants;
import model.order.Order;
import model.order.OrderItem;
import model.user.User;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpecificOrderAdmin {
    User user2SOA = null;
    Order order2SOA = null;
    @FXML
    protected ImageView homepageImageView2SOA;
    @FXML
    protected ImageView goPreviousPageImageView2SOA;
    @FXML
    protected ImageView cartImageView2SOA;
    @FXML
    protected Text totalPriceText2SOA;
    @FXML
    protected Text timestampText2SOA;
    @FXML
    protected Text orderNumberText2SOA;
    @FXML
    protected Text completedText2SOA;
    @FXML
    protected TableView<OrderItem> orderItemTableView2SOA = new TableView<>();
    protected TableColumn<OrderItem, String> nameColumn2SOA;
    protected TableColumn<OrderItem, String> brandColumn2SOA;
    protected TableColumn<OrderItem, Integer> quantityOrderedColumn2SOA;
    protected TableColumn<OrderItem, Double> priceTotalColumn2SOA;
    protected TableColumn<OrderItem, String> currencyColumn2SOA;
    static final Logger logger = Logger.getLogger(SpecificOrderAdmin.class.getName());

    @FXML
    protected void onHomepageImageClick2SOA() throws IOException {
        FXMLLoader loader2SOA = new FXMLLoader(getClass().getResource("homepageAdmin.fxml"));
        Parent root2SOA = loader2SOA.load();
        HomepageAdmin homepage2SOA = loader2SOA.getController();
        homepage2SOA.passParams(user2SOA);
        Stage newStage2SOA = new Stage();
        newStage2SOA.setScene(new Scene(root2SOA));
        newStage2SOA.show();
        newStage2SOA.setResizable(false);
        Stage stage2SOA = (Stage) homepageImageView2SOA.getScene().getWindow();
        stage2SOA.close();
    }

    @FXML
    public void initialize() {
        orderItemTableView2SOA.setEditable(true);

        nameColumn2SOA = new TableColumn<>("Name");
        brandColumn2SOA = new TableColumn<>("Brand");
        quantityOrderedColumn2SOA = new TableColumn<>("Quantity ordered");
        priceTotalColumn2SOA = new TableColumn<>("Price total");
        currencyColumn2SOA = new TableColumn<>("Currency");

        nameColumn2SOA.setMinWidth(10);
        brandColumn2SOA.setMinWidth(10);
        quantityOrderedColumn2SOA.setMinWidth(10);
        priceTotalColumn2SOA.setMinWidth(10);
        currencyColumn2SOA.setMinWidth(10);

        nameColumn2SOA.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandColumn2SOA.setCellValueFactory(new PropertyValueFactory<>("brand"));
        quantityOrderedColumn2SOA.setCellValueFactory(new PropertyValueFactory<>("quantityOrdered"));
        priceTotalColumn2SOA.setCellValueFactory(new PropertyValueFactory<>("priceTotal"));
        currencyColumn2SOA.setCellValueFactory(new PropertyValueFactory<>("currency"));

        orderItemTableView2SOA.getColumns().addAll(
                nameColumn2SOA, brandColumn2SOA, quantityOrderedColumn2SOA, priceTotalColumn2SOA, currencyColumn2SOA);
    }

    protected void fillOrder2SOA() {
        orderItemTableView2SOA.getItems().clear();
        ObservableList<OrderItem> orderItemObservableList2SOA = FXCollections.observableArrayList();
        order2SOA = OrderHandler.populateOrderWithOrderItems(order2SOA);
        if (order2SOA != null) {
            totalPriceText2SOA.setText(String.valueOf(order2SOA.getTotalPrice()));
            timestampText2SOA.setText(String.valueOf(order2SOA.getOrderTimestamp()));
            orderNumberText2SOA.setText(String.valueOf(order2SOA.getOrderId()));
            if (order2SOA.getOrderItemArrayList() != null) {
                for (OrderItem orderItem2SOA : order2SOA.getOrderItemArrayList()) {
                    orderItemObservableList2SOA.add(orderItem2SOA);
                }
                orderItemTableView2SOA.setItems(orderItemObservableList2SOA);
            } else {
                logger.log(Level.INFO, Constants.NO_RESULT);
            }
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    public void passParams(User user2SOA, Order order2SOA){
        this.user2SOA = user2SOA;
        this.order2SOA = order2SOA;
        fillOrder2SOA();
    }
    @FXML
    protected void completeOrder2SOA() {
        if (OrderHandler.setStatusOrder(order2SOA, Constants.COMPLETED)) {
            completedText2SOA.setVisible(true);
        }
    }
}


