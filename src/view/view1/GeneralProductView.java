package view.view1;

import control.ShopHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Constants;
import model.product.SimpleProduct;
import model.shop.Shop;
import model.shop.Shop2;
import model.user.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneralProductView {
    User user ;
    List<Shop2> arrayShopList;
    @FXML
    protected ImageView productPhoto;
    @FXML
    protected ImageView homepageImageView;
    @FXML
    protected ImageView previousPageImageView;
    @FXML
    protected Text textHi;
    @FXML
    protected Text nameProd;
    @FXML
    protected Text brandProd;
    @FXML
    protected TableView<Shop2> shopsTableView = new TableView<>();
    protected TableColumn<Shop2, String> nameColumn;
    protected TableColumn<Shop2, String> addressColumn;
    protected TableColumn<Shop2, String> cityColumn;
    protected TableColumn<Shop2, String> openingColumn;
    protected TableColumn<Shop2, String> closingColumn;
    InputStream stream = null;
    static final Logger logger = Logger.getLogger(GeneralProductView.class.getName());

    @FXML
    protected void onTableViewItemClick() throws IOException {

        Shop2 shop = shopsTableView.getSelectionModel().getSelectedItem();
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
            Stage stage = (Stage) shopsTableView.getScene().getWindow();
            stage.close();
        }
    }
    public void passParams(User user, SimpleProduct simpleProduct) throws FileNotFoundException {

        this.user = user;
        textHi.setText(user.getUsername());
        brandProd.setText(simpleProduct.getBrand());
        nameProd.setText(simpleProduct.getName());

        stream = new FileInputStream(simpleProduct.getLogoImagepath());
        Image productImage = new Image(stream, 200, 200, false, false);
        productPhoto.setImage(productImage);

        ObservableList<Shop2> observableListShops = FXCollections.observableArrayList();
        arrayShopList = ShopHandler.findShopByProduct(simpleProduct);

        if (arrayShopList != null) {
            for (Shop2 s : arrayShopList) {
                observableListShops.add(s);
            }
            shopsTableView.setItems(observableListShops);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
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
    protected void previousPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchProduct.fxml"));
        Parent root = loader.load();
        SearchProduct searchProduct = loader.getController();
        searchProduct.passUser(user);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) homepageImageView.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize(){

        shopsTableView.setEditable(true);

        nameColumn = new TableColumn<>("NAME");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("shopName"));

        addressColumn = new TableColumn<>("ADDRESS");
        addressColumn.setMinWidth(200);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        cityColumn = new TableColumn<>("CITY");
        cityColumn.setMinWidth(50);
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));

        openingColumn = new TableColumn<>("OPENING TIME");
        openingColumn.setMinWidth(10);
        openingColumn.setCellValueFactory(new PropertyValueFactory<>("openingTime"));

        closingColumn = new TableColumn<>("CLOSING TIME");
        openingColumn.setMinWidth(10);
        closingColumn.setCellValueFactory(new PropertyValueFactory<>("closingTime"));

        shopsTableView.getColumns().addAll(nameColumn, addressColumn, cityColumn, openingColumn, closingColumn);
    }
}
