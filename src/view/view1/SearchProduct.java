package view.view1;

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
import model.Constants;
import model.Product.SimpleProduct;
import model.Shop.Shop;
import model.User.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchProduct {
    User user;
    ArrayList<Shop> arrayShopsList;
    @FXML
    protected ImageView homepageImageView;
    @FXML
    protected TextField requestTextField;
    @FXML
    protected Text textHi;
    @FXML
    TableView<SimpleProduct> productTableView = new TableView<>();
    TableColumn<SimpleProduct, String> nameColumn;
    TableColumn<SimpleProduct, Double> sizeColumn;
    TableColumn<SimpleProduct, String> unitOfMeasureColumn;
    TableColumn<SimpleProduct, String> brandColumn;
    static final Logger logger = Logger.getLogger(SearchProduct.class.getName());

    @FXML
    protected void onListViewItemClick() throws IOException {
        SimpleProduct simpleProduct = productTableView.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (simpleProduct != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("generalProductView.fxml"));
            Parent root = loader.load();
            GeneralProductView generalProductView = loader.getController();
            generalProductView.passParams(user, simpleProduct, arrayShopsList);
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
            newStage.setResizable(false);
            Stage stage = (Stage) productTableView.getScene().getWindow();
            stage.close();
        }

    }

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
            logger.log(Level.INFO, Constants.NO_RESULT);
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
        this.user = user;
        textHi.setText(user.getUsername());
    }

    @FXML
    public void initialize(){

        productTableView.setEditable(true);
        productTableView.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        nameColumn = new TableColumn<>("NAME");
        nameColumn.setMinWidth(50);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        sizeColumn = new TableColumn<>("SIZE");
        sizeColumn.setMinWidth(10);
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));

        unitOfMeasureColumn = new TableColumn<>("UNIT OF MEASURE");
        unitOfMeasureColumn.setMinWidth(50);
        unitOfMeasureColumn.setCellValueFactory(new PropertyValueFactory<>("unitOfMeasure"));

        brandColumn = new TableColumn<>("BRAND");
        brandColumn.setMinWidth(70);
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

        productTableView.getColumns().addAll(nameColumn, sizeColumn, unitOfMeasureColumn,brandColumn);
    }
}