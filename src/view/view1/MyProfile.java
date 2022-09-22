package view.view1;

import control.FileElaboration;
import control.UserHandler;
import exceptions.FileElaborationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import constants.Constants;
import model.user.Admin;
import model.user.Buyer;
import model.user.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class MyProfile {

    User user1 = null;
    @FXML
    protected Rectangle borderProfileImageRectangle1;
    @FXML
    protected ImageView myProfileImage1;
    @FXML
    protected Text nameText1;
    @FXML
    protected Text surnameText1;
    @FXML
    protected Text usernameText1;
    @FXML
    protected Text addressText1;
    @FXML
    protected Text phoneText1;
    @FXML
    protected Text saveYourProfileText1;
    @FXML
    protected Text errorText1;
    @FXML
    protected TextField nameTextField1;
    @FXML
    protected TextField surnameTextField1;
    @FXML
    protected TextField streetTextField1;
    @FXML
    protected TextField cityTextField1;
    @FXML
    protected TextField countryTextField1;
    @FXML
    protected TextField zipTextField1;
    @FXML
    protected TextField phoneTextField1;
    @FXML
    protected ImageView editImageView1;
    @FXML
    protected ImageView saveImageView1;
    @FXML
    protected ImageView homepageImageView1MP;
    @FXML
    protected AnchorPane anchorPane21;

    @FXML
    protected void onClickAnchorPane2() throws IOException {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("myOrders.fxml"));
        Parent root1 = loader1.load();
        MyOrders myOrders1 = loader1.getController();
        myOrders1.passUser(user1);
        Stage newStage1 = new Stage();
        newStage1.setScene(new Scene(root1));
        newStage1.show();
        newStage1.setResizable(false);
        Stage stage1 = (Stage) homepageImageView1MP.getScene().getWindow();
        stage1.close();
    }

    public void passUser(User user1) throws IOException {
        this.user1 = user1;
        usernameText1.setText(user1.getUsername());

        if (user1 instanceof Buyer) {
            InputStream stream = new FileInputStream(Constants.PROFILE_IMAGES_PATH + ((Buyer) user1).getProfileImagepath());
            Image profileImage = new Image(stream, 200, 200, false, false);
            myProfileImage1.setImage(profileImage);
            stream.close();
            //show textField
            nameTextField1.setVisible(true);
            surnameTextField1.setVisible(true);
            streetTextField1.setVisible(true);
            cityTextField1.setVisible(true);
            countryTextField1.setVisible(true);
            zipTextField1.setVisible(true);
            phoneTextField1.setVisible(true);
            // populate text fields
            nameTextField1.setText(user1.getName());
            surnameTextField1.setText(user1.getSurname());
            streetTextField1.setText(((Buyer) user1).getBillingStreet());
            cityTextField1.setText(((Buyer) user1).getBillingCity());
            countryTextField1.setText(((Buyer) user1).getBillingCountry());
            zipTextField1.setText(((Buyer) user1).getBillingZip());
            phoneTextField1.setText(((Buyer) user1).getPhone());

            saveYourProfileText1.setVisible(true);
        }
        if (user1 instanceof Admin) {
            phoneText1.setVisible(false);
            addressText1.setVisible(false);
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
        Stage stage1 = (Stage) homepageImageView1MP.getScene().getWindow();
        stage1.close();
    }

    @FXML
    protected void saveProfile() {
        if (nameTextField1.getText().isBlank() || surnameTextField1.getText().isBlank() ||
                streetTextField1.getText().isBlank() || cityTextField1.getText().isBlank() ||
                countryTextField1.getText().isBlank() || zipTextField1.getText().isBlank() ||
                phoneTextField1.getText().isBlank()) {
            errorText1.setVisible(true);
        }
        else{
            if(UserHandler.updateRecord2(user1, Arrays.asList(nameTextField1.getText(), surnameTextField1.getText(),
                    streetTextField1.getText(), cityTextField1.getText(), countryTextField1.getText(),
                    zipTextField1.getText(), phoneTextField1.getText(), ((Buyer) user1).getProfileImagepath()))){
                //
            }
        }
    }

    @FXML
    protected void enterProfileImage() {
        borderProfileImageRectangle1.setVisible(true);
    }
    @FXML
    protected void exitProfileImage(){
        borderProfileImageRectangle1.setVisible(false);
    }
    @FXML
    public void initialize(){
        editImageView1.setVisible(true);
        saveImageView1.setVisible(false);
    }

    @FXML
    protected void onClickProfileImageView() throws IOException, FileElaborationException {

        InputStream stream1 = new FileInputStream(Constants.PROFILE_IMAGE_BLANK);
        Image profileImage1 = new Image(stream1, 200, 200, false, false);

        myProfileImage1.setImage(profileImage1);
        String newFileName = user1.getUsername() + ".jpg";
        String newFilepath1 = Constants.PROFILE_IMAGES_PATH + newFileName;
        FileChooser fileChooser1 = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG1 = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        fileChooser1.getExtensionFilters().addAll(extFilterJPG1);
        File file1 = fileChooser1.showOpenDialog(null);

        if (file1 != null) {
            if (FileElaboration.copyAndReplaceFile(file1, newFilepath1)) {
                stream1 = new FileInputStream(Constants.PROFILE_IMAGES_PATH + ((Buyer) user1).getProfileImagepath());
                profileImage1 = new Image(stream1, 200, 200, false, false);
                myProfileImage1.setImage(profileImage1);
                stream1.close();
            }
            UserHandler.updateLogoImagePath(user1, newFileName);
        }
    }
}