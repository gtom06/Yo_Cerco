package view.view1;

import control.CartElaboration;
import control.OrderHandler;
import control.PaymentHandler;
import control.UserHandler;
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
    protected ImageView homepageImageView1;
    @FXML
    protected Button payButton1;
    @FXML
    protected Text textHi1;
    @FXML
    protected Text slashText1;
    @FXML
    protected ToggleGroup paymentType1;
    @FXML
    protected RadioButton codRadioButton1;
    @FXML
    protected RadioButton cardRadioButton1;
    @FXML
    protected Text orderCreatedText1;
    @FXML
    protected TextField nameTextField1;
    @FXML
    protected TextField surnameTextField1;
    @FXML
    protected TextField phoneNumberTextField1;
    @FXML
    protected TextField billingStreetTextField1;
    @FXML
    protected TextField billingCityTextField1;
    @FXML
    protected TextField billingCountryTextField1;
    @FXML
    protected TextField billingZipTextField1;
    @FXML
    protected TextField cardholderTextField1;
    @FXML
    protected TextField creditcardTextField1;
    @FXML
    protected TextField mmTextField1;
    @FXML
    protected TextField yyTextField1;
    @FXML
    protected TextField cvvTextField1;
    @FXML
    protected Text totalPriceText1;
    @FXML
    protected Text shopNameText1;
    @FXML
    protected Text totalQuantityText1;
    @FXML
    protected TableView<OrderItem> orderItemsTableView1 = new TableView<>();
    protected TableColumn<OrderItem, String> nameColumn1;
    protected TableColumn<OrderItem, String> quantityOrderedColumn1;
    protected TableColumn<OrderItem, Double> pricePerItemColumn1;
    protected TableColumn<OrderItem, Double> priceTotalColumn1;
    static final Logger logger = Logger.getLogger(CartAndPayment.class.getName());

    User user1 = null;
    Shop shop1 = null;

    public void passParam(Shop shop1, User user1) throws ExceptionCart {
        this.shop1 = shop1;
        this.user1 = user1;

        if (user1 != null) {
            textHi1.setText(user1.getUsername());
            nameTextField1.setText(user1.getName());
            surnameTextField1.setText(user1.getSurname());
            phoneNumberTextField1.setText(((Buyer) user1).getPhone());
            billingStreetTextField1.setText(((Buyer) user1).getBillingStreet());
            billingCityTextField1.setText(((Buyer) user1).getBillingCity());
            billingCountryTextField1.setText(((Buyer) user1).getBillingCountry());
            billingZipTextField1.setText(((Buyer) user1).getBillingZip());
        }


        List<OrderItem> orderItemArrayList = CartElaboration.readOrderItemsFromCart();
        if (orderItemArrayList != null && (orderItemArrayList).size() != 0) {
            ObservableList<OrderItem> observableListProducts =
                    FXCollections.observableArrayList(orderItemArrayList);
            orderItemsTableView1.setItems(observableListProducts);
        }

        Order order = OrderHandler.previewOrder();
        if (order != null){
            totalPriceText1.setText(order.getCurrency() + "" + order.getTotalPrice());
            totalQuantityText1.setText(String.valueOf(order.getOrderTotalQuantity()));
        } else {
            totalPriceText1.setText("0");
            totalQuantityText1.setText("0");
        }

    }

    @FXML
    protected void onHomepageImageClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root = loader.load();
        Homepage homepage = loader.getController();
        homepage.passUser(user1);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) homepageImageView1.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void previousPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("shopView.fxml"));
        Parent root = loader.load();
        ShopView shopView = loader.getController();
        shopView.passUser(user1);
        shopView.passShop(shop1);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) homepageImageView1.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onCodClicked() {
        cardholderTextField1.setVisible(false);
        creditcardTextField1.setVisible(false);
        mmTextField1.setVisible(false);
        yyTextField1.setVisible(false);
        cvvTextField1.setVisible(false);
        slashText1.setVisible(false);

    }

    @FXML
    protected void onCardClicked() {
        cardholderTextField1.setVisible(true);
        creditcardTextField1.setVisible(true);
        mmTextField1.setVisible(true);
        yyTextField1.setVisible(true);
        cvvTextField1.setVisible(true);
        slashText1.setVisible(true);
        if (user1.getName() != null || !user1.getName().equals("")) {
            nameTextField1.setText(user1.getName());
        }
        if (user1.getSurname() != null || !user1.getUsername().equals("")) {
            surnameTextField1.setText(user1.getSurname());
        }
        if (((Buyer) user1).getPhone() != null || !((Buyer) user1).getPhone().equals("")) {
            phoneNumberTextField1.setText(((Buyer) user1).getPhone());
        }
        if (((Buyer) user1).getBillingStreet()!= null || !(((Buyer) user1).getBillingStreet()).equals("")) {
            billingStreetTextField1.setText(((Buyer) user1).getBillingStreet());
        }
        if (((Buyer) user1).getPhone() != null || !((Buyer) user1).getPhone().equals("")) {
            billingCityTextField1.setText(((Buyer) user1).getBillingCity());
        }
        if (((Buyer) user1).getBillingCountry() != null || !((Buyer) user1).getBillingCountry().equals("")) {
            billingCountryTextField1.setText(((Buyer) user1).getBillingCountry());
        }
        if (((Buyer) user1).getBillingZip() != null || !((Buyer) user1).getBillingZip().equals("")) {
            billingZipTextField1.setText(((Buyer) user1).getBillingZip());
        }
    }

    @FXML
    protected void onPayButtonClick() throws Exception {
        String name1 = nameTextField1.getText();
        String surname1 = surnameTextField1.getText();
        String phoneNumber1 = phoneNumberTextField1.getText();
        String billingStreet1 = billingStreetTextField1.getText();
        String billingCity1 = billingCityTextField1.getText();
        String billingCountry1 = billingCountryTextField1.getText();
        String billingZip1 = billingZipTextField1.getText();

        String paymentMethod1 = "";
        String cardholder1 = cardholderTextField1.getText();
        String cardNumber1 = creditcardTextField1.getText();
        String mm1 = mmTextField1.getText();
        String yy1 = yyTextField1.getText();
        String cvv1 = cvvTextField1.getText();
        Order order1 = null;

        if (!PaymentHandler.validateParams(paymentMethod1, cardNumber1, mm1, yy1, cvv1)) {
            logger.log(Level.INFO, "reviewPayment");
        }
        else {
            if (!CartElaboration.isEmptyCart()) {
                if (OrderHandler.someBlankDataUser(name1, surname1,billingStreet1,billingCity1,billingCountry1,billingZip1,phoneNumber1)) {
                    logger.log(Level.INFO, "please fill data");
                } else {
                    UserHandler.updateRecord2(
                            user1, Arrays.asList(name1, surname1, billingStreet1, billingCity1, billingCountry1,
                            billingZip1, phoneNumber1, ((Buyer) user1).getProfileImagepath()));

                    order1 = OrderHandler.createOrder(
                            user1,
                            paymentMethod1,
                            cardholder1,
                            cardNumber1,
                            mm1,
                            yy1,
                            cvv1
                    );
                    orderItemsTableView1.setItems(null);
                    orderCreatedText1.setVisible(true);
                    totalPriceText1.setText("0");
                    totalQuantityText1.setText("0");

                    //and load specificOrder.fxml
                    FXMLLoader loader1 = new FXMLLoader(getClass().getResource("specificOrder.fxml"));
                    Parent root1 = loader1.load();
                    SpecificOrder specificOrder1 = loader1.getController();
                    specificOrder1.passParams(user1, order1);
                    Stage newStage = new Stage();
                    newStage.setScene(new Scene(root1));
                    newStage.show();
                    newStage.setResizable(false);
                    Stage stage = (Stage) homepageImageView1.getScene().getWindow();
                    stage.close();
                }
            }
        }
    }

    @FXML
    public void initialize(){
        orderItemsTableView1.setEditable(true);

        nameColumn1 = new TableColumn<>("Name");
        nameColumn1.setMinWidth(276.0);
        nameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));

        quantityOrderedColumn1 = new TableColumn<>("Quantity ordered");
        quantityOrderedColumn1.setMinWidth(100.0);
        quantityOrderedColumn1.setCellValueFactory(new PropertyValueFactory<>("quantityOrdered"));

        pricePerItemColumn1 = new TableColumn<>("Price per item");
        pricePerItemColumn1.setMinWidth(100.0);
        pricePerItemColumn1.setCellValueFactory(new PropertyValueFactory<>("price"));


        priceTotalColumn1 = new TableColumn<>("Total price");
        priceTotalColumn1.setMinWidth(100.0);
        priceTotalColumn1.setCellValueFactory(new PropertyValueFactory<>("priceTotal"));

        orderItemsTableView1.getColumns().addAll(nameColumn1, quantityOrderedColumn1, pricePerItemColumn1, priceTotalColumn1);
    }

    @FXML
    public void onClearCartClicked() throws ExceptionCart {
        CartElaboration.deleteCart();
        orderItemsTableView1.setItems(null);
    }
}
