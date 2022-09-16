package view.view1;

import control.ShopHandler;
import exceptions.AddressException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Constants;
import model.Shop.Shop;
import model.User.Buyer;
import model.User.User;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchShop  {

    User user = null;
    @FXML
    private ImageView homepageImageView, userPositionImageView;
    @FXML
    ToggleGroup findValue;
    @FXML
    RadioButton param1;
    @FXML
    RadioButton param2;
    @FXML
    RadioButton param3;
    @FXML
    TextField requestTextField;
    @FXML
    Label labelHi;
    @FXML
    ChoiceBox choiceBoxTypeShop;
    @FXML
    TableView<Shop> tableView = new TableView<>();
    TableColumn<Shop, String> addressColumn;
    TableColumn<Shop, String> cityColumn;
    TableColumn<Shop, String> nameColumn;
    TableColumn<Shop, String> openingColumn;
    TableColumn<Shop, String> closingColumn;
    TableColumn<Shop, Double> distanceColumn;
    @FXML
    CheckBox openNow;
    static final Logger logger = Logger.getLogger(SearchShop.class.getName());

    @FXML
    protected void onTableViewItemClick() throws IOException {

        Shop shop = tableView.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (shop != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("shopView.fxml"));
            Parent root = loader.load();
            ShopView shopView = loader.getController();
            shopView.passUser(user);
            shopView.passShop(shop);
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
            newStage.setResizable(false);
            Stage stage = (Stage) tableView.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    protected void onUserPositionImageViewClick() {
        if (((Buyer) user).getBillingStreet() != null) {
            requestTextField.setText(((Buyer) user).getBillingAddress());
            param3.setSelected(true);
        } else {
            requestTextField.setText("");
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
    protected void onSearchButtonClick() throws AddressException {
        tableView.getItems().clear();
        List<Shop> shopArrayList = null;
        String type = choiceBoxTypeShop.getSelectionModel().getSelectedItem().toString();


        if (requestTextField.getText() == null || requestTextField.getText().length() == 0 || requestTextField.getText().length() > 50) {
            distanceColumn.setVisible(true);
            shopArrayList = ShopHandler.findShopNearbyWithParams(requestTextField.getText(), openNow.isSelected(), type);
        }

        if (((RadioButton) findValue.getSelectedToggle()).getText() == Constants.NEARBY) {
            distanceColumn.setVisible(true);
            shopArrayList = ShopHandler.findShopNearbyWithParams(requestTextField.getText(), openNow.isSelected(), type);
        }
        else if (((RadioButton) findValue.getSelectedToggle()).getText() == Constants.BY_CITY) {
            distanceColumn.setVisible(false);
            shopArrayList = ShopHandler.findShopByCityWithParams(requestTextField.getText(), openNow.isSelected(), type);
        }
        else if (((RadioButton) findValue.getSelectedToggle()).getText() == Constants.BY_NAME){
            distanceColumn.setVisible(false);
            shopArrayList = ShopHandler.findShopByNameWithParams(requestTextField.getText(), openNow.isSelected(), type);
        }
        if (shopArrayList != null && shopArrayList.size() != 0) {
            ObservableList<Shop> observableListShops = FXCollections.observableArrayList(shopArrayList);
            tableView.setItems(observableListShops);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    public void passUser(User user) {
        this.user = user;
        labelHi.setText(user.getUsername());


    }

    @FXML
    public void initialize(){
        ObservableList<String> observableListTypeShop = FXCollections.observableArrayList(Constants.SHOP_TYPE);
        choiceBoxTypeShop.setItems(FXCollections.observableArrayList(observableListTypeShop));
        choiceBoxTypeShop.setValue(observableListTypeShop.get(0));

        param1.setText(Constants.BY_CITY);
        param2.setText(Constants.BY_NAME);
        param3.setText(Constants.NEARBY);

        tableView.setEditable(true);
        addressColumn = new TableColumn<>("Address");
        addressColumn.setMinWidth(200);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        cityColumn = new TableColumn<>("City");
        cityColumn.setMinWidth(50);
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));

        nameColumn = new TableColumn<>("Shop Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("shopName"));

        openingColumn = new TableColumn<>("Opening");
        openingColumn.setMinWidth(10);
        openingColumn.setCellValueFactory(new PropertyValueFactory<>("openingTime"));

        closingColumn = new TableColumn<>("Closing");
        closingColumn.setMinWidth(10);
        closingColumn.setCellValueFactory(new PropertyValueFactory<>("closingTime"));

        distanceColumn = new TableColumn<>("Distance");
        distanceColumn.setMinWidth(10);
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));

        tableView.getColumns().addAll(nameColumn, addressColumn, cityColumn, openingColumn, closingColumn, distanceColumn);

        distanceColumn.setVisible(false);
    }
}