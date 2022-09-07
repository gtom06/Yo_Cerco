package view;

import control.CartElaboration;
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
import model.Department.Department;
import model.Product.ProductShop;
import model.Shop.Shop;
import model.User.User;

import java.io.IOException;
import java.util.ArrayList;

public class DepartProductView {

    Department department = null;
    Shop shop = null;
    User user = null;

    @FXML
    ImageView homepageImageView;
    @FXML
    Text departmentName, shopName, textHi;
    @FXML
    TableView<ProductShop> productTable = new TableView<>();
    //TableColumn<ProductShop, Integer> sku;
    //TableColumn<ProductShop, Integer> shop_id;
    //TableColumn<ProductShop, Integer> department_id;
    TableColumn<ProductShop,String>nameColumn;
    TableColumn<ProductShop,String> descriptionColumn;
    TableColumn<ProductShop,String> brandColumn;
    TableColumn<ProductShop,Float> sizeColumn;
    TableColumn<ProductShop,String> unitOfMeasureColumn;

    TableColumn<ProductShop, Integer> availableQuantityColumn;
    TableColumn<ProductShop, Integer> currencyColumn;
    TableColumn<ProductShop, Integer> priceColumn;

    @FXML
    Button addToCartButton;

    @FXML
    protected void onListViewItemClick() throws IOException {

        ProductShop productShop = productTable.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (productShop != null){
            System.out.println(productShop);
            /*
            if (shop.getType == "ferramenta") {
                carica ferramentaview.fxml
            }
            //todo: non chiudere la finestra della searchShop.fxml quando si apre la shopView.fxml
            */
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
    protected void onAddToCartClick() {
        ProductShop productShop = productTable.getSelectionModel().getSelectedItem();
        CartElaboration.addOrderItemsToCart(productShop, 1);
    }

    public void passParams(User user, Department department, Shop shop) {
        this.shop = shop;
        this.user = user;
        this.department = department;
        System.out.println("**********************************");
        System.out.println(shop);
        System.out.println("**********************************");
        ArrayList<ProductShop> productShopArrayList = department.getItems();
        if (productShopArrayList!=null){
            System.out.println(department.getItems().get(0));
            ObservableList<ProductShop> observableListProducts = FXCollections.observableArrayList(productShopArrayList);
            productTable.setItems(observableListProducts);
        }
        else {
            System.out.println("no result");
        }

        departmentName.setText(department.getName());
        shopName.setText(shop.getShopName());
        textHi.setText(user.getUsername());

    }

    public void previousPage() throws IOException {
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

        availableQuantityColumn = new TableColumn<>("AVAILABLE QUANTITY");
        availableQuantityColumn.setMinWidth(10);
        availableQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("availableQuantity"));

        priceColumn = new TableColumn<>("PRICE");
        priceColumn.setMinWidth(50);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        currencyColumn = new TableColumn<>("CURRENCY");
        currencyColumn.setMinWidth(50);
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));



        productTable.getColumns().addAll(nameColumn,brandColumn,sizeColumn,unitOfMeasureColumn,availableQuantityColumn,priceColumn,currencyColumn);


    }

}
