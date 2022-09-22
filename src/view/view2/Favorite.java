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
import constants.Constants;
import model.product.SimpleProduct;
import model.shop.Shop;
import model.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Favorite {
    User user2F = null;
    @FXML
    protected ImageView homepageImageView2F;
    @FXML
    protected TableView<Shop> shopTableView2F = new TableView<>();
    protected TableColumn<Shop, String> addressColumn2F;
    protected TableColumn<Shop, String> cityColumn2F;
    protected TableColumn<Shop, String> nameColumn2F;
    protected TableColumn<Shop, String> openingColumn2F;
    protected TableColumn<Shop, String> closingColumn2F;

    @FXML
    protected TableView<SimpleProduct> simpleProductTableView2F = new TableView<>();
    protected TableColumn<SimpleProduct, String> brandColumn2F;
    protected TableColumn<SimpleProduct, Integer> sizeColumn2F;
    protected TableColumn<SimpleProduct, String> nameProductColumn2F;
    protected TableColumn<SimpleProduct, Double> unitOfMeasureColumn2F;

    static final Logger logger = Logger.getLogger(Favorite.class.getName());

    @FXML
    protected void onTableViewShopClick2F() throws IOException {
        Shop shop2F = shopTableView2F.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (shop2F != null) {
            FXMLLoader loader2F = new FXMLLoader(getClass().getResource("shopView.fxml"));
            Parent root2F = loader2F.load();
            ShopView shopView2F = loader2F.getController();
            shopView2F.passParams(user2F, shop2F);
            Stage newStage2F = new Stage();
            newStage2F.setScene(new Scene(root2F));
            newStage2F.show();
            newStage2F.setResizable(false);
            Stage stage2F = (Stage) shopTableView2F.getScene().getWindow();
            stage2F.close();
        }
    }

    @FXML
    protected void onTableViewProductClick2F() throws IOException {
        SimpleProduct product2F = simpleProductTableView2F.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (product2F != null) {
            FXMLLoader loader2F = new FXMLLoader(getClass().getResource("generalProductView.fxml"));
            Parent root2F = loader2F.load();
            GeneralProductView generalProductView2F = loader2F.getController();
            generalProductView2F.passParams(user2F, product2F);
            Stage newStage2F = new Stage();
            newStage2F.setScene(new Scene(root2F));
            newStage2F.show();
            newStage2F.setResizable(false);
            Stage stage2F = (Stage) shopTableView2F.getScene().getWindow();
            stage2F.close();
        }
    }


    @FXML
    protected void onHomepageImageClick2F() throws IOException, AddressException {
        FXMLLoader loader2F = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root2F = loader2F.load();
        Homepage homepage2F = loader2F.getController();
        homepage2F.passParams(user2F);
        Stage newStage2F = new Stage();
        newStage2F.setScene(new Scene(root2F));
        newStage2F.show();
        newStage2F.setResizable(false);
        Stage stage2F = (Stage) homepageImageView2F.getScene().getWindow();
        stage2F.close();
    }

    public void passParams(User user2F) {
        this.user2F = user2F;
        fillShopTableView2F();
        fillSimpleProductTableView2F();
    }

    @FXML
    public void initialize() {
        shopTableView2F.setEditable(true);
        //897
        nameColumn2F = new TableColumn<>("ShopName");
        addressColumn2F = new TableColumn<>("Address");
        cityColumn2F = new TableColumn<>("City");
        openingColumn2F = new TableColumn<>("Opening time");
        closingColumn2F = new TableColumn<>("Closing time");

        nameColumn2F.setMinWidth(250);
        addressColumn2F.setMinWidth(377);
        cityColumn2F.setMinWidth(100);
        nameColumn2F.setMinWidth(10);
        nameColumn2F.setMinWidth(10);

        nameColumn2F.setCellValueFactory(new PropertyValueFactory<>("shopName"));
        addressColumn2F.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityColumn2F.setCellValueFactory(new PropertyValueFactory<>("city"));
        openingColumn2F.setCellValueFactory(new PropertyValueFactory<>("openingTime"));
        closingColumn2F.setCellValueFactory(new PropertyValueFactory<>("closingTime"));

        shopTableView2F.getColumns().addAll(
                nameColumn2F, addressColumn2F, cityColumn2F, openingColumn2F, closingColumn2F);

        simpleProductTableView2F.setEditable(true);
        simpleProductTableView2F.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        //897
        nameProductColumn2F = new TableColumn<>("Name");
        brandColumn2F = new TableColumn<>("Brand");
        sizeColumn2F = new TableColumn<>("Size");
        unitOfMeasureColumn2F = new TableColumn<>("Unit of measure");

        nameProductColumn2F.setMinWidth(477);
        brandColumn2F.setMinWidth(400);
        sizeColumn2F.setMinWidth(10);
        unitOfMeasureColumn2F.setMinWidth(10);

        nameProductColumn2F.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandColumn2F.setCellValueFactory(new PropertyValueFactory<>("brand"));
        sizeColumn2F.setCellValueFactory(new PropertyValueFactory<>("size"));
        unitOfMeasureColumn2F.setCellValueFactory(new PropertyValueFactory<>("unitOfMeasure"));

        simpleProductTableView2F.getColumns().addAll(
                nameProductColumn2F, brandColumn2F, sizeColumn2F, unitOfMeasureColumn2F);
    }

    protected void fillShopTableView2F() {
        shopTableView2F.getItems().clear();
        ObservableList<Shop> observableListShops2F = FXCollections.observableArrayList();
        ArrayList<Shop> shopArrayList2F = (ArrayList<Shop>) ShopHandler.findFavoriteShopsFromUser(user2F);
        if (shopArrayList2F != null) {
            for (Shop s : shopArrayList2F) {
                observableListShops2F.add(s);
            }
            shopTableView2F.setItems(observableListShops2F);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    protected void fillSimpleProductTableView2F() {
        simpleProductTableView2F.getItems().clear();
        ObservableList<SimpleProduct> simpleProductObservableList2F = FXCollections.observableArrayList();
        ArrayList<SimpleProduct> simpleProductArrayList2F = (ArrayList<SimpleProduct>) ProductHandler.findFavoriteSimpleProductFromUser(user2F);
        if (simpleProductArrayList2F != null) {
            for (SimpleProduct sp : simpleProductArrayList2F) {
                simpleProductObservableList2F.add(sp);
            }
            simpleProductTableView2F.setItems(simpleProductObservableList2F);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }
}
