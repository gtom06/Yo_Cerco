package view.view2;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Product.SimpleProduct;
import model.Shop.Shop;
import model.User.User;
import view.view1.SearchProduct;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GeneralProductView {
    User user;
    SimpleProduct simpleProduct;

    @FXML
    ImageView productPhoto, homepageImageView, previousPage;
    @FXML
    Label nameProd, brandProd;
    @FXML
    Label productNameLabel;
    @FXML
    TableView<Shop> shopsTableView = new TableView<>();
    TableColumn<Shop, String> nameColumn;
    TableColumn<Shop, String> addressColumn;
    TableColumn<Shop, String> cityColumn;
    TableColumn<Shop, String> openingColumn;
    TableColumn<Shop, String> closingColumn;
    InputStream stream = null;


    public void passParams(User user, SimpleProduct simpleProduct) throws FileNotFoundException {
        this.user = user;
        this.simpleProduct = simpleProduct;
        brandProd.setText(simpleProduct.getBrand());
        nameProd.setText(simpleProduct.getName());
        ArrayList<Shop> arrayShopList = ShopHandler.findShopByProduct(simpleProduct);

        stream = new FileInputStream(simpleProduct.getLogoImagepath());
        Image productImage = new Image(stream, 200, 200, false, false);
        productPhoto.setImage(productImage);
        ObservableList<Shop> observableListShops = FXCollections.observableArrayList();
        if (arrayShopList != null) {
            for (Shop s : arrayShopList) {
                observableListShops.add(s);
            }
            shopsTableView.setItems(observableListShops);
        }
        else {
            System.out.println("no result");
        }
    }

    @FXML
    protected void onHomepageImageClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root = loader.load();
        Homepage homepage = loader.getController();
        homepage.passParams(user);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) homepageImageView.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize(){

        shopsTableView.setEditable(true);

        nameColumn = new TableColumn<>("NAME");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("shopName"));

        addressColumn = new TableColumn<>("ADDRESS");
        addressColumn.setMinWidth(200);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        cityColumn = new TableColumn<>("CITY");
        cityColumn.setMinWidth(50);
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));

        openingColumn = new TableColumn<>("OPENING TIME");
        openingColumn.setMinWidth(10);
        openingColumn.setCellValueFactory(new PropertyValueFactory<>("openingTime"));

        closingColumn = new TableColumn<>("CLOSING TIME");
        openingColumn.setMinWidth(10);
        closingColumn.setCellValueFactory(new PropertyValueFactory<>("closingTime"));


        shopsTableView.getColumns().addAll(nameColumn, addressColumn, cityColumn, openingColumn, closingColumn);

    }

}
