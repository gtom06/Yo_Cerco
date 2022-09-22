package view.view2;

import control.ProductHandler;
import control.ShopHandler;
import exceptions.AddressException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import constants.Constants;
import model.product.SimpleProduct;
import model.shop.Shop;
import model.user.Buyer;
import model.user.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Homepage {
    @FXML
    protected ImageView cartImageView2H;
    @FXML
    protected ImageView logoutImageView2H;
    @FXML
    protected ImageView shopImageView12H;
    @FXML
    protected ImageView shopImageView22H;
    @FXML
    protected ImageView shopImageView32H;
    @FXML
    protected ImageView shopImageView42H;
    @FXML
    protected Text shopText12H;
    @FXML
    protected Text shopText22H;
    @FXML
    protected Text shopText32H;
    @FXML
    protected Text shopText42H;
    @FXML
    protected ChoiceBox choiceBox2H;
    @FXML
    protected TextField searchParam2H;
    @FXML
    protected TableView<Shop> shopTableView2H = new TableView<>();
    protected TableColumn<Shop, String> addressColumn2H;
    protected TableColumn<Shop, String> cityColumn2H;
    protected TableColumn<Shop, String> nameColumn2H;
    protected TableColumn<Shop, String> openingColumn2H;
    protected TableColumn<Shop, String> closingColumn2H;
    protected TableColumn<Shop, Double> distanceColumn2H;

    @FXML
    protected TableView<SimpleProduct> productTableView2H = new TableView<>();
    protected TableColumn<SimpleProduct, String> nameProductColumn2H;
    protected TableColumn<SimpleProduct, Double> sizeColumn2H;
    protected TableColumn<SimpleProduct, String> unitOfMeasureColumn2H;
    protected TableColumn<SimpleProduct, String> brandColumn2H;

    static final Logger logger = Logger.getLogger(Homepage.class.getName());

    User user2H = null;
    List<Shop> shopArrayList2H;

    public void passParams(User user2H) throws FileNotFoundException, AddressException {
        this.user2H = user2H;
        ArrayList<Text> textArrayList2H = new ArrayList<>(
                Arrays.asList(shopText12H, shopText22H, shopText32H, shopText42H)
        );
        ArrayList<ImageView> imageViewArrayList2H = new ArrayList<>(
                Arrays.asList(shopImageView12H, shopImageView22H, shopImageView32H, shopImageView42H)
        );
        shopArrayList2H = ShopHandler.findShopNearbyWithParams(((Buyer) user2H).getBillingAddress(), false, Constants.SHOP_TYPE.get(0));
        if (shopArrayList2H != null && shopArrayList2H.size() != 0){
            for (int i = 0; i < 4; i++) {
                imageViewArrayList2H.get(i).setImage(new Image(new FileInputStream(shopArrayList2H.get(i).getLogoImagepath())));
                textArrayList2H.get(i).setText(shopArrayList2H.get(i).getShopName());
                imageViewArrayList2H.get(i).setId(String.valueOf(shopArrayList2H.get(i).getShopId()));
            }
        }
    }

    @FXML
    protected void openCartAndPayment2H() throws IOException {
        FXMLLoader loader2H = new FXMLLoader(getClass().getResource("cartAndPayment.fxml"));
        Parent root2H = loader2H.load();
        CartAndPayment cartAndPayment2H = loader2H.getController();
        cartAndPayment2H.passParam(null, user2H);
        Stage newStage2H = new Stage();
        newStage2H.setScene(new Scene(root2H));
        newStage2H.show();
        newStage2H.setResizable(false);
        Stage stage2H = (Stage) cartImageView2H.getScene().getWindow();
        stage2H.close();
    }

    @FXML
    protected void onClickProfileImageView2H() throws IOException {
        FXMLLoader loader2H = new FXMLLoader(getClass().getResource("myProfile.fxml"));
        Parent root2H = loader2H.load();
        MyProfile myProfile2H = loader2H.getController();
        myProfile2H.passParams(user2H);
        Stage newStage2H = new Stage();
        newStage2H.setScene(new Scene(root2H));
        newStage2H.show();
        newStage2H.setResizable(false);
        Stage stage2H = (Stage) cartImageView2H.getScene().getWindow();
        stage2H.close();
    }

    @FXML
    protected void onLogoutButtonClick2H() throws IOException {
        FXMLLoader loader2H = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root2H = loader2H.load();
        Stage newStage2H = new Stage();
        newStage2H.setScene(new Scene(root2H));
        newStage2H.show();
        newStage2H.setResizable(false);
        Stage stage2H = (Stage) logoutImageView2H.getScene().getWindow();
        stage2H.close();
    }

    @FXML
    protected void onClickOnShop2H(MouseEvent mouseEvent) {
        Shop shop2H = null;
        try {
            int ref2H = Integer.parseInt(mouseEvent.getPickResult().getIntersectedNode().getId());
            for (Shop s : shopArrayList2H){
                if (s.getShopId() == ref2H) {
                    shop2H = s;
                    break;
                }
            }
            goToShop2H(shop2H);
        } catch (Exception e) {
            logger.log(Level.INFO, "no selected shop");
        }
    }

    @FXML
    protected void onClickOnShopTableView2H() {
        try {
            Shop shop2H = shopTableView2H.getSelectionModel().getSelectedItem();
            if (shop2H != null) {
                goToShop2H(shop2H);
            }
        } catch (Exception e){
            logger.log(Level.INFO, "no selected shop");
        }
    }

    @FXML
    protected void goToShop2H(Shop shop2H) throws IOException {
        FXMLLoader loader2H = new FXMLLoader(getClass().getResource("shopView.fxml"));
        Parent root2H = loader2H.load();
        ShopView shopView2H = loader2H.getController();
        shopView2H.passParams(user2H, shop2H);
        Stage newStage2H = new Stage();
        newStage2H.setScene(new Scene(root2H));
        newStage2H.show();
        newStage2H.setResizable(false);
        Stage stage2H = (Stage) logoutImageView2H.getScene().getWindow();
        stage2H.close();
    }

    @FXML
    protected void onClickOnProductTableView2H() throws IOException {
        SimpleProduct simpleProduct2H = productTableView2H.getSelectionModel().getSelectedItem();
        if (simpleProduct2H != null) {
            FXMLLoader loader2H = new FXMLLoader(getClass().getResource("generalProductView.fxml"));
            Parent root2H = loader2H.load();
            GeneralProductView generalProductView2H = loader2H.getController();
            generalProductView2H.passParams(user2H, simpleProduct2H);
            Stage newStage2H = new Stage();
            newStage2H.setScene(new Scene(root2H));
            newStage2H.show();
            newStage2H.setResizable(false);
            Stage stage2H = (Stage) logoutImageView2H.getScene().getWindow();
            stage2H.close();
        }
    }


    @FXML
    protected void onClickOnLocation2H() throws AddressException {
        List<Shop> searchShopArrayList2H;
        distanceColumn2H.setVisible(true);
        shopTableView2H.getItems().clear();
        searchShopArrayList2H = ShopHandler.findShopNearbyWithParams(((Buyer) user2H).getBillingAddress(), false, Constants.SHOP_TYPE.get(0));
        productTableView2H.setVisible(true);
        shopTableView2H.setVisible(true);
        choiceBox2H.setValue(Constants.SHOP);
        searchParam2H.setText("My position");
        if (searchShopArrayList2H != null && searchShopArrayList2H.size() != 0) {
            ObservableList<Shop> observableListShop2H = FXCollections.observableArrayList(searchShopArrayList2H);
            if (observableListShop2H.size()!= 0){
                shopTableView2H.setItems(observableListShop2H);
            }
        } else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    protected void fillShopTableView2H() throws AddressException {
        ArrayList<Shop> searchShopArrayList2H;
        if (choiceBox2H.getSelectionModel().getSelectedItem().toString().equals("Shop")){
            String searchString2H = searchParam2H.getText();
            if (searchString2H.equals("My position")) {
                onClickOnLocation2H();
            } else {
                shopTableView2H.getItems().clear();
                distanceColumn2H.setVisible(false);
                productTableView2H.setVisible(true);
                shopTableView2H.setVisible(true);
                distanceColumn2H.setVisible(false);
                searchShopArrayList2H = (ArrayList<Shop>) ShopHandler.findShopByCityWithParams(searchString2H, false, Constants.ALL_TYPES);
                if (searchShopArrayList2H != null) {
                    ObservableList<Shop> observableListShop2H = FXCollections.observableArrayList(searchShopArrayList2H);
                    if (!observableListShop2H.isEmpty()) {
                        shopTableView2H.setItems(observableListShop2H);
                    }
                } else {
                    logger.log(Level.INFO, Constants.NO_RESULT);
                }
            }
        }
    }

    protected void fillProductTableView2H(){
        ArrayList<SimpleProduct> searchSimpleProductArrayList2H = null;
        if (choiceBox2H.getSelectionModel().getSelectedItem().toString().equals("Product")) {
            productTableView2H.setVisible(true);
            productTableView2H.getItems().clear();
            shopTableView2H.setVisible(false);
            searchSimpleProductArrayList2H = (ArrayList<SimpleProduct>) ProductHandler.findSimpleProductBy(searchParam2H.getText());
            if (searchSimpleProductArrayList2H != null && !searchSimpleProductArrayList2H.isEmpty()) {
                ObservableList<SimpleProduct> observableListProducts = FXCollections.observableArrayList(searchSimpleProductArrayList2H);
                if (observableListProducts.size()!= 0){
                    productTableView2H.setItems(observableListProducts);
                }
            } else {
                logger.log(Level.INFO, Constants.NO_RESULT);
            }
        }
    }

    @FXML
    protected void onSearchButtonClick2H() throws AddressException {
        Object selected = choiceBox2H.getSelectionModel().getSelectedItem();
        if (selected != null){
            if (selected == "Product"){
                fillProductTableView2H();
            }
            else if (selected == "Shop"){
                fillShopTableView2H();
            } else {
                logger.log(Level.INFO, Constants.NO_RESULT);
            }
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    @FXML
    public void initialize() {
        choiceBox2H.getItems().add(Constants.SHOP);
        choiceBox2H.getItems().add(Constants.PRODUCT);
        choiceBox2H.setValue(Constants.SHOP);

        shopTableView2H.setEditable(true);
        addressColumn2H = new TableColumn<>(Constants.ADDRESS);
        cityColumn2H = new TableColumn<>(Constants.CITY);
        nameColumn2H = new TableColumn<>(Constants.SHOP_NAME);
        openingColumn2H = new TableColumn<>(Constants.OPENING);
        closingColumn2H = new TableColumn<>(Constants.CLOSING);
        distanceColumn2H = new TableColumn<>(Constants.DISTANCE);
        nameProductColumn2H = new TableColumn<>("Name");
        sizeColumn2H = new TableColumn<>("Size");
        unitOfMeasureColumn2H = new TableColumn<>("Unit of measure");
        brandColumn2H = new TableColumn<>("Brand");
        addressColumn2H.setMinWidth(200);
        cityColumn2H.setMinWidth(50);
        nameColumn2H.setMinWidth(200);
        openingColumn2H.setMinWidth(10);
        closingColumn2H.setMinWidth(10);
        distanceColumn2H.setMinWidth(10);
        nameProductColumn2H.setMinWidth(50);
        sizeColumn2H.setMinWidth(10);
        unitOfMeasureColumn2H.setMinWidth(50);
        brandColumn2H.setMinWidth(70);
        addressColumn2H.setCellValueFactory(new PropertyValueFactory<>("completeAddress"));
        cityColumn2H.setCellValueFactory(new PropertyValueFactory<>("city"));
        nameColumn2H.setCellValueFactory(new PropertyValueFactory<>("shopName"));
        openingColumn2H.setCellValueFactory(new PropertyValueFactory<>("openingTime"));
        closingColumn2H.setCellValueFactory(new PropertyValueFactory<>("closingTime"));
        distanceColumn2H.setCellValueFactory(new PropertyValueFactory<>("distance"));
        nameProductColumn2H.setCellValueFactory(new PropertyValueFactory<>("name"));
        sizeColumn2H.setCellValueFactory(new PropertyValueFactory<>("size"));
        unitOfMeasureColumn2H.setCellValueFactory(new PropertyValueFactory<>("unitOfMeasure"));
        brandColumn2H.setCellValueFactory(new PropertyValueFactory<>("brand"));
        shopTableView2H.getColumns().addAll(nameColumn2H, addressColumn2H, cityColumn2H, openingColumn2H, closingColumn2H, distanceColumn2H);
        productTableView2H.getColumns().addAll(nameProductColumn2H, sizeColumn2H, unitOfMeasureColumn2H,brandColumn2H);
        distanceColumn2H.setVisible(false);
    }

    public void openFavorites2H() throws IOException {
        FXMLLoader loader2H = new FXMLLoader(getClass().getResource("favorite.fxml"));
        Parent root2H = loader2H.load();
        Favorite favorite2H = loader2H.getController();
        favorite2H.passParams(user2H);
        Stage newStage2H = new Stage();
        newStage2H.setScene(new Scene(root2H));
        newStage2H.show();
        newStage2H.setResizable(false);
        Stage stage2H = (Stage) logoutImageView2H.getScene().getWindow();
        stage2H.close();
    }
}