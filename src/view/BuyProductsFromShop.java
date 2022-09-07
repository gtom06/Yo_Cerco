package view;

import control.FileElaboration;
import control.ProductHandler;
import control.ShopHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Constants;
import model.Product.ProductShop;
import model.Product.SimpleProduct;
import model.Shop.Shop;
import model.User.User;

import java.io.IOException;
import java.util.ArrayList;

public class BuyProductsFromShop {
    User u = null;
    Shop s = null;
    @FXML
    private ImageView homepageImageView;
    @FXML
    TextField requestTextField;
    @FXML
    Label labelHi;
    @FXML
    Button searchForShop;
    @FXML
    TableView<ProductShop> tableView = new TableView<>();
    TableColumn<ProductShop, String> descriptionColumn;
    TableColumn<ProductShop, Integer> availableQuantity; //todo: rimuovere il type se non esiste un type description in Constants.
    TableColumn<ProductShop, String> nameColumn;
    TableColumn<ProductShop, Double> weightColumn;

    @FXML
    protected void onHomepageImageClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root = loader.load();
        Homepage homepage = loader.getController();
        homepage.passUser(u);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) homepageImageView.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onSearchShopButtonClick() {
        /*
        ArrayList<SimpleProduct> simpleProductArrayList = new ArrayList<>();
        for (SimpleProduct sp : tableView.getSelectionModel().getSelectedItems()) {
            simpleProductArrayList.add(sp);
        }
        ShopHandler.findShopsContainingProductBy(simpleProductArrayList);
        */
    }

    public void passUser(User user) {
        u = user;
        labelHi.setText(user.getUsername());
    }

    public void passShop(Shop shop) {
        s = shop;
    }

    @FXML
    public void initialize() throws IOException {

        FileElaboration.writeOnFile(Constants.CART_PATH, "");

        tableView.setEditable(true);
        tableView.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(10);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setMinWidth(10);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        weightColumn = new TableColumn<>("Weight");
        weightColumn.setMinWidth(30);
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));

        availableQuantity = new TableColumn<>("Weight");
        availableQuantity.setMinWidth(30);
        availableQuantity.setCellValueFactory(new PropertyValueFactory<>("availableQuantity"));

        tableView.getColumns().addAll(nameColumn, descriptionColumn, availableQuantity, weightColumn);

        fillTableView();
    }

    protected void fillTableView() {
        tableView.getItems().clear();
        String paramForSearch = requestTextField.getText();
        ObservableList<ProductShop> observableListProducts = FXCollections.observableArrayList();
/*
        ArrayList<ProductShop> productShopArrayList = ProductHandler.findProductShopBy(s.getShopId());

        if (productShopArrayList != null) {
            for (ProductShop ps : productShopArrayList) {
                observableListProducts.add(ps);
            }
            tableView.setItems(observableListProducts);
        } else {
            System.out.println("no result");
        }

 */
    }
}