package view;

import control.BrowserHandler;
import control.DepartmentHandler;
import control.ShopHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Constants;
import model.Department.Department;
import model.Shop.Shop;
import model.User.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

import static model.Constants.REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK;


public class ShopView {
    Shop shop = null;
    User user = null;

    @FXML
    ImageView   homepageImageView, shopLogo,
                addShopToFavorites, removeShopFromFavorites,
                dep1, dep2, dep3, dep4, dep5, dep6, dep7, dep8, dep9, dep10, dep11, dep12, dep13, dep14;
    //departmentLabel
    @FXML
    Label   dep1Label, dep2Label, dep3Label, dep4Label, dep5Label, dep6Label, dep7Label, dep8Label, dep9Label,
            dep10Label, dep11Label, dep12Label, dep13Label, dep14Label;
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

    ArrayList<Label> labelDepartmentsArrayList = new ArrayList<>() {
        {
            add(dep1Label);
            add(dep2Label);
            add(dep3Label);
            add(dep4Label);
            add(dep5Label);
            add(dep6Label);
            add(dep7Label);
            add(dep8Label);
            add(dep9Label);
            add(dep10Label);
            add(dep11Label);
            add(dep12Label);
            add(dep13Label);
            add(dep14Label);
        }
    };
    @FXML
    Label   labelHi, labelShopName, labelShopAddress, labelShopOpeningTime, labelShopClosingTime, labelFavorite,
            labelPhoneShop;
    InputStream stream = null;
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

    public void passUser(User user) {
        this.user = user;
        labelHi.setText(user.getUsername());
    }

    public void passShop(Shop shop) throws FileNotFoundException {
        this.shop = shop;
        System.out.println(shop.getLogoImagepath());
        stream = new FileInputStream(shop.getLogoImagepath());
        Image shopImage = new Image(stream, 200, 200, false, false);
        shopLogo.setImage(shopImage);
        labelShopName.setText(shop.getShopName());

        ArrayList<Department> departmentArrayList = DepartmentHandler.findDepartmentByShop(shop);
        System.out.println(imageViewDepartmentsArrayList);
        System.out.println(labelDepartmentsArrayList);
        if (departmentArrayList != null && departmentArrayList.size() != 0) {
            for (int i = 0; i < departmentArrayList.size(); i++) {
                imageViewDepartmentsArrayList.get(i).setImage(new Image(new FileInputStream(departmentArrayList.get(i).getLogoImagepath())));
                labelDepartmentsArrayList.get(i).setText(departmentArrayList.get(i).getName());
            }
        }

        if (ShopHandler.isFavoriteShop(shop, user)) {
            //set to remove
            labelFavorite.setText(Constants.REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK);
            removeShopFromFavorites.setVisible(true);
            addShopToFavorites.setVisible(false);
        } else {
            //set to add
            labelFavorite.setText(Constants.ADD_TO_FAVORITE_SHOP_CAPSLOCK);
            removeShopFromFavorites.setVisible(false);
            addShopToFavorites.setVisible(true);
        }

        labelShopAddress.setText(shop.getCompleteAddress());
        labelShopOpeningTime.setText(shop.getOpeningTime());
        labelShopClosingTime.setText(shop.getClosingTime());
        labelPhoneShop.setText(shop.getPhone());
    }

    @FXML
    public void onClickGMapsHyperLink(){
        BrowserHandler.openWebpage(URI.create(shop.getGmapsLink()));
    }

    public void onMouseEnteredAddress(){
        labelShopAddress.setTextFill(Color.web("#ed2224"));
        labelShopAddress.setUnderline(true);
    }

    public void onMouseExitedAddress(){
        labelShopAddress.setTextFill(Color.web("#ffb703"));
        labelShopAddress.setUnderline(false);
    }

    //methods for adding and removing shops from favorite
    public void addToFavorite() {
        ShopHandler.insertShopIntoFavorite(shop, user);
        labelFavorite.setText(REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK);
        removeShopFromFavorites.setVisible(true);
        addShopToFavorites.setVisible(false);
    }

    public void removeFromFavorite(){
        ShopHandler.removeShopFromFavorite(shop, user);
        labelFavorite.setText(Constants.ADD_TO_FAVORITE_SHOP_CAPSLOCK);
        removeShopFromFavorites.setVisible(false);
        addShopToFavorites.setVisible(true);
    }

    public void addRemoveFavoriteFromLabel(){
        if (labelFavorite.getText() == REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK) {
            removeFromFavorite();
        } else {
            addToFavorite();
        }
    }
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
}
