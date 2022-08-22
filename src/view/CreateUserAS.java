package view;

import control.UserHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Constants;
import model.User.User;

import java.io.IOException;
import java.sql.Date;

public class CreateUserAS {
    User u = null;
    // Strings which hold css elements to easily re-use in the application
    protected
    String successMessage = String.format("-fx-text-fill: GREEN;");
    String errorMessage = String.format("-fx-text-fill: RED;");
    String errorStyle = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    String successStyle = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    @FXML
    private TextField signUpUsernameTextField;
    @FXML
    private TextField signUpEmailTextField;
    @FXML
    private Label yocerco;
    @FXML
    ToggleGroup role;
    @FXML
    private Label labelHi;
    @FXML
    private ImageView homepageImageView;

    @FXML
    protected void onSignUpButtonClick() throws InterruptedException {

        if (signUpUsernameTextField.getText().isBlank() || signUpEmailTextField.getText().isBlank()) {
            signUpEmailTextField.clear();
            signUpUsernameTextField.clear();
        } else {
            RadioButton rb = (RadioButton) role.getSelectedToggle();
            String roleText = rb.getText();
            //Admin Buyer Owner
            UserHandler.insertUser(signUpUsernameTextField.getText(),
                    null,
                    null,
                    Constants.STANDARD_PASSWORD,
                    signUpEmailTextField.getText(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    roleText);
        }
    }

    @FXML
    protected void onLogoutButtonClick() throws IOException {
        Stage stage2 = new Stage();
        Parent root = FXMLLoader.load((getClass().getResource("login.fxml")));
        Scene scene = new Scene(root, 1000, 700);
        stage2.setTitle("LoginPage");
        stage2.setScene(scene);
        stage2.show();
        stage2.setResizable(false);
        /*
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
        */
    }

    @FXML
    protected void onHomepageImageClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homepageAdmin.fxml"));
        Parent root = loader.load();
        HomepageAdmin homepage = loader.getController();
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
}
