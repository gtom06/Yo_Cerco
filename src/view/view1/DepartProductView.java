package view.view1;

import control.CartElaboration;
import exceptions.ExceptionCart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import constants.Constants;
import model.department.Department;
import model.product.ProductShop;
import model.shop.Shop;
import model.user.User;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DepartProductView {
    Department department1 = null;
    Shop shop1 = null;
    User user1 = null;

    @FXML
    protected ImageView homepageImageView1DPV;
    @FXML
    protected Text departmentName1;
    @FXML
    protected Text shopName1;
    @FXML
    protected Text textHi1;
    @FXML
    protected TableView<ProductShop> productTable1 = new TableView<>();
    @FXML
    protected TableColumn<ProductShop,String> nameColumn1;
    @FXML
    protected TableColumn<ProductShop,String> brandColumn1;
    @FXML
    protected TableColumn<ProductShop,Float> sizeColumn1;
    @FXML
    protected TableColumn<ProductShop,String> unitOfMeasureColumn1;
    @FXML
    protected TableColumn<ProductShop, String> currencyColumn1;
    @FXML
    protected TableColumn<ProductShop, Double> discountedPriceColumn1;
    @FXML
    protected TableColumn<ProductShop, Double> priceColumn1;
    static final Logger logger = Logger.getLogger(DepartProductView.class.getName());

    @FXML
    protected void onListViewItemClick() throws IOException {
        ProductShop productShop1 = productTable1.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (productShop1 != null){
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("productView.fxml"));
            Parent root1 = loader1.load();
            ProductView productView1 = loader1.getController();
            productView1.passParams(user1, department1, productShop1, shop1);
            Stage newStage1 = new Stage();
            newStage1.setScene(new Scene(root1));
            newStage1.show();
            newStage1.setResizable(false);
            Stage stage1 = (Stage) productTable1.getScene().getWindow();
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
        Stage stage1 = (Stage) homepageImageView1DPV.getScene().getWindow();
        stage1.close();
    }

    @FXML
    protected void onAddToCartClick() throws ExceptionCart {
        ProductShop productShop = productTable1.getSelectionModel().getSelectedItem();
        CartElaboration.addOrderItemsToCart(productShop, 1);
    }

    public void passParams(User user, Department department, Shop shop) {
        this.shop1 = shop;
        this.user1 = user;
        this.department1 = department;
        List<ProductShop> productShopArrayList = department.getItems();
        if (productShopArrayList!=null){
            ObservableList<ProductShop> observableListProducts = FXCollections.observableArrayList(productShopArrayList);
            productTable1.setItems(observableListProducts);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }

        departmentName1.setText(department.getName());
        shopName1.setText(shop.getShopName());
        textHi1.setText(user.getUsername());

    }

    @FXML
    protected void previousPage() throws IOException {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("shopView.fxml"));
        Parent root1 = loader1.load();
        ShopView shopView1 = loader1.getController();
        shopView1.passUser(user1);
        shopView1.passShop(shop1);
        Stage newStage1 = new Stage();
        newStage1.setScene(new Scene(root1));
        newStage1.show();
        newStage1.setResizable(false);
        Stage stage1 = (Stage) homepageImageView1DPV.getScene().getWindow();
        stage1.close();
    }

    @FXML
    public void initialize(){

        productTable1.setEditable(true);

        nameColumn1 = new TableColumn<>("NAME");
        nameColumn1.setMinWidth(20);
        nameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));

        priceColumn1 = new TableColumn<>("PRICE");
        priceColumn1.setMinWidth(50);
        priceColumn1.setCellValueFactory(new PropertyValueFactory<>("price"));

        brandColumn1 = new TableColumn<>("BRAND");
        brandColumn1.setMinWidth(10);
        brandColumn1.setCellValueFactory(new PropertyValueFactory<>("brand"));

        sizeColumn1 = new TableColumn<>("SIZE");
        sizeColumn1.setMinWidth(10);
        sizeColumn1.setCellValueFactory(new PropertyValueFactory<>("size"));

        unitOfMeasureColumn1 = new TableColumn<>("UNIT OF MEASURE");
        unitOfMeasureColumn1.setMinWidth(10);
        unitOfMeasureColumn1.setCellValueFactory(new PropertyValueFactory<>("unitOfMeasure"));

        priceColumn1 = new TableColumn<>("PRICE");
        priceColumn1.setMinWidth(50);
        priceColumn1.setCellValueFactory(new PropertyValueFactory<>("price"));

        discountedPriceColumn1 = new TableColumn<>("DISCOUNTED PRICE");
        discountedPriceColumn1.setMinWidth(50);
        discountedPriceColumn1.setCellValueFactory(new PropertyValueFactory<>("discountedPrice"));

        currencyColumn1 = new TableColumn<>("CURRENCY");
        currencyColumn1.setMinWidth(50);
        currencyColumn1.setCellValueFactory(new PropertyValueFactory<>("currency"));

        productTable1.getColumns().addAll(nameColumn1,brandColumn1,sizeColumn1,unitOfMeasureColumn1,priceColumn1, discountedPriceColumn1, currencyColumn1);

    }

}
