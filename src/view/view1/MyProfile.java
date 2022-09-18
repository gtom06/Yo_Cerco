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
import model.Constants;
import model.user.Admin;
import model.user.Buyer;
import model.user.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyProfile {

    User user = null;
    @FXML
    protected Rectangle borderProfileImageRectangle;
    @FXML
    protected ImageView myProfileImage;
    @FXML
    protected Text nameText;
    @FXML
    protected Text surnameText;
    @FXML
    protected Text usernameText;
    @FXML
    protected Text addressText;
    @FXML
    protected Text phoneText;
    @FXML
    protected Text saveYourProfileText;
    @FXML
    protected Text modifyYourProfileText;
    @FXML
    protected Text completeYourProfileText;
    @FXML
    protected Text errorText;
    @FXML
    protected TextField nameTextField;
    @FXML
    protected TextField surnameTextField;
    @FXML
    protected TextField streetTextField;
    @FXML
    protected TextField cityTextField;
    @FXML
    protected TextField countryTextField;
    @FXML
    protected TextField zipTextField;
    @FXML
    protected TextField phoneTextField;
    @FXML
    protected ImageView editImageView;
    @FXML
    protected ImageView saveImageView;
    @FXML
    protected ImageView homepageImageView;
    @FXML
    protected AnchorPane anchorPane2;

    boolean isSomeFieldBlank;

    @FXML
    protected void onClickAnchorPane2() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("myOrders.fxml"));
        Parent root = loader.load();
        MyOrders myOrders = loader.getController();
        myOrders.passUser(user);
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        newStage.setResizable(false);
        Stage stage = (Stage) homepageImageView.getScene().getWindow();
        stage.close();
    }

    public void passUser(User user) throws IOException {
        this.user = user;
        usernameText.setText(user.getUsername());

        if (user instanceof Buyer) {
            InputStream stream = new FileInputStream(Constants.PROFILE_IMAGES_PATH + ((Buyer) user).getProfileImagepath());
            Image profileImage = new Image(stream, 200, 200, false, false);
            myProfileImage.setImage(profileImage);
            stream.close();
            if (this.isSomeFieldBlank == ((Buyer) user).isSomeFieldBlank()) {
                saveYourProfileText.setVisible(false);
                completeYourProfileText.setVisible(true);
                modifyYourProfileText.setVisible(false);
            }
            else {
                saveYourProfileText.setVisible(false);
                completeYourProfileText.setVisible(false);
                modifyYourProfileText.setVisible(true);
                nameText.setText(user.getName());
                surnameText.setText(user.getSurname());
                phoneText.setText(((Buyer) user).getPhone());
                addressText.setText(((Buyer) user).getBillingAddress());
            }
        }
        if (user instanceof Admin) {
            phoneText.setVisible(false);
            addressText.setVisible(false);
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
    protected void editProfile() {
        editImageView.setVisible(false);
        saveImageView.setVisible(true);
        saveYourProfileText.setVisible(true);
        completeYourProfileText.setVisible(false);
        modifyYourProfileText.setVisible(false);

        //hide Text
        nameText.setVisible(false);
        surnameText.setVisible(false);
        addressText.setVisible(false);
        phoneText.setVisible(false);

        //show textField
        nameTextField.setVisible(true);
        surnameTextField.setVisible(true);
        streetTextField.setVisible(true);
        cityTextField.setVisible(true);
        countryTextField.setVisible(true);
        zipTextField.setVisible(true);
        phoneTextField.setVisible(true);
        // populate text fields
        nameTextField.setText(user.getName());
        surnameTextField.setText(user.getSurname());
        streetTextField.setText(((Buyer) user).getBillingStreet());
        cityTextField.setText(((Buyer) user).getBillingCity());
        countryTextField.setText(((Buyer) user).getBillingCountry());
        zipTextField.setText(((Buyer) user).getBillingZip());
        phoneTextField.setText(((Buyer) user).getPhone());
    }

    @FXML
    protected void saveProfile() {
        if (nameTextField.getText().isBlank() || surnameTextField.getText().isBlank() ||
                streetTextField.getText().isBlank() || cityTextField.getText().isBlank() ||
                countryTextField.getText().isBlank() || zipTextField.getText().isBlank() ||
                phoneTextField.getText().isBlank()) {
            errorText.setVisible(true);
        }
        else{
            if (UserHandler.updateRecord(user, nameTextField.getText(), surnameTextField.getText(),
                    streetTextField.getText(), cityTextField.getText(), countryTextField.getText(),
                    zipTextField.getText(), phoneTextField.getText(), ((Buyer) user).getProfileImagepath())) {
                //hide textfields
                nameTextField.setVisible(false);
                surnameTextField.setVisible(false);
                streetTextField.setVisible(false);
                cityTextField.setVisible(false);
                countryTextField.setVisible(false);
                zipTextField.setVisible(false);
                phoneTextField.setVisible(false);
                saveYourProfileText.setVisible(false);
                completeYourProfileText.setVisible(false);
                modifyYourProfileText.setVisible(true);
                editImageView.setVisible(true);
                saveImageView.setVisible(false);
                errorText.setVisible(false);
                //show Texts
                nameText.setText(user.getName());
                surnameText.setText(user.getSurname());
                addressText.setText(((Buyer) user).getBillingAddress());
                phoneText.setText(((Buyer) user).getPhone());
                nameText.setVisible(true);
                surnameText.setVisible(true);
                addressText.setVisible(true);
                phoneText.setVisible(true);
            }
        }
    }

    @FXML
    protected void enterProfileImage() {
        borderProfileImageRectangle.setVisible(true);
    }
    @FXML
    protected void exitProfileImage(){
        borderProfileImageRectangle.setVisible(false);
    }
    @FXML
    public void initialize(){
        editImageView.setVisible(true);
        saveImageView.setVisible(false);
    }

    @FXML
    protected void onClickProfileImageView() throws IOException, FileElaborationException {

        InputStream stream = new FileInputStream(Constants.PROFILE_IMAGE_BLANK);
        Image profileImage = new Image(stream, 200, 200, false, false);

        myProfileImage.setImage(profileImage);
        String newFileName = user.getUsername() + ".jpg";
        String newFilepath = Constants.PROFILE_IMAGES_PATH + newFileName;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG);
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            if (FileElaboration.copyAndReplaceFile(file, newFilepath)) {
                stream = new FileInputStream(Constants.PROFILE_IMAGES_PATH + ((Buyer) user).getProfileImagepath());
                profileImage = new Image(stream, 200, 200, false, false);
                myProfileImage.setImage(profileImage);
                stream.close();
            }
            UserHandler.updateLogoImagePath(user, newFileName);
        }
    }
}