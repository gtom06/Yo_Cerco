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
import model.User.User;

import java.io.IOException;
import java.util.ArrayList;

public class FavoriteShops {
    User u = null;
    @FXML
    private ImageView homepageImageView;
    @FXML
    Label param1;
    @FXML
    Label labelHi;
    @FXML
    TableView<Shop> tableView = new TableView<>();
    TableColumn<Shop, String> addressColumn;
    TableColumn<Shop, String> cityColumn;
    TableColumn<Shop, String> nameColumn;
    TableColumn<Shop, String> openingColumn;
    TableColumn<Shop, String> closingColumn;

    @FXML
    protected void onListViewItemClick() throws IOException {
        //todo: todo
        Shop shop = tableView.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (shop != null) {
            System.out.println(shop);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("shopView.fxml"));
            Parent root = loader.load();
            ShopView shopView = loader.getController();
            shopView.passUser(u);
            shopView.passShop(shop);
            //shopView.passDepartments(departments);
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
            newStage.setResizable(false);
            Stage stage = (Stage) tableView.getScene().getWindow();
            stage.close();
        }
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

    public void passUser(User user) {
        u = user;
        labelHi.setText(user.getUsername());
        fillTableView();
    }

    @FXML
    public void initialize() {
        param1.setText(Constants.MY_FAVORITE_SHOPS_CAPSLOCK);
        tableView.setEditable(true);

        addressColumn = new TableColumn<>("Address");
        addressColumn.setMinWidth(10);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        cityColumn = new TableColumn<>("City");
        cityColumn.setMinWidth(10);
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));

        nameColumn = new TableColumn<>("ShopName");
        nameColumn.setMinWidth(30);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("shopName"));

        openingColumn = new TableColumn<>("Opening");
        nameColumn.setMinWidth(10);
        openingColumn.setCellValueFactory(new PropertyValueFactory<>("openingTime"));

        closingColumn = new TableColumn<>("Closing");
        nameColumn.setMinWidth(10);
        closingColumn.setCellValueFactory(new PropertyValueFactory<>("closingTime"));

        tableView.getColumns().addAll(nameColumn, addressColumn, cityColumn, openingColumn, closingColumn);
    }

    public void fillTableView() {
        tableView.getItems().clear();
        ObservableList<Shop> observableListShops = FXCollections.observableArrayList();
        ArrayList<Shop> shopArrayList = ShopHandler.findFavouriteShopsFromUser(u);
        if (shopArrayList != null) {
            for (Shop s : shopArrayList) {
                observableListShops.add(s);
            }
            tableView.setItems(observableListShops);
        }
        else {
            System.out.println("no result");
        }
    }
}
