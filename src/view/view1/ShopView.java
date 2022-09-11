package view.view1;

import control.BrowserHandler;
import control.DepartmentHandler;
import control.ShopHandler;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Constants;
import model.Department.Department;
import model.Shop.Shop;
import model.User.User;

import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.io.*;
import java.net.URI;
import java.util.ArrayList;

import static model.Constants.REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK;


public class ShopView {
    Shop shop = null;
    User user = null;

    ArrayList<Department> departmentArrayList = null;

    Department department = null;

    @FXML
    ImageView   homepageImageView, shopLogo,
                addShopToFavorites, removeShopFromFavorites, offersFlyerTagShop,
                dep1, dep2, dep3, dep4, dep5, dep6, dep7, dep8, dep9, dep10, dep11, dep12, dep13, dep14;

    //departmentText
    @FXML
    Text dep1Text, dep2Text, dep3Text, dep4Text, dep5Text, dep6Text, dep7Text, dep8Text, dep9Text,
            dep10Text, dep11Text, dep12Text, dep13Text, dep14Text;

    @FXML
    Text   TextHi, TextShopName, TextShopAddress, TextShopOpeningTime, TextShopClosingTime, TextFavorite,
            TextPhoneShop, offersFlyerShop;
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
        TextHi.setText(user.getUsername());
    }

    public void passShop(Shop shop) throws FileNotFoundException {
        this.shop = shop;
        stream = new FileInputStream(shop.getLogoImagepath());
        Image shopImage = new Image(stream, 200, 200, false, false);
        shopLogo.setImage(shopImage);
        TextShopName.setText(shop.getShopName());
        checkOffersFlyer(shop);
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

        ArrayList<Text> TextDepartmentsArrayList = new ArrayList<>() {
            {
                add(dep1Text);
                add(dep2Text);
                add(dep3Text);
                add(dep4Text);
                add(dep5Text);
                add(dep6Text);
                add(dep7Text);
                add(dep8Text);
                add(dep9Text);
                add(dep10Text);
                add(dep11Text);
                add(dep12Text);
                add(dep13Text);
                add(dep14Text);
            }
        };
        this.departmentArrayList = DepartmentHandler.findDepartmentByShop(shop);
        if (departmentArrayList != null && departmentArrayList.size() != 0) {
            for (int i = 0; i < departmentArrayList.size(); i++) {
                imageViewDepartmentsArrayList.get(i).setImage(new Image(new FileInputStream(departmentArrayList.get(i).getLogoImagepath())));
                imageViewDepartmentsArrayList.get(i).setId(String.valueOf(departmentArrayList.get(i).getDepartmentId()));
                TextDepartmentsArrayList.get(i).setText(departmentArrayList.get(i).getName());
                TextDepartmentsArrayList.get(i).setId(String.valueOf(departmentArrayList.get(i).getDepartmentId()));
            }
        }
        if (ShopHandler.isFavoriteShop(shop, user)) {
            //set to remove
            TextFavorite.setText(Constants.REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK);
            removeShopFromFavorites.setVisible(true);
            addShopToFavorites.setVisible(false);
        } else {
            //set to add
            TextFavorite.setText(Constants.ADD_TO_FAVORITE_SHOP_CAPSLOCK);
            removeShopFromFavorites.setVisible(false);
            addShopToFavorites.setVisible(true);
        }
        TextShopAddress.setText(shop.getCompleteAddress());
        TextShopOpeningTime.setText(shop.getOpeningTime());
        TextShopClosingTime.setText(shop.getClosingTime());
        TextPhoneShop.setText(shop.getPhone());
    }

    @FXML
    public void onClickGMapsHyperLink(){
        BrowserHandler.openWebpage(URI.create(shop.getGmapsLink()));
    }

    public void onMouseEnteredAddress(){
        TextShopAddress.setUnderline(true);
    }

    public void onMouseExitedAddress(){
        TextShopAddress.setUnderline(false);
    }

    //methods for adding and removing shops from favorite
    public void addToFavorite() {
        ShopHandler.insertShopIntoFavorite(shop, user);
        TextFavorite.setText(REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK);
        removeShopFromFavorites.setVisible(true);
        addShopToFavorites.setVisible(false);
    }

    public void removeFromFavorite(){
        ShopHandler.removeShopFromFavorite(shop, user);
        TextFavorite.setText(Constants.ADD_TO_FAVORITE_SHOP_CAPSLOCK);
        removeShopFromFavorites.setVisible(false);
        addShopToFavorites.setVisible(true);
    }

    public void addRemoveFavoriteFromText(){
        if (TextFavorite.getText() == REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK) {
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

    public void onClickDepartmentImage(MouseEvent mouseEvent) throws IOException {

        int ref = Integer.parseInt(mouseEvent.getPickResult().getIntersectedNode().getId());
        for (Department dep : departmentArrayList){
            if (dep.getDepartmentId()==ref) {
                department = dep;
                break;
            }
        }
        department.setItems(DepartmentHandler.findProductByDepartmentAndShop(shop, department));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("departProductView.fxml"));
        Parent root = loader.load();
        DepartProductView departProductView = loader.getController();
        departProductView.passParams(user,department,shop);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) homepageImageView.getScene().getWindow();
        stage.close();

    }

    public void onClickOnOffersFlyer(){
        if (!BrowserHandler.openWebpage(URI.create(shop.getOffersFlyerPath()))) {
            System.out.println("failed to open webpage");
        }
    }

    public void checkOffersFlyer(Shop shop){
        if (shop.getOffersFlyerPath() == null || shop.getOffersFlyerPath().isBlank() || shop.getOffersFlyerPath().isBlank()){
            offersFlyerShop.setVisible(false);
            offersFlyerTagShop.setVisible(false);
        }
        else {
            offersFlyerShop.setVisible(true);
            offersFlyerTagShop.setVisible(true);
        }
    }
}
