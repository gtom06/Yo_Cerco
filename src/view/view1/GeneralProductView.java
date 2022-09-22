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
import constants.Constants;
import model.product.SimpleProduct;
import model.shop.Shop;
import model.user.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneralProductView {
    User user1;
    List<Shop> arrayShopList1;
    @FXML
    protected ImageView productPhoto1;
    @FXML
    protected ImageView homepageImageView1GPV;
    @FXML
    protected ImageView previousPageImageView1;
    @FXML
    protected Text textHi1;
    @FXML
    protected Text nameProd1;
    @FXML
    protected Text brandProd1;
    @FXML
    protected TableView<Shop> shopsTableView1 = new TableView<>();
    protected TableColumn<Shop, String> nameColumn1;
    protected TableColumn<Shop, String> addressColumn1;
    protected TableColumn<Shop, String> cityColumn1;
    protected TableColumn<Shop, String> openingColumn1;
    protected TableColumn<Shop, String> closingColumn1;
    InputStream stream1 = null;
    static final Logger logger = Logger.getLogger(GeneralProductView.class.getName());

    @FXML
    protected void onTableViewItemClick() throws IOException {

        Shop shop1 = shopsTableView1.getSelectionModel().getSelectedItem();
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
            Stage stage1 = (Stage) shopsTableView1.getScene().getWindow();
            stage1.close();
        }
    }
    public void passParams(User user, SimpleProduct simpleProduct) throws FileNotFoundException {

        this.user1 = user;
        textHi1.setText(user.getUsername());
        brandProd1.setText(simpleProduct.getBrand());
        nameProd1.setText(simpleProduct.getName());

        stream1 = new FileInputStream(simpleProduct.getLogoImagepath());
        Image productImage1 = new Image(stream1, 200, 200, false, false);
        productPhoto1.setImage(productImage1);

        ObservableList<Shop> observableListShops = FXCollections.observableArrayList();
        arrayShopList1 = ShopHandler.findShopByProduct(simpleProduct);

        if (arrayShopList1 != null) {
            for (Shop s : arrayShopList1) {
                observableListShops.add(s);
            }
            shopsTableView1.setItems(observableListShops);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
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
        Stage stage1 = (Stage) homepageImageView1GPV.getScene().getWindow();
        stage1.close();
    }

    @FXML
    protected void previousPage() throws IOException {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("searchProduct.fxml"));
        Parent root1 = loader1.load();
        SearchProduct searchProduct1 = loader1.getController();
        searchProduct1.passUser(user1);
        Stage newStage1 = new Stage();
        newStage1.setScene(new Scene(root1));
        newStage1.show();
        newStage1.setResizable(false);
        Stage stage1 = (Stage) homepageImageView1GPV.getScene().getWindow();
        stage1.close();
    }

    @FXML
    public void initialize(){

        shopsTableView1.setEditable(true);

        nameColumn1 = new TableColumn<>("NAME");
        nameColumn1.setMinWidth(200);
        nameColumn1.setCellValueFactory(new PropertyValueFactory<>("shopName"));

        addressColumn1 = new TableColumn<>("ADDRESS");
        addressColumn1.setMinWidth(200);
        addressColumn1.setCellValueFactory(new PropertyValueFactory<>("address"));

        cityColumn1 = new TableColumn<>("CITY");
        cityColumn1.setMinWidth(50);
        cityColumn1.setCellValueFactory(new PropertyValueFactory<>("city"));

        openingColumn1 = new TableColumn<>("OPENING TIME");
        openingColumn1.setMinWidth(10);
        openingColumn1.setCellValueFactory(new PropertyValueFactory<>("openingTime"));

        closingColumn1 = new TableColumn<>("CLOSING TIME");
        openingColumn1.setMinWidth(10);
        closingColumn1.setCellValueFactory(new PropertyValueFactory<>("closingTime"));

        shopsTableView1.getColumns().addAll(nameColumn1, addressColumn1, cityColumn1, openingColumn1, closingColumn1);
    }
}
