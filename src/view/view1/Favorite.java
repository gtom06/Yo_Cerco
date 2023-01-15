package view.view1;

import bean.ShopBean;
import bean.SimpleProductBean;
import bean.UserBean;
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
    UserBean user1F = null;
    @FXML
    protected ImageView homepageImageView1F;
    @FXML
    protected Text textHi1F;
    @FXML
    protected TableView<ShopBean> shopTableView1F = new TableView<>();
    protected TableColumn<ShopBean, String> addressColumn1F;
    protected TableColumn<ShopBean, String> cityColumn1F;
    protected TableColumn<ShopBean, String> nameColumn1F;
    protected TableColumn<ShopBean, String> openingColumn1F;
    protected TableColumn<ShopBean, String> closingColumn1F;

    @FXML
    protected TableView<SimpleProductBean> simpleProductTableView1F = new TableView<>();
    protected TableColumn<SimpleProductBean, String> brandColumn1F;
    protected TableColumn<SimpleProductBean, Integer> sizeColumn1F;
    protected TableColumn<SimpleProductBean, String> nameProductColumn1F;
    protected TableColumn<SimpleProductBean, Double> unitOfMeasureColumn1F;
    static final Logger logger = Logger.getLogger(Favorite.class.getName());

    @FXML
    protected void onShopTableViewClick1F() throws IOException {

        ShopBean shop1F = shopTableView1F.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (shop1F != null) {
            FXMLLoader loader1F = new FXMLLoader(getClass().getResource("shopView.fxml"));
            Parent root1F = loader1F.load();
            ShopView shopView1F = loader1F.getController();
            shopView1F.passUser(user1F);
            shopView1F.passShop(shop1F);
            Stage newStage1F = new Stage();
            newStage1F.setScene(new Scene(root1F));
            newStage1F.show();
            newStage1F.setResizable(false);
            Stage stage1 = (Stage) shopTableView1F.getScene().getWindow();
            stage1.close();
        }
    }


    @FXML
    protected void onProductTableViewClick1F() throws IOException {

        SimpleProductBean product1F = simpleProductTableView1F.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (product1F != null) {
            FXMLLoader loader1F = new FXMLLoader(getClass().getResource("generalProductView.fxml"));
            Parent root1F = loader1F.load();
            GeneralProductView generalProductView1F = loader1F.getController();
            generalProductView1F.passParams(user1F, product1F);
            Stage newStage1F = new Stage();
            newStage1F.setScene(new Scene(root1F));
            newStage1F.show();
            newStage1F.setResizable(false);
            Stage stage1F = (Stage) simpleProductTableView1F.getScene().getWindow();
            stage1F.close();
        }

    }

    @FXML
    protected void onHomepageImageClick1F() throws IOException {
        FXMLLoader loader1F = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root1F = loader1F.load();
        Homepage homepage1F = loader1F.getController();
        homepage1F.passUser(user1F);
        Stage newStage1F = new Stage();
        newStage1F.setScene(new Scene(root1F));
        newStage1F.show();
        newStage1F.setResizable(false);
        Stage stage1F = (Stage) homepageImageView1F.getScene().getWindow();
        stage1F.close();
    }

    public void passUser(UserBean user) {
        this.user1F = user;
        textHi1F.setText(user.getUsername());
        fillShopTableView1F();
        fillSimpleProductTableView1F();
    }

    @FXML
    public void initialize() {
        shopTableView1F.setEditable(true);
        //897
        nameColumn1F = new TableColumn<>("ShopName");
        addressColumn1F = new TableColumn<>("Address");
        cityColumn1F = new TableColumn<>("City");
        openingColumn1F = new TableColumn<>("Opening time");
        closingColumn1F = new TableColumn<>("Closing time");

        nameColumn1F.setMinWidth(250);
        addressColumn1F.setMinWidth(377);
        cityColumn1F.setMinWidth(100);
        nameColumn1F.setMinWidth(10);
        nameColumn1F.setMinWidth(10);

        nameColumn1F.setCellValueFactory(new PropertyValueFactory<>("shopName"));
        addressColumn1F.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityColumn1F.setCellValueFactory(new PropertyValueFactory<>("city"));
        openingColumn1F.setCellValueFactory(new PropertyValueFactory<>("openingTime"));
        closingColumn1F.setCellValueFactory(new PropertyValueFactory<>("closingTime"));

        shopTableView1F.getColumns().addAll(nameColumn1F, addressColumn1F, cityColumn1F, openingColumn1F, closingColumn1F);

        simpleProductTableView1F.setEditable(true);
        simpleProductTableView1F.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        //897
        nameProductColumn1F = new TableColumn<>("Name");
        brandColumn1F = new TableColumn<>("Brand");
        sizeColumn1F = new TableColumn<>("Size");
        unitOfMeasureColumn1F = new TableColumn<>("Unit of measure");

        nameProductColumn1F.setMinWidth(477);
        brandColumn1F.setMinWidth(400);
        sizeColumn1F.setMinWidth(10);
        unitOfMeasureColumn1F.setMinWidth(10);

        nameProductColumn1F.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandColumn1F.setCellValueFactory(new PropertyValueFactory<>("brand"));
        sizeColumn1F.setCellValueFactory(new PropertyValueFactory<>("size"));
        unitOfMeasureColumn1F.setCellValueFactory(new PropertyValueFactory<>("unitOfMeasure"));

        simpleProductTableView1F.getColumns().addAll(nameProductColumn1F, brandColumn1F, sizeColumn1F, unitOfMeasureColumn1F);
    }

    protected void fillShopTableView1F() {
        shopTableView1F.getItems().clear();
        ObservableList<ShopBean> observableListShops1F = FXCollections.observableArrayList();
        List<ShopBean> shopArrayList1F = ShopHandler.findFavoriteShopsFromUser(user1F);
        if (shopArrayList1F != null) {
            observableListShops1F.addAll(shopArrayList1F);
            shopTableView1F.setItems(observableListShops1F);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    protected void fillSimpleProductTableView1F() {
        simpleProductTableView1F.getItems().clear();
        ObservableList<SimpleProductBean> simpleProductObservableList1F = FXCollections.observableArrayList();
        ArrayList<SimpleProductBean> simpleProductArrayList1F = (ArrayList<SimpleProductBean>) ProductHandler.findFavoriteSimpleProductFromUser(user1F);
        if (simpleProductArrayList1F != null) {
            simpleProductObservableList1F.addAll(simpleProductArrayList1F);
            simpleProductTableView1F.setItems(simpleProductObservableList1F);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }
}
