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
import model.Constants;
import model.department.Department;
import model.product.ProductShop;
import model.shop.Shop;
import model.user.User;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static model.Constants.REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK;

public class ProductView {
    Shop shop = null;
    User user = null;
    ProductShop productShop = null;
    Department department = null;
    @FXML
    protected ImageView productPhoto;
    @FXML
    protected ImageView homepageImageView;
    @FXML
    protected ImageView addShopToFavorites;
    @FXML
    protected ImageView removeShopFromFavorites;
    @FXML
    protected ImageView cartImage;
    @FXML
    protected ImageView previousPage;
    @FXML
    protected ImageView discountedTagProd;
    @FXML
    protected Text textHi;
    @FXML
    protected Text nameProd;
    @FXML
    protected Text descriptionProd;
    @FXML
    protected Text priceProd;
    @FXML
    protected Text brandProd;
    @FXML
    protected Text favoriteText;
    @FXML
    protected Text discountedPriceProd;
    @FXML
    protected Text itemAddedText;

    protected InputStream stream = null;

    public void passParams(User user, Department department, ProductShop productShop, Shop shop) throws FileNotFoundException {

        this.user = user;
        this.department = department;
        this.productShop = productShop;
        this.shop = shop;

        itemAddedText.setVisible(false);
        textHi.setText(user.getUsername());
        descriptionProd.setText(productShop.getDescription());
        priceProd.setText(String.valueOf(productShop.getPrice()));
        brandProd.setText(productShop.getBrand());
        nameProd.setText(productShop.getName());
        checkDiscount(productShop);

        stream = new FileInputStream(productShop.getLogoImagepath());
        Image productImage = new Image(stream, 200, 200, false, false);
        productPhoto.setImage(productImage);

        if (ProductHandler.isFavoriteProduct(productShop, user)){
            //set to remove
            favoriteText.setText(Constants.REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK);
            removeShopFromFavorites.setVisible(true);
            addShopToFavorites.setVisible(false);
        } else {
            //set to add
            favoriteText.setText(Constants.ADD_TO_FAVORITE_SHOP_CAPSLOCK);
            removeShopFromFavorites.setVisible(false);
            addShopToFavorites.setVisible(true);
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
    public void previousPage() throws IOException {
        Parent root = null;
        if (shop != null || department != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("departProductView.fxml"));
            root = loader.load();
            DepartProductView departProductView = loader.getController();
            departProductView.passParams(user, department, shop);
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("searchShop.fxml"));
            root = loader.load();
            SearchProduct searchProduct = loader.getController();
            searchProduct.passUser(user);
        }
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) previousPage.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void addToFavorite() {
        ProductHandler.insertProductIntoFavorites(user,productShop);
        favoriteText.setText(REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK);
        removeShopFromFavorites.setVisible(true);
        addShopToFavorites.setVisible(false);
    }
    @FXML
    protected void removeFromFavorite(){
        ProductHandler.removeProductFromFavorites(user,productShop);
        favoriteText.setText(Constants.ADD_TO_FAVORITE_SHOP_CAPSLOCK);
        removeShopFromFavorites.setVisible(false);
        addShopToFavorites.setVisible(true);
    }

    @FXML
    protected void addRemoveFavoriteFromText(){
        if (favoriteText.getText() == REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK) {
            removeFromFavorite();
        } else {
            addToFavorite();
        }
    }

    @FXML
    protected void addToCartClick() throws Exception {
        if (!CartElaboration.addOrderItemsToCart(productShop, 1)) {
            throw new Exception("item not added in cart");
        }
        CartElaboration.readOrderItemsFromCart();
        itemAddedText.setVisible(true);
    }

    @FXML
    protected void checkDiscount(ProductShop productShop){
        productShop.setDiscountedPrice(0.50);
        if (productShop.getDiscountedPrice() != 0){
            discountedPriceProd.setText(String.valueOf(productShop.getDiscountedPrice()));
            priceProd.setStrikethrough(true);
        }
        else {
            discountedTagProd.setVisible(false);
            discountedPriceProd.setVisible(false);
        }
    }
}
