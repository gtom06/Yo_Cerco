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
import model.Constants;
import model.product.SimpleProduct;
import model.shop.Shop;
import model.shop.Shop2;
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
    protected ImageView cartImageView;
    @FXML
    protected ImageView logoutImageView;
    @FXML
    protected ImageView shopImageView1;
    @FXML
    protected ImageView shopImageView2;
    @FXML
    protected ImageView shopImageView3;
    @FXML
    protected ImageView shopImageView4;
    @FXML
    protected Text shopText1;
    @FXML
    protected Text shopText2;
    @FXML
    protected Text shopText3;
    @FXML
    protected Text shopText4;
    @FXML
    protected ChoiceBox choiceBox;
    @FXML
    protected TextField searchParam;
    @FXML
    protected TableView<Shop2> shopTableView = new TableView<>();
    protected TableColumn<Shop2, String> addressColumn;
    protected TableColumn<Shop2, String> cityColumn;
    protected TableColumn<Shop2, String> nameColumn;
    protected TableColumn<Shop2, String> openingColumn;
    protected TableColumn<Shop2, String> closingColumn;
    protected TableColumn<Shop2, Double> distanceColumn;

    @FXML
    protected TableView<SimpleProduct> productTableView = new TableView<>();
    protected TableColumn<SimpleProduct, String> nameProductColumn;
    protected TableColumn<SimpleProduct, Double> sizeColumn;
    protected TableColumn<SimpleProduct, String> unitOfMeasureColumn;
    protected TableColumn<SimpleProduct, String> brandColumn;

    static final Logger logger = Logger.getLogger(Homepage.class.getName());

    User user = null;
    List<Shop2> shopArrayList;

    public void passParams(User user) throws FileNotFoundException, AddressException {
        this.user = user;
        ArrayList<Text> textArrayList = new ArrayList<>(
                Arrays.asList(shopText1, shopText2, shopText3, shopText4)
        );
        ArrayList<ImageView> imageViewArrayList = new ArrayList<>(
                Arrays.asList(shopImageView1, shopImageView2, shopImageView3, shopImageView4)
        );
        shopArrayList = ShopHandler.findShopNearbyWithParams(((Buyer) user).getBillingAddress(), false, Constants.SHOP_TYPE.get(0));
        if (shopArrayList != null && shopArrayList.size() != 0){
            for (int i = 0; i < 4; i++) {
                imageViewArrayList.get(i).setImage(new Image(new FileInputStream(shopArrayList.get(i).getLogoImagepath())));
                textArrayList.get(i).setText(shopArrayList.get(i).getShopName());
                imageViewArrayList.get(i).setId(String.valueOf(shopArrayList.get(i).getShopId()));
            }
        }
    }

    @FXML
    protected void openCartAndPayment() throws IOException {
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
    protected void onClickProfileImageView() throws IOException {
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
    protected void onLogoutButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) logoutImageView.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onClickOnShop(MouseEvent mouseEvent) {
        Shop2 shop = null;
        try {
            int ref = Integer.parseInt(mouseEvent.getPickResult().getIntersectedNode().getId());
            for (Shop2 s : shopArrayList){
                if (s.getShopId() == ref) {
                    shop = s;
                    break;
                }
            }
            goToShop(shop);
        } catch (Exception e) {
            logger.log(Level.INFO, "no selected shop");
        }
    }

    @FXML
    protected void onClickOnShopTableView() {
        try {
            Shop2 shop = shopTableView.getSelectionModel().getSelectedItem();
            if (shop != null) {
                goToShop(shop);
            }
        } catch (Exception e){
            logger.log(Level.INFO, "no selected shop");
        }
    }

    @FXML
    protected void goToShop(Shop2 shop) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("shopView.fxml"));
        Parent root = loader.load();
        ShopView shopView = loader.getController();
        shopView.passParams(user, shop);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) logoutImageView.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onClickOnProductTableView() throws IOException {
        SimpleProduct simpleProduct = productTableView.getSelectionModel().getSelectedItem();
        if (simpleProduct != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("generalProductView.fxml"));
            Parent root = loader.load();
            GeneralProductView generalProductView = loader.getController();
            generalProductView.passParams(user, simpleProduct);
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
            newStage.setResizable(false);
            Stage stage = (Stage) logoutImageView.getScene().getWindow();
            stage.close();
        }
    }


    @FXML
    protected void onClickOnLocation() throws AddressException {
        List<Shop2> searchShopArrayList;
        distanceColumn.setVisible(true);
        shopTableView.getItems().clear();
        searchShopArrayList = ShopHandler.findShopNearbyWithParams(((Buyer) user).getBillingAddress(), false, Constants.SHOP_TYPE.get(0));
        productTableView.setVisible(true);
        shopTableView.setVisible(true);
        choiceBox.setValue(Constants.SHOP);
        searchParam.setText("My position");
        if (searchShopArrayList != null && searchShopArrayList.size() != 0) {
            ObservableList<Shop2> observableListShop = FXCollections.observableArrayList(searchShopArrayList);
            if (observableListShop.size()!= 0){
                shopTableView.setItems(observableListShop);
            }
        } else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    @FXML
    protected void onSearchButtonClick() throws AddressException {
        Object selected = choiceBox.getSelectionModel().getSelectedItem();
        ArrayList<Shop2> searchShopArrayList;
        ArrayList<SimpleProduct> searchSimpleProductArrayList = null;

        if (selected != null){
            if (selected.toString().equals("Shop")){
                String searchString = searchParam.getText();
                if (searchString.equals("My position")) {
                    onClickOnLocation();
                } else {
                    shopTableView.getItems().clear();
                    distanceColumn.setVisible(false);
                    productTableView.setVisible(true);
                    shopTableView.setVisible(true);
                    distanceColumn.setVisible(false);
                    searchShopArrayList = (ArrayList<Shop2>) ShopHandler.findShopByCityWithParams(searchString, false, Constants.ALL_TYPES);
                    if (searchShopArrayList != null) {
                        ObservableList<Shop2> observableListShop = FXCollections.observableArrayList(searchShopArrayList);
                        if (!observableListShop.isEmpty()) {
                            shopTableView.setItems(observableListShop);
                        }
                    } else {
                        logger.log(Level.INFO, Constants.NO_RESULT);
                    }
                }
            }
            if (selected.toString().equals("Product")) {
                productTableView.setVisible(true);
                productTableView.getItems().clear();
                shopTableView.setVisible(false);
                searchSimpleProductArrayList = (ArrayList<SimpleProduct>) ProductHandler.findSimpleProductBy(searchParam.getText());
                if (searchSimpleProductArrayList != null && !searchSimpleProductArrayList.isEmpty()) {
                    ObservableList<SimpleProduct> observableListProducts = FXCollections.observableArrayList(searchSimpleProductArrayList);
                    if (observableListProducts.size()!= 0){
                        productTableView.setItems(observableListProducts);
                    }
                } else {
                    logger.log(Level.INFO, Constants.NO_RESULT);
                }
            }
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    @FXML
    public void initialize() {
        choiceBox.getItems().add(Constants.SHOP);
        choiceBox.getItems().add(Constants.PRODUCT);
        choiceBox.setValue(Constants.SHOP);

        shopTableView.setEditable(true);
        addressColumn = new TableColumn<>(Constants.ADDRESS);
        cityColumn = new TableColumn<>(Constants.CITY);
        nameColumn = new TableColumn<>(Constants.SHOP_NAME);
        openingColumn = new TableColumn<>(Constants.OPENING);
        closingColumn = new TableColumn<>(Constants.CLOSING);
        distanceColumn = new TableColumn<>(Constants.DISTANCE);
        nameProductColumn = new TableColumn<>("Name");
        sizeColumn = new TableColumn<>("Size");
        unitOfMeasureColumn = new TableColumn<>("Unit of measure");
        brandColumn = new TableColumn<>("Brand");
        addressColumn.setMinWidth(200);
        cityColumn.setMinWidth(50);
        nameColumn.setMinWidth(200);
        openingColumn.setMinWidth(10);
        closingColumn.setMinWidth(10);
        distanceColumn.setMinWidth(10);
        nameProductColumn.setMinWidth(50);
        sizeColumn.setMinWidth(10);
        unitOfMeasureColumn.setMinWidth(50);
        brandColumn.setMinWidth(70);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("shopName"));
        openingColumn.setCellValueFactory(new PropertyValueFactory<>("openingTime"));
        closingColumn.setCellValueFactory(new PropertyValueFactory<>("closingTime"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        nameProductColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        unitOfMeasureColumn.setCellValueFactory(new PropertyValueFactory<>("unitOfMeasure"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        shopTableView.getColumns().addAll(nameColumn, addressColumn, cityColumn, openingColumn, closingColumn, distanceColumn);
        productTableView.getColumns().addAll(nameProductColumn, sizeColumn, unitOfMeasureColumn,brandColumn);
        distanceColumn.setVisible(false);
    }

    public void openFavorites() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("favorite.fxml"));
        Parent root = loader.load();
        Favorite favorite = loader.getController();
        favorite.passParams(user);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) logoutImageView.getScene().getWindow();
        stage.close();
    }
}