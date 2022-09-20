package view.view2;

import control.ProductHandler;
import exceptions.AddressException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.product.ProductShop;
import model.product.SimpleProduct;
import model.shop.Shop;
import model.shop.Shop2;
import model.user.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ShopProductView {
    User user;
    SimpleProduct simpleProduct;
    InputStream stream = null;
    ProductShop productShop = null;

    @FXML
    protected ImageView productPhoto;
    @FXML
    protected ImageView homepageImageView;
    @FXML
    protected ImageView previousPage;
    @FXML
    protected ImageView cartImageView;
    @FXML
    protected ImageView addShopToFavorites;
    @FXML
    protected ImageView removeShopFromFavorites;
    @FXML
    protected Text nameProd;
    @FXML
    protected Text brandProd;
    @FXML
    protected Text productShopPrice1;
    @FXML
    protected Text productShopPrice2;
    @FXML
    protected Text sizeLabel;
    @FXML
    protected TextArea descriptionTextArea;

    public void passParams(User user, SimpleProduct simpleProduct, Shop2 shop) throws FileNotFoundException {
        this.user = user;
        this.simpleProduct = simpleProduct;

        this.productShop = ProductHandler.findProductShopByShopAndSimpleProduct(shop, simpleProduct);
        if (productShop != null) {
            brandProd.setText(productShop.getBrand());
            nameProd.setText(productShop.getName());
            descriptionTextArea.setText(productShop.getDescription());
            productShopPrice1.setText(productShop.getPrice() + " " + productShop.getCurrency());
            productShopPrice2.setText(productShop.getDiscountedPrice() + " " + productShop.getCurrency());
            sizeLabel.setText(productShop.getSize() + " " + productShop.getUnitOfMeasure());
            stream = new FileInputStream(simpleProduct.getLogoImagepath());
            Image productImage = new Image(stream, 200, 200, false, false);
            productPhoto.setImage(productImage);
            checkDiscount(productShop);
            if (ProductHandler.isFavoriteProduct(productShop, user)){
                //set to remove
                removeShopFromFavorites.setVisible(true);
                addShopToFavorites.setVisible(false);
            } else {
                //set to add
                removeShopFromFavorites.setVisible(false);
                addShopToFavorites.setVisible(true);
            }
        }
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
        Stage stage = (Stage) cartImageView.getScene().getWindow();
        stage.close();
    }

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
    protected void addToFavorite() {
        ProductHandler.insertProductIntoFavorites(user,productShop);
        removeShopFromFavorites.setVisible(true);
        addShopToFavorites.setVisible(false);
    }
    @FXML
    protected void removeFromFavorite(){
        ProductHandler.removeProductFromFavorites(user,productShop);
        removeShopFromFavorites.setVisible(false);
        addShopToFavorites.setVisible(true);
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

    protected void checkDiscount(ProductShop productShop){
        if (productShop.getDiscountedPrice() != 0){
            productShopPrice1.setStrikethrough(true);
        }
        else {
            productShopPrice2.setVisible(false);
        }
    }
}
