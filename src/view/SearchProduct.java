package view;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Product.SimpleProduct;
import model.User.User;

import java.io.IOException;
import java.util.ArrayList;

public class SearchProduct {
    User u = null;
    @FXML
    private ImageView homepageImageView;
    @FXML
    TextField requestTextField;
    @FXML
    Text textHi;
    @FXML
    Button searchForShop;
    @FXML
    TableView<SimpleProduct> productTableView = new TableView<>();
    TableColumn<SimpleProduct, String> descriptionColumn;
    TableColumn<SimpleProduct, Integer> typeColumn;
    TableColumn<SimpleProduct, String> nameColumn;
    TableColumn<SimpleProduct, Double> weightColumn;

    @FXML
    protected void onListViewItemClick() throws IOException {
        /*

        SimpleProduct simpleProduct = tableView.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (simpleProduct != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("shopView.fxml"));
            Parent root = loader.load();
            ShopView shopView = loader.getController();
            shopView.passUser(u);
            //shopView.passShop(shop);
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
            newStage.setResizable(false);
            Stage stage = (Stage) tableView.getScene().getWindow();
            stage.close();
        }
        */
    }

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
    protected void onSearchButtonClick() {
        productTableView.getItems().clear();
        String paramForSearch = requestTextField.getText();
        ObservableList<SimpleProduct> observableListProducts = FXCollections.observableArrayList();
        ArrayList<SimpleProduct> simpleProductArrayList = ProductHandler.findSimpleProductBy(paramForSearch);

        if (simpleProductArrayList != null) {
            for (SimpleProduct sp : simpleProductArrayList) {
                observableListProducts.add(sp);
            }
            productTableView.setItems(observableListProducts);
        }
        else {
            System.out.println("no result");
        }
    }

    @FXML
    protected void onSearchShopButtonClick() {
        ArrayList<SimpleProduct> simpleProductArrayList = new ArrayList<>();
        for (SimpleProduct sp : productTableView.getSelectionModel().getSelectedItems()) {
            simpleProductArrayList.add(sp);
        }
        ShopHandler.findShopsContainingProductBy(simpleProductArrayList);
    }
    public void passUser(User user) {
        u = user;
        textHi.setText(user.getUsername());
    }

    @FXML
    public void initialize(){
        productTableView.setEditable(true);
        productTableView.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(10);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setMinWidth(10);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        typeColumn = new TableColumn<>("Type");
        typeColumn.setMinWidth(30);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        weightColumn = new TableColumn<>("Weight");
        weightColumn.setMinWidth(30);
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));

        productTableView.getColumns().addAll(nameColumn, descriptionColumn, typeColumn, weightColumn);
    }
}