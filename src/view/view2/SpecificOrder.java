package view.view2;

import bean.OrderBean;
import bean.OrderItemBean;
import bean.UserBean;
import control.OrderHandler;
import exceptions.AddressException;
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
    UserBean user2SO = null;
    OrderBean order2SO = null;
    @FXML
    protected ImageView homepageImageView2SO;
    @FXML
    protected ImageView goPreviousPageImageView2SO;
    @FXML
    protected ImageView cartImageView2SO;
    @FXML
    protected Text totalPriceText2SO;
    @FXML
    protected Text timestampText2SO;
    @FXML
    protected Text orderNumberText2SO;
    @FXML
    protected TableView<OrderItemBean> orderItemTableView2SO = new TableView<>();
    protected TableColumn<OrderItemBean, String> nameColumn2SO;
    protected TableColumn<OrderItemBean, String> brandColumn2SO;
    protected TableColumn<OrderItemBean, Integer> quantityOrderedColumn2SO;
    protected TableColumn<OrderItemBean, Double> priceTotalColumn2SO;
    protected TableColumn<OrderItemBean, String> currencyColumn2SO;

    static final Logger logger = Logger.getLogger(SpecificOrder.class.getName());

    @FXML
    protected void onHomepageImageClick2SO() throws IOException, AddressException {
        FXMLLoader loader2SO = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root2SO = loader2SO.load();
        Homepage homepage2SO = loader2SO.getController();
        homepage2SO.passParams(user2SO);
        Stage newStage2SO = new Stage();
        newStage2SO.setScene(new Scene(root2SO));
        newStage2SO.show();
        newStage2SO.setResizable(false);
        Stage stage2SO = (Stage) homepageImageView2SO.getScene().getWindow();
        stage2SO.close();
    }

    @FXML
    protected void previousPage2SO() throws IOException {
        FXMLLoader loader2SO = new FXMLLoader(getClass().getResource("myProfile.fxml"));
        Parent root2SO = loader2SO.load();
        MyProfile myProfile2SO = loader2SO.getController();
        myProfile2SO.passParams(user2SO);
        Stage newStage2SO = new Stage();
        newStage2SO.setScene(new Scene(root2SO));
        newStage2SO.show();
        newStage2SO.setResizable(false);
        Stage stage2SO = (Stage) goPreviousPageImageView2SO.getScene().getWindow();
        stage2SO.close();
    }

    @FXML
    protected void openCartAndPayment2SO() throws IOException {
        FXMLLoader loader2SO = new FXMLLoader(getClass().getResource("cartAndPayment.fxml"));
        Parent root2SO = loader2SO.load();
        CartAndPayment cartAndPayment2SO = loader2SO.getController();
        cartAndPayment2SO.passParam(null, user2SO);
        Stage newStage2SO = new Stage();
        newStage2SO.setScene(new Scene(root2SO));
        newStage2SO.show();
        newStage2SO.setResizable(false);
        Stage stage2SO = (Stage) cartImageView2SO.getScene().getWindow();
        stage2SO.close();
    }

    @FXML
    public void initialize() {
        orderItemTableView2SO.setEditable(true);

        nameColumn2SO = new TableColumn<>("Name");
        brandColumn2SO = new TableColumn<>("Brand");
        quantityOrderedColumn2SO = new TableColumn<>("Quantity ordered");
        priceTotalColumn2SO = new TableColumn<>("Price total");
        currencyColumn2SO = new TableColumn<>("Currency");

        nameColumn2SO.setMinWidth(10);
        brandColumn2SO.setMinWidth(10);
        quantityOrderedColumn2SO.setMinWidth(10);
        priceTotalColumn2SO.setMinWidth(10);
        currencyColumn2SO.setMinWidth(10);

        nameColumn2SO.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandColumn2SO.setCellValueFactory(new PropertyValueFactory<>("brand"));
        quantityOrderedColumn2SO.setCellValueFactory(new PropertyValueFactory<>("quantityOrdered"));
        priceTotalColumn2SO.setCellValueFactory(new PropertyValueFactory<>("priceTotal"));
        currencyColumn2SO.setCellValueFactory(new PropertyValueFactory<>("currency"));

        orderItemTableView2SO.getColumns().addAll(
                nameColumn2SO, brandColumn2SO, quantityOrderedColumn2SO, priceTotalColumn2SO, currencyColumn2SO);
    }

    protected void fillOrder2SO() throws Exception {
        orderItemTableView2SO.getItems().clear();
        ObservableList<OrderItemBean> orderItemObservableList2SO = FXCollections.observableArrayList();
        order2SO = OrderHandler.populateOrderWithOrderItems(order2SO);
        if (order2SO != null) {
            totalPriceText2SO.setText(String.valueOf(order2SO.getTotalPrice()));
            timestampText2SO.setText(String.valueOf(order2SO.getOrderTimestamp()));
            orderNumberText2SO.setText(String.valueOf(order2SO.getOrderId()));
            if (order2SO.getOrderItemArrayList() != null) {
                for (OrderItemBean orderItem2SO : order2SO.getOrderItemArrayList()) {
                    orderItemObservableList2SO.add(orderItem2SO);
                }
                orderItemTableView2SO.setItems(orderItemObservableList2SO);
            } else {
                logger.log(Level.INFO, Constants.NO_RESULT);
            }
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    public void passParams(UserBean user2SO, OrderBean order2SO) throws Exception {
        this.user2SO = user2SO;
        this.order2SO = order2SO;
        fillOrder2SO();
    }
}
