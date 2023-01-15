package view.view1;

import bean.ShopBean;
import bean.UserBean;
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

    UserBean user1SS = null;
    @FXML
    protected ImageView homepageImageView1SS;
    @FXML
    protected ToggleGroup findValue1SS;
    @FXML
    protected RadioButton param11SS;
    @FXML
    protected RadioButton param21SS;
    @FXML
    protected RadioButton param31SS;
    @FXML
    protected TextField requestTextField1SS;
    @FXML
    protected Label labelHi1SS;
    @FXML
    protected ChoiceBox choiceBoxTypeShop1SS;
    @FXML
    protected TableView<ShopBean> tableView1SS = new TableView<>();
    protected TableColumn<ShopBean, String> addressColumn1SS;
    protected TableColumn<ShopBean, String> cityColumn1SS;
    protected TableColumn<ShopBean, String> nameColumn1SS;
    protected TableColumn<ShopBean, String> openingColumn1SS;
    protected TableColumn<ShopBean, String> closingColumn1SS;
    protected TableColumn<ShopBean, Double> distanceColumn1SS;
    @FXML
    protected CheckBox openNow1SS;

    static final Logger logger = Logger.getLogger(SearchShop.class.getName());

    @FXML
    protected void onTableViewItemClick1SS() throws IOException {

        ShopBean shop1SS = tableView1SS.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (shop1SS != null){
            FXMLLoader loader1SS = new FXMLLoader(getClass().getResource("shopView.fxml"));
            Parent root1SS = loader1SS.load();
            ShopView shopView1SS = loader1SS.getController();
            shopView1SS.passUser(user1SS);
            shopView1SS.passShop(shop1SS);
            Stage newStage1SS = new Stage();
            newStage1SS.setScene(new Scene(root1SS));
            newStage1SS.show();
            newStage1SS.setResizable(false);
            Stage stage1SS = (Stage) tableView1SS.getScene().getWindow();
            stage1SS.close();
        }
    }

    @FXML
    protected void onUserPositionImageViewClick1SS() {
        if (user1SS.getBillingStreet() != null) {
            requestTextField1SS.setText(user1SS.getBillingAddress());
            param31SS.setSelected(true);
        } else {
            requestTextField1SS.setText("");
        }
    }

    @FXML
    protected void onHomepageImageClick1SS() throws IOException {
        FXMLLoader loader1SS = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root1SS = loader1SS.load();
        Homepage homepage1SS = loader1SS.getController();
        homepage1SS.passUser(user1SS);
        Stage newStage1SS = new Stage();
        newStage1SS.setScene(new Scene(root1SS));
        newStage1SS.show();
        newStage1SS.setResizable(false);
        Stage stage1SS = (Stage) homepageImageView1SS.getScene().getWindow();
        stage1SS.close();
    }

    @FXML
    protected void onSearchButtonClick1SS() throws AddressException {
        tableView1SS.getItems().clear();
        List<ShopBean> shopArrayList1SS = null;
        String type1SS = choiceBoxTypeShop1SS.getSelectionModel().getSelectedItem().toString();


        if (requestTextField1SS.getText() == null || requestTextField1SS.getText().isBlank() ||
                requestTextField1SS.getText().length() > 50) {
            distanceColumn1SS.setVisible(true);
            shopArrayList1SS = ShopHandler.findShopNearbyWithParams(
                    requestTextField1SS.getText(), openNow1SS.isSelected(), type1SS);
        }

        if (((RadioButton) findValue1SS.getSelectedToggle()).getText() == Constants.NEARBY) {
            distanceColumn1SS.setVisible(true);
            shopArrayList1SS = ShopHandler.findShopNearbyWithParams(
                    requestTextField1SS.getText(), openNow1SS.isSelected(), type1SS);
        }
        else if (((RadioButton) findValue1SS.getSelectedToggle()).getText() == Constants.BY_CITY) {
            distanceColumn1SS.setVisible(false);
            shopArrayList1SS = ShopHandler.findShopByCityWithParams(
                    requestTextField1SS.getText(), openNow1SS.isSelected(), type1SS);
        }
        else if (((RadioButton) findValue1SS.getSelectedToggle()).getText() == Constants.BY_NAME){
            distanceColumn1SS.setVisible(false);
            shopArrayList1SS = ShopHandler.findShopByNameWithParams(
                    requestTextField1SS.getText(), openNow1SS.isSelected(), type1SS);
        }
        if (shopArrayList1SS != null && shopArrayList1SS.size() != 0) {
            ObservableList<ShopBean> observableListShops1SS = FXCollections.observableArrayList(shopArrayList1SS);
            tableView1SS.setItems(observableListShops1SS);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    public void passUser(UserBean user1SS) {
        this.user1SS = user1SS;
        labelHi1SS.setText(user1SS.getUsername());


    }

    @FXML
    public void initialize(){
        ObservableList<String> observableListTypeShop1SS = FXCollections.observableArrayList(Constants.SHOP_TYPE);
        choiceBoxTypeShop1SS.setItems(FXCollections.observableArrayList(observableListTypeShop1SS));
        choiceBoxTypeShop1SS.setValue(observableListTypeShop1SS.get(0));

        param11SS.setText(Constants.BY_CITY);
        param21SS.setText(Constants.BY_NAME);
        param31SS.setText(Constants.NEARBY);

        tableView1SS.setEditable(true);
        addressColumn1SS = new TableColumn<>("Address");
        addressColumn1SS.setMinWidth(200);
        addressColumn1SS.setCellValueFactory(new PropertyValueFactory<>("completeAddress"));

        cityColumn1SS = new TableColumn<>("City");
        cityColumn1SS.setMinWidth(50);
        cityColumn1SS.setCellValueFactory(new PropertyValueFactory<>("city"));

        nameColumn1SS = new TableColumn<>("Shop Name");
        nameColumn1SS.setMinWidth(200);
        nameColumn1SS.setCellValueFactory(new PropertyValueFactory<>("shopName"));

        openingColumn1SS = new TableColumn<>("Opening");
        openingColumn1SS.setMinWidth(10);
        openingColumn1SS.setCellValueFactory(new PropertyValueFactory<>("openingTime"));

        closingColumn1SS = new TableColumn<>("Closing");
        closingColumn1SS.setMinWidth(10);
        closingColumn1SS.setCellValueFactory(new PropertyValueFactory<>("closingTime"));

        distanceColumn1SS = new TableColumn<>("Distance");
        distanceColumn1SS.setMinWidth(10);
        distanceColumn1SS.setCellValueFactory(new PropertyValueFactory<>("distance"));

        tableView1SS.getColumns().addAll(
                nameColumn1SS, addressColumn1SS, cityColumn1SS, openingColumn1SS, closingColumn1SS, distanceColumn1SS);

        distanceColumn1SS.setVisible(false);
    }
}