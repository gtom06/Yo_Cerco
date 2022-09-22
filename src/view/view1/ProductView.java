package view.view1;


import control.CartElaboration;
import control.ProductHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import constants.Constants;
import model.department.Department;
import model.product.ProductShop;
import model.shop.Shop;
import model.user.User;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import static constants.Constants.REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK;

public class ProductView {
    Shop shop1PV = null;
    User user1PV = null;
    ProductShop productShop1PV = null;
    Department department1PV = null;
    @FXML
    protected ImageView productPhoto1PV;
    @FXML
    protected ImageView homepageImageView1PV;
    @FXML
    protected ImageView addShopToFavorites1PV;
    @FXML
    protected ImageView removeShopFromFavorites1PV;
    @FXML
    protected ImageView cartImage1PV;
    @FXML
    protected ImageView previousPage1PV;
    @FXML
    protected ImageView discountedTagProd1PV;
    @FXML
    protected Text textHi1PV;
    @FXML
    protected Text nameProd1PV;
    @FXML
    protected Text descriptionProd1PV;
    @FXML
    protected Text priceProd1PV;
    @FXML
    protected Text brandProd1PV;
    @FXML
    protected Text favoriteText1PV;
    @FXML
    protected Text discountedPriceProd1PV;
    @FXML
    protected Text itemAddedText1PV;

    protected InputStream stream1PV = null;

    static final Logger logger = Logger.getLogger(ProductView.class.getName());


    public void passParams(User user1PV, Department department1PV, ProductShop productShop1PV, Shop shop1PV) throws FileNotFoundException {

        this.user1PV = user1PV;
        this.department1PV = department1PV;
        this.productShop1PV = productShop1PV;
        this.shop1PV = shop1PV;

        itemAddedText1PV.setVisible(false);
        textHi1PV.setText(user1PV.getUsername());
        descriptionProd1PV.setText(productShop1PV.getDescription());
        priceProd1PV.setText(String.valueOf(productShop1PV.getPrice()));
        brandProd1PV.setText(productShop1PV.getBrand());
        nameProd1PV.setText(productShop1PV.getName());
        checkDiscount(productShop1PV);

        stream1PV = new FileInputStream(productShop1PV.getLogoImagepath());
        Image productImage1PV = new Image(stream1PV, 200, 200, false, false);
        productPhoto1PV.setImage(productImage1PV);

        if (ProductHandler.isFavoriteProduct(productShop1PV, user1PV)){
            //set to remove
            favoriteText1PV.setText(Constants.REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK);
            removeShopFromFavorites1PV.setVisible(true);
            addShopToFavorites1PV.setVisible(false);
        } else {
            //set to add
            favoriteText1PV.setText(Constants.ADD_TO_FAVORITE_SHOP_CAPSLOCK);
            removeShopFromFavorites1PV.setVisible(false);
            addShopToFavorites1PV.setVisible(true);
        }
    }

    @FXML
    protected void onHomepageImageClick1PV() throws IOException {
        FXMLLoader loader1PV = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root1PV = loader1PV.load();
        Homepage homepage1PV = loader1PV.getController();
        homepage1PV.passUser(user1PV);
        Stage newStage1PV = new Stage();
        newStage1PV.setScene(new Scene(root1PV));
        newStage1PV.show();
        newStage1PV.setResizable(false);
        Stage stage1PV = (Stage) homepageImageView1PV.getScene().getWindow();
        stage1PV.close();
    }

    @FXML
    public void previousPage1PV() throws IOException {
        Parent root1PV;
        if (shop1PV != null || department1PV != null) {
            FXMLLoader loader1PV = new FXMLLoader(getClass().getResource("departProductView.fxml"));
            root1PV = loader1PV.load();
            DepartProductView departProductView1 = loader1PV.getController();
            departProductView1.passParams(user1PV, department1PV, shop1PV);
        }
        else {
            FXMLLoader loader1PV = new FXMLLoader(getClass().getResource("searchShop.fxml"));
            root1PV = loader1PV.load();
            SearchProduct searchProduct1PV = loader1PV.getController();
            searchProduct1PV.passUser(user1PV);
        }
        Stage newStage1PV = new Stage();
        newStage1PV.setScene(new Scene(root1PV));
        newStage1PV.show();
        newStage1PV.setResizable(false);
        Stage stage1PV = (Stage) previousPage1PV.getScene().getWindow();
        stage1PV.close();
    }

    @FXML
    protected void addToFavorite1PV() {
        ProductHandler.insertProductIntoFavorites(user1PV,productShop1PV);
        favoriteText1PV.setText(REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK);
        removeShopFromFavorites1PV.setVisible(true);
        addShopToFavorites1PV.setVisible(false);
    }
    @FXML
    protected void removeFromFavorite1PV(){
        ProductHandler.removeProductFromFavorites(user1PV,productShop1PV);
        favoriteText1PV.setText(Constants.ADD_TO_FAVORITE_SHOP_CAPSLOCK);
        removeShopFromFavorites1PV.setVisible(false);
        addShopToFavorites1PV.setVisible(true);
    }

    @FXML
    protected void addRemoveFavoriteFromText1PV(){
        if (favoriteText1PV.getText().equals(REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK)) {
            removeFromFavorite1PV();
        } else {
            addToFavorite1PV();
        }
    }

    @FXML
    protected void addToCartClick1PV() throws Exception {
        if (!CartElaboration.addOrderItemsToCart(productShop1PV, 1)) {
            logger.log(Level.WARNING, "item not added to cart");
        }
        CartElaboration.readOrderItemsFromCart();
        itemAddedText1PV.setVisible(true);
    }

    @FXML
    protected void checkDiscount(ProductShop productShop1PV){
        productShop1PV.setDiscountedPrice(0.50);
        if (productShop1PV.getDiscountedPrice() != 0){
            discountedPriceProd1PV.setText(String.valueOf(productShop1PV.getDiscountedPrice()));
            priceProd1PV.setStrikethrough(true);
        }
        else {
            discountedTagProd1PV.setVisible(false);
            discountedPriceProd1PV.setVisible(false);
        }
    }
}
