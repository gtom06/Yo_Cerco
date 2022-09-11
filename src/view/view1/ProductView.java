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
import model.Department.Department;
import model.Product.ProductShop;
import model.Shop.Shop;
import model.User.User;

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
    ImageView productPhoto, homepageImageView, addShopToFavorites,
            removeShopFromFavorites, cartImage, previousPage, discountedTagProd;
    @FXML
    Text textHi, nameProd, descriptionProd, priceProd, brandProd, favoriteText, discountedPrice;

    InputStream stream = null;

    public void passParams(User user, Department department, ProductShop productShop, Shop shop) throws FileNotFoundException {

        this.user = user;
        this.department = department;
        this.productShop = productShop;
        this.shop = shop;

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

    public void addToFavorite() {
        ProductHandler.insertProductIntoFavorites(user,productShop);
        favoriteText.setText(REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK);
        removeShopFromFavorites.setVisible(true);
        addShopToFavorites.setVisible(false);
    }
    public void removeFromFavorite(){
        ProductHandler.removeProductFromFavorites(user,productShop);
        favoriteText.setText(Constants.ADD_TO_FAVORITE_SHOP_CAPSLOCK);
        removeShopFromFavorites.setVisible(false);
        addShopToFavorites.setVisible(true);
    }

    public void addRemoveFavoriteFromText(){
        if (favoriteText.getText() == REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK) {
            removeFromFavorite();
        } else {
            addToFavorite();
        }
    }

    @FXML
    public void addToCartClick() throws Exception {
        if (!CartElaboration.addOrderItemsToCart(productShop, 1)) {
            throw new Exception("item not added in cart");
        }
        CartElaboration.readOrderItemsFromCart();
    }

    @FXML
    public void checkDiscount(ProductShop productShop){
        productShop.setDiscountedPrice(0.50);
        if (productShop.getDiscountedPrice() != 0){
            discountedPrice.setText(String.valueOf(productShop.getDiscountedPrice()));
            priceProd.setStrikethrough(true);
        }
        else {
            discountedTagProd.setVisible(false);
            discountedPrice.setVisible(false);
        }
    }
}
