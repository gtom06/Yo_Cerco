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
    protected ImageView homepageImageView1CAP;
    @FXML
    protected Button payButton1CAP;
    @FXML
    protected Text textHi1CAP;
    @FXML
    protected Text slashText1CAP;
    @FXML
    protected ToggleGroup paymentType1CAP;
    @FXML
    protected RadioButton codRadioButton1CAP;
    @FXML
    protected RadioButton cardRadioButton1CAP;
    @FXML
    protected Text orderCreatedText1CAP;
    @FXML
    protected TextField nameTextField1CAP;
    @FXML
    protected TextField surnameTextField1CAP;
    @FXML
    protected TextField phoneNumberTextField1CAP;
    @FXML
    protected TextField billingStreetTextField1CAP;
    @FXML
    protected TextField billingCityTextField1CAP;
    @FXML
    protected TextField billingCountryTextField1CAP;
    @FXML
    protected TextField billingZipTextField1CAP;
    @FXML
    protected TextField cardholderTextField1CAP;
    @FXML
    protected TextField creditcardTextField1CAP;
    @FXML
    protected TextField mmTextField1CAP;
    @FXML
    protected TextField yyTextField1CAP;
    @FXML
    protected TextField cvvTextField1CAP;
    @FXML
    protected Text totalPriceText1CAP;
    @FXML
    protected Text shopNameText1CAP;
    @FXML
    protected Text totalQuantityText1CAP;
    @FXML
    protected TableView<OrderItem> orderItemsTableView1CAP = new TableView<>();
    protected TableColumn<OrderItem, String> nameColumn1CAP;
    protected TableColumn<OrderItem, String> quantityOrderedColumn1CAP;
    protected TableColumn<OrderItem, Double> pricePerItemColumn1CAP;
    protected TableColumn<OrderItem, Double> priceTotalColumn1CAP;
    static final Logger logger = Logger.getLogger(CartAndPayment.class.getName());

    User user1CAP = null;
    Shop shop1CAP = null;

    public void passParam(Shop shop1CAP, User user1CAP) throws ExceptionCart {
        this.shop1CAP = shop1CAP;
        this.user1CAP = user1CAP;

        if (user1CAP != null) {
            textHi1CAP.setText(user1CAP.getUsername());
            nameTextField1CAP.setText(user1CAP.getName());
            surnameTextField1CAP.setText(user1CAP.getSurname());
            phoneNumberTextField1CAP.setText(((Buyer) user1CAP).getPhone());
            billingStreetTextField1CAP.setText(((Buyer) user1CAP).getBillingStreet());
            billingCityTextField1CAP.setText(((Buyer) user1CAP).getBillingCity());
            billingCountryTextField1CAP.setText(((Buyer) user1CAP).getBillingCountry());
            billingZipTextField1CAP.setText(((Buyer) user1CAP).getBillingZip());
        }


        List<OrderItem> orderItemArrayList1CAP = CartElaboration.readOrderItemsFromCart();
        if (orderItemArrayList1CAP != null && (orderItemArrayList1CAP).size() != 0) {
            ObservableList<OrderItem> observableListProducts =
                    FXCollections.observableArrayList(orderItemArrayList1CAP);
            orderItemsTableView1CAP.setItems(observableListProducts);
        }

        Order order1CAP = OrderHandler.previewOrder();
        if (order1CAP != null){
            totalPriceText1CAP.setText(order1CAP.getCurrency() + "" + order1CAP.getTotalPrice());
            totalQuantityText1CAP.setText(String.valueOf(order1CAP.getOrderTotalQuantity()));
        } else {
            totalPriceText1CAP.setText("0");
            totalQuantityText1CAP.setText("0");
        }

    }

    @FXML
    protected void onHomepageImageClick1CAP() throws IOException {
        FXMLLoader loader1CAP = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root1CAP = loader1CAP.load();
        Homepage homepage1CAP = loader1CAP.getController();
        homepage1CAP.passUser(user1CAP);
        Stage newStage1CAP = new Stage();
        newStage1CAP.setScene(new Scene(root1CAP));
        newStage1CAP.show();
        newStage1CAP.setResizable(false);
        Stage stage1CAP = (Stage) homepageImageView1CAP.getScene().getWindow();
        stage1CAP.close();
    }

    @FXML
    protected void previousPage1CAP() throws IOException {
        FXMLLoader loader1CAP = new FXMLLoader(getClass().getResource("shopView.fxml"));
        Parent root1CAP = loader1CAP.load();
        ShopView shopView1CAP = loader1CAP.getController();
        shopView1CAP.passUser(user1CAP);
        shopView1CAP.passShop(shop1CAP);
        Stage newStage1CAP = new Stage();
        newStage1CAP.setScene(new Scene(root1CAP));
        newStage1CAP.show();
        newStage1CAP.setResizable(false);
        Stage stage1CAP = (Stage) homepageImageView1CAP.getScene().getWindow();
        stage1CAP.close();
    }

    @FXML
    protected void onCodClicked() {
        cardholderTextField1CAP.setVisible(false);
        creditcardTextField1CAP.setVisible(false);
        mmTextField1CAP.setVisible(false);
        yyTextField1CAP.setVisible(false);
        cvvTextField1CAP.setVisible(false);
        slashText1CAP.setVisible(false);

    }

    @FXML
    protected void onCardClicked() {
        cardholderTextField1CAP.setVisible(true);
        creditcardTextField1CAP.setVisible(true);
        mmTextField1CAP.setVisible(true);
        yyTextField1CAP.setVisible(true);
        cvvTextField1CAP.setVisible(true);
        slashText1CAP.setVisible(true);
        if (user1CAP.getName() != null || !user1CAP.getName().equals("")) {
            nameTextField1CAP.setText(user1CAP.getName());
        }
        if (user1CAP.getSurname() != null || !user1CAP.getUsername().equals("")) {
            surnameTextField1CAP.setText(user1CAP.getSurname());
        }
        if (((Buyer) user1CAP).getPhone() != null || !((Buyer) user1CAP).getPhone().equals("")) {
            phoneNumberTextField1CAP.setText(((Buyer) user1CAP).getPhone());
        }
        if (((Buyer) user1CAP).getBillingStreet()!= null || !(((Buyer) user1CAP).getBillingStreet()).equals("")) {
            billingStreetTextField1CAP.setText(((Buyer) user1CAP).getBillingStreet());
        }
        if (((Buyer) user1CAP).getPhone() != null || !((Buyer) user1CAP).getPhone().equals("")) {
            billingCityTextField1CAP.setText(((Buyer) user1CAP).getBillingCity());
        }
        if (((Buyer) user1CAP).getBillingCountry() != null || !((Buyer) user1CAP).getBillingCountry().equals("")) {
            billingCountryTextField1CAP.setText(((Buyer) user1CAP).getBillingCountry());
        }
        if (((Buyer) user1CAP).getBillingZip() != null || !((Buyer) user1CAP).getBillingZip().equals("")) {
            billingZipTextField1CAP.setText(((Buyer) user1CAP).getBillingZip());
        }
    }

    @FXML
    protected void onPayButtonClick() throws Exception {
        String name1 = nameTextField1CAP.getText();
        String surname1 = surnameTextField1CAP.getText();
        String phoneNumber1 = phoneNumberTextField1CAP.getText();
        String billingStreet1 = billingStreetTextField1CAP.getText();
        String billingCity1 = billingCityTextField1CAP.getText();
        String billingCountry1 = billingCountryTextField1CAP.getText();
        String billingZip1 = billingZipTextField1CAP.getText();

        String paymentMethod1 = "";
        String cardholder1 = cardholderTextField1CAP.getText();
        String cardNumber1 = creditcardTextField1CAP.getText();
        String mm1 = mmTextField1CAP.getText();
        String yy1 = yyTextField1CAP.getText();
        String cvv1 = cvvTextField1CAP.getText();
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
                            user1CAP, Arrays.asList(name1, surname1, billingStreet1, billingCity1, billingCountry1,
                            billingZip1, phoneNumber1, ((Buyer) user1CAP).getProfileImagepath()));

                    order1 = OrderHandler.createOrder(
                            user1CAP,
                            paymentMethod1,
                            cardholder1,
                            cardNumber1,
                            mm1,
                            yy1,
                            cvv1
                    );
                    orderItemsTableView1CAP.setItems(null);
                    orderCreatedText1CAP.setVisible(true);
                    totalPriceText1CAP.setText("0");
                    totalQuantityText1CAP.setText("0");

                    //and load specificOrder.fxml
                    FXMLLoader loader1CAP = new FXMLLoader(getClass().getResource("specificOrder.fxml"));
                    Parent root1CAP = loader1CAP.load();
                    SpecificOrder specificOrder1CAP = loader1CAP.getController();
                    specificOrder1CAP.passParams(user1CAP, order1);
                    Stage newStage1CAP = new Stage();
                    newStage1CAP.setScene(new Scene(root1CAP));
                    newStage1CAP.show();
                    newStage1CAP.setResizable(false);
                    Stage stage1CAP = (Stage) homepageImageView1CAP.getScene().getWindow();
                    stage1CAP.close();
                }
            }
        }
    }

    @FXML
    public void initialize(){
        orderItemsTableView1CAP.setEditable(true);

        nameColumn1CAP = new TableColumn<>("Name");
        nameColumn1CAP.setMinWidth(276.0);
        nameColumn1CAP.setCellValueFactory(new PropertyValueFactory<>("name"));

        quantityOrderedColumn1CAP = new TableColumn<>("Quantity ordered");
        quantityOrderedColumn1CAP.setMinWidth(100.0);
        quantityOrderedColumn1CAP.setCellValueFactory(new PropertyValueFactory<>("quantityOrdered"));

        pricePerItemColumn1CAP = new TableColumn<>("Price per item");
        pricePerItemColumn1CAP.setMinWidth(100.0);
        pricePerItemColumn1CAP.setCellValueFactory(new PropertyValueFactory<>("price"));


        priceTotalColumn1CAP = new TableColumn<>("Total price");
        priceTotalColumn1CAP.setMinWidth(100.0);
        priceTotalColumn1CAP.setCellValueFactory(new PropertyValueFactory<>("priceTotal"));

        orderItemsTableView1CAP.getColumns().addAll(nameColumn1CAP, quantityOrderedColumn1CAP, pricePerItemColumn1CAP, priceTotalColumn1CAP);
    }

    @FXML
    public void onClearCartClicked() throws ExceptionCart {
        CartElaboration.deleteCart();
        orderItemsTableView1CAP.setItems(null);
    }
}
