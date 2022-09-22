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

    User user1MP = null;
    @FXML
    protected Rectangle borderProfileImageRectangle1MP;
    @FXML
    protected ImageView myProfileImage1MP;
    @FXML
    protected Text nameText1MP;
    @FXML
    protected Text surnameText1MP;
    @FXML
    protected Text usernameText1MP;
    @FXML
    protected Text addressText1MP;
    @FXML
    protected Text phoneText1MP;
    @FXML
    protected Text saveYourProfileText1MP;
    @FXML
    protected Text errorText1MP;
    @FXML
    protected TextField nameTextField1MP;
    @FXML
    protected TextField surnameTextField1MP;
    @FXML
    protected TextField streetTextField1MP;
    @FXML
    protected TextField cityTextField1MP;
    @FXML
    protected TextField countryTextField1MP;
    @FXML
    protected TextField zipTextField1MP;
    @FXML
    protected TextField phoneTextField1MP;
    @FXML
    protected ImageView editImageView1MP;
    @FXML
    protected ImageView saveImageView1MP;
    @FXML
    protected ImageView homepageImageView1MP;
    @FXML
    protected AnchorPane anchorPane21MP;

    @FXML
    protected void onClickAnchorPane2MP() throws IOException {
        FXMLLoader loader1MP = new FXMLLoader(getClass().getResource("myOrders.fxml"));
        Parent root1MP = loader1MP.load();
        MyOrders myOrders1MP = loader1MP.getController();
        myOrders1MP.passUser(user1MP);
        Stage newStage1MP = new Stage();
        newStage1MP.setScene(new Scene(root1MP));
        newStage1MP.show();
        newStage1MP.setResizable(false);
        Stage stage1MP = (Stage) homepageImageView1MP.getScene().getWindow();
        stage1MP.close();
    }

    public void passUser(User user1MP) throws IOException {
        this.user1MP = user1MP;
        usernameText1MP.setText(user1MP.getUsername());

        if (user1MP instanceof Buyer) {
            InputStream stream1MP = new FileInputStream(Constants.PROFILE_IMAGES_PATH + ((Buyer) user1MP).getProfileImagepath());
            Image profileImage1MP = new Image(stream1MP, 200, 200, false, false);
            myProfileImage1MP.setImage(profileImage1MP);
            stream1MP.close();
            //show textField
            nameTextField1MP.setVisible(true);
            surnameTextField1MP.setVisible(true);
            streetTextField1MP.setVisible(true);
            cityTextField1MP.setVisible(true);
            countryTextField1MP.setVisible(true);
            zipTextField1MP.setVisible(true);
            phoneTextField1MP.setVisible(true);
            // populate text fields
            nameTextField1MP.setText(user1MP.getName());
            surnameTextField1MP.setText(user1MP.getSurname());
            streetTextField1MP.setText(((Buyer) user1MP).getBillingStreet());
            cityTextField1MP.setText(((Buyer) user1MP).getBillingCity());
            countryTextField1MP.setText(((Buyer) user1MP).getBillingCountry());
            zipTextField1MP.setText(((Buyer) user1MP).getBillingZip());
            phoneTextField1MP.setText(((Buyer) user1MP).getPhone());

            saveYourProfileText1MP.setVisible(true);
        }
        if (user1MP instanceof Admin) {
            phoneText1MP.setVisible(false);
            addressText1MP.setVisible(false);
        }
    }

    @FXML
    protected void onHomepageImageClick1MP() throws IOException {
        FXMLLoader loader1MP = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Parent root1MP = loader1MP.load();
        Homepage homepage1MP = loader1MP.getController();
        homepage1MP.passUser(user1MP);
        Stage newStage1MP = new Stage();
        newStage1MP.setScene(new Scene(root1MP));
        newStage1MP.show();
        newStage1MP.setResizable(false);
        Stage stage1MP = (Stage) homepageImageView1MP.getScene().getWindow();
        stage1MP.close();
    }

    @FXML
    protected void saveProfile1MP() {
        if (nameTextField1MP.getText().isBlank() || surnameTextField1MP.getText().isBlank() ||
                streetTextField1MP.getText().isBlank() || cityTextField1MP.getText().isBlank() ||
                countryTextField1MP.getText().isBlank() || zipTextField1MP.getText().isBlank() ||
                phoneTextField1MP.getText().isBlank()) {
            errorText1MP.setVisible(true);
        }
        else{
            if(UserHandler.updateRecord2(user1MP, Arrays.asList(nameTextField1MP.getText(), surnameTextField1MP.getText(),
                    streetTextField1MP.getText(), cityTextField1MP.getText(), countryTextField1MP.getText(),
                    zipTextField1MP.getText(), phoneTextField1MP.getText(), ((Buyer) user1MP).getProfileImagepath()))){
                //
            }
        }
    }

    @FXML
    protected void enterProfileImage1MP() {
        borderProfileImageRectangle1MP.setVisible(true);
    }
    @FXML
    protected void exitProfileImage1MP(){
        borderProfileImageRectangle1MP.setVisible(false);
    }
    @FXML
    public void initialize(){
        editImageView1MP.setVisible(true);
        saveImageView1MP.setVisible(false);
    }

    @FXML
    protected void onClickProfileImageView1MP() throws IOException, FileElaborationException {

        InputStream stream1MP = new FileInputStream(Constants.PROFILE_IMAGE_BLANK);
        Image profileImage1MP = new Image(stream1MP, 200, 200, false, false);

        myProfileImage1MP.setImage(profileImage1MP);
        String newFileName1MP = user1MP.getUsername() + ".jpg";
        String newFilepath1MP = Constants.PROFILE_IMAGES_PATH + newFileName1MP;
        FileChooser fileChooser1MP = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG1MP = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        fileChooser1MP.getExtensionFilters().addAll(extFilterJPG1MP);
        File file1MP = fileChooser1MP.showOpenDialog(null);

        if (file1MP != null) {
            if (FileElaboration.copyAndReplaceFile(file1MP, newFilepath1MP)) {
                stream1MP = new FileInputStream(Constants.PROFILE_IMAGES_PATH + ((Buyer) user1MP).getProfileImagepath());
                profileImage1MP = new Image(stream1MP, 200, 200, false, false);
                myProfileImage1MP.setImage(profileImage1MP);
                stream1MP.close();
            }
            UserHandler.updateLogoImagePath(user1MP, newFileName1MP);
        }
    }
}