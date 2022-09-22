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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import constants.Constants;
import model.order.Order;
import model.user.Buyer;
import model.user.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyProfile {
    User user2MP = null;

    @FXML
    protected ImageView myProfileImage2MP;
    @FXML
    protected Text emailText2MP;
    @FXML
    protected Text usernameText2MP;
    @FXML
    protected Text errorText2MP;
    @FXML
    protected TextField nameTextField2MP;
    @FXML
    protected TextField surnameTextField2MP;
    @FXML
    protected TextField streetTextField2MP;
    @FXML
    protected TextField cityTextField2MP;
    @FXML
    protected TextField countryTextField2MP;
    @FXML
    protected TextField zipTextField2MP;
    @FXML
    protected TextField phoneTextField2MP;
    @FXML
    protected ImageView homepageImageView2MP;
    @FXML
    protected TableView<Order> orderTableView2MP = new TableView<>();
    protected TableColumn<Order, String> orderNumber2MP;
    protected TableColumn<Order, Integer> orderTotalQuantity2MP;
    protected TableColumn<Order, String> orderTotalPrice2MP;
    protected TableColumn<Order, Timestamp> orderTimeStamp2MP;
    protected TableColumn<Order, Timestamp> orderStatus2MP;
    @FXML
    protected Text numberOfOrdersText2MP;

    static final Logger logger = Logger.getLogger(MyProfile.class.getName());

    @FXML
    protected void onHomepageImageClick2MP() throws IOException, AddressException {
        FXMLLoader loader2MP = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root2MP = loader2MP.load();
        Homepage homepage2MP = loader2MP.getController();
        homepage2MP.passParams(user2MP);
        Stage newStage2MP = new Stage();
        newStage2MP.setScene(new Scene(root2MP));
        newStage2MP.show();
        newStage2MP.setResizable(false);
        Stage stage2MP = (Stage) homepageImageView2MP.getScene().getWindow();
        stage2MP.close();
    }

    @FXML
    protected void saveProfile2MP() {
        if (nameTextField2MP.getText().isBlank() || surnameTextField2MP.getText().isBlank() ||
                streetTextField2MP.getText().isBlank() || cityTextField2MP.getText().isBlank() ||
                countryTextField2MP.getText().isBlank() || zipTextField2MP.getText().isBlank() ||
                phoneTextField2MP.getText().isBlank()) {
            errorText2MP.setVisible(true);
        }
        else{
            if (UserHandler.updateRecord2(user2MP, Arrays.asList(nameTextField2MP.getText(), surnameTextField2MP.getText(),
                    streetTextField2MP.getText(), cityTextField2MP.getText(), countryTextField2MP.getText(),
                    zipTextField2MP.getText(), phoneTextField2MP.getText(), ((Buyer) user2MP).getProfileImagepath()))) {
                logger.log(Level.INFO, "update ok");
            }
        }
    }

    @FXML
    protected void onClickProfileImageView2MP() throws IOException, FileElaborationException {
        InputStream stream2MP = new FileInputStream(Constants.PROFILE_IMAGE_BLANK);
        Image profileImage2MP = new Image(stream2MP, 200, 200, false, false);

        myProfileImage2MP.setImage(profileImage2MP);
        String newFileName2MP = user2MP.getUsername() + ".jpg";
        String newFilepath2MP = Constants.PROFILE_IMAGES_PATH + newFileName2MP;
        FileChooser fileChooser2MP = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG2MP = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        fileChooser2MP.getExtensionFilters().addAll(extFilterJPG2MP);
        File file2MP = fileChooser2MP.showOpenDialog(null);

        if (file2MP != null) {
            if (FileElaboration.copyAndReplaceFile(file2MP, newFilepath2MP)) {
                stream2MP = new FileInputStream(Constants.PROFILE_IMAGES_PATH + ((Buyer) user2MP).getProfileImagepath());
                profileImage2MP = new Image(stream2MP, 200, 200, false, false);
                myProfileImage2MP.setImage(profileImage2MP);
                stream2MP.close();
            }
            UserHandler.updateLogoImagePath(user2MP, newFileName2MP);
        }
    }

    @FXML
    public void initialize() {
        orderTableView2MP.setEditable(true);

        orderNumber2MP = new TableColumn<>("Order No");
        orderNumber2MP.setMinWidth(10);
        orderNumber2MP.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        orderTotalQuantity2MP = new TableColumn<>("Quantity");
        orderTotalQuantity2MP.setMinWidth(10);
        orderTotalQuantity2MP.setCellValueFactory(new PropertyValueFactory<>("orderTotalQuantity"));

        orderTotalPrice2MP = new TableColumn<>("Price");
        orderTotalPrice2MP.setMinWidth(30);
        orderTotalPrice2MP.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        orderTimeStamp2MP = new TableColumn<>("Order date");
        orderTimeStamp2MP.setMinWidth(10);
        orderTimeStamp2MP.setCellValueFactory(new PropertyValueFactory<>("orderTimestamp"));

        orderStatus2MP = new TableColumn<>("Status");
        orderStatus2MP.setMinWidth(10);
        orderStatus2MP.setCellValueFactory(new PropertyValueFactory<>("status"));

        orderTableView2MP.getColumns().addAll(
                orderNumber2MP, orderTotalQuantity2MP, orderTotalPrice2MP, orderTimeStamp2MP, orderStatus2MP);

    }

    protected void fillView2MP() {
        int numberOfOrders2MP = 0;
        orderTableView2MP.getItems().clear();
        ObservableList<Order> orderObservableList2MP = FXCollections.observableArrayList();
        List<Order> orderArrayList2MP = OrderHandler.findOrdersInfoFromUser(user2MP);
        if (orderArrayList2MP != null) {
            orderObservableList2MP.addAll(orderArrayList2MP);
            orderTableView2MP.setItems(orderObservableList2MP);
            numberOfOrders2MP = orderArrayList2MP.size();
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
        numberOfOrdersText2MP.setText(String.valueOf(numberOfOrders2MP));
    }


    public void passParams(User user2MP){
        this.user2MP = user2MP;
        fillView2MP();
        usernameText2MP.setText(user2MP.getUsername());
        emailText2MP.setText(user2MP.getEmail());
        nameTextField2MP.setText(user2MP.getName());
        surnameTextField2MP.setText(user2MP.getSurname());
        streetTextField2MP.setText(((Buyer) user2MP).getBillingStreet());
        cityTextField2MP.setText(((Buyer) user2MP).getBillingCity());
        countryTextField2MP.setText(((Buyer) user2MP).getBillingCountry());
        zipTextField2MP.setText(((Buyer) user2MP).getBillingZip());
        phoneTextField2MP.setText(((Buyer) user2MP).getPhone());
    }

    @FXML
    protected void onClickOrderTableView2MP() throws Exception {
        Order order2MP = orderTableView2MP.getSelectionModel().getSelectedItem();
        FXMLLoader loader2MP = new FXMLLoader(getClass().getResource("specificOrder.fxml"));
        Parent root2MP = loader2MP.load();
        SpecificOrder specificOrder2MP = loader2MP.getController();

        specificOrder2MP.passParams(user2MP, order2MP);
        Stage newStage2MP = new Stage();
        newStage2MP.setScene(new Scene(root2MP));
        newStage2MP.show();
        newStage2MP.setResizable(false);
        Stage stage2MP = (Stage) orderTableView2MP.getScene().getWindow();
        stage2MP.close();
    }
}
