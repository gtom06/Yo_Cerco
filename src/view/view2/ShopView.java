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
import constants.Constants;
import model.department.Department;
import model.product.ProductShop;
import model.shop.Shop;
import model.user.User;
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
    InputStream stream2SV = null;
    Shop shop2SV = null;
    User user2SV = null;
    List<Department> departmentArrayList2SV = null;
    @FXML
    protected Button addToCartButton2SV;
    @FXML
    protected ImageView homepageImageView2SV;
    @FXML
    protected ImageView shopLogo2SV;
    @FXML
    protected ImageView profileImageView2SV;
    @FXML
    protected ImageView cartImageView2SV;
    @FXML
    protected ImageView addShopToFavorites2SV;
    @FXML
    protected ImageView removeShopFromFavorites2SV;
    @FXML
    protected ImageView dep12SV;
    @FXML
    protected ImageView dep22SV;
    @FXML
    protected ImageView dep32SV;
    @FXML
    protected ImageView dep42SV;
    @FXML
    protected ImageView dep52SV;
    @FXML
    protected ImageView dep62SV;
    @FXML
    protected ImageView dep72SV;
    @FXML
    protected ImageView dep82SV;
    @FXML
    protected ImageView dep92SV;
    @FXML
    protected ImageView dep102SV;
    @FXML
    protected ImageView dep112SV;
    @FXML
    protected ImageView dep122SV;
    @FXML
    protected ImageView dep132SV;
    @FXML
    protected ImageView dep142SV;
    @FXML
    protected Text textShopName2SV;
    @FXML
    protected Text textShopAddress2SV;
    @FXML
    protected Text textShopTime2SV;
    @FXML
    protected Text textPhoneShop2SV;
    @FXML
    protected TableView<ProductShop> productTable2SV = new TableView<>();
    protected TableColumn<ProductShop, String> nameColumn2SV;
    protected TableColumn<ProductShop, String> brandColumn2SV;
    protected TableColumn<ProductShop, Float> sizeColumn2SV;
    protected TableColumn<ProductShop, String> unitOfMeasureColumn2SV;
    protected TableColumn<ProductShop, Integer> currencyColumn2SV;
    protected TableColumn<ProductShop, Integer> priceColumn2SV;

    static final Logger logger = Logger.getLogger(ShopView.class.getName());

    @FXML
    protected void onHomepageImageClick2SV() throws IOException, AddressException {
        FXMLLoader loader2SV = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root2SV = loader2SV.load();
        Homepage homepage2SV = loader2SV.getController();
        homepage2SV.passParams(user2SV);
        Stage newStage2SV = new Stage();
        newStage2SV.setScene(new Scene(root2SV));
        newStage2SV.show();
        newStage2SV.setResizable(false);
        Stage stage2SV = (Stage) homepageImageView2SV.getScene().getWindow();
        stage2SV.close();
    }

    @FXML
    protected void openCartAndPayment2SV() throws IOException {
        FXMLLoader loader2SV = new FXMLLoader(getClass().getResource("cartAndPayment.fxml"));
        Parent root2SV = loader2SV.load();
        CartAndPayment cartAndPayment2SV = loader2SV.getController();
        cartAndPayment2SV.passParam(null, user2SV);
        Stage newStage2SV = new Stage();
        newStage2SV.setScene(new Scene(root2SV));
        newStage2SV.show();
        newStage2SV.setResizable(false);
        Stage stage2SV = (Stage) cartImageView2SV.getScene().getWindow();
        stage2SV.close();
    }
    @FXML
    protected void onClickProfileImageView2SV() throws IOException {
        FXMLLoader loader2SV = new FXMLLoader(getClass().getResource("myProfile.fxml"));
        Parent root2SV = loader2SV.load();
        MyProfile myProfile2SV = loader2SV.getController();
        myProfile2SV.passParams(user2SV);
        Stage newStage2SV = new Stage();
        newStage2SV.setScene(new Scene(root2SV));
        newStage2SV.show();
        newStage2SV.setResizable(false);
        Stage stage2SV = (Stage) profileImageView2SV.getScene().getWindow();
        stage2SV.close();
    }

    public void passParams(User user2SV, Shop shop2SV) throws FileNotFoundException {
        this.user2SV = user2SV;
        this.shop2SV = shop2SV;

        stream2SV = new FileInputStream(shop2SV.getLogoImagepath());
        Image shopImage = new Image(stream2SV, 200, 200, false, false);
        shopLogo2SV.setImage(shopImage);
        textShopName2SV.setText(shop2SV.getShopName());
        ArrayList<ImageView> imageViewDepartmentsArrayList2SV = new ArrayList<>(
                Arrays.asList(
                        dep12SV,dep22SV,dep32SV,dep42SV,dep52SV,dep62SV,dep72SV,
                        dep82SV,dep92SV,dep102SV,dep112SV,dep122SV,dep132SV,dep142SV)
        );
        this.departmentArrayList2SV = DepartmentHandler.findDepartmentByShop(shop2SV);
        if (departmentArrayList2SV != null && !departmentArrayList2SV.isEmpty()) {
            for (int i = 0; i < departmentArrayList2SV.size(); i++) {
                imageViewDepartmentsArrayList2SV.get(i).setImage(new Image(new FileInputStream(departmentArrayList2SV.get(i).getLogoImagepath())));
                imageViewDepartmentsArrayList2SV.get(i).setId(String.valueOf(departmentArrayList2SV.get(i).getDepartmentId()));
            }
        }
        if (ShopHandler.isFavoriteShop(shop2SV, user2SV)) {
            //set to remove
            removeShopFromFavorites2SV.setVisible(true);
            addShopToFavorites2SV.setVisible(false);
        } else {
            //set to add
            removeShopFromFavorites2SV.setVisible(false);
            addShopToFavorites2SV.setVisible(true);
        }

        textShopAddress2SV.setText(shop2SV.getCompleteAddress());
        textShopTime2SV.setText(shop2SV.getOpeningTime() + " - " + shop2SV.getClosingTime());
        textPhoneShop2SV.setText(shop2SV.getPhone());
    }

    @FXML
    protected void onClickGMapsHyperLink2SV() throws ExceptionBrowser {
        BrowserHandler.openWebpage(URI.create(shop2SV.getGmapsLink()));
    }

    //methods for adding and removing shops from favorite
    @FXML
    protected void addToFavorite2SV() {
        ShopHandler.insertShopIntoFavorite(shop2SV, user2SV);
        removeShopFromFavorites2SV.setVisible(true);
        addShopToFavorites2SV.setVisible(false);
    }

    @FXML
    protected void removeFromFavorite2SV() {
        ShopHandler.removeShopFromFavorite(shop2SV, user2SV);
        removeShopFromFavorites2SV.setVisible(false);
        addShopToFavorites2SV.setVisible(true);
    }

    @FXML
    protected void previousPage2SV() throws IOException {
        FXMLLoader loader2SV = new FXMLLoader(getClass().getResource("searchShop.fxml"));
        Parent root2SV = loader2SV.load();
        SearchShop searchShop2SV = loader2SV.getController();
        searchShop2SV.passUser(user2SV);
        Stage newStage2SV = new Stage();
        newStage2SV.setScene(new Scene(root2SV));
        newStage2SV.show();
        newStage2SV.setResizable(false);
        Stage stage2SV = (Stage) homepageImageView2SV.getScene().getWindow();
        stage2SV.close();
    }

    @FXML
    protected void onClickDepartmentImage2SV(MouseEvent mouseEvent) {
        productTable2SV.getItems().clear();
        List<ProductShop> productShopArrayList2SV = null;
        Department department2SV = null;
        int ref;
        try {
            ref = Integer.parseInt(mouseEvent.getPickResult().getIntersectedNode().getId());
            for (Department dep : departmentArrayList2SV) {
                if (dep.getDepartmentId() == ref) {
                    department2SV = dep;
                    break;
                }
            }

            productShopArrayList2SV = DepartmentHandler.findProductByDepartmentAndShop(shop2SV, department2SV);
            if (productShopArrayList2SV != null) {
                ObservableList<ProductShop> observableListProducts2SV =
                        FXCollections.observableArrayList(productShopArrayList2SV);
                productTable2SV.setItems(observableListProducts2SV);
            } else {
                logger.log(Level.INFO, Constants.NO_RESULT);
            }
        } catch (Exception e){
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    @FXML
    public void initialize() {
        productTable2SV.setEditable(true);

        nameColumn2SV = new TableColumn<>("NAME");
        nameColumn2SV.setMinWidth(20);
        nameColumn2SV.setCellValueFactory(new PropertyValueFactory<>("name"));

        priceColumn2SV = new TableColumn<>("PRICE");
        priceColumn2SV.setMinWidth(50);
        priceColumn2SV.setCellValueFactory(new PropertyValueFactory<>("price"));

        brandColumn2SV = new TableColumn<>("BRAND");
        brandColumn2SV.setMinWidth(10);
        brandColumn2SV.setCellValueFactory(new PropertyValueFactory<>("brand"));

        sizeColumn2SV = new TableColumn<>("SIZE");
        sizeColumn2SV.setMinWidth(10);
        sizeColumn2SV.setCellValueFactory(new PropertyValueFactory<>("size"));

        unitOfMeasureColumn2SV = new TableColumn<>("UNIT OF MEASURE");
        unitOfMeasureColumn2SV.setMinWidth(10);
        unitOfMeasureColumn2SV.setCellValueFactory(new PropertyValueFactory<>("unitOfMeasure"));

        priceColumn2SV = new TableColumn<>("PRICE");
        priceColumn2SV.setMinWidth(50);
        priceColumn2SV.setCellValueFactory(new PropertyValueFactory<>("price"));

        currencyColumn2SV = new TableColumn<>("CURRENCY");
        currencyColumn2SV.setMinWidth(50);
        currencyColumn2SV.setCellValueFactory(new PropertyValueFactory<>("currency"));

        productTable2SV.getColumns().addAll(nameColumn2SV, brandColumn2SV, sizeColumn2SV, unitOfMeasureColumn2SV, priceColumn2SV, currencyColumn2SV);
    }

    @FXML
    protected void onClickOnOffersFlyer2SV() throws ExceptionBrowser {
        if (shop2SV.getOffersFlyerPath() != null && !BrowserHandler.openWebpage(URI.create(shop2SV.getOffersFlyerPath()))) {
            logger.log(Level.WARNING, "failed to open webpage");
        }
    }

    @FXML
    protected void onAddToCartClick2SV() throws ExceptionCart {
        ProductShop productShop2SV = productTable2SV.getSelectionModel().getSelectedItem();
        if (productShop2SV != null) {
            CartElaboration.addOrderItemsToCart(productShop2SV, 1);
        }
    }
}