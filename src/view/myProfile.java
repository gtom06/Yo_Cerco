package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import model.Constants;
import model.User.Admin;
import model.User.Buyer;
import model.User.User;

import java.io.IOException;

public class MyProfile {

    User user = null;

    @FXML
    Rectangle borderProfileImageRectangle;

    @FXML
    ImageView myProfileImage;
    @FXML
    Label nameLabel, surnameLabel, usernameLabel, addressLabel, phoneLabel, editLabel;

    @FXML
    TextField nameTextField, surnameTextField, addressTextField, phoneTextField;

    @FXML
    ImageView editImageView, saveImageView;

    @FXML
    AnchorPane anchorPane1, anchorPane2;
    private boolean isSomeFieldBlank;

    @FXML
    protected void onClickAnchorPane2() throws IOException {

    }

    public void passUser(User user) {
        this.user = user;
        usernameLabel.setText(user.getUsername());
        editImageView.setVisible(true);
        saveImageView.setVisible(false);
        if (user instanceof Buyer) {
            if (this.isSomeFieldBlank = ((Buyer) user).isSomeFieldBlank()) {
                editLabel.setText(Constants.COMPLETE_YOUR_PROFILE_CAPSLOCK);
            }
            else {
                editLabel.setText(Constants.MODIFY_YOUR_PROFILE_STRING_CAPSLOCK);
                nameLabel.setText(user.getName());
                surnameLabel.setText(user.getSurname());
                phoneLabel.setText(((Buyer) user).getPhone());
                addressLabel.setText(((Buyer) user).getBillingAddress());
            }
        }
        if (user instanceof Admin) {
            phoneLabel.setVisible(false);
            addressLabel.setVisible(false);
        }
        //myProfileImage.setImage();
    }

    public void editProfile() {
        editImageView.setVisible(false);
        saveImageView.setVisible(true);
        editLabel.setText(Constants.SAVE);

        nameTextField.setVisible(true);
        surnameTextField.setVisible(true);
        addressTextField.setVisible(true);
        phoneTextField.setVisible(true);


    }

    public void saveProfile() {
        if (isSomeFieldBlank == true) {
            nameTextField.setVisible(false);
            surnameTextField.setVisible(false);
            addressTextField.setVisible(false);
            phoneTextField.setVisible(false);
            //todo: se qualche field è blank, bisogna inserire tutte le fields
        }
    }

    public void do1() {
        borderProfileImageRectangle.setVisible(true);
        System.out.println("entered");
    }
    public void exit1(){
        borderProfileImageRectangle.setVisible(false);
        System.out.println("exited");
    }
}
