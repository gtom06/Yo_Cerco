package view.view1;

import control.ProductHandler;
import control.ShopHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import constants.Constants;
import model.product.SimpleProduct;
import model.shop.Shop;
import model.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Favorite {
    User user = null;
    @FXML
    protected ImageView homepageImageView;
    @FXML
    protected Text textHi;
    @FXML
    protected TableView<Shop> shopTableView = new TableView<>();
    protected TableColumn<Shop, String> addressColumn;
    protected TableColumn<Shop, String> cityColumn;
    protected TableColumn<Shop, String> nameColumn;
    protected TableColumn<Shop, String> openingColumn;
    protected TableColumn<Shop, String> closingColumn;

    @FXML
    protected TableView<SimpleProduct> simpleProductTableView = new TableView<>();
    protected TableColumn<SimpleProduct, String> brandColumn;
    protected TableColumn<SimpleProduct, Integer> sizeColumn;
    protected TableColumn<SimpleProduct, String> nameProductColumn;
    protected TableColumn<SimpleProduct, Double> unitOfMeasureColumn;
    static final Logger logger = Logger.getLogger(Favorite.class.getName());

    @FXML
    protected void onShopTableViewClick() throws IOException {

        Shop shop = shopTableView.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (shop != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("shopView.fxml"));
            Parent root = loader.load();
            ShopView shopView = loader.getController();
            shopView.passUser(user);
            shopView.passShop(shop);
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
            newStage.setResizable(false);
            Stage stage = (Stage) shopTableView.getScene().getWindow();
            stage.close();
        }
    }


    @FXML
    protected void onProductTableViewClick() throws IOException {

        SimpleProduct product = simpleProductTableView.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (product != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("generalProductView.fxml"));
            Parent root = loader.load();
            GeneralProductView generalProductView = loader.getController();
            generalProductView.passParams(user, product);
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
            newStage.setResizable(false);
            Stage stage = (Stage) simpleProductTableView.getScene().getWindow();
            stage.close();
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

    public void passUser(User user) {
        this.user = user;
        textHi.setText(user.getUsername());
        fillShopTableView();
        fillSimpleProductTableView();
    }

    @FXML
    public void initialize() {
        shopTableView.setEditable(true);
        //897
        nameColumn = new TableColumn<>("ShopName");
        addressColumn = new TableColumn<>("Address");
        cityColumn = new TableColumn<>("City");
        openingColumn = new TableColumn<>("Opening time");
        closingColumn = new TableColumn<>("Closing time");

        nameColumn.setMinWidth(250);
        addressColumn.setMinWidth(377);
        cityColumn.setMinWidth(100);
        nameColumn.setMinWidth(10);
        nameColumn.setMinWidth(10);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("shopName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        openingColumn.setCellValueFactory(new PropertyValueFactory<>("openingTime"));
        closingColumn.setCellValueFactory(new PropertyValueFactory<>("closingTime"));

        shopTableView.getColumns().addAll(nameColumn, addressColumn, cityColumn, openingColumn, closingColumn);

        simpleProductTableView.setEditable(true);
        simpleProductTableView.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        //897
        nameProductColumn = new TableColumn<>("Name");
        brandColumn = new TableColumn<>("Brand");
        sizeColumn = new TableColumn<>("Size");
        unitOfMeasureColumn = new TableColumn<>("Unit of measure");

        nameProductColumn.setMinWidth(477);
        brandColumn.setMinWidth(400);
        sizeColumn.setMinWidth(10);
        unitOfMeasureColumn.setMinWidth(10);

        nameProductColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        unitOfMeasureColumn.setCellValueFactory(new PropertyValueFactory<>("unitOfMeasure"));

        simpleProductTableView.getColumns().addAll(nameProductColumn, brandColumn, sizeColumn, unitOfMeasureColumn);
    }

    protected void fillShopTableView() {
        shopTableView.getItems().clear();
        ObservableList<Shop> observableListShops = FXCollections.observableArrayList();
        List<Shop> shopArrayList = ShopHandler.findFavoriteShopsFromUser(user);
        if (shopArrayList != null) {
            observableListShops.addAll(shopArrayList);
            shopTableView.setItems(observableListShops);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    protected void fillSimpleProductTableView() {
        simpleProductTableView.getItems().clear();
        ObservableList<SimpleProduct> simpleProductObservableList = FXCollections.observableArrayList();
        ArrayList<SimpleProduct> simpleProductArrayList = (ArrayList<SimpleProduct>) ProductHandler.findFavoriteSimpleProductFromUser(user);
        if (simpleProductArrayList != null) {
            simpleProductObservableList.addAll(simpleProductArrayList);
            simpleProductTableView.setItems(simpleProductObservableList);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }
}
