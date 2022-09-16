package view.view2;

import control.BrowserHandler;
import control.CartElaboration;
import control.DepartmentHandler;
import control.ShopHandler;
import exceptions.AddressException;
import exceptions.ExceptionBrowser;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Constants;
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
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShopView {
    Shop shop = null;
    User user = null;
    List<Department> departmentArrayList = null;
    @FXML
    protected Button addToCartButton;
    @FXML
    protected ImageView homepageImageView, shopLogo, profileImageView, cartImageView,
                        addShopToFavorites, removeShopFromFavorites,
                        dep1, dep2, dep3, dep4, dep5, dep6, dep7, dep8, dep9, dep10, dep11, dep12, dep13, dep14;

    @FXML
    protected Text TextShopName, TextShopAddress, TextShopTime, TextPhoneShop;
    @FXML
    protected TableView<ProductShop> productTable = new TableView<>();
    protected TableColumn<ProductShop, String> nameColumn;
    protected TableColumn<ProductShop, String> brandColumn;
    protected TableColumn<ProductShop, Float> sizeColumn;
    protected TableColumn<ProductShop, String> unitOfMeasureColumn;
    protected TableColumn<ProductShop, Integer> currencyColumn;
    protected TableColumn<ProductShop, Integer> priceColumn;
    InputStream stream = null;

    static final Logger logger = Logger.getLogger(ShopView.class.getName());

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
    protected void onClickProfileImageView() throws IOException {
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
        ArrayList<ImageView> imageViewDepartmentsArrayList = new ArrayList<>(
                Arrays.asList(dep1,dep2,dep3,dep4,dep5,dep6,dep7,dep8,dep9,dep10,dep11,dep12,dep13,dep14)
        );
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
    protected void onClickGMapsHyperLink() throws ExceptionBrowser {
        BrowserHandler.openWebpage(URI.create(shop.getGmapsLink()));
    }

    //methods for adding and removing shops from favorite
    @FXML
    protected void addToFavorite() {
        ShopHandler.insertShopIntoFavorite(shop, user);
        removeShopFromFavorites.setVisible(true);
        addShopToFavorites.setVisible(false);
    }

    @FXML
    protected void removeFromFavorite() {
        ShopHandler.removeShopFromFavorite(shop, user);
        removeShopFromFavorites.setVisible(false);
        addShopToFavorites.setVisible(true);
    }

    @FXML
    protected void previousPage() throws IOException {
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
    protected void onClickDepartmentImage(MouseEvent mouseEvent) {
        productTable.getItems().clear();
        List<ProductShop> productShopArrayList = null;
        Department department = null;
        int ref = 0;
        try {
            ref = Integer.parseInt(mouseEvent.getPickResult().getIntersectedNode().getId());
            for (Department dep : departmentArrayList) {
                if (dep.getDepartmentId() == ref) {
                    department = dep;
                    break;
                }
            }

            productShopArrayList = DepartmentHandler.findProductByDepartmentAndShop(shop, department);
            if (productShopArrayList != null) {
                ObservableList<ProductShop> observableListProducts = FXCollections.observableArrayList(productShopArrayList);
                productTable.setItems(observableListProducts);
            } else {
                logger.log(Level.INFO, Constants.NO_RESULT);
            }
        } catch (Exception e){}
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

    @FXML
    protected void onClickOnOffersFlyer() throws ExceptionBrowser {
        if (shop.getOffersFlyerPath() != null ) {
            if (!BrowserHandler.openWebpage(URI.create(shop.getOffersFlyerPath()))) {
                logger.log(Level.WARNING, "failed to open webpage");
            }
        }
    }

    @FXML
    protected void onAddToCartClick() throws ExceptionCart {
        ProductShop productShop = productTable.getSelectionModel().getSelectedItem();
        if (productShop != null) {
            CartElaboration.addOrderItemsToCart(productShop, 1);
        }
    }
}