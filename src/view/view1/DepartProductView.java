package view.view1;

import bean.DepartmentBean;
import bean.ProductShopBean;
import bean.ShopBean;
import bean.UserBean;
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
    DepartmentBean department1DPV = null;
    ShopBean shop1DPV = null;
    UserBean user1DPV = null;

    @FXML
    protected ImageView homepageImageView1DPV;
    @FXML
    protected Text departmentName1DPV;
    @FXML
    protected Text shopName1DPV;
    @FXML
    protected Text textHi1DPV;
    @FXML
    protected TableView<ProductShopBean> productTable1DPV = new TableView<>();
    @FXML
    protected TableColumn<ProductShopBean,String> nameColumn1DPV;
    @FXML
    protected TableColumn<ProductShopBean,String> brandColumn1DPV;
    @FXML
    protected TableColumn<ProductShopBean,Float> sizeColumn1DPV;
    @FXML
    protected TableColumn<ProductShopBean,String> unitOfMeasureColumn1DPV;
    @FXML
    protected TableColumn<ProductShopBean, String> currencyColumn1DPV;
    @FXML
    protected TableColumn<ProductShopBean, Double> discountedPriceColumn1DPV;
    @FXML
    protected TableColumn<ProductShopBean, Double> priceColumn1DPV;
    static final Logger logger = Logger.getLogger(DepartProductView.class.getName());

    @FXML
    protected void onListViewItemClick1DPV() throws IOException {
        ProductShopBean productShop1DPV = productTable1DPV.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (productShop1DPV != null){
            FXMLLoader loader1DPV = new FXMLLoader(getClass().getResource("productView.fxml"));
            Parent root1DPV = loader1DPV.load();
            ProductView productView1DPV = loader1DPV.getController();
            productView1DPV.passParams(user1DPV, department1DPV, productShop1DPV, shop1DPV);
            Stage newStage1DPV = new Stage();
            newStage1DPV.setScene(new Scene(root1DPV));
            newStage1DPV.show();
            newStage1DPV.setResizable(false);
            Stage stage1DPV = (Stage) productTable1DPV.getScene().getWindow();
            stage1DPV.close();
        }
    }

    @FXML
    protected void onHomepageImageClick1DPV() throws IOException {
        FXMLLoader loader1DPV = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root1DPV = loader1DPV.load();
        Homepage homepage1DPV = loader1DPV.getController();
        homepage1DPV.passUser(user1DPV);
        Stage newStage1DPV = new Stage();
        newStage1DPV.setScene(new Scene(root1DPV));
        newStage1DPV.show();
        newStage1DPV.setResizable(false);
        Stage stage1DPV = (Stage) homepageImageView1DPV.getScene().getWindow();
        stage1DPV.close();
    }

    @FXML
    protected void onAddToCartClick1DPV() throws ExceptionCart {
        ProductShopBean productShop = productTable1DPV.getSelectionModel().getSelectedItem();
        CartElaboration.addOrderItemsToCart(productShop, 1);
    }

    public void passParams(UserBean user1DPV, DepartmentBean department1DPV, ShopBean shop1DPV) {
        this.shop1DPV = shop1DPV;
        this.user1DPV = user1DPV;
        this.department1DPV = department1DPV;
        List<ProductShopBean> productShopArrayList = department1DPV.getItems();
        if (productShopArrayList!=null){
            ObservableList<ProductShopBean> observableListProducts = FXCollections.observableArrayList(productShopArrayList);
            productTable1DPV.setItems(observableListProducts);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }

        departmentName1DPV.setText(department1DPV.getName());
        shopName1DPV.setText(shop1DPV.getShopName());
        textHi1DPV.setText(user1DPV.getUsername());

    }

    @FXML
    protected void previousPage1DPV() throws IOException {
        FXMLLoader loader1DPV = new FXMLLoader(getClass().getResource("shopView.fxml"));
        Parent root1DPV = loader1DPV.load();
        ShopView shopView1DPV = loader1DPV.getController();
        shopView1DPV.passUser(user1DPV);
        shopView1DPV.passShop(shop1DPV);
        Stage newStage1DPV = new Stage();
        newStage1DPV.setScene(new Scene(root1DPV));
        newStage1DPV.show();
        newStage1DPV.setResizable(false);
        Stage stage1DPV = (Stage) homepageImageView1DPV.getScene().getWindow();
        stage1DPV.close();
    }

    @FXML
    public void initialize(){

        productTable1DPV.setEditable(true);

        nameColumn1DPV = new TableColumn<>("NAME");
        nameColumn1DPV.setMinWidth(20);
        nameColumn1DPV.setCellValueFactory(new PropertyValueFactory<>("name"));

        priceColumn1DPV = new TableColumn<>("PRICE");
        priceColumn1DPV.setMinWidth(50);
        priceColumn1DPV.setCellValueFactory(new PropertyValueFactory<>("price"));

        brandColumn1DPV = new TableColumn<>("BRAND");
        brandColumn1DPV.setMinWidth(10);
        brandColumn1DPV.setCellValueFactory(new PropertyValueFactory<>("brand"));

        sizeColumn1DPV = new TableColumn<>("SIZE");
        sizeColumn1DPV.setMinWidth(10);
        sizeColumn1DPV.setCellValueFactory(new PropertyValueFactory<>("size"));

        unitOfMeasureColumn1DPV = new TableColumn<>("UNIT OF MEASURE");
        unitOfMeasureColumn1DPV.setMinWidth(10);
        unitOfMeasureColumn1DPV.setCellValueFactory(new PropertyValueFactory<>("unitOfMeasure"));

        priceColumn1DPV = new TableColumn<>("PRICE");
        priceColumn1DPV.setMinWidth(50);
        priceColumn1DPV.setCellValueFactory(new PropertyValueFactory<>("price"));

        discountedPriceColumn1DPV = new TableColumn<>("DISCOUNTED PRICE");
        discountedPriceColumn1DPV.setMinWidth(50);
        discountedPriceColumn1DPV.setCellValueFactory(new PropertyValueFactory<>("discountedPrice"));

        currencyColumn1DPV = new TableColumn<>("CURRENCY");
        currencyColumn1DPV.setMinWidth(50);
        currencyColumn1DPV.setCellValueFactory(new PropertyValueFactory<>("currency"));

        productTable1DPV.getColumns().addAll(nameColumn1DPV,brandColumn1DPV,sizeColumn1DPV,unitOfMeasureColumn1DPV,priceColumn1DPV, discountedPriceColumn1DPV, currencyColumn1DPV);

    }

}
