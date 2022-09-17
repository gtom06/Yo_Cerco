package view.view2;

import control.FileElaboration;
import control.OrderHandler;
import control.UserHandler;
import exceptions.AddressException;
import exceptions.FileElaborationException;
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
import model.order.Order;
import model.user.Buyer;
import model.user.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyProfile {
    User user = null;
    
    @FXML
    protected ImageView myProfileImage;
    @FXML
    protected Text emailText;
    @FXML
    protected Text usernameText;
    @FXML
    protected Text errorText;
    @FXML
    protected TextField nameTextField;
    @FXML
    protected TextField surnameTextField;
    @FXML
    protected TextField streetTextField;
    @FXML
    protected TextField cityTextField;
    @FXML
    protected TextField countryTextField;
    @FXML
    protected TextField zipTextField;
    @FXML
    protected TextField phoneTextField;
    @FXML
    protected ImageView homepageImageView;
    @FXML
    protected TableView<Order> orderTableView = new TableView<>();
    protected TableColumn<Order, String> orderNumber;
    protected TableColumn<Order, Integer> orderTotalQuantity;
    protected TableColumn<Order, String> orderTotalPrice;
    protected TableColumn<Order, Timestamp> orderTimeStamp;
    protected TableColumn<Order, Timestamp> orderStatus;
    @FXML
    protected Text numberOfOrdersText;

    static final Logger logger = Logger.getLogger(MyProfile.class.getName());

    @FXML
    protected void onHomepageImageClick() throws IOException, AddressException {
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
    protected void saveProfile() {
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
                logger.log(Level.INFO, "update ok");
            }
        }
    }

    @FXML
    protected void onClickProfileImageView() throws IOException, FileElaborationException {
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

    protected void fillView() {
        int numberOfOrders = 0;
        orderTableView.getItems().clear();
        ObservableList<Order> orderObservableList = FXCollections.observableArrayList();
        ArrayList<Order> orderArrayList = OrderHandler.findOrdersInfoFromUser(user);
        if (orderArrayList != null) {
            orderObservableList.addAll(orderArrayList);
            orderTableView.setItems(orderObservableList);
            numberOfOrders = orderArrayList.size();
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
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
    protected void onClickOrderTableView() throws Exception {
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
