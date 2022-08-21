package view;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.User.User;

import java.io.IOException;

public class myProfile {

    User u = null;

    @FXML
    ImageView myProfileImage;

    @FXML
    AnchorPane anchorPane1, anchorPane2;

    @FXML
    protected void onClickAnchorPane2() throws IOException {

    }

    public void passUser(User user) {
        u = user;
        //myProfileImage.setImage();
    }
}
