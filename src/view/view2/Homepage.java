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
import model.Product.SimpleProduct;
import model.Shop.Shop;
import model.User.Buyer;
import model.User.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Homepage {
    @FXML
    protected ImageView cartImageView,
                        logoutImageView;

    @FXML
    protected ImageView shopImageView1,
                        shopImageView2,
                        shopImageView3,
                        shopImageView4;

    @FXML
    protected Text  shopText1,
                    shopText2,
                    shopText3,
                    shopText4;

    @FXML
    protected ChoiceBox choiceBox;

    @FXML
    protected TextField searchParam;

    @FXML
    protected TableView<Shop> shopTableView = new TableView<>();
    protected TableColumn<Shop, String> addressColumn;
    protected TableColumn<Shop, String> cityColumn;
    protected TableColumn<Shop, String> nameColumn;
    protected TableColumn<Shop, String> openingColumn;
    protected TableColumn<Shop, String> closingColumn;
    protected TableColumn<Shop, Double> distanceColumn;

    @FXML
    protected TableView<SimpleProduct> productTableView = new TableView<>();
    protected TableColumn<SimpleProduct, String> nameProductColumn;
    protected TableColumn<SimpleProduct, Double> sizeColumn;
    protected TableColumn<SimpleProduct, String> unitOfMeasureColumn;
    protected TableColumn<SimpleProduct, String> brandColumn;

    User user = null;
    ArrayList<Shop> shopArrayList;

    public void passParams(User user) throws FileNotFoundException, AddressException {
        this.user = user;

        ArrayList<Text> textArrayList = new ArrayList<>(){
            {
                add(shopText1);
                add(shopText2);
                add(shopText3);
                add(shopText4);
            }
        };
        ArrayList<ImageView> imageViewArrayList = new ArrayList<>() {
            {
                add(shopImageView1);
                add(shopImageView2);
                add(shopImageView3);
                add(shopImageView4);
            }
        };

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
        Login login = loader.getController();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) logoutImageView.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onClickOnShop(MouseEvent mouseEvent) throws IOException {
        Shop shop = null;
        int ref = Integer.parseInt(mouseEvent.getPickResult().getIntersectedNode().getId());
        for (Shop s : shopArrayList){
            if (s.getShopId() == ref) {
                shop = s;
                break;
            }
        }
        goToShop(shop);
    }

    @FXML
    protected void onClickOnShopTableView() throws IOException {
        Shop shop = shopTableView.getSelectionModel().getSelectedItem();
        if (shop != null) {
            goToShop(shop);
        }
    }

    @FXML
    protected void goToShop(Shop shop) throws IOException {
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


    @FXML
    protected void onClickOnLocation() throws AddressException {
        ArrayList<Shop> searchShopArrayList= null;
        distanceColumn.setVisible(true);
        shopTableView.getItems().clear();
        searchShopArrayList = ShopHandler.findShopNearbyWithParams(((Buyer) user).getBillingAddress(), false, Constants.SHOP_TYPE.get(0));
        productTableView.setVisible(true);
        shopTableView.setVisible(true);
        choiceBox.setValue(Constants.SHOP);
        searchParam.setText("My position");
        if (searchShopArrayList != null && searchShopArrayList.size() != 0) {
            ObservableList<Shop> observableListShop = FXCollections.observableArrayList(searchShopArrayList);
            if (observableListShop.size()!= 0 && observableListShop != null){
                shopTableView.setItems(observableListShop);
            }
        } else {
            System.out.println(Constants.NO_RESULT);
        }
    }

    @FXML
    protected void onSearchButtonClick() throws AddressException {
        Object selected = choiceBox.getSelectionModel().getSelectedItem();
        ArrayList<Shop> searchShopArrayList= null;
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
                    searchShopArrayList = ShopHandler.findShopByCityWithParams(searchString, false, Constants.ALL_TYPES);
                    if (searchShopArrayList != null && searchShopArrayList.size() != 0) {
                        ObservableList<Shop> observableListShop = FXCollections.observableArrayList(searchShopArrayList);
                        if (observableListShop.size() != 0 && observableListShop != null) {
                            shopTableView.setItems(observableListShop);
                        }
                    } else {
                        System.out.println(Constants.NO_RESULT);
                    }
                }
            }
            if (selected.toString().equals("Product")) {
                productTableView.setVisible(true);
                productTableView.getItems().clear();
                shopTableView.setVisible(false);
                searchSimpleProductArrayList = ProductHandler.findSimpleProductBy(searchParam.getText());
                if (searchSimpleProductArrayList != null && searchSimpleProductArrayList.size() != 0) {
                    ObservableList<SimpleProduct> observableListProducts = FXCollections.observableArrayList(searchSimpleProductArrayList);
                    if (observableListProducts.size()!= 0 && observableListProducts != null){
                        productTableView.setItems(observableListProducts);
                    }
                } else {
                    System.out.println(Constants.NO_RESULT);
                }
            }
        }
        else {
            System.out.println(Constants.NO_RESULT);
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