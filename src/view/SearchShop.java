package view;

import control.ShopHandler;
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
import java.util.ArrayList;

public class SearchShop  {

    User u = null;
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
    Label selected;
    @FXML
    Label labelHi;
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

    @FXML
    protected void onListViewItemClick() throws IOException {

        Shop shop = tableView.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (shop != null){
            System.out.println(shop);
            /*
            if (shop.getType == "ferramenta") {
                carica ferramentaview.fxml
            }
            //todo: non chiudere la finestra della searchShop.fxml quando si apre la shopView.fxml
            */
            FXMLLoader loader = new FXMLLoader(getClass().getResource("shopView.fxml"));
            Parent root = loader.load();
            ShopView shopView = loader.getController();
            shopView.passUser(u);
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
        requestTextField.setText(((Buyer) u).getBillingAddress());
        param3.setSelected(true);
    }

    @FXML
    protected void onHomepageImageClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root = loader.load();
        Homepage homepage = loader.getController();
        homepage.passUser(u);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) homepageImageView.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onSearchButtonClick() {
        tableView.getItems().clear();
        RadioButton rb = (RadioButton) findValue.getSelectedToggle();
        String radioButtonValue = rb.getText();
        String paramForSearch = requestTextField.getText();
        boolean checkboxValue = openNow.isSelected();
        System.out.println(checkboxValue);
        ObservableList<Shop> observableListShops = FXCollections.observableArrayList();
        ArrayList<Shop> shopArrayList = ShopHandler.findShopBy(paramForSearch, radioButtonValue, checkboxValue);

        if (shopArrayList != null) {
            for (Shop s : shopArrayList) {
                observableListShops.add(s);
            }
            tableView.setItems(observableListShops);
        }
        else {
            System.out.println("no result");
        }
        if (radioButtonValue != Constants.NEARBY) {
            distanceColumn.setVisible(false);
        } else {
            distanceColumn.setVisible(true);
        }
    }

    public void passUser(User user) {
        u = user;
        labelHi.setText(user.getUsername());
    }

    @FXML
    public void initialize(){
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
        nameColumn.setMinWidth(10);
        openingColumn.setCellValueFactory(new PropertyValueFactory<>("openingTime"));

        closingColumn = new TableColumn<>("Closing");
        nameColumn.setMinWidth(10);
        closingColumn.setCellValueFactory(new PropertyValueFactory<>("closingTime"));

        distanceColumn = new TableColumn<>("Distance");
        distanceColumn.setMinWidth(10);
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));

        tableView.getColumns().addAll(nameColumn, addressColumn, cityColumn, openingColumn, closingColumn, distanceColumn);

        distanceColumn.setVisible(false);
    }
}