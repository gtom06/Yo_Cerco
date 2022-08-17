package view;

import control.DepartmentHandler;
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
import javafx.stage.Stage;
import model.Constants;
import model.Department.Department;
import model.Shop.Shop;
import model.User.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class ShopView {
    User u = null;
    @FXML
    Label shopName, shopAddress, shopCity;
    @FXML
    ImageView homepageImageView, shopLogo,
            dep1,dep2,dep3,dep4,dep5,dep6,dep7,dep8,dep9;
    @FXML
    TableView<Department> tableView = new TableView<>();
    TableColumn<Department, String> nameColumn;
    @FXML
    Label labelHi;
    ArrayList<ImageView> imageDep= new ArrayList<>();
    ArrayList<String> dep = new ArrayList<>();
    String shopId = "";
    InputStream stream = null;
    Image image = null;

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

    public void passUser(User user) {
        u = user;
        labelHi.setText(user.getUsername());
    }

    public void passShop(Shop shop) throws FileNotFoundException {
        stream = new FileInputStream(shop.getLogoImagepath());
        image = new Image(stream);
        shopLogo.setImage(image);
        shopCity.setText(shop.getCity());
        shopAddress.setText(shop.getAddress());
        shopName.setText(shop.getShopName());
        shopId = String.valueOf(shop.getShopId());

        //for departments
        imageDep.add(dep1);
        imageDep.add(dep2);
        imageDep.add(dep3);
        imageDep.add(dep4);
        imageDep.add(dep5);
        imageDep.add(dep6);
        imageDep.add(dep7);
        imageDep.add(dep8);
        imageDep.add(dep9);

        ArrayList<Department> departmentArrayList = DepartmentHandler.findDepartmentByShop(shop.getShopId());
        if (departmentArrayList != null) {
            int departmentArrayListSize = departmentArrayList.size();
            for (int i = 0; i < departmentArrayListSize; i++) {
                if (i > 8 && departmentArrayListSize > 9) {
                    String logoImagepath = departmentArrayList.get(i).getLogoImagepath();
                    if (logoImagepath != "") {
                        FileInputStream stream = new FileInputStream(Constants.DEPARTMENT_IMAGE + departmentArrayList.get(i).getLogoImagepath());
                        Image image = new Image(stream);
                        imageDep.get(i).setImage(image);
                        dep.add(departmentArrayList.get(i).getName());
                    }
                }
                else {
                }
            }
        }
        else{
            System.out.println("no department result");
        }

        //create tableView
        tableView.setEditable(false);
        nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(10);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView.getColumns().addAll(nameColumn);
        //retrieve objects
        ObservableList<Department> observableListDepartment = FXCollections.observableArrayList();
        departmentArrayList = DepartmentHandler.findDepartmentByShop(shop.getShopId());
        if (departmentArrayList != null) {
            for (Department d : departmentArrayList) {
                observableListDepartment.add(d);
            }
            tableView.setItems(observableListDepartment);
        }
        else {
            System.out.println("no result");
        }
    }

    public void addToFavorite() {
        ShopHandler.insertShopIntoFavorite(shopId, labelHi.getText());
    }
}
