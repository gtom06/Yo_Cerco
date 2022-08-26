package view;

import control.DepartmentHandler;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Constants;
import model.Department.Department;
import model.Shop.Shop;
import model.User.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static model.Constants.ADD_TO_FAVORITE_SHOP_CAPSLOCK;
import static model.Constants.REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK;


public class ShopView {
    User user = null;
    @FXML
    ImageView   homepageImageView, shopLogo,
                addShopToFavorites, removeShopFromFavorites,
                dep1,dep2,dep3,dep4,dep5,dep6,dep7,dep8,dep9;
    @FXML
    TableView<Department> tableView = new TableView<>();
    TableColumn<Department, String> nameColumn;
    @FXML
    Label labelHi;
    @FXML
    Label labelShopName, labelShopAddress, labelShopOpeningTime, labelFavorite;

    //departmentLabel
    @FXML
    Label fruitLabel, vegetablesLabel, curedMeatLabel, cheeseLabel, beverageLabel, meatLabel,
    foodstuffLabel, milkLabel, breakfastLabel, icecreamLabel, cosmeticsLabel, housewareLabel,
    petsLabel;

    ArrayList<ImageView> imageDep= new ArrayList<>();
    ArrayList<String> dep = new ArrayList<>();
    String shopId = "";
    InputStream stream = null;
    Image image = null;

    Shop shop = null;

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
        stream = new FileInputStream(shop.getLogoImagepath());
        image = new Image(stream, 200, 200, false, false);
        labelShopName.setText(shop.getShopName());

        fruitLabel.setText(Constants.FRUIT_DEPARTMENT);
        vegetablesLabel.setText(Constants.VEGETABLES_DEPARTMENT);
        curedMeatLabel.setText(Constants.CURED_MEAT_DEPARTMENT);
        cheeseLabel.setText(Constants.CHEESE_DEPARTMENT);
        beverageLabel.setText(Constants.BEVERAGE_DEPARTMENT);
        meatLabel.setText(Constants.MEAT_DEPARTMENT);
        foodstuffLabel.setText(Constants.FOODSTUFF_DEPARTMENT);
        milkLabel.setText(Constants.MILK_BUTTER_EGGS_DEPARTMENT);
        breakfastLabel.setText(Constants.BREAKFAST_SWEETS_CAKES_DEPARTMENT);
        icecreamLabel.setText(Constants.ICECREAM_FROZEN_DEPARTMENT);
        cosmeticsLabel.setText(Constants.COSMETICS_DEPARTMENT);
        housewareLabel.setText(Constants.HOUSEWARE_DEPARTMENT);
        petsLabel.setText(Constants.PETS_DEPARTMENT);

        //todo: mettere X sui departments non presenti

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

        shopLogo.setImage(image);
        shopId = String.valueOf(shop.getShopId());
        labelShopAddress.setText(shop.getCompleteAddress());
        labelShopOpeningTime.setText(shop.getOpeningTime());
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
