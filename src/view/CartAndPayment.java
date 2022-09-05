package view;

import control.OrderHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Constants;
import model.Department.Department;
import model.Shop.Shop;
import model.User.Buyer;
import model.User.User;

import java.io.IOException;

public class CartAndPayment {
    @FXML
    ImageView homepageImageView;
    @FXML
    Button payButton;
    @FXML
    Label labelHi;
    @FXML
    ToggleGroup paymentType;
    @FXML
    RadioButton codRadioButton;
    @FXML
    RadioButton cardRadioButton;
    @FXML
    TextField
                nameTextField,
                surnameTextField,
                phoneNumberTextField,
                billingStreetTextField,
                billingCityTextField,
                billingCountryTextField,
                billingZipTextField,
                cardholderTextField,
                creditcardTextField,
                mmTextField,
                yyTextField,
                cvvTextField;

    User user;
    Shop shop;

    public void passParam(Shop shop, Department department, User user) {
        this.shop = shop;
        this.user = user;
        labelHi.setText(user.getUsername());
    }

    @FXML
    public void onHomepageImageClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchShop.fxml"));
        Parent root = loader.load();
        SearchShop searchShop = loader.getController();
        searchShop.passUser(user);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) homepageImageView.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void previousPage() throws IOException {/*
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchShop.fxml"));
        Parent root = loader.load();
        SearchShop searchShop = loader.getController();
        searchShop.passUser(user);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) homepageImageView.getScene().getWindow();
        stage.close();*/
    }

    @FXML
    public void onCodClicked() {
        cardholderTextField.setVisible(false);
        creditcardTextField.setVisible(false);
        mmTextField.setVisible(false);
        yyTextField.setVisible(false);
        cvvTextField.setVisible(false);
    }

    @FXML
    public void onCardClicked() {
        cardholderTextField.setVisible(true);
        creditcardTextField.setVisible(true);
        mmTextField.setVisible(true);
        yyTextField.setVisible(true);
        cvvTextField.setVisible(true);
    }

    @FXML
    public void onPayButtonClick() throws IOException {
        String paymentMethod = "";
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String billingStreet = billingStreetTextField.getText();
        String billingCity = billingCityTextField.getText();
        String billingCountry = billingCountryTextField.getText();
        String billingZip = billingZipTextField.getText();
        String cardholder = cardholderTextField.getText();
        String cardNumber = creditcardTextField.getText();
        int mm = Integer.parseInt(mmTextField.getText());
        int yy = Integer.parseInt(yyTextField.getText());
        String cvv = cvvTextField.getText();

        user.setName(name);
        user.setSurname(surname);
        ((Buyer) user).setBillingStreet(billingStreet);
        ((Buyer) user).setBillingCity(billingCity);
        ((Buyer) user).setBillingCountry(billingCountry);
        ((Buyer) user).setBillingZip(billingZip);
        ((Buyer) user).setBillingAddress();
        System.out.println(user);

        if (cardRadioButton.isSelected()) {
            paymentMethod = Constants.CREDITCARD_PAYMENT;
        }
        if (codRadioButton.isSelected()) {
            paymentMethod = Constants.CASH_ON_DELIVERY_PAYMENT;
        }


        OrderHandler.createOrder(
                user,
                shop,
                paymentMethod,
                cardholder,
                cardNumber,
                mm,
                yy,
                cvv
        );

    }
}
