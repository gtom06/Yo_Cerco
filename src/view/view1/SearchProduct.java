package view.view1;

import bean.SimpleProductBean;
import bean.UserBean;
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
    UserBean user1SP;
    @FXML
    protected ImageView homepageImageView1SP;
    @FXML
    protected TextField requestTextField1SP;
    @FXML
    protected Text textHi1SP;
    @FXML
    protected TableView<SimpleProductBean> productTableView1SP = new TableView<>();
    protected TableColumn<SimpleProductBean, String> nameColumn1SP;
    protected TableColumn<SimpleProductBean, Double> sizeColumn1SP;
    protected TableColumn<SimpleProductBean, String> unitOfMeasureColumn1SP;
    protected TableColumn<SimpleProductBean, String> brandColumn1SP;

    static final Logger logger = Logger.getLogger(SearchProduct.class.getName());

    @FXML
    protected void onListViewItemClick1SP() throws IOException {
        SimpleProductBean simpleProduct1SP = productTableView1SP.getSelectionModel().getSelectedItem();
        //check if shop selected: used to avoid exception when clicking wrong on tableview
        if (simpleProduct1SP != null){
            FXMLLoader loader1SP = new FXMLLoader(getClass().getResource("generalProductView.fxml"));
            Parent root1SP = loader1SP.load();
            GeneralProductView generalProductView1SP = loader1SP.getController();
            generalProductView1SP.passParams(user1SP, simpleProduct1SP);
            Stage newStage1SP = new Stage();
            newStage1SP.setScene(new Scene(root1SP));
            newStage1SP.show();
            newStage1SP.setResizable(false);
            Stage stage1SP = (Stage) productTableView1SP.getScene().getWindow();
            stage1SP.close();
        }

    }

    @FXML
    protected void onHomepageImageClick1SP() throws IOException {
        FXMLLoader loader1SP = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root1SP = loader1SP.load();
        Homepage homepage1SP = loader1SP.getController();
        homepage1SP.passUser(user1SP);
        Stage newStage1SP = new Stage();
        newStage1SP.setScene(new Scene(root1SP));
        newStage1SP.show();
        newStage1SP.setResizable(false);
        Stage stage1SP = (Stage) homepageImageView1SP.getScene().getWindow();
        stage1SP.close();
    }

    @FXML
    protected void onSearchButtonClick1SP() {
        productTableView1SP.getItems().clear();
        String paramForSearch1SP = requestTextField1SP.getText();
        ObservableList<SimpleProductBean> observableListProducts1SP = FXCollections.observableArrayList();
        ArrayList<SimpleProductBean> simpleProductArrayList1SP = (ArrayList<SimpleProductBean>) ProductHandler.findSimpleProductBy(paramForSearch1SP);

        if (simpleProductArrayList1SP != null) {
            for (SimpleProductBean sp1 : simpleProductArrayList1SP) {
                observableListProducts1SP.add(sp1);
            }
            productTableView1SP.setItems(observableListProducts1SP);
        }
        else {
            logger.log(Level.INFO, Constants.NO_RESULT);
        }
    }

    public void passUser(UserBean user1SP) {
        this.user1SP = user1SP;
        textHi1SP.setText(user1SP.getUsername());
    }

    @FXML
    public void initialize(){

        productTableView1SP.setEditable(true);
        productTableView1SP.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        nameColumn1SP = new TableColumn<>("NAME");
        nameColumn1SP.setMinWidth(50);
        nameColumn1SP.setCellValueFactory(new PropertyValueFactory<>("name"));

        sizeColumn1SP = new TableColumn<>("SIZE");
        sizeColumn1SP.setMinWidth(10);
        sizeColumn1SP.setCellValueFactory(new PropertyValueFactory<>("size"));

        unitOfMeasureColumn1SP = new TableColumn<>("UNIT OF MEASURE");
        unitOfMeasureColumn1SP.setMinWidth(50);
        unitOfMeasureColumn1SP.setCellValueFactory(new PropertyValueFactory<>("unitOfMeasure"));

        brandColumn1SP = new TableColumn<>("BRAND");
        brandColumn1SP.setMinWidth(70);
        brandColumn1SP.setCellValueFactory(new PropertyValueFactory<>("brand"));

        productTableView1SP.getColumns().addAll(nameColumn1SP, sizeColumn1SP, unitOfMeasureColumn1SP,brandColumn1SP);
    }
}