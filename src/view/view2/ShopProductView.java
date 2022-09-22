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
import model.user.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ShopProductView {
    User user2SPV;
    SimpleProduct simpleProduct2SPV;
    InputStream stream2SPV = null;
    ProductShop productShop2SPV = null;

    @FXML
    protected ImageView productPhoto2SPV;
    @FXML
    protected ImageView homepageImageView2SPV;
    @FXML
    protected ImageView previousPage2SPV;
    @FXML
    protected ImageView cartImageView2SPV;
    @FXML
    protected ImageView addShopToFavorites2SPV;
    @FXML
    protected ImageView removeShopFromFavorites2SPV;
    @FXML
    protected Text nameProd2SPV;
    @FXML
    protected Text brandProd2SPV;
    @FXML
    protected Text productShopPrice12SPV;
    @FXML
    protected Text productShopPrice22SPV;
    @FXML
    protected Text sizeLabel2SPV;
    @FXML
    protected TextArea descriptionTextArea2SPV;

    public void passParams(User user2SPV, SimpleProduct simpleProduct2SPV, Shop shop2SPV) throws FileNotFoundException {
        this.user2SPV = user2SPV;
        this.simpleProduct2SPV = simpleProduct2SPV;

        this.productShop2SPV = ProductHandler.findProductShopByShopAndSimpleProduct(shop2SPV, simpleProduct2SPV);
        if (productShop2SPV != null) {
            brandProd2SPV.setText(productShop2SPV.getBrand());
            nameProd2SPV.setText(productShop2SPV.getName());
            descriptionTextArea2SPV.setText(productShop2SPV.getDescription());
            productShopPrice12SPV.setText(productShop2SPV.getPrice() + " " + productShop2SPV.getCurrency());
            productShopPrice22SPV.setText(productShop2SPV.getDiscountedPrice() + " " + productShop2SPV.getCurrency());
            sizeLabel2SPV.setText(productShop2SPV.getSize() + " " + productShop2SPV.getUnitOfMeasure());
            stream2SPV = new FileInputStream(simpleProduct2SPV.getLogoImagepath());
            Image productImage = new Image(stream2SPV, 200, 200, false, false);
            productPhoto2SPV.setImage(productImage);
            checkDiscount2SPV(productShop2SPV);
            if (ProductHandler.isFavoriteProduct(productShop2SPV, user2SPV)){
                //set to remove
                removeShopFromFavorites2SPV.setVisible(true);
                addShopToFavorites2SPV.setVisible(false);
            } else {
                //set to add
                removeShopFromFavorites2SPV.setVisible(false);
                addShopToFavorites2SPV.setVisible(true);
            }
        }
    }

    @FXML
    protected void onClickProfileImageView2SPV() throws IOException {
        FXMLLoader loader2SPV = new FXMLLoader(getClass().getResource("myProfile.fxml"));
        Parent root2SPV = loader2SPV.load();
        MyProfile myProfile2SPV = loader2SPV.getController();
        myProfile2SPV.passParams(user2SPV);
        Stage newStage2SPV = new Stage();
        newStage2SPV.setScene(new Scene(root2SPV));
        newStage2SPV.show();
        newStage2SPV.setResizable(false);
        Stage stage2SPV = (Stage) cartImageView2SPV.getScene().getWindow();
        stage2SPV.close();
    }

    @FXML
    protected void onHomepageImageClick2SPV() throws IOException, AddressException {
        FXMLLoader loader2SPV = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root2SPV = loader2SPV.load();
        Homepage homepage2SPV = loader2SPV.getController();
        homepage2SPV.passParams(user2SPV);
        Stage newStage2SPV = new Stage();
        newStage2SPV.setScene(new Scene(root2SPV));
        newStage2SPV.show();
        newStage2SPV.setResizable(false);
        Stage stage2SPV = (Stage) homepageImageView2SPV.getScene().getWindow();
        stage2SPV.close();
    }

    @FXML
    protected void addToFavorite2SPV() {
        ProductHandler.insertProductIntoFavorites(user2SPV,productShop2SPV);
        removeShopFromFavorites2SPV.setVisible(true);
        addShopToFavorites2SPV.setVisible(false);
    }
    @FXML
    protected void removeFromFavorite2SPV(){
        ProductHandler.removeProductFromFavorites(user2SPV,productShop2SPV);
        removeShopFromFavorites2SPV.setVisible(false);
        addShopToFavorites2SPV.setVisible(true);
    }

    @FXML
    protected void openCartAndPayment2SPV() throws IOException {
        FXMLLoader loader2SPV = new FXMLLoader(getClass().getResource("cartAndPayment.fxml"));
        Parent root2SPV = loader2SPV.load();
        CartAndPayment cartAndPayment2SPV = loader2SPV.getController();
        cartAndPayment2SPV.passParam(null, user2SPV);
        Stage newStage2SPV = new Stage();
        newStage2SPV.setScene(new Scene(root2SPV));
        newStage2SPV.show();
        newStage2SPV.setResizable(false);
        Stage stage2SPV = (Stage) cartImageView2SPV.getScene().getWindow();
        stage2SPV.close();
    }

    protected void checkDiscount2SPV(ProductShop productShop2SPV){
        if (productShop2SPV.getDiscountedPrice() != 0){
            productShopPrice12SPV.setStrikethrough(true);
        }
        else {
            productShopPrice22SPV.setVisible(false);
        }
    }
}
