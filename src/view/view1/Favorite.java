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
    User user1 = null;
    @FXML
    protected ImageView homepageImageView1;
    @FXML
    protected Text textHi1;
    @FXML
    protected TableView<Shop> shopTableView1 = new TableView<>();
    protected TableColumn<Shop, String> addressColumn1;
    protected TableColumn<Shop, String> cityColumn1;
    protected TableColumn<Shop, String> nameColumn1;
    protected TableColumn<Shop, String> openingColumn1;
    protected TableColumn<Shop, String> closingColumn1;

    @FXML
    protected TableView<SimpleProduct> simpleProductTableView1 = new TableView<>();
    protected TableColumn<SimpleProduct, String> brandColumn1;
    protected TableColumn<SimpleProduct, Integer> sizeColumn1;
    protected TableColumn<SimpleProduct, String> nameProductColumn1;
    protected TableColumn<SimpleProduct, Double> unitOfMeasureColumn1;
    static final Logger logger = Logger.getLogger(Favorite.class.getName());

    @FXML
    protected void onShopTableViewClick() throws IOException {

        Shop shop1 = shopTableView1.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (shop1 != null) {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("shopView.fxml"));
            Parent root1 = loader1.load();
            ShopView shopView1 = loader1.getController();
            shopView1.passUser(user1);
            shopView1.passShop(shop1);
            Stage newStage1 = new Stage();
            newStage1.setScene(new Scene(root1));
            newStage1.show();
            newStage1.setResizable(false);
            Stage stage1 = (Stage) shopTableView1.getScene().getWindow();
            stage1.close();
        }
    }


    @FXML
    protected void onProductTableViewClick() throws IOException {

        SimpleProduct product1 = simpleProductTableView1.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (product1 != null) {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("generalProductView.fxml"));
            Parent root1 = loader1.load();
            GeneralProductView generalProductView1 = loader1.getController();
            generalProductView1.passParams(user1, product1);
            Stage newStage1 = new Stage();
            newStage1.setScene(new Scene(root1));
            newStage1.show();
            newStage1.setResizable(false);
            Stage stage1 = (Stage) simpleProductTableView1.getScene().getWindow();
            stage1.close();
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

    public void passUser(User user) {
        this.user1 = user;
        textHi1.setText(user.getUsername());
        fillShopTableView();
        fillSimpleProductTableView();
    }

    @FXML
    public void initialize() {
        shopTableView1.setEditable(true);
        //897
        nameColumn1 = new TableColumn<>("ShopName");
        addressColumn1 = new TableColumn<>("Address");
        cityColumn1 = new TableColumn<>("City");
        openingColumn1 = new TableColumn<>("Opening time");
        closingColumn1 = new TableColumn<>("Closing time");

        nameColumn1.setMinWidth(250);
        addressColumn1.setMinWidth(377);
        cityColumn1.setMinWidth(100);
        nameColumn1.setMinWidth(10);
        nameColumn1.setMinWidth(10);

        nameColumn1.setCellValueFactory(new PropertyValueFactory<>("shopName"));
        addressColumn1.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityColumn1.setCellValueFactory(new PropertyValueFactory<>("city"));
        openingColumn1.setCellValueFactory(new PropertyValueFactory<>("openingTime"));
        closingColumn1.setCellValueFactory(new PropertyValueFactory<>("closingTime"));

        shopTableView1.getColumns().addAll(nameColumn1, addressColumn1, cityColumn1, openingColumn1, closingColumn1);

        simpleProductTableView1.setEditable(true);
        simpleProductTableView1.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        //897
        nameProductColumn1 = new TableColumn<>("Name");
        brandColumn1 = new TableColumn<>("Brand");
        sizeColumn1 = new TableColumn<>("Size");
        unitOfMeasureColumn1 = new TableColumn<>("Unit of measure");

        nameProductColumn1.setMinWidth(477);
        brandColumn1.setMinWidth(400);
        sizeColumn1.setMinWidth(10);
        unitOfMeasureColumn1.setMinWidth(10);

        nameProductColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandColumn1.setCellValueFactory(new PropertyValueFactory<>("brand"));
        sizeColumn1.setCellValueFactory(new PropertyValueFactory<>("size"));
        unitOfMeasureColumn1.setCellValueFactory(new PropertyValueFactory<>("unitOfMeasure"));

        simpleProductTableView1.getColumns().addAll(nameProductColumn1, brandColumn1, sizeColumn1, unitOfMeasureColumn1);
    }

    protected void fillShopTableView() {
        shopTableView1.getItems().clear();
        ObservableList<Shop> observableListShops = FXCollections.observableArrayList();
        List<Shop> shopArrayList = ShopHandler.findFavoriteShopsFromUser(user1);
        if (shopArrayList != null) {
            observableListShops.addAll(shopArrayList);
            shopTableView1.setItems(observableListShops);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    protected void fillSimpleProductTableView() {
        simpleProductTableView1.getItems().clear();
        ObservableList<SimpleProduct> simpleProductObservableList = FXCollections.observableArrayList();
        ArrayList<SimpleProduct> simpleProductArrayList = (ArrayList<SimpleProduct>) ProductHandler.findFavoriteSimpleProductFromUser(user1);
        if (simpleProductArrayList != null) {
            simpleProductObservableList.addAll(simpleProductArrayList);
            simpleProductTableView1.setItems(simpleProductObservableList);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }
}
