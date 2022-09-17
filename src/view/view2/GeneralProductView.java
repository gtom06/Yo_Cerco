package view.view2;

import control.ShopHandler;
import exceptions.AddressException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Constants;
import model.product.SimpleProduct;
import model.shop.Shop;
import model.user.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneralProductView {
    User user;
    SimpleProduct simpleProduct;

    @FXML
    protected ImageView productPhoto;
    @FXML
    protected ImageView homepageImageView;
    @FXML
    protected ImageView previousPage;
    @FXML
    protected ImageView  cartImageView;
    @FXML
    protected Label nameProd;
    @FXML
    protected Label brandProd;
    @FXML
    protected TableView<Shop> shopsTableView = new TableView<>();
    protected TableColumn<Shop, String> nameColumn;
    protected TableColumn<Shop, String> addressColumn;
    protected TableColumn<Shop, String> cityColumn;
    protected TableColumn<Shop, String> openingColumn;
    protected TableColumn<Shop, String> closingColumn;

    InputStream stream = null;
    static final Logger logger = Logger.getLogger(GeneralProductView.class.getName());


    public void passParams(User user, SimpleProduct simpleProduct) throws FileNotFoundException {
        this.user = user;
        this.simpleProduct = simpleProduct;
        brandProd.setText(simpleProduct.getBrand());
        nameProd.setText(simpleProduct.getName());
        ArrayList<Shop> arrayShopList = ShopHandler.findShopByProduct(simpleProduct);

        stream = new FileInputStream(simpleProduct.getLogoImagepath());
        Image productImage = new Image(stream, 200, 200, false, false);
        productPhoto.setImage(productImage);
        ObservableList<Shop> observableListShops = FXCollections.observableArrayList();
        if (arrayShopList != null) {
            for (Shop s : arrayShopList) {
                observableListShops.add(s);
            }
            shopsTableView.setItems(observableListShops);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    @FXML
    protected void onClickProfileImageView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("myProfile.fxml"));
        Parent root = loader.load();
        MyProfile myProfile = loader.getController();
        myProfile.passParams(user);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) cartImageView.getScene().getWindow();
        stage.close();
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

    @FXML
    protected void openCartAndPayment() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("cartAndPayment.fxml"));
        Parent root = loader.load();
        CartAndPayment cartAndPayment = loader.getController();
        cartAndPayment.passParam(null, user);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) cartImageView.getScene().getWindow();
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

    @FXML
    protected void onItemClickedTableView() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("shopProductView.fxml"));
        Parent root = loader.load();
        ShopProductView shopProductView = loader.getController();
        shopProductView.passParams(user, simpleProduct, shopsTableView.getSelectionModel().getSelectedItem());
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) cartImageView.getScene().getWindow();
        stage.close();
    }
}
