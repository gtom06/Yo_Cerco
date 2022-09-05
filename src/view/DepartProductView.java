package view;

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
import model.Department.Department;
import model.Product.ProductShop;
import model.Shop.Shop;
import model.User.User;

import java.io.IOException;
import java.util.ArrayList;

public class DepartProductView {


    @FXML
    ImageView homepageImageView;
    @FXML
    Text departmentNameText;
    @FXML
    TableView<ProductShop> productTable = new TableView<>();
    TableColumn<ProductShop,String>nameColumn;
    TableColumn<ProductShop,String> descriptionColumn;
    TableColumn<ProductShop,String> brandColumn;
    TableColumn<ProductShop,Float> sizeColumn;
    TableColumn<ProductShop,String> unitOfMeasureColumn;
    TableColumn<ProductShop, String> locationColumn;
    TableColumn<ProductShop, Integer> qtyOnStockColumn;
    TableColumn<ProductShop, Float> discounted_priceColumn;
    TableColumn<ProductShop, Integer> available_quantityColumn;
    TableColumn<ProductShop, Integer> number_of_purchaseColumn;
    TableColumn<ProductShop, Integer> currencyColumn;
    TableColumn<ProductShop, Integer> priceColumn;
    Department department = null;
    Shop shop = null;
    User user = null;

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

    public void passParams(User user, Department department, Shop shop) {

        this.user = user;
        this.department = department;
        ArrayList<ProductShop> productShopArrayList = department.getItems();
        this.shop = shop;
        departmentNameText.setText(department.getName());

        ObservableList<ProductShop> observableListProducts = FXCollections.observableArrayList(productShopArrayList);

        productTable.setItems(observableListProducts);
    }

    public void previousPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("shopView.fxml"));
        Parent root = loader.load();
        ShopView shopView = loader.getController();
        shopView.passUser(user);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) departmentNameText.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize(){

        productTable.setEditable(true);

        nameColumn = new TableColumn<>("NAME");
        nameColumn.setMinWidth(20);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        descriptionColumn = new TableColumn<>("DESCRIPTION");
        descriptionColumn.setMinWidth(50);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        brandColumn = new TableColumn<>("BRAND");
        brandColumn.setMinWidth(10);
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

        sizeColumn = new TableColumn<>("SIZE");
        sizeColumn.setMinWidth(10);
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));

        unitOfMeasureColumn = new TableColumn<>("UNIT OF MEASURE");
        unitOfMeasureColumn.setMinWidth(10);
        unitOfMeasureColumn.setCellValueFactory(new PropertyValueFactory<>("unitOfMeasure"));

        qtyOnStockColumn = new TableColumn<>("QTY ON STOCK");
        qtyOnStockColumn.setMinWidth(10);
        qtyOnStockColumn.setCellValueFactory(new PropertyValueFactory<>("availableQuantity"));



        productTable.getColumns().addAll(nameColumn, brandColumn, sizeColumn, unitOfMeasureColumn, qtyOnStockColumn);


    }

}
