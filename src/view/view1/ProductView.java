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
    Shop shop1 = null;
    User user1 = null;
    ProductShop productShop1 = null;
    Department department1 = null;
    @FXML
    protected ImageView productPhoto1;
    @FXML
    protected ImageView homepageImageView1;
    @FXML
    protected ImageView addShopToFavorites1;
    @FXML
    protected ImageView removeShopFromFavorites1;
    @FXML
    protected ImageView cartImage1;
    @FXML
    protected ImageView previousPage1;
    @FXML
    protected ImageView discountedTagProd1;
    @FXML
    protected Text textHi1;
    @FXML
    protected Text nameProd1;
    @FXML
    protected Text descriptionProd1;
    @FXML
    protected Text priceProd1;
    @FXML
    protected Text brandProd1;
    @FXML
    protected Text favoriteText1;
    @FXML
    protected Text discountedPriceProd1;
    @FXML
    protected Text itemAddedText1;

    protected InputStream stream1 = null;

    static final Logger logger = Logger.getLogger(ProductView.class.getName());


    public void passParams(User user1, Department department1, ProductShop productShop1, Shop shop1) throws FileNotFoundException {

        this.user1 = user1;
        this.department1 = department1;
        this.productShop1 = productShop1;
        this.shop1 = shop1;

        itemAddedText1.setVisible(false);
        textHi1.setText(user1.getUsername());
        descriptionProd1.setText(productShop1.getDescription());
        priceProd1.setText(String.valueOf(productShop1.getPrice()));
        brandProd1.setText(productShop1.getBrand());
        nameProd1.setText(productShop1.getName());
        checkDiscount(productShop1);

        stream1 = new FileInputStream(productShop1.getLogoImagepath());
        Image productImage1 = new Image(stream1, 200, 200, false, false);
        productPhoto1.setImage(productImage1);

        if (ProductHandler.isFavoriteProduct(productShop1, user1)){
            //set to remove
            favoriteText1.setText(Constants.REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK);
            removeShopFromFavorites1.setVisible(true);
            addShopToFavorites1.setVisible(false);
        } else {
            //set to add
            favoriteText1.setText(Constants.ADD_TO_FAVORITE_SHOP_CAPSLOCK);
            removeShopFromFavorites1.setVisible(false);
            addShopToFavorites1.setVisible(true);
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
        Stage stage1 = (Stage) homepageImageView1.getScene().getWindow();
        stage1.close();
    }

    @FXML
    public void previousPage() throws IOException {
        Parent root1;
        if (shop1 != null || department1 != null) {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("departProductView.fxml"));
            root1 = loader1.load();
            DepartProductView departProductView1 = loader1.getController();
            departProductView1.passParams(user1, department1, shop1);
        }
        else {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("searchShop.fxml"));
            root1 = loader1.load();
            SearchProduct searchProduct1 = loader1.getController();
            searchProduct1.passUser(user1);
        }
        Stage newStage1 = new Stage();
        newStage1.setScene(new Scene(root1));
        newStage1.show();
        newStage1.setResizable(false);
        Stage stage1 = (Stage) previousPage1.getScene().getWindow();
        stage1.close();
    }

    @FXML
    protected void addToFavorite() {
        ProductHandler.insertProductIntoFavorites(user1,productShop1);
        favoriteText1.setText(REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK);
        removeShopFromFavorites1.setVisible(true);
        addShopToFavorites1.setVisible(false);
    }
    @FXML
    protected void removeFromFavorite(){
        ProductHandler.removeProductFromFavorites(user1,productShop1);
        favoriteText1.setText(Constants.ADD_TO_FAVORITE_SHOP_CAPSLOCK);
        removeShopFromFavorites1.setVisible(false);
        addShopToFavorites1.setVisible(true);
    }

    @FXML
    protected void addRemoveFavoriteFromText(){
        if (favoriteText1.getText().equals(REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK)) {
            removeFromFavorite();
        } else {
            addToFavorite();
        }
    }

    @FXML
    protected void addToCartClick() throws Exception {
        if (!CartElaboration.addOrderItemsToCart(productShop1, 1)) {
            logger.log(Level.WARNING, "item not added to cart");
        }
        CartElaboration.readOrderItemsFromCart();
        itemAddedText1.setVisible(true);
    }

    @FXML
    protected void checkDiscount(ProductShop productShop1){
        productShop1.setDiscountedPrice(0.50);
        if (productShop1.getDiscountedPrice() != 0){
            discountedPriceProd1.setText(String.valueOf(productShop1.getDiscountedPrice()));
            priceProd1.setStrikethrough(true);
        }
        else {
            discountedTagProd1.setVisible(false);
            discountedPriceProd1.setVisible(false);
        }
    }
}
