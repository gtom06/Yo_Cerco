package view.view2;

import bean.OrderBean;
import bean.OrderItemBean;
import bean.ShopBean;
import bean.UserBean;
import control.CartElaboration;
import control.OrderHandler;
import control.PaymentHandler;
import control.UserHandler;
import exceptions.AddressException;
import exceptions.ExceptionCart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.order.Order;
import model.order.OrderItem;
import model.shop.Shop;
import model.user.Buyer;
import model.user.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CartAndPayment {
    @FXML
    protected ImageView homepageImageView2CA;
    @FXML
    protected Button payButton2CA;
    @FXML
    protected Text slashText2CA;
    @FXML
    protected RadioButton codRadioButton2CA;
    @FXML
    protected RadioButton cardRadioButton2CA;
    @FXML
    protected ToggleGroup paymentType2CA;
    @FXML
    protected TextField nameTextField2CA;
    @FXML
    protected TextField surnameTextField2CA;
    @FXML
    protected TextField phoneNumberTextField2CA;
    @FXML
    protected TextField billingStreetTextField2CA;
    @FXML
    protected TextField billingCityTextField2CA;
    @FXML
    protected TextField billingCountryTextField2CA;
    @FXML
    protected TextField billingZipTextField2CA;
    @FXML
    protected TextField cardholderTextField2CA;
    @FXML
    protected TextField creditcardTextField2CA;
    @FXML
    protected TextField mmTextField2CA;
    @FXML
    protected TextField yyTextField2CA;
    @FXML
    protected TextField cvvTextField2CA;
    @FXML
    protected Text orderCreatedText2CA;
    @FXML
    protected Text totalPriceText2CA;
    @FXML
    protected Text totalQuantityText2CA;
    @FXML
    protected TableView<OrderItemBean> orderItemsTableView2CA = new TableView<>();
    protected TableColumn<OrderItemBean, String> nameColumn2CA;
    protected TableColumn<OrderItemBean, String> quantityOrderedColumn2CA;
    protected TableColumn<OrderItemBean, Double> pricePerItemColumn2CA;
    protected TableColumn<OrderItemBean, Double> priceTotalColumn2CA;

    UserBean user2CA;
    ShopBean shop2CA;

    static final Logger logger = Logger.getLogger(CartAndPayment.class.getName());

    public void passParam(ShopBean shop2CA, UserBean user2CA) throws ExceptionCart {
        this.shop2CA = shop2CA;
        this.user2CA = user2CA;

        if (user2CA != null) {
            nameTextField2CA.setText(user2CA.getName());
            surnameTextField2CA.setText(user2CA.getSurname());
            phoneNumberTextField2CA.setText(user2CA.getPhone());
            billingStreetTextField2CA.setText(user2CA.getBillingStreet());
            billingCityTextField2CA.setText(user2CA.getBillingCity());
            billingCountryTextField2CA.setText(user2CA.getBillingCountry());
            billingZipTextField2CA.setText(user2CA.getBillingZip());
        }

        List<OrderItemBean> orderItemArrayList2CA = CartElaboration.readOrderItemsFromCart();
        if (orderItemArrayList2CA != null && !orderItemArrayList2CA.isEmpty()) {
            ObservableList<OrderItemBean> observableListProducts2CA =
                    FXCollections.observableArrayList(orderItemArrayList2CA);
            orderItemsTableView2CA.setItems(observableListProducts2CA);
        }

        OrderBean order2CA = OrderHandler.previewOrder();
        if (order2CA != null){
            totalPriceText2CA.setText(order2CA.getCurrency() + "" + order2CA.getTotalPrice());
            totalQuantityText2CA.setText(String.valueOf(order2CA.getOrderTotalQuantity()));
        } else {
            totalPriceText2CA.setText("0");
            totalQuantityText2CA.setText("0");
        }

    }

    @FXML
    protected void onHomepageImageClick2CA() throws IOException, AddressException {
        FXMLLoader loader2CA = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root2CA = loader2CA.load();
        Homepage homepage2CA = loader2CA.getController();
        homepage2CA.passParams(user2CA);
        Stage newStage2CA = new Stage();
        newStage2CA.setScene(new Scene(root2CA));
        newStage2CA.show();
        newStage2CA.setResizable(false);
        Stage stage2CA = (Stage) homepageImageView2CA.getScene().getWindow();
        stage2CA.close();
    }

    @FXML
    protected void onCodClicked2CA() {
        cardholderTextField2CA.setVisible(false);
        creditcardTextField2CA.setVisible(false);
        mmTextField2CA.setVisible(false);
        yyTextField2CA.setVisible(false);
        cvvTextField2CA.setVisible(false);
        slashText2CA.setVisible(false);

    }

    @FXML
    protected void onCardClicked2CA() {
        cardholderTextField2CA.setVisible(true);
        creditcardTextField2CA.setVisible(true);
        mmTextField2CA.setVisible(true);
        yyTextField2CA.setVisible(true);
        cvvTextField2CA.setVisible(true);
        slashText2CA.setVisible(true);
        if (user2CA.getName() != null || user2CA.getName().equals("")) {
            nameTextField2CA.setText(user2CA.getName());
        }
        if (user2CA.getSurname() != null || user2CA.getUsername().equals("")) {
            surnameTextField2CA.setText(user2CA.getSurname());
        }
        if (user2CA.getPhone() != null || user2CA.getPhone().equals("")) {
            phoneNumberTextField2CA.setText(user2CA.getPhone());
        }
        if (user2CA.getBillingStreet()!= null || user2CA.getBillingStreet().equals("")) {
            billingStreetTextField2CA.setText(user2CA.getBillingStreet());
        }
        if (user2CA.getPhone() != null || user2CA.getPhone().equals("")) {
            billingCityTextField2CA.setText(user2CA.getBillingCity());
        }
        if (user2CA.getBillingCountry() != null || user2CA.getBillingCountry().equals("")) {
            billingCountryTextField2CA.setText(user2CA.getBillingCountry());
        }
        if (user2CA.getBillingZip() != null || user2CA.getBillingZip().equals("")) {
            billingZipTextField2CA.setText(user2CA.getBillingZip());
        }
    }

    @FXML
    protected void onPayButtonClick2CA() throws Exception {
        String name2CA = nameTextField2CA.getText();
        String surname2CA = surnameTextField2CA.getText();
        String phoneNumber2CA = phoneNumberTextField2CA.getText();
        String billingStreet2CA = billingStreetTextField2CA.getText();
        String billingCity2CA = billingCityTextField2CA.getText();
        String billingCountry2CA = billingCountryTextField2CA.getText();
        String billingZip2CA = billingZipTextField2CA.getText();
        String paymentMethod2CA = ((RadioButton) paymentType2CA.getSelectedToggle()).getText();
        String cardholder2CA = cardholderTextField2CA.getText();
        String cardNumber2CA = creditcardTextField2CA.getText();
        String mm2CA = mmTextField2CA.getText();
        String yy2CA = yyTextField2CA.getText();
        String cvv2CA = cvvTextField2CA.getText();
        OrderBean order2CA;

        if (!PaymentHandler.validateParams(paymentMethod2CA, cardNumber2CA, mm2CA, yy2CA, cvv2CA)) {
            logger.log(Level.INFO, "reviewPayment");
        }
        else {
            if (!CartElaboration.isEmptyCart()) {
                if (OrderHandler.someBlankDataUser(
                        name2CA, surname2CA,billingStreet2CA,billingCity2CA,billingCountry2CA,billingZip2CA,phoneNumber2CA)) {
                        logger.log(Level.INFO, "please fill data");
                } else {
                    UserHandler.updateRecord2(
                            user2CA, Arrays.asList(name2CA, surname2CA, billingStreet2CA, billingCity2CA,
                                    billingCountry2CA,
                            billingZip2CA, phoneNumber2CA, user2CA.getProfileImagepath()));

                    order2CA = OrderHandler.createOrder(
                            user2CA,
                            paymentMethod2CA,
                            cardholder2CA);
                    orderItemsTableView2CA.setItems(null);
                    orderCreatedText2CA.setVisible(true);
                    totalPriceText2CA.setText("0");
                    totalQuantityText2CA.setText("0");

                    //and load specificOrder.fxml
                    FXMLLoader loader2CA = new FXMLLoader(getClass().getResource("specificOrder.fxml"));
                    Parent root2CA = loader2CA.load();
                    SpecificOrder specificOrder2CA = loader2CA.getController();
                    specificOrder2CA.passParams(user2CA, order2CA);
                    Stage newStage2CA = new Stage();
                    newStage2CA.setScene(new Scene(root2CA));
                    newStage2CA.show();
                    newStage2CA.setResizable(false);
                    Stage stage2CA = (Stage) homepageImageView2CA.getScene().getWindow();
                    stage2CA.close();
                }
            }
        }
    }

    @FXML
    protected void initialize(){
        orderItemsTableView2CA.setEditable(true);

        nameColumn2CA = new TableColumn<>("Name");
        nameColumn2CA.setMinWidth(276.0);
        nameColumn2CA.setCellValueFactory(new PropertyValueFactory<>("name"));

        quantityOrderedColumn2CA = new TableColumn<>("Quantity ordered");
        quantityOrderedColumn2CA.setMinWidth(100.0);
        quantityOrderedColumn2CA.setCellValueFactory(new PropertyValueFactory<>("quantityOrdered"));

        pricePerItemColumn2CA = new TableColumn<>("Price per item");
        pricePerItemColumn2CA.setMinWidth(100.0);
        pricePerItemColumn2CA.setCellValueFactory(new PropertyValueFactory<>("price"));


        priceTotalColumn2CA = new TableColumn<>("Total price");
        priceTotalColumn2CA.setMinWidth(100.0);
        priceTotalColumn2CA.setCellValueFactory(new PropertyValueFactory<>("priceTotal"));

        orderItemsTableView2CA.getColumns().addAll(
                nameColumn2CA, quantityOrderedColumn2CA, pricePerItemColumn2CA, priceTotalColumn2CA);
    }

    @FXML
    protected void onClearCartClicked2CA() throws ExceptionCart {
        CartElaboration.deleteCart();
        orderItemsTableView2CA.setItems(null);
        totalPriceText2CA.setText("0");
        totalQuantityText2CA.setText("0");
    }
}
