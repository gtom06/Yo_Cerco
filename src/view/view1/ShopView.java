package view.view1;

import control.BrowserHandler;
import control.DepartmentHandler;
import control.ShopHandler;
import exceptions.ExceptionBrowser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Constants;
import model.Department.Department;
import model.Shop.Shop;
import model.User.User;
import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static model.Constants.REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK;

public class ShopView {
    Shop shop = null;
    User user = null;
    List<Department> departmentArrayList = null;
    Department department = null;
    @FXML
    ImageView homepageImageView;
    @FXML
    ImageView shopLogo;
    @FXML
    ImageView addShopToFavorites;
    @FXML
    ImageView removeShopFromFavorites;
    @FXML
    ImageView offersFlyerTagShop;
    @FXML
    ImageView dep1;
    @FXML
    ImageView dep2;
    @FXML
    ImageView dep3;
    @FXML
    ImageView dep4;
    @FXML
    ImageView dep5;
    @FXML
    ImageView dep6;
    @FXML
    ImageView dep7;
    @FXML
    ImageView dep8;
    @FXML
    ImageView dep9;
    @FXML
    ImageView dep10;
    @FXML
    ImageView dep11;
    @FXML
    ImageView dep12;
    @FXML
    ImageView dep13;
    @FXML
    ImageView dep14;
    //departmentText
    @FXML
    Text dep1Text;
    @FXML
    Text dep2Text;
    @FXML
    Text dep3Text;
    @FXML
    Text dep4Text;
    @FXML
    Text dep5Text;
    @FXML
    Text dep6Text;
    @FXML
    Text dep7Text;
    @FXML
    Text dep8Text;
    @FXML
    Text dep9Text;
    @FXML
    Text dep10Text;
    @FXML
    Text dep11Text;
    @FXML
    Text dep12Text;
    @FXML
    Text dep13Text;
    @FXML
    Text dep14Text;
    @FXML
    Text TextHi;
    @FXML
    Text TextShopName;
    @FXML
    Text TextShopAddress;
    @FXML
    Text TextShopOpeningTime;
    @FXML
    Text TextShopClosingTime;
    @FXML
    Text TextFavorite;
    @FXML
    Text TextPhoneShop;
    @FXML
    Text offersFlyerShop;
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
        ArrayList<ImageView> imageViewDepartmentsArrayList = new ArrayList<>(
                Arrays.asList(dep1,dep2,dep3,dep4,dep5,dep6,dep7,dep8,dep9,dep10,dep11,dep12,dep13,dep14)
        );

        ArrayList<Text> TextDepartmentsArrayList = new ArrayList<>(
                Arrays.asList(dep1Text,dep2Text,dep3Text,dep4Text,dep5Text,dep6Text,dep7Text,dep8Text,dep9Text,
                        dep10Text, dep11Text,dep12Text,dep13Text,dep14Text)
        );
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
    protected void onClickGMapsHyperLink() throws ExceptionBrowser {
        BrowserHandler.openWebpage(URI.create(shop.getGmapsLink()));
    }

    @FXML
    protected void onMouseEnteredAddress(){
        TextShopAddress.setUnderline(true);
    }

    @FXML
    protected void onMouseEnteredFlyer(){
        offersFlyerShop.setUnderline(true);
    }

    @FXML
    protected void onMouseExitedAddress(){
        TextShopAddress.setUnderline(false);
    }

    @FXML
    protected void onMouseExitedFlyer(){
        offersFlyerShop.setUnderline(false);
    }

    //methods for adding and removing shops from favorite
    @FXML
    protected void addToFavorite() {
        ShopHandler.insertShopIntoFavorite(shop, user);
        TextFavorite.setText(REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK);
        removeShopFromFavorites.setVisible(true);
        addShopToFavorites.setVisible(false);
    }

    @FXML
    protected void removeFromFavorite(){
        ShopHandler.removeShopFromFavorite(shop, user);
        TextFavorite.setText(Constants.ADD_TO_FAVORITE_SHOP_CAPSLOCK);
        removeShopFromFavorites.setVisible(false);
        addShopToFavorites.setVisible(true);
    }

    @FXML
    protected void addRemoveFavoriteFromText(){
        if (TextFavorite.getText() == REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK) {
            removeFromFavorite();
        } else {
            addToFavorite();
        }
    }
    @FXML
    protected void previousPage() throws IOException {
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

    @FXML
    protected void onClickDepartmentImage(MouseEvent mouseEvent) throws IOException {

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

    @FXML
    protected void onClickOnOffersFlyer() throws ExceptionBrowser {
        if (shop.getOffersFlyerPath() != null ) {
            if (!BrowserHandler.openWebpage(URI.create(shop.getOffersFlyerPath()))) {
                System.out.println("failed to open webpage");
            }
        }
    }

    public void checkOffersFlyer(Shop shop){
        if (shop.getOffersFlyerPath().equals("") || shop.getOffersFlyerPath() == null ){
            offersFlyerShop.setVisible(false);
            offersFlyerTagShop.setVisible(false);
        }
        else {
            offersFlyerShop.setVisible(true);
            offersFlyerTagShop.setVisible(true);
        }
    }
}
