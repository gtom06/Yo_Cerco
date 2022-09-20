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
import model.department.Department;
import model.shop.Shop;
import model.user.User;
import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static model.Constants.REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK;

public class ShopView {
    Shop shop = null;
    User user = null;
    List<Department> departmentArrayList = null;
    Department department = null;
    @FXML
    protected ImageView homepageImageView;
    @FXML
    protected ImageView shopLogo;
    @FXML
    protected ImageView addShopToFavorites;
    @FXML
    protected ImageView removeShopFromFavorites;
    @FXML
    protected ImageView offersFlyerTagShop;
    @FXML
    protected ImageView dep1;
    @FXML
    protected ImageView dep2;
    @FXML
    protected ImageView dep3;
    @FXML
    protected ImageView dep4;
    @FXML
    protected ImageView dep5;
    @FXML
    protected ImageView dep6;
    @FXML
    protected ImageView dep7;
    @FXML
    protected ImageView dep8;
    @FXML
    protected ImageView dep9;
    @FXML
    protected ImageView dep10;
    @FXML
    protected ImageView dep11;
    @FXML
    protected ImageView dep12;
    @FXML
    protected ImageView dep13;
    @FXML
    protected ImageView dep14;
    //departmentText
    @FXML
    protected Text dep1Text;
    @FXML
    protected Text dep2Text;
    @FXML
    protected Text dep3Text;
    @FXML
    protected Text dep4Text;
    @FXML
    protected Text dep5Text;
    @FXML
    protected Text dep6Text;
    @FXML
    protected Text dep7Text;
    @FXML
    protected Text dep8Text;
    @FXML
    protected Text dep9Text;
    @FXML
    protected Text dep10Text;
    @FXML
    protected Text dep11Text;
    @FXML
    protected Text dep12Text;
    @FXML
    protected Text dep13Text;
    @FXML
    protected Text dep14Text;
    @FXML
    protected Text textHi;
    @FXML
    protected Text textShopName;
    @FXML
    protected Text textShopAddress;
    @FXML
    protected Text textShopOpeningTime;
    @FXML
    protected Text textShopClosingTime;
    @FXML
    protected Text textFavorite;
    @FXML
    protected Text textPhoneShop;
    @FXML
    protected Text offersFlyerShop;
    InputStream stream = null;
    static final Logger logger = Logger.getLogger(view.view1.ShopView.class.getName());

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
        textHi.setText(user.getUsername());
    }

    public void passShop(Shop shop) throws FileNotFoundException {
        this.shop = shop;
        stream = new FileInputStream(shop.getLogoImagepath());
        Image shopImage = new Image(stream, 200, 200, false, false);
        shopLogo.setImage(shopImage);
        textShopName.setText(shop.getShopName());
        checkOffersFlyer(shop);
        ArrayList<ImageView> imageViewDepartmentsArrayList = new ArrayList<>(
                Arrays.asList(dep1,dep2,dep3,dep4,dep5,dep6,dep7,dep8,dep9,dep10,dep11,dep12,dep13,dep14)
        );

        ArrayList<Text> textDepartmentsArrayList = new ArrayList<>(
                Arrays.asList(dep1Text,dep2Text,dep3Text,dep4Text,dep5Text,dep6Text,dep7Text,dep8Text,dep9Text,
                        dep10Text, dep11Text,dep12Text,dep13Text,dep14Text)
        );
        this.departmentArrayList = DepartmentHandler.findDepartmentByShop(shop);
        if (departmentArrayList != null && !departmentArrayList.isEmpty()) {
            for (int i = 0; i < departmentArrayList.size(); i++) {
                imageViewDepartmentsArrayList.get(i).setImage(new Image(new FileInputStream(departmentArrayList.get(i).getLogoImagepath())));
                imageViewDepartmentsArrayList.get(i).setId(String.valueOf(departmentArrayList.get(i).getDepartmentId()));
                textDepartmentsArrayList.get(i).setText(departmentArrayList.get(i).getName());
                textDepartmentsArrayList.get(i).setId(String.valueOf(departmentArrayList.get(i).getDepartmentId()));
            }
        }
        if (ShopHandler.isFavoriteShop(shop, user)) {
            //set to remove
            textFavorite.setText(Constants.REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK);
            removeShopFromFavorites.setVisible(true);
            addShopToFavorites.setVisible(false);
        } else {
            //set to add
            textFavorite.setText(Constants.ADD_TO_FAVORITE_SHOP_CAPSLOCK);
            removeShopFromFavorites.setVisible(false);
            addShopToFavorites.setVisible(true);
        }
        textShopAddress.setText(shop.getCompleteAddress());
        textShopOpeningTime.setText(shop.getOpeningTime());
        textShopClosingTime.setText(shop.getClosingTime());
        textPhoneShop.setText(shop.getPhone());
    }

    @FXML
    protected void onClickGMapsHyperLink() throws ExceptionBrowser {
        BrowserHandler.openWebpage(URI.create(shop.getGmapsLink()));
    }

    @FXML
    protected void onMouseEnteredAddress() {
        textShopAddress.setUnderline(true);
    }

    @FXML
    protected void onMouseEnteredFlyer(){
        offersFlyerShop.setUnderline(true);
    }

    @FXML
    protected void onMouseExitedAddress(){
        textShopAddress.setUnderline(false);
    }

    @FXML
    protected void onMouseExitedFlyer(){
        offersFlyerShop.setUnderline(false);
    }

    //methods for adding and removing shops from favorite
    @FXML
    protected void addToFavorite() {
        ShopHandler.insertShopIntoFavorite(shop, user);
        textFavorite.setText(REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK);
        removeShopFromFavorites.setVisible(true);
        addShopToFavorites.setVisible(false);
    }

    @FXML
    protected void removeFromFavorite(){
        ShopHandler.removeShopFromFavorite(shop, user);
        textFavorite.setText(Constants.ADD_TO_FAVORITE_SHOP_CAPSLOCK);
        removeShopFromFavorites.setVisible(false);
        addShopToFavorites.setVisible(true);
    }

    @FXML
    protected void addRemoveFavoriteFromText(){
        if (textFavorite.getText() == REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK) {
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
        if (shop.getOffersFlyerPath() != null && !BrowserHandler.openWebpage(URI.create(shop.getOffersFlyerPath()))) {
            logger.log(Level.WARNING, "failed to open webpage");
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
