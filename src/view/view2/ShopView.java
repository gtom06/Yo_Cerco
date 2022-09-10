package view.view2;

import control.BrowserHandler;
import control.CartElaboration;
import control.DepartmentHandler;
import control.ShopHandler;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Department.Department;
import model.Product.ProductShop;
import model.Shop.Shop;
import model.User.User;
import view.view1.SearchShop;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

public class ShopView {
    Shop shop = null;
    User user = null;
    ArrayList<Department> departmentArrayList = null;
    Department department = null;
    @FXML
    Button addToCartButton;
    @FXML
    ImageView homepageImageView, shopLogo, profileImageView, cartImageView,
            addShopToFavorites, removeShopFromFavorites,
            dep1, dep2, dep3, dep4, dep5, dep6, dep7, dep8, dep9, dep10, dep11, dep12, dep13, dep14;

    @FXML
    Text TextShopName, TextShopAddress, TextShopTime, TextPhoneShop;
    @FXML
    TableView<ProductShop> productTable = new TableView<>();
    TableColumn<ProductShop, String> nameColumn;
    TableColumn<ProductShop, String> brandColumn;
    TableColumn<ProductShop, Float> sizeColumn;
    TableColumn<ProductShop, String> unitOfMeasureColumn;
    TableColumn<ProductShop, Integer> currencyColumn;
    TableColumn<ProductShop, Integer> priceColumn;
    InputStream stream = null;
    @FXML
    protected void onHomepageImageClick() throws IOException {
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
    public void openCartAndPayment() throws IOException {
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
    public void onClickProfileImageView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("myProfile.fxml"));
        Parent root = loader.load();
        MyProfile myProfile = loader.getController();
        myProfile.passParams(user);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) profileImageView.getScene().getWindow();
        stage.close();
    }

    public void passParams(User user, Shop shop) throws FileNotFoundException {
        this.user = user;
        this.shop = shop;

        stream = new FileInputStream(shop.getLogoImagepath());
        Image shopImage = new Image(stream, 200, 200, false, false);
        shopLogo.setImage(shopImage);
        TextShopName.setText(shop.getShopName());
        ArrayList<ImageView> imageViewDepartmentsArrayList = new ArrayList<>() {
            {
                add(dep1);
                add(dep2);
                add(dep3);
                add(dep4);
                add(dep5);
                add(dep6);
                add(dep7);
                add(dep8);
                add(dep9);
                add(dep10);
                add(dep11);
                add(dep12);
                add(dep13);
                add(dep14);
            }
        };
        this.departmentArrayList = DepartmentHandler.findDepartmentByShop(shop);
        if (departmentArrayList != null && departmentArrayList.size() != 0) {
            for (int i = 0; i < departmentArrayList.size(); i++) {
                imageViewDepartmentsArrayList.get(i).setImage(new Image(new FileInputStream(departmentArrayList.get(i).getLogoImagepath())));
                imageViewDepartmentsArrayList.get(i).setId(String.valueOf(departmentArrayList.get(i).getDepartmentId()));
            }
        }
        if (ShopHandler.isFavoriteShop(shop, user)) {
            //set to remove
            removeShopFromFavorites.setVisible(true);
            addShopToFavorites.setVisible(false);
        } else {
            //set to add
            removeShopFromFavorites.setVisible(false);
            addShopToFavorites.setVisible(true);
        }

        TextShopAddress.setText(shop.getCompleteAddress());
        TextShopTime.setText(shop.getOpeningTime() + " - " + shop.getClosingTime());
        TextPhoneShop.setText(shop.getPhone());
    }

    @FXML
    public void onClickGMapsHyperLink() {
        BrowserHandler.openWebpage(URI.create(shop.getGmapsLink()));
    }

    //methods for adding and removing shops from favorite
    @FXML
    public void addToFavorite() {
        ShopHandler.insertShopIntoFavorite(shop, user);
        removeShopFromFavorites.setVisible(true);
        addShopToFavorites.setVisible(false);
    }

    @FXML
    public void removeFromFavorite() {
        ShopHandler.removeShopFromFavorite(shop, user);
        removeShopFromFavorites.setVisible(false);
        addShopToFavorites.setVisible(true);
    }
    @FXML
    public void previousPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchShop.fxml"));
        Parent root = loader.load();
        SearchShop searchShop = loader.getController();
        searchShop.passUser(user);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) homepageImageView.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onClickDepartmentImage(MouseEvent mouseEvent) {
        int ref = Integer.parseInt(mouseEvent.getPickResult().getIntersectedNode().getId());
        for (Department dep : departmentArrayList) {
            if (dep.getDepartmentId() == ref) {
                department = dep;
                break;
            }
        }
        department.setItems(DepartmentHandler.findProductByDepartmentAndShop(shop, department.getDepartmentId()));

        ArrayList<ProductShop> productShopArrayList = department.getItems();
        if (productShopArrayList != null) {
            ObservableList<ProductShop> observableListProducts = FXCollections.observableArrayList(productShopArrayList);
            productTable.setItems(observableListProducts);
        } else {
            System.out.println("no result");
        }
    }

    @FXML
    public void initialize() {
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

        currencyColumn = new TableColumn<>("CURRENCY");
        currencyColumn.setMinWidth(50);
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));

        productTable.getColumns().addAll(nameColumn, brandColumn, sizeColumn, unitOfMeasureColumn, priceColumn, currencyColumn);
    }


    /*
    @FXML
    protected void onListViewItemClick() throws IOException {

        ProductShop productShop = productTable.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (productShop != null) {
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
    */
    @FXML
    protected void onAddToCartClick() {
        ProductShop productShop = productTable.getSelectionModel().getSelectedItem();
        if (productShop != null) {
            CartElaboration.addOrderItemsToCart(productShop, 1);
        }
    }
}