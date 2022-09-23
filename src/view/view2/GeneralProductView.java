package view.view2;

import control.ShopHandler;
import exceptions.AddressException;
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
import constants.Constants;
import model.product.SimpleProduct;
import model.shop.Shop;
import model.user.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneralProductView {
    User user2GPV;
    SimpleProduct simpleProduct2GPV;

    @FXML
    protected ImageView productPhoto2GPV;
    @FXML
    protected ImageView homepageImageView2GPV;
    @FXML
    protected ImageView previousPage2GPV;
    @FXML
    protected ImageView  cartImageView2GPV;
    @FXML
    protected Label nameProd2GPV;
    @FXML
    protected Label brandProd2GPV;
    @FXML
    protected TableView<Shop> shopsTableView2GPV = new TableView<>();
    protected TableColumn<Shop, String> nameColumn2GPV;
    protected TableColumn<Shop, String> addressColumn2GPV;
    protected TableColumn<Shop, String> cityColumn2GPV;
    protected TableColumn<Shop, String> openingColumn2GPV;
    protected TableColumn<Shop, String> closingColumn2GPV;

    InputStream stream2GPV = null;
    static final Logger logger = Logger.getLogger(GeneralProductView.class.getName());


    public void passParams(User user2GPV, SimpleProduct simpleProduct2GPV) throws FileNotFoundException {
        this.user2GPV = user2GPV;
        this.simpleProduct2GPV = simpleProduct2GPV;
        brandProd2GPV.setText(simpleProduct2GPV.getBrand());
        nameProd2GPV.setText(simpleProduct2GPV.getName());
        List<Shop> arrayShopList2GPV = ShopHandler.findShopByProduct(simpleProduct2GPV);

        stream2GPV = new FileInputStream(simpleProduct2GPV.getLogoImagepath());
        Image productImage2GPV = new Image(stream2GPV, 200, 200, false, false);
        productPhoto2GPV.setImage(productImage2GPV);
        ObservableList<Shop> observableListShops2GPV = FXCollections.observableArrayList();
        if (arrayShopList2GPV != null) {
            observableListShops2GPV.addAll(arrayShopList2GPV);
            shopsTableView2GPV.setItems(observableListShops2GPV);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    @FXML
    protected void onClickProfileImageView2GPV() throws IOException {
        FXMLLoader loader2GPV = new FXMLLoader(getClass().getResource("myProfile.fxml"));
        Parent root2GPV = loader2GPV.load();
        MyProfile myProfile2GPV = loader2GPV.getController();
        myProfile2GPV.passParams(user2GPV);
        Stage newStage2GPV = new Stage();
        newStage2GPV.setScene(new Scene(root2GPV));
        newStage2GPV.show();
        newStage2GPV.setResizable(false);
        Stage stage2GPV = (Stage) cartImageView2GPV.getScene().getWindow();
        stage2GPV.close();
    }

    @FXML
    protected void onHomepageImageClick2GPV() throws IOException, AddressException {
        FXMLLoader loader2GPV = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root2GPV = loader2GPV.load();
        Homepage homepage2GPV = loader2GPV.getController();
        homepage2GPV.passParams(user2GPV);
        Stage newStage2GPV = new Stage();
        newStage2GPV.setScene(new Scene(root2GPV));
        newStage2GPV.show();
        newStage2GPV.setResizable(false);
        Stage stage2GPV = (Stage) homepageImageView2GPV.getScene().getWindow();
        stage2GPV.close();
    }

    @FXML
    protected void openCartAndPayment2GPV() throws IOException {
        FXMLLoader loader2GPV = new FXMLLoader(getClass().getResource("cartAndPayment.fxml"));
        Parent root2GPV = loader2GPV.load();
        CartAndPayment cartAndPayment2GPV = loader2GPV.getController();
        cartAndPayment2GPV.passParam(null, user2GPV);
        Stage newStage2GPV = new Stage();
        newStage2GPV.setScene(new Scene(root2GPV));
        newStage2GPV.show();
        newStage2GPV.setResizable(false);
        Stage stage2GPV = (Stage) cartImageView2GPV.getScene().getWindow();
        stage2GPV.close();
    }

    @FXML
    public void initialize(){
        shopsTableView2GPV.setEditable(true);

        nameColumn2GPV = new TableColumn<>("NAME");
        nameColumn2GPV.setMinWidth(200);
        nameColumn2GPV.setCellValueFactory(new PropertyValueFactory<>("shopName"));

        addressColumn2GPV = new TableColumn<>("ADDRESS");
        addressColumn2GPV.setMinWidth(200);
        addressColumn2GPV.setCellValueFactory(new PropertyValueFactory<>("completeAddress"));

        cityColumn2GPV = new TableColumn<>("CITY");
        cityColumn2GPV.setMinWidth(50);
        cityColumn2GPV.setCellValueFactory(new PropertyValueFactory<>("city"));

        openingColumn2GPV = new TableColumn<>("OPENING TIME");
        openingColumn2GPV.setMinWidth(10);
        openingColumn2GPV.setCellValueFactory(new PropertyValueFactory<>("openingTime"));

        closingColumn2GPV = new TableColumn<>("CLOSING TIME");
        openingColumn2GPV.setMinWidth(10);
        closingColumn2GPV.setCellValueFactory(new PropertyValueFactory<>("closingTime"));
        shopsTableView2GPV.getColumns().addAll(
                nameColumn2GPV, addressColumn2GPV, cityColumn2GPV, openingColumn2GPV, closingColumn2GPV);
    }

    @FXML
    protected void onItemClickedTableView2GPV() throws IOException {
        FXMLLoader loader2GPV = new FXMLLoader(getClass().getResource("shopProductView.fxml"));
        Parent root2GPV = loader2GPV.load();
        Shop shop2GPV = shopsTableView2GPV.getSelectionModel().getSelectedItem();
        if (shop2GPV != null) {
            ShopProductView shopProductView2GPV = loader2GPV.getController();
            shopProductView2GPV.passParams(user2GPV, simpleProduct2GPV, shop2GPV);
            Stage newStage2GPV = new Stage();
            newStage2GPV.setScene(new Scene(root2GPV));
            newStage2GPV.show();
            newStage2GPV.setResizable(false);
            Stage stage2GPV = (Stage) cartImageView2GPV.getScene().getWindow();
            stage2GPV.close();
        }
    }
}
