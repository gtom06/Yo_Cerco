package view.view1;

import bean.ShopBean;
import bean.SimpleProductBean;
import bean.UserBean;
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
    UserBean user1GPV;
    List<ShopBean> arrayShopList1GPV;
    @FXML
    protected ImageView productPhoto1GPV;
    @FXML
    protected ImageView homepageImageView1GPV;
    @FXML
    protected ImageView previousPageImageView1GPV;
    @FXML
    protected Text textHi1GPV;
    @FXML
    protected Text nameProd1GPV;
    @FXML
    protected Text brandProd1GPV;
    @FXML
    protected TableView<ShopBean> shopsTableView1GPV = new TableView<>();
    protected TableColumn<ShopBean, String> nameColumn1GPV;
    protected TableColumn<ShopBean, String> addressColumn1GPV;
    protected TableColumn<ShopBean, String> cityColumn1GPV;
    protected TableColumn<ShopBean, String> openingColumn1GPV;
    protected TableColumn<ShopBean, String> closingColumn1GPV;
    InputStream stream1GPV = null;
    static final Logger logger = Logger.getLogger(GeneralProductView.class.getName());

    @FXML
    protected void onTableViewItemClick1GPV() throws IOException {

        ShopBean shop1GPV = shopsTableView1GPV.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (shop1GPV != null){
            FXMLLoader loader1GPV = new FXMLLoader(getClass().getResource("shopView.fxml"));
            Parent root1GPV = loader1GPV.load();
            ShopView shopView1GPV = loader1GPV.getController();
            shopView1GPV.passUser(user1GPV);
            shopView1GPV.passShop(shop1GPV);
            Stage newStage1GPV = new Stage();
            newStage1GPV.setScene(new Scene(root1GPV));
            newStage1GPV.show();
            newStage1GPV.setResizable(false);
            Stage stage1GPV = (Stage) shopsTableView1GPV.getScene().getWindow();
            stage1GPV.close();
        }
    }
    public void passParams(UserBean user1GPV, SimpleProductBean simpleProduct1GPV) throws FileNotFoundException {

        this.user1GPV = user1GPV;
        textHi1GPV.setText(user1GPV.getUsername());
        brandProd1GPV.setText(simpleProduct1GPV.getBrand());
        nameProd1GPV.setText(simpleProduct1GPV.getName());

        stream1GPV = new FileInputStream(simpleProduct1GPV.getLogoImagepath());
        Image productImage1GPV = new Image(stream1GPV, 200, 200, false, false);
        productPhoto1GPV.setImage(productImage1GPV);

        ObservableList<ShopBean> observableListShops1GPV = FXCollections.observableArrayList();
        arrayShopList1GPV = ShopHandler.findShopByProduct(simpleProduct1GPV);

        if (arrayShopList1GPV != null) {
            for (ShopBean s1 : arrayShopList1GPV) {
                observableListShops1GPV.add(s1);
            }
            shopsTableView1GPV.setItems(observableListShops1GPV);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    @FXML
    protected void onHomepageImageClick1GPV() throws IOException {
        FXMLLoader loader1GPV = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root1GPV = loader1GPV.load();
        Homepage homepage1GPV = loader1GPV.getController();
        homepage1GPV.passUser(user1GPV);
        Stage newStage1GPV = new Stage();
        newStage1GPV.setScene(new Scene(root1GPV));
        newStage1GPV.show();
        newStage1GPV.setResizable(false);
        Stage stage1GPV = (Stage) homepageImageView1GPV.getScene().getWindow();
        stage1GPV.close();
    }

    @FXML
    protected void previousPage() throws IOException {
        FXMLLoader loader1GPV = new FXMLLoader(getClass().getResource("searchProduct.fxml"));
        Parent root1GPV = loader1GPV.load();
        SearchProduct searchProduct1GPV = loader1GPV.getController();
        searchProduct1GPV.passUser(user1GPV);
        Stage newStage1GPV = new Stage();
        newStage1GPV.setScene(new Scene(root1GPV));
        newStage1GPV.show();
        newStage1GPV.setResizable(false);
        Stage stage1GPV = (Stage) homepageImageView1GPV.getScene().getWindow();
        stage1GPV.close();
    }

    @FXML
    public void initialize(){

        shopsTableView1GPV.setEditable(true);

        nameColumn1GPV = new TableColumn<>("NAME");
        nameColumn1GPV.setMinWidth(200);
        nameColumn1GPV.setCellValueFactory(new PropertyValueFactory<>("shopName"));

        addressColumn1GPV = new TableColumn<>("ADDRESS");
        addressColumn1GPV.setMinWidth(200);
        addressColumn1GPV.setCellValueFactory(new PropertyValueFactory<>("address"));

        cityColumn1GPV = new TableColumn<>("CITY");
        cityColumn1GPV.setMinWidth(50);
        cityColumn1GPV.setCellValueFactory(new PropertyValueFactory<>("city"));

        openingColumn1GPV = new TableColumn<>("OPENING TIME");
        openingColumn1GPV.setMinWidth(10);
        openingColumn1GPV.setCellValueFactory(new PropertyValueFactory<>("openingTime"));

        closingColumn1GPV = new TableColumn<>("CLOSING TIME");
        openingColumn1GPV.setMinWidth(10);
        closingColumn1GPV.setCellValueFactory(new PropertyValueFactory<>("closingTime"));

        shopsTableView1GPV.getColumns().addAll(nameColumn1GPV, addressColumn1GPV, cityColumn1GPV, openingColumn1GPV, closingColumn1GPV);
    }
}
