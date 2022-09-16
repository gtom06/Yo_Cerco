package view.view1;

import control.CartElaboration;
import exceptions.ExceptionCart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Constants;
import model.Department.Department;
import model.Product.ProductShop;
import model.Shop.Shop;
import model.User.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DepartProductView {
    Department department = null;
    Shop shop = null;
    User user = null;

    @FXML
    protected ImageView homepageImageView;
    @FXML
    protected Text departmentName, shopName, textHi;
    @FXML
    protected TableView<ProductShop> productTable = new TableView<>();
    @FXML
    protected TableColumn<ProductShop,String>nameColumn;
    @FXML
    protected TableColumn<ProductShop,String> brandColumn;
    @FXML
    protected TableColumn<ProductShop,Float> sizeColumn;
    @FXML
    protected TableColumn<ProductShop,String> unitOfMeasureColumn;
    @FXML
    protected TableColumn<ProductShop, String> currencyColumn;
    @FXML
    protected TableColumn<ProductShop, Double> discountedPriceColumn;
    @FXML
    protected TableColumn<ProductShop, Double> priceColumn;
    static final Logger logger = Logger.getLogger(DepartProductView.class.getName());

    @FXML
    protected void onListViewItemClick() throws IOException {
        ProductShop productShop = productTable.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (productShop != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("productView.fxml"));
            Parent root = loader.load();
            ProductView productView = loader.getController();
            productView.passParams(user, department, productShop, shop);
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
            newStage.setResizable(false);
            Stage stage = (Stage) productTable.getScene().getWindow();
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

    @FXML
    protected void onAddToCartClick() throws ExceptionCart {
        ProductShop productShop = productTable.getSelectionModel().getSelectedItem();
        CartElaboration.addOrderItemsToCart(productShop, 1);
    }

    public void passParams(User user, Department department, Shop shop) {
        this.shop = shop;
        this.user = user;
        this.department = department;
        List<ProductShop> productShopArrayList = department.getItems();
        if (productShopArrayList!=null){
            ObservableList<ProductShop> observableListProducts = FXCollections.observableArrayList(productShopArrayList);
            productTable.setItems(observableListProducts);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }

        departmentName.setText(department.getName());
        shopName.setText(shop.getShopName());
        textHi.setText(user.getUsername());

    }

    @FXML
    protected void previousPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("shopView.fxml"));
        Parent root = loader.load();
        ShopView shopView = loader.getController();
        shopView.passUser(user);
        shopView.passShop(shop);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) homepageImageView.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize(){

        productTable.setEditable(true);

        nameColumn = new TableColumn<>("NAME");
        nameColumn.setMinWidth(20);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        priceColumn = new TableColumn<>("PRICE");
        priceColumn.setMinWidth(50);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        brandColumn = new TableColumn<>("BRAND");
        brandColumn.setMinWidth(10);
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

        sizeColumn = new TableColumn<>("SIZE");
        sizeColumn.setMinWidth(10);
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));

        unitOfMeasureColumn = new TableColumn<>("UNIT OF MEASURE");
        unitOfMeasureColumn.setMinWidth(10);
        unitOfMeasureColumn.setCellValueFactory(new PropertyValueFactory<>("unitOfMeasure"));

        priceColumn = new TableColumn<>("PRICE");
        priceColumn.setMinWidth(50);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        discountedPriceColumn = new TableColumn<>("DISCOUNTED PRICE");
        discountedPriceColumn.setMinWidth(50);
        discountedPriceColumn.setCellValueFactory(new PropertyValueFactory<>("discountedPrice"));

        currencyColumn = new TableColumn<>("CURRENCY");
        currencyColumn.setMinWidth(50);
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));

        productTable.getColumns().addAll(nameColumn,brandColumn,sizeColumn,unitOfMeasureColumn,priceColumn, discountedPriceColumn, currencyColumn);

    }

}
