package view;

import control.FileElaboration;
import control.UserHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Constants;
import model.User.Admin;
import model.User.Buyer;
import model.User.ShopHolder;
import model.User.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyProfile {

    User user = null;

    @FXML
    Rectangle borderProfileImageRectangle;

    @FXML
    ImageView myProfileImage;
    @FXML
    Label nameLabel, surnameLabel, usernameLabel, addressLabel, phoneLabel, saveYourProfileLabel, modifyYourProfileLabel, completeYourProfileLabel, errorLabel;

    @FXML
    TextField nameTextField, surnameTextField, streetTextField, cityTextField, countryTextField, zipTextField, phoneTextField;

    @FXML
    ImageView editImageView, saveImageView, homepageImageView;

    @FXML
    AnchorPane anchorPane1, anchorPane2;
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
        usernameLabel.setText(user.getUsername());

        if (user instanceof Buyer) {
            InputStream stream = new FileInputStream(Constants.PROFILE_IMAGES_PATH + ((Buyer) user).getProfileImagepath());
            Image profileImage = new Image(stream, 200, 200, false, false);
            myProfileImage.setImage(profileImage);
            stream.close();
            if (this.isSomeFieldBlank = ((Buyer) user).isSomeFieldBlank()) {
                saveYourProfileLabel.setVisible(false);
                completeYourProfileLabel.setVisible(true);
                modifyYourProfileLabel.setVisible(false);
                //editLabel.setText(Constants.COMPLETE_YOUR_PROFILE_CAPSLOCK);
            }
            else {
                saveYourProfileLabel.setVisible(false);
                completeYourProfileLabel.setVisible(false);
                modifyYourProfileLabel.setVisible(true);
                //editLabel.setText(Constants.MODIFY_YOUR_PROFILE_STRING_CAPSLOCK);
                nameLabel.setText(user.getName());
                surnameLabel.setText(user.getSurname());
                phoneLabel.setText(((Buyer) user).getPhone());
                addressLabel.setText(((Buyer) user).getBillingAddress());
            }
        }
        if (user instanceof Admin) {
            //todo
            phoneLabel.setVisible(false);
            addressLabel.setVisible(false);
        }
        if (user instanceof ShopHolder){
            //todo
        }
        //myProfileImage.setImage();
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

    public void editProfile() {
        editImageView.setVisible(false);
        saveImageView.setVisible(true);
        saveYourProfileLabel.setVisible(true);
        completeYourProfileLabel.setVisible(false);
        modifyYourProfileLabel.setVisible(false);

        //hide label
        nameLabel.setVisible(false);
        surnameLabel.setVisible(false);
        addressLabel.setVisible(false);
        phoneLabel.setVisible(false);

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

    public void saveProfile() {
        if (nameTextField.getText().isBlank() || surnameTextField.getText().isBlank() ||
                streetTextField.getText().isBlank() || cityTextField.getText().isBlank() ||
                countryTextField.getText().isBlank() || zipTextField.getText().isBlank() ||
                phoneTextField.getText().isBlank()) {
            errorLabel.setVisible(true);
            //todo: se qualche field è blank, bisogna inserire tutte le fields
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


                saveYourProfileLabel.setVisible(false);
                completeYourProfileLabel.setVisible(false);
                modifyYourProfileLabel.setVisible(true);

                //editLabel.setText(Constants.MODIFY_YOUR_PROFILE_STRING_CAPSLOCK);
                editImageView.setVisible(true);
                saveImageView.setVisible(false);

                errorLabel.setVisible(false);
                //show labels
                nameLabel.setText(user.getName());
                surnameLabel.setText(user.getSurname());
                addressLabel.setText(((Buyer) user).getBillingAddress());
                phoneLabel.setText(((Buyer) user).getPhone());
                nameLabel.setVisible(true);
                surnameLabel.setVisible(true);
                addressLabel.setVisible(true);
                phoneLabel.setVisible(true);

            }
        }
    }

    @FXML
    public void enterProfileImage() {
        borderProfileImageRectangle.setVisible(true);
    }
    @FXML
    public void exitProfileImage(){
        borderProfileImageRectangle.setVisible(false);
    }
    @FXML
    public void initialize(){
        editImageView.setVisible(true);
        saveImageView.setVisible(false);
    }

    @FXML
    public void onClickProfileImageView() throws IOException {

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
            UserHandler.updateLogoImagePath(user, file, newFileName);
        }
    }
}