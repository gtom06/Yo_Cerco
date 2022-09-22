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
    Shop shop1 = null;
    User user1 = null;
    List<Department> departmentArrayList1 = null;
    Department department1 = null;
    @FXML
    protected ImageView homepageImageView1;
    @FXML
    protected ImageView shopLogo1;
    @FXML
    protected ImageView addShopToFavorites1;
    @FXML
    protected ImageView removeShopFromFavorites1;
    @FXML
    protected ImageView offersFlyerTagShop1;
    @FXML
    protected ImageView dep11;
    @FXML
    protected ImageView dep21;
    @FXML
    protected ImageView dep31;
    @FXML
    protected ImageView dep41;
    @FXML
    protected ImageView dep51;
    @FXML
    protected ImageView dep61;
    @FXML
    protected ImageView dep71;
    @FXML
    protected ImageView dep81;
    @FXML
    protected ImageView dep91;
    @FXML
    protected ImageView dep101;
    @FXML
    protected ImageView dep111;
    @FXML
    protected ImageView dep121;
    @FXML
    protected ImageView dep131;
    @FXML
    protected ImageView dep141;
    //departmentText
    @FXML
    protected Text dep1Text1;
    @FXML
    protected Text dep2Text1;
    @FXML
    protected Text dep3Text1;
    @FXML
    protected Text dep4Text1;
    @FXML
    protected Text dep5Text1;
    @FXML
    protected Text dep6Text1;
    @FXML
    protected Text dep7Text1;
    @FXML
    protected Text dep8Text1;
    @FXML
    protected Text dep9Text1;
    @FXML
    protected Text dep10Text1;
    @FXML
    protected Text dep11Text1;
    @FXML
    protected Text dep12Text1;
    @FXML
    protected Text dep13Text1;
    @FXML
    protected Text dep14Text1;
    @FXML
    protected Text textHi1;
    @FXML
    protected Text textShopName1;
    @FXML
    protected Text textShopAddress1;
    @FXML
    protected Text textShopOpeningTime1;
    @FXML
    protected Text textShopClosingTime1;
    @FXML
    protected Text textFavorite1;
    @FXML
    protected Text textPhoneShop1;
    @FXML
    protected Text offersFlyerShop1;
    InputStream stream1 = null;
    static final Logger logger = Logger.getLogger(view.view1.ShopView.class.getName());

    @FXML
    protected void onHomepageImageClick() throws IOException {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root1 = loader1.load();
        Homepage homepage1 = loader1.getController();
        homepage1.passUser(user1);
        Stage newStage1 = new Stage();
        newStage1.setScene(new Scene(root1));
        newStage1.show();
        newStage1.setResizable(false);
        Stage stage1 = (Stage) homepageImageView1.getScene().getWindow();
        stage1.close();
    }


    public void passUser(User user) {
        this.user1 = user;
        textHi1.setText(user.getUsername());
    }

    public void passShop(Shop shop1) throws FileNotFoundException {
        this.shop1 = shop1;
        stream1 = new FileInputStream(shop1.getLogoImagepath());
        Image shopImage = new Image(stream1, 200, 200, false, false);
        shopLogo1.setImage(shopImage);
        textShopName1.setText(shop1.getShopName());
        checkOffersFlyer(shop1);
        ArrayList<ImageView> imageViewDepartmentsArrayList1 = new ArrayList<>(
                Arrays.asList(dep11,dep21,dep31,dep41,dep51,dep61,dep71,dep81,dep91,dep101,dep111,dep121,dep131,dep141)
        );

        ArrayList<Text> textDepartmentsArrayList1 = new ArrayList<>(
                Arrays.asList(dep1Text1,dep2Text1,dep3Text1,dep4Text1,dep5Text1,dep6Text1,dep7Text1,dep8Text1,dep9Text1,
                        dep10Text1, dep11Text1,dep12Text1,dep13Text1,dep14Text1)
        );
        this.departmentArrayList1 = DepartmentHandler.findDepartmentByShop(shop1);
        if (departmentArrayList1 != null && !departmentArrayList1.isEmpty()) {
            for (int i = 0; i < departmentArrayList1.size(); i++) {
                imageViewDepartmentsArrayList1.get(i).setImage(new Image(new FileInputStream(departmentArrayList1.get(i).getLogoImagepath())));
                imageViewDepartmentsArrayList1.get(i).setId(String.valueOf(departmentArrayList1.get(i).getDepartmentId()));
                textDepartmentsArrayList1.get(i).setText(departmentArrayList1.get(i).getName());
                textDepartmentsArrayList1.get(i).setId(String.valueOf(departmentArrayList1.get(i).getDepartmentId()));
            }
        }
        if (ShopHandler.isFavoriteShop(shop1, user1)) {
            //set to remove
            textFavorite1.setText(Constants.REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK);
            removeShopFromFavorites1.setVisible(true);
            addShopToFavorites1.setVisible(false);
        } else {
            //set to add
            textFavorite1.setText(Constants.ADD_TO_FAVORITE_SHOP_CAPSLOCK);
            removeShopFromFavorites1.setVisible(false);
            addShopToFavorites1.setVisible(true);
        }
        textShopAddress1.setText(shop1.getCompleteAddress());
        textShopOpeningTime1.setText(shop1.getOpeningTime());
        textShopClosingTime1.setText(shop1.getClosingTime());
        textPhoneShop1.setText(shop1.getPhone());
    }

    @FXML
    protected void onClickGMapsHyperLink() throws ExceptionBrowser {
        BrowserHandler.openWebpage(URI.create(shop1.getGmapsLink()));
    }

    @FXML
    protected void onMouseEnteredAddress() {
        textShopAddress1.setUnderline(true);
    }

    @FXML
    protected void onMouseEnteredFlyer(){
        offersFlyerShop1.setUnderline(true);
    }

    @FXML
    protected void onMouseExitedAddress(){
        textShopAddress1.setUnderline(false);
    }

    @FXML
    protected void onMouseExitedFlyer(){
        offersFlyerShop1.setUnderline(false);
    }

    //methods for adding and removing shops from favorite
    @FXML
    protected void addToFavorite() {
        ShopHandler.insertShopIntoFavorite(shop1, user1);
        textFavorite1.setText(REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK);
        removeShopFromFavorites1.setVisible(true);
        addShopToFavorites1.setVisible(false);
    }

    @FXML
    protected void removeFromFavorite(){
        ShopHandler.removeShopFromFavorite(shop1, user1);
        textFavorite1.setText(Constants.ADD_TO_FAVORITE_SHOP_CAPSLOCK);
        removeShopFromFavorites1.setVisible(false);
        addShopToFavorites1.setVisible(true);
    }

    @FXML
    protected void addRemoveFavoriteFromText(){
        if (textFavorite1.getText() == REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK) {
            removeFromFavorite();
        } else {
            addToFavorite();
        }
    }
    @FXML
    protected void previousPage() throws IOException {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("searchShop.fxml"));
        Parent root1 = loader1.load();
        SearchShop searchShop1 = loader1.getController();
        searchShop1.passUser(user1);
        Stage newStage1 = new Stage();
        newStage1.setScene(new Scene(root1));
        newStage1.show();
        newStage1.setResizable(false);
        Stage stage1 = (Stage) homepageImageView1.getScene().getWindow();
        stage1.close();
    }

    @FXML
    protected void onClickDepartmentImage(MouseEvent mouseEvent) throws IOException {

        int ref1 = Integer.parseInt(mouseEvent.getPickResult().getIntersectedNode().getId());
        for (Department dep1 : departmentArrayList1){
            if (dep1.getDepartmentId()==ref1) {
                department1 = dep1;
                break;
            }
        }
        department1.setItems(DepartmentHandler.findProductByDepartmentAndShop(shop1, department1));

        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("departProductView.fxml"));
        Parent root1 = loader1.load();
        DepartProductView departProductView1 = loader1.getController();
        departProductView1.passParams(user1,department1,shop1);
        Stage newStage1 = new Stage();
        newStage1.setScene(new Scene(root1));
        newStage1.show();
        newStage1.setResizable(false);
        Stage stage1 = (Stage) homepageImageView1.getScene().getWindow();
        stage1.close();

    }

    @FXML
    protected void onClickOnOffersFlyer() throws ExceptionBrowser {
        if (shop1.getOffersFlyerPath() != null && !BrowserHandler.openWebpage(URI.create(shop1.getOffersFlyerPath()))) {
            logger.log(Level.WARNING, "failed to open webpage");
        }
    }

    public void checkOffersFlyer(Shop shop1){
        if (shop1.getOffersFlyerPath().equals("") || shop1.getOffersFlyerPath() == null ){
            offersFlyerShop1.setVisible(false);
            offersFlyerTagShop1.setVisible(false);
        }
        else {
            offersFlyerShop1.setVisible(true);
            offersFlyerTagShop1.setVisible(true);
        }
    }
}
