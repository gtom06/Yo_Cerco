package view.view2;

import control.ProductHandler;
import control.ShopHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Product.ProductShop;
import model.Product.SimpleProduct;
import model.Shop.Shop;
import model.User.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ShopProductView {
    User user;
    SimpleProduct simpleProduct;

    @FXML
    ImageView productPhoto, homepageImageView, previousPage, cartImageView;
    @FXML
    Label nameProd, brandProd, productShopPrice, sizeLabel;
    @FXML
    TextArea descriptionTextArea;
    InputStream stream = null;


    public void passParams(User user, SimpleProduct simpleProduct, Shop shop) throws FileNotFoundException {
        this.user = user;
        this.simpleProduct = simpleProduct;

        ProductShop productShop = ProductHandler.findProductShopByShopAndSimpleProduct(shop, simpleProduct);
        brandProd.setText(productShop.getBrand());
        nameProd.setText(productShop.getName());
        descriptionTextArea.setText(productShop.getDescription());
        productShopPrice.setText(productShop.getPrice() + " " + productShop.getCurrency());
        sizeLabel.setText(productShop.getSize() + " " + productShop.getUnitOfMeasure());
        stream = new FileInputStream(simpleProduct.getLogoImagepath());
        Image productImage = new Image(stream, 200, 200, false, false);
        productPhoto.setImage(productImage);
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
        Stage stage = (Stage) cartImageView.getScene().getWindow();
        stage.close();
    }

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
    public void initialize(){

    }
}
