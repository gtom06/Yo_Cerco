package view.view1;

import control.CartElaboration;
import control.OrderHandler;
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
import model.Constants;
import model.order.Order;
import model.order.OrderItem;
import model.shop.Shop;
import model.user.Buyer;
import model.user.User;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CartAndPayment {
    @FXML
    protected ImageView homepageImageView;
    @FXML
    protected Button payButton;
    @FXML
    protected Text textHi;
    @FXML
    protected Text slashText;
    @FXML
    protected ToggleGroup paymentType;
    @FXML
    protected RadioButton codRadioButton;
    @FXML
    protected RadioButton cardRadioButton;
    @FXML
    protected Text orderCreatedText;
    @FXML
    protected TextField nameTextField;
    @FXML
    protected TextField surnameTextField;
    @FXML
    protected TextField phoneNumberTextField;
    @FXML
    protected TextField billingStreetTextField;
    @FXML
    protected TextField billingCityTextField;
    @FXML
    protected TextField billingCountryTextField;
    @FXML
    protected TextField billingZipTextField;
    @FXML
    protected TextField cardholderTextField;
    @FXML
    protected TextField creditcardTextField;
    @FXML
    protected TextField mmTextField;
    @FXML
    protected TextField yyTextField;
    @FXML
    protected TextField cvvTextField;
    @FXML
    protected Text totalPriceText;
    @FXML
    protected Text shopNameText;
    @FXML
    protected Text totalQuantityText;
    @FXML
    protected TableView<OrderItem> orderItemsTableView = new TableView<>();
    protected TableColumn<OrderItem, String> nameColumn;
    protected TableColumn<OrderItem, String> quantityOrderedColumn;
    protected TableColumn<OrderItem, Double> pricePerItemColumn;
    protected TableColumn<OrderItem, Double> priceTotalColumn;
    static final Logger logger = Logger.getLogger(CartAndPayment.class.getName());

    User user = null;
    Shop shop = null;

    public void passParam(Shop shop, User user) throws ExceptionCart {
        this.shop = shop;
        this.user = user;
        textHi.setText(user.getUsername());

        if (user != null) {
            nameTextField.setText(user.getName());
            surnameTextField.setText(user.getSurname());
            phoneNumberTextField.setText(((Buyer) user).getPhone());
            billingStreetTextField.setText(((Buyer) user).getBillingStreet());
            billingCityTextField.setText(((Buyer) user).getBillingCity());
            billingCountryTextField.setText(((Buyer) user).getBillingCountry());
            billingZipTextField.setText(((Buyer) user).getBillingZip());
        }


        List<OrderItem> orderItemArrayList = CartElaboration.readOrderItemsFromCart();
        if (orderItemArrayList != null && (orderItemArrayList).size() != 0) {
            ObservableList<OrderItem> observableListProducts =
                    FXCollections.observableArrayList(orderItemArrayList);
            orderItemsTableView.setItems(observableListProducts);
        }

        Order order = OrderHandler.previewOrder();
        if (order != null){
            totalPriceText.setText(order.getCurrency() + "" + order.getTotalPrice());
            totalQuantityText.setText(String.valueOf(order.getOrderTotalQuantity()));
        } else {
            totalPriceText.setText("0");
            totalQuantityText.setText("0");
        }

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
    protected void previousPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("shopView.fxml"));
        Parent root = loader.load();
        ShopView shopView = loader.getController();
        shopView.passUser(user);
        shopView.passShop(shop);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) homepageImageView.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onCodClicked() {
        cardholderTextField.setVisible(false);
        creditcardTextField.setVisible(false);
        mmTextField.setVisible(false);
        yyTextField.setVisible(false);
        cvvTextField.setVisible(false);
        slashText.setVisible(false);

    }

    @FXML
    protected void onCardClicked() {
        cardholderTextField.setVisible(true);
        creditcardTextField.setVisible(true);
        mmTextField.setVisible(true);
        yyTextField.setVisible(true);
        cvvTextField.setVisible(true);
        slashText.setVisible(true);
        if (user.getName() != null || !user.getName().equals("")) {
            nameTextField.setText(user.getName());
        }
        if (user.getSurname() != null || !user.getUsername().equals("")) {
            surnameTextField.setText(user.getSurname());
        }
        if (((Buyer) user).getPhone() != null || !((Buyer) user).getPhone().equals("")) {
            phoneNumberTextField.setText(((Buyer) user).getPhone());
        }
        if (((Buyer) user).getBillingStreet()!= null || !(((Buyer) user).getBillingStreet()).equals("")) {
            billingStreetTextField.setText(((Buyer) user).getBillingStreet());
        }
        if (((Buyer) user).getPhone() != null || !((Buyer) user).getPhone().equals("")) {
            billingCityTextField.setText(((Buyer) user).getBillingCity());
        }
        if (((Buyer) user).getBillingCountry() != null || !((Buyer) user).getBillingCountry().equals("")) {
            billingCountryTextField.setText(((Buyer) user).getBillingCountry());
        }
        if (((Buyer) user).getBillingZip() != null || !((Buyer) user).getBillingZip().equals("")) {
            billingZipTextField.setText(((Buyer) user).getBillingZip());
        }
    }

    @FXML
    protected void onPayButtonClick() throws Exception {
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String billingStreet = billingStreetTextField.getText();
        String billingCity = billingCityTextField.getText();
        String billingCountry = billingCountryTextField.getText();
        String billingZip = billingZipTextField.getText();

        String paymentMethod = "";
        String cardholder = cardholderTextField.getText();
        String cardNumber = creditcardTextField.getText();
        String mm = mmTextField.getText();
        String yy = yyTextField.getText();
        String cvv = cvvTextField.getText();

        if (cardRadioButton.isSelected() && cardNumber.length() < 16 && mm.isBlank() && yy.isBlank() && cvv.length() < 3) {
            logger.log(Level.INFO, "review payment");
        }
        else{
            if (!CartElaboration.isEmptyCart()) {
                if (cardRadioButton.isSelected()) {
                    paymentMethod = Constants.CREDITCARD_PAYMENT;
                }
                if (codRadioButton.isSelected()) {
                    paymentMethod = Constants.CASH_ON_DELIVERY_PAYMENT;
                }

                if (name.isBlank() || surname.isBlank() || billingStreet.isBlank() ||
                        billingCity.isBlank() || billingCountry.isBlank() ||
                        billingZip.isBlank() || phoneNumber.isBlank()) {
                    if (codRadioButton.isSelected()) {
                        throw new Exception("please fill data");
                    } else {
                        if (cardholder.isBlank() || cardNumber.isBlank() || mm.isBlank() || yy.isBlank() || cvv.isBlank()) {
                            logger.log(Level.INFO, "please fill data & card");
                        }
                    }
                } else {
                    boolean out = UserHandler.updateRecord(
                            user, name, surname, billingStreet, billingCity, billingCountry,
                            billingZip, phoneNumber, ((Buyer) user).getProfileImagepath());
                    if (out) {
                        OrderHandler.createOrder(
                                user,
                                paymentMethod,
                                cardholder,
                                cardNumber,
                                mm,
                                yy,
                                cvv
                        );
                    }
                    orderItemsTableView.setItems(null);
                    orderCreatedText.setVisible(true);
                    totalPriceText.setText("0");
                    totalQuantityText.setText("0");
                }
            }
        }
    }

    @FXML
    public void initialize(){
        orderItemsTableView.setEditable(true);

        nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(276.0);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        quantityOrderedColumn = new TableColumn<>("Quantity ordered");
        quantityOrderedColumn.setMinWidth(100.0);
        quantityOrderedColumn.setCellValueFactory(new PropertyValueFactory<>("quantityOrdered"));

        pricePerItemColumn = new TableColumn<>("Price per item");
        pricePerItemColumn.setMinWidth(100.0);
        pricePerItemColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        priceTotalColumn = new TableColumn<>("Total price");
        priceTotalColumn.setMinWidth(100.0);
        priceTotalColumn.setCellValueFactory(new PropertyValueFactory<>("priceTotal"));

        orderItemsTableView.getColumns().addAll(nameColumn, quantityOrderedColumn, pricePerItemColumn, priceTotalColumn);
    }

    @FXML
    public void onClearCartClicked() throws ExceptionCart {
        CartElaboration.deleteCart();
        orderItemsTableView.setItems(null);
    }
}
