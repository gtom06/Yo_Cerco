package view.view2;

import control.FileElaboration;
import control.OrderHandler;
import control.UserHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Constants;
import model.Order.Order;
import model.User.Buyer;
import model.User.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MyProfile {
    User user = null;

    @FXML
    Rectangle borderProfileImageRectangle;

    @FXML
    ImageView myProfileImage;
    @FXML
    Text emailText, nameText, surnameText, usernameText, addressText, phoneText, saveYourProfileText, modifyYourProfileText, completeYourProfileText, errorText;

    @FXML
    TextField nameTextField, surnameTextField, streetTextField, cityTextField, countryTextField, zipTextField, phoneTextField;

    @FXML
    ImageView homepageImageView;

    @FXML
    TableView<Order> orderTableView = new TableView<>();
    TableColumn<Order, String> orderNumber;
    TableColumn<Order, Integer> orderTotalQuantity;
    TableColumn<Order, String> orderTotalPrice;
    TableColumn<Order, Timestamp> orderTimeStamp;
    TableColumn<Order, Timestamp> orderStatus;

    @FXML
    Text numberOfOrdersText;

    @FXML
    protected void onHomepageImageClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root = loader.load();
        Homepage homepage = loader.getController();
        homepage.passParams(user);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) homepageImageView.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void saveProfile() {
        if (nameTextField.getText().isBlank() || surnameTextField.getText().isBlank() ||
                streetTextField.getText().isBlank() || cityTextField.getText().isBlank() ||
                countryTextField.getText().isBlank() || zipTextField.getText().isBlank() ||
                phoneTextField.getText().isBlank()) {
            errorText.setVisible(true);
        }
        else{
            if (UserHandler.updateRecord(user, nameTextField.getText(), surnameTextField.getText(),
                    streetTextField.getText(), cityTextField.getText(), countryTextField.getText(),
                    zipTextField.getText(), phoneTextField.getText(), ((Buyer) user).getProfileImagepath())) {
                System.out.println("update effettuato");
            }
        }
    }

    @FXML
    public void enterProfileImage() {
        borderProfileImageRectangle.setVisible(true);
    }
    @FXML
    public void exitProfileImage(){
        borderProfileImageRectangle.setVisible(false);
    }

    @FXML
    public void onClickProfileImageView() throws IOException {
        InputStream stream = new FileInputStream(Constants.PROFILE_IMAGE_BLANK);
        Image profileImage = new Image(stream, 200, 200, false, false);

        myProfileImage.setImage(profileImage);
        String newFileName = user.getUsername() + ".jpg";
        String newFilepath = Constants.PROFILE_IMAGES_PATH + newFileName;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG);
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            if (FileElaboration.copyAndReplaceFile(file, newFilepath)) {
                stream = new FileInputStream(Constants.PROFILE_IMAGES_PATH + ((Buyer) user).getProfileImagepath());
                profileImage = new Image(stream, 200, 200, false, false);
                myProfileImage.setImage(profileImage);
                stream.close();
            }
            UserHandler.updateLogoImagePath(user, newFileName);
        }
    }

    @FXML
    public void initialize() {
        orderTableView.setEditable(true);

        orderNumber = new TableColumn<>("Order No");
        orderNumber.setMinWidth(10);
        orderNumber.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        orderTotalQuantity = new TableColumn<>("Quantity");
        orderTotalQuantity.setMinWidth(10);
        orderTotalQuantity.setCellValueFactory(new PropertyValueFactory<>("orderTotalQuantity"));

        orderTotalPrice = new TableColumn<>("Price");
        orderTotalPrice.setMinWidth(30);
        orderTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        orderTimeStamp = new TableColumn<>("Order date");
        orderTimeStamp.setMinWidth(10);
        orderTimeStamp.setCellValueFactory(new PropertyValueFactory<>("orderTimestamp"));

        orderStatus = new TableColumn<>("Status");
        orderStatus.setMinWidth(10);
        orderStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        orderTableView.getColumns().addAll(orderNumber, orderTotalQuantity, orderTotalPrice, orderTimeStamp, orderStatus);

    }

    public void fillView() {
        int numberOfOrders = 0;
        orderTableView.getItems().clear();
        ObservableList<Order> orderObservableList = FXCollections.observableArrayList();
        ArrayList<Order> orderArrayList = OrderHandler.findOrdersInfoFromUser(user);
        if (orderArrayList != null) {
            for (Order o : orderArrayList) {
                orderObservableList.add(o);
                numberOfOrders++;
            }
            orderTableView.setItems(orderObservableList);
        }
        else {
            System.out.println("no result");
        }
        numberOfOrdersText.setText(String.valueOf(numberOfOrders));
    }


    public void passParams(User user){
        this.user = user;
        fillView();
        usernameText.setText(user.getUsername());
        emailText.setText(user.getEmail());
        nameTextField.setText(user.getName());
        surnameTextField.setText(user.getSurname());
        streetTextField.setText(((Buyer) user).getBillingStreet());
        cityTextField.setText(((Buyer) user).getBillingCity());
        countryTextField.setText(((Buyer) user).getBillingCountry());
        zipTextField.setText(((Buyer) user).getBillingZip());
        phoneTextField.setText(((Buyer) user).getPhone());
    }

    @FXML
    public void onClickOrderTableView() throws Exception {
        Order order = orderTableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("specificOrder.fxml"));
        Parent root = loader.load();
        SpecificOrder specificOrder = loader.getController();

        specificOrder.passParams(user, order);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) orderTableView.getScene().getWindow();
        stage.close();
    }
}
