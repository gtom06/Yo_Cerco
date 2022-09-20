package view.view2;

import control.ProductHandler;
import control.ShopHandler;
import exceptions.AddressException;
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
import javafx.stage.Stage;
import model.Constants;
import model.product.SimpleProduct;
import model.shop.Shop;
import model.shop.Shop2;
import model.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Favorite {
    User user = null;
    @FXML
    protected ImageView homepageImageView;
    @FXML
    protected TableView<Shop2> shopTableView = new TableView<>();
    protected TableColumn<Shop2, String> addressColumn;
    protected TableColumn<Shop2, String> cityColumn;
    protected TableColumn<Shop2, String> nameColumn;
    protected TableColumn<Shop2, String> openingColumn;
    protected TableColumn<Shop2, String> closingColumn;

    @FXML
    protected TableView<SimpleProduct> simpleProductTableView = new TableView<>();
    protected TableColumn<SimpleProduct, String> brandColumn;
    protected TableColumn<SimpleProduct, Integer> sizeColumn;
    protected TableColumn<SimpleProduct, String> nameProductColumn;
    protected TableColumn<SimpleProduct, Double> unitOfMeasureColumn;

    static final Logger logger = Logger.getLogger(Favorite.class.getName());

    @FXML
    protected void onTableViewShopClick() throws IOException {
        Shop2 shop = shopTableView.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (shop != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("shopView.fxml"));
            Parent root = loader.load();
            ShopView shopView = loader.getController();
            shopView.passParams(user, shop);
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
            newStage.setResizable(false);
            Stage stage = (Stage) shopTableView.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    protected void onTableViewProductClick() throws IOException {
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
            Stage stage = (Stage) shopTableView.getScene().getWindow();
            stage.close();
        }
    }


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

    public void passParams(User user) {
        this.user = user;
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
        ObservableList<Shop2> observableListShops = FXCollections.observableArrayList();
        ArrayList<Shop2> shopArrayList = (ArrayList<Shop2>) ShopHandler.findFavoriteShopsFromUser(user);
        if (shopArrayList != null) {
            for (Shop2 s : shopArrayList) {
                observableListShops.add(s);
            }
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
            for (SimpleProduct sp : simpleProductArrayList) {
                simpleProductObservableList.add(sp);
            }
            simpleProductTableView.setItems(simpleProductObservableList);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }
}
