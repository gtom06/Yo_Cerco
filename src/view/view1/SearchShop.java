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
import constants.Constants;
import model.shop.Shop;
import model.user.Buyer;
import model.user.User;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchShop  {

    User user1 = null;
    @FXML
    protected ImageView homepageImageView1;
    @FXML
    protected ToggleGroup findValue1;
    @FXML
    protected RadioButton param11;
    @FXML
    protected RadioButton param21;
    @FXML
    protected RadioButton param31;
    @FXML
    protected TextField requestTextField1;
    @FXML
    protected Label labelHi1;
    @FXML
    protected ChoiceBox choiceBoxTypeShop1;
    @FXML
    protected TableView<Shop> tableView1 = new TableView<>();
    protected TableColumn<Shop, String> addressColumn1;
    protected TableColumn<Shop, String> cityColumn1;
    protected TableColumn<Shop, String> nameColumn1;
    protected TableColumn<Shop, String> openingColumn1;
    protected TableColumn<Shop, String> closingColumn1;
    protected TableColumn<Shop, Double> distanceColumn1;
    @FXML
    protected CheckBox openNow1;

    static final Logger logger = Logger.getLogger(SearchShop.class.getName());

    @FXML
    protected void onTableViewItemClick() throws IOException {

        Shop shop1 = tableView1.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (shop1 != null){
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("shopView.fxml"));
            Parent root1 = loader1.load();
            ShopView shopView1 = loader1.getController();
            shopView1.passUser(user1);
            shopView1.passShop(shop1);
            Stage newStage1 = new Stage();
            newStage1.setScene(new Scene(root1));
            newStage1.show();
            newStage1.setResizable(false);
            Stage stage1 = (Stage) tableView1.getScene().getWindow();
            stage1.close();
        }
    }

    @FXML
    protected void onUserPositionImageViewClick() {
        if (((Buyer) user1).getBillingStreet() != null) {
            requestTextField1.setText(((Buyer) user1).getBillingAddress());
            param31.setSelected(true);
        } else {
            requestTextField1.setText("");
        }
    }

    @FXML
    protected void onHomepageImageClick() throws IOException {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root1 = loader1.load();
        Homepage homepage1 = loader1.getController();
        homepage1.passUser(user1);
        Stage newStage1 = new Stage();
        newStage1.setScene(new Scene(root1));
        newStage1.show();
        newStage1.setResizable(false);
        Stage stage1 = (Stage) homepageImageView1.getScene().getWindow();
        stage1.close();
    }

    @FXML
    protected void onSearchButtonClick() throws AddressException {
        tableView1.getItems().clear();
        List<Shop> shopArrayList1 = null;
        String type1 = choiceBoxTypeShop1.getSelectionModel().getSelectedItem().toString();


        if (requestTextField1.getText() == null || requestTextField1.getText().isBlank() || requestTextField1.getText().length() > 50) {
            distanceColumn1.setVisible(true);
            shopArrayList1 = ShopHandler.findShopNearbyWithParams(requestTextField1.getText(), openNow1.isSelected(), type1);
        }

        if (((RadioButton) findValue1.getSelectedToggle()).getText() == Constants.NEARBY) {
            distanceColumn1.setVisible(true);
            shopArrayList1 = ShopHandler.findShopNearbyWithParams(requestTextField1.getText(), openNow1.isSelected(), type1);
        }
        else if (((RadioButton) findValue1.getSelectedToggle()).getText() == Constants.BY_CITY) {
            distanceColumn1.setVisible(false);
            shopArrayList1 = ShopHandler.findShopByCityWithParams(requestTextField1.getText(), openNow1.isSelected(), type1);
        }
        else if (((RadioButton) findValue1.getSelectedToggle()).getText() == Constants.BY_NAME){
            distanceColumn1.setVisible(false);
            shopArrayList1 = ShopHandler.findShopByNameWithParams(requestTextField1.getText(), openNow1.isSelected(), type1);
        }
        if (shopArrayList1 != null && shopArrayList1.size() != 0) {
            ObservableList<Shop> observableListShops1 = FXCollections.observableArrayList(shopArrayList1);
            tableView1.setItems(observableListShops1);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    public void passUser(User user1) {
        this.user1 = user1;
        labelHi1.setText(user1.getUsername());


    }

    @FXML
    public void initialize(){
        ObservableList<String> observableListTypeShop1 = FXCollections.observableArrayList(Constants.SHOP_TYPE);
        choiceBoxTypeShop1.setItems(FXCollections.observableArrayList(observableListTypeShop1));
        choiceBoxTypeShop1.setValue(observableListTypeShop1.get(0));

        param11.setText(Constants.BY_CITY);
        param21.setText(Constants.BY_NAME);
        param31.setText(Constants.NEARBY);

        tableView1.setEditable(true);
        addressColumn1 = new TableColumn<>("Address");
        addressColumn1.setMinWidth(200);
        addressColumn1.setCellValueFactory(new PropertyValueFactory<>("completeAddress"));

        cityColumn1 = new TableColumn<>("City");
        cityColumn1.setMinWidth(50);
        cityColumn1.setCellValueFactory(new PropertyValueFactory<>("city"));

        nameColumn1 = new TableColumn<>("Shop Name");
        nameColumn1.setMinWidth(200);
        nameColumn1.setCellValueFactory(new PropertyValueFactory<>("shopName"));

        openingColumn1 = new TableColumn<>("Opening");
        openingColumn1.setMinWidth(10);
        openingColumn1.setCellValueFactory(new PropertyValueFactory<>("openingTime"));

        closingColumn1 = new TableColumn<>("Closing");
        closingColumn1.setMinWidth(10);
        closingColumn1.setCellValueFactory(new PropertyValueFactory<>("closingTime"));

        distanceColumn1 = new TableColumn<>("Distance");
        distanceColumn1.setMinWidth(10);
        distanceColumn1.setCellValueFactory(new PropertyValueFactory<>("distance"));

        tableView1.getColumns().addAll(nameColumn1, addressColumn1, cityColumn1, openingColumn1, closingColumn1, distanceColumn1);

        distanceColumn1.setVisible(false);
    }
}