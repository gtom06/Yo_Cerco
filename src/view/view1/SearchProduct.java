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
import constants.Constants;
import model.product.SimpleProduct;
import model.user.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchProduct {
    User user1;
    @FXML
    protected ImageView homepageImageView1SP;
    @FXML
    protected TextField requestTextField1;
    @FXML
    protected Text textHi1;
    @FXML
    protected TableView<SimpleProduct> productTableView1 = new TableView<>();
    protected TableColumn<SimpleProduct, String> nameColumn1;
    protected TableColumn<SimpleProduct, Double> sizeColumn1;
    protected TableColumn<SimpleProduct, String> unitOfMeasureColumn1;
    protected TableColumn<SimpleProduct, String> brandColumn1;

    static final Logger logger = Logger.getLogger(SearchProduct.class.getName());

    @FXML
    protected void onListViewItemClick() throws IOException {
        SimpleProduct simpleProduct1 = productTableView1.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (simpleProduct1 != null){
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("generalProductView.fxml"));
            Parent root1 = loader1.load();
            GeneralProductView generalProductView1 = loader1.getController();
            generalProductView1.passParams(user1, simpleProduct1);
            Stage newStage1 = new Stage();
            newStage1.setScene(new Scene(root1));
            newStage1.show();
            newStage1.setResizable(false);
            Stage stage1 = (Stage) productTableView1.getScene().getWindow();
            stage1.close();
        }

    }

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
        Stage stage1 = (Stage) homepageImageView1SP.getScene().getWindow();
        stage1.close();
    }

    @FXML
    protected void onSearchButtonClick() {
        productTableView1.getItems().clear();
        String paramForSearch1 = requestTextField1.getText();
        ObservableList<SimpleProduct> observableListProducts1 = FXCollections.observableArrayList();
        ArrayList<SimpleProduct> simpleProductArrayList1 = (ArrayList<SimpleProduct>) ProductHandler.findSimpleProductBy(paramForSearch1);

        if (simpleProductArrayList1 != null) {
            for (SimpleProduct sp1 : simpleProductArrayList1) {
                observableListProducts1.add(sp1);
            }
            productTableView1.setItems(observableListProducts1);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    public void passUser(User user1) {
        this.user1 = user1;
        textHi1.setText(user1.getUsername());
    }

    @FXML
    public void initialize(){

        productTableView1.setEditable(true);
        productTableView1.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        nameColumn1 = new TableColumn<>("NAME");
        nameColumn1.setMinWidth(50);
        nameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));

        sizeColumn1 = new TableColumn<>("SIZE");
        sizeColumn1.setMinWidth(10);
        sizeColumn1.setCellValueFactory(new PropertyValueFactory<>("size"));

        unitOfMeasureColumn1 = new TableColumn<>("UNIT OF MEASURE");
        unitOfMeasureColumn1.setMinWidth(50);
        unitOfMeasureColumn1.setCellValueFactory(new PropertyValueFactory<>("unitOfMeasure"));

        brandColumn1 = new TableColumn<>("BRAND");
        brandColumn1.setMinWidth(70);
        brandColumn1.setCellValueFactory(new PropertyValueFactory<>("brand"));

        productTableView1.getColumns().addAll(nameColumn1, sizeColumn1, unitOfMeasureColumn1,brandColumn1);
    }
}