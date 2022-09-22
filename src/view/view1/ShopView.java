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
import constants.Constants;
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

import static constants.Constants.REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK;

public class ShopView {
    Shop shop1SV = null;
    User user1SV = null;
    List<Department> departmentArrayList1SV = null;
    Department department1SV = null;
    @FXML
    protected ImageView homepageImageView1SV;
    @FXML
    protected ImageView shopLogo1SV;
    @FXML
    protected ImageView addShopToFavorites1SV;
    @FXML
    protected ImageView removeShopFromFavorites1SV;
    @FXML
    protected ImageView offersFlyerTagShop1SV;
    @FXML
    protected ImageView dep11SV;
    @FXML
    protected ImageView dep21SV;
    @FXML
    protected ImageView dep31SV;
    @FXML
    protected ImageView dep41SV;
    @FXML
    protected ImageView dep51SV;
    @FXML
    protected ImageView dep61SV;
    @FXML
    protected ImageView dep71SV;
    @FXML
    protected ImageView dep81SV;
    @FXML
    protected ImageView dep91SV;
    @FXML
    protected ImageView dep101SV;
    @FXML
    protected ImageView dep111SV;
    @FXML
    protected ImageView dep121SV;
    @FXML
    protected ImageView dep131SV;
    @FXML
    protected ImageView dep141SV;
    //departmentText
    @FXML
    protected Text dep1Text1SV;
    @FXML
    protected Text dep2Text1SV;
    @FXML
    protected Text dep3Text1SV;
    @FXML
    protected Text dep4Text1SV;
    @FXML
    protected Text dep5Text1SV;
    @FXML
    protected Text dep6Text1SV;
    @FXML
    protected Text dep7Text1SV;
    @FXML
    protected Text dep8Text1SV;
    @FXML
    protected Text dep9Text1SV;
    @FXML
    protected Text dep10Text1SV;
    @FXML
    protected Text dep11Text1SV;
    @FXML
    protected Text dep12Text1SV;
    @FXML
    protected Text dep13Text1SV;
    @FXML
    protected Text dep14Text1SV;
    @FXML
    protected Text textHi1SV;
    @FXML
    protected Text textShopName1SV;
    @FXML
    protected Text textShopAddress1SV;
    @FXML
    protected Text textShopOpeningTime1SV;
    @FXML
    protected Text textShopClosingTime1SV;
    @FXML
    protected Text textFavorite1SV;
    @FXML
    protected Text textPhoneShop1SV;
    @FXML
    protected Text offersFlyerShop1SV;
    InputStream stream1SV = null;
    static final Logger logger = Logger.getLogger(view.view1.ShopView.class.getName());

    @FXML
    protected void onHomepageImageClick1SV() throws IOException {
        FXMLLoader loader1SV = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root1SV = loader1SV.load();
        Homepage homepage1SV = loader1SV.getController();
        homepage1SV.passUser(user1SV);
        Stage newStage1SV = new Stage();
        newStage1SV.setScene(new Scene(root1SV));
        newStage1SV.show();
        newStage1SV.setResizable(false);
        Stage stage1SV = (Stage) homepageImageView1SV.getScene().getWindow();
        stage1SV.close();
    }


    public void passUser(User user1SV) {
        this.user1SV = user1SV;
        textHi1SV.setText(user1SV.getUsername());
    }

    public void passShop(Shop shop1SV) throws FileNotFoundException {
        this.shop1SV = shop1SV;
        stream1SV = new FileInputStream(shop1SV.getLogoImagepath());
        Image shopImage1SV = new Image(stream1SV, 200, 200, false, false);
        shopLogo1SV.setImage(shopImage1SV);
        textShopName1SV.setText(shop1SV.getShopName());
        checkOffersFlyer1SV(shop1SV);
        ArrayList<ImageView> imageViewDepartmentsArrayList1SV = new ArrayList<>(
                Arrays.asList(dep11SV,dep21SV,dep31SV,dep41SV,dep51SV,dep61SV,dep71SV,dep81SV,dep91SV,dep101SV,dep111SV,dep121SV,dep131SV,dep141SV)
        );

        ArrayList<Text> textDepartmentsArrayList1SV = new ArrayList<>(
                Arrays.asList(dep1Text1SV,dep2Text1SV,dep3Text1SV,dep4Text1SV,dep5Text1SV,dep6Text1SV,dep7Text1SV,dep8Text1SV,dep9Text1SV,
                        dep10Text1SV, dep11Text1SV,dep12Text1SV,dep13Text1SV,dep14Text1SV)
        );
        this.departmentArrayList1SV = DepartmentHandler.findDepartmentByShop(shop1SV);
        if (departmentArrayList1SV != null && !departmentArrayList1SV.isEmpty()) {
            for (int i = 0; i < departmentArrayList1SV.size(); i++) {
                imageViewDepartmentsArrayList1SV.get(i).setImage(new Image(new FileInputStream(departmentArrayList1SV.get(i).getLogoImagepath())));
                imageViewDepartmentsArrayList1SV.get(i).setId(String.valueOf(departmentArrayList1SV.get(i).getDepartmentId()));
                textDepartmentsArrayList1SV.get(i).setText(departmentArrayList1SV.get(i).getName());
                textDepartmentsArrayList1SV.get(i).setId(String.valueOf(departmentArrayList1SV.get(i).getDepartmentId()));
            }
        }
        if (ShopHandler.isFavoriteShop(shop1SV, user1SV)) {
            //set to remove
            textFavorite1SV.setText(Constants.REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK);
            removeShopFromFavorites1SV.setVisible(true);
            addShopToFavorites1SV.setVisible(false);
        } else {
            //set to add
            textFavorite1SV.setText(Constants.ADD_TO_FAVORITE_SHOP_CAPSLOCK);
            removeShopFromFavorites1SV.setVisible(false);
            addShopToFavorites1SV.setVisible(true);
        }
        textShopAddress1SV.setText(shop1SV.getCompleteAddress());
        textShopOpeningTime1SV.setText(shop1SV.getOpeningTime());
        textShopClosingTime1SV.setText(shop1SV.getClosingTime());
        textPhoneShop1SV.setText(shop1SV.getPhone());
    }

    @FXML
    protected void onClickGMapsHyperLink1SV() throws ExceptionBrowser {
        BrowserHandler.openWebpage(URI.create(shop1SV.getGmapsLink()));
    }

    @FXML
    protected void onMouseEnteredAddress1SV() {
        textShopAddress1SV.setUnderline(true);
    }

    @FXML
    protected void onMouseEnteredFlyer1SV(){
        offersFlyerShop1SV.setUnderline(true);
    }

    @FXML
    protected void onMouseExitedAddress1SV(){
        textShopAddress1SV.setUnderline(false);
    }

    @FXML
    protected void onMouseExitedFlyer1SV(){
        offersFlyerShop1SV.setUnderline(false);
    }

    //methods for adding and removing shops from favorite
    @FXML
    protected void addToFavorite1SV() {
        ShopHandler.insertShopIntoFavorite(shop1SV, user1SV);
        textFavorite1SV.setText(REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK);
        removeShopFromFavorites1SV.setVisible(true);
        addShopToFavorites1SV.setVisible(false);
    }

    @FXML
    protected void removeFromFavorite1SV(){
        ShopHandler.removeShopFromFavorite(shop1SV, user1SV);
        textFavorite1SV.setText(Constants.ADD_TO_FAVORITE_SHOP_CAPSLOCK);
        removeShopFromFavorites1SV.setVisible(false);
        addShopToFavorites1SV.setVisible(true);
    }

    @FXML
    protected void addRemoveFavoriteFromText1SV(){
        if (textFavorite1SV.getText().equals(REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK)) {
            removeFromFavorite1SV();
        } else {
            addToFavorite1SV();
        }
    }
    @FXML
    protected void previousPage1SV() throws IOException {
        FXMLLoader loader1SV = new FXMLLoader(getClass().getResource("searchShop.fxml"));
        Parent root1SV = loader1SV.load();
        SearchShop searchShop1SV = loader1SV.getController();
        searchShop1SV.passUser(user1SV);
        Stage newStage1SV = new Stage();
        newStage1SV.setScene(new Scene(root1SV));
        newStage1SV.show();
        newStage1SV.setResizable(false);
        Stage stage1SV = (Stage) homepageImageView1SV.getScene().getWindow();
        stage1SV.close();
    }

    @FXML
    protected void onClickDepartmentImage1SV(MouseEvent mouseEvent) throws IOException {

        int ref1SV = Integer.parseInt(mouseEvent.getPickResult().getIntersectedNode().getId());
        for (Department dep1SV : departmentArrayList1SV){
            if (dep1SV.getDepartmentId()==ref1SV) {
                department1SV = dep1SV;
                break;
            }
        }
        department1SV.setItems(DepartmentHandler.findProductByDepartmentAndShop(shop1SV, department1SV));

        FXMLLoader loader1SV = new FXMLLoader(getClass().getResource("departProductView.fxml"));
        Parent root1SV = loader1SV.load();
        DepartProductView departProductView1SV = loader1SV.getController();
        departProductView1SV.passParams(user1SV,department1SV,shop1SV);
        Stage newStage1SV = new Stage();
        newStage1SV.setScene(new Scene(root1SV));
        newStage1SV.show();
        newStage1SV.setResizable(false);
        Stage stage1SV = (Stage) homepageImageView1SV.getScene().getWindow();
        stage1SV.close();

    }

    @FXML
    protected void onClickOnOffersFlyer1SV() throws ExceptionBrowser {
        if (shop1SV.getOffersFlyerPath() != null && !BrowserHandler.openWebpage(URI.create(shop1SV.getOffersFlyerPath()))) {
            logger.log(Level.WARNING, "failed to open webpage");
        }
    }

    public void checkOffersFlyer1SV(Shop shop1SV){
        if (shop1SV.getOffersFlyerPath().equals("") || shop1SV.getOffersFlyerPath() == null ){
            offersFlyerShop1SV.setVisible(false);
            offersFlyerTagShop1SV.setVisible(false);
        }
        else {
            offersFlyerShop1SV.setVisible(true);
            offersFlyerTagShop1SV.setVisible(true);
        }
    }
}
