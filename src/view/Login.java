package view;

import control.UserHandler;
import control.FileElaboration;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Constants;
import model.User.Admin;
import model.User.Buyer;
import model.User.User;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

public class Login {

    // Strings which hold css elements to easily re-use in the application
    protected
    String successMessage = String.format("-fx-text-fill: GREEN;");
    String errorMessage = String.format("-fx-text-fill: RED;");
    String errorStyle = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    String successStyle = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    // Import the application's controls
    @FXML
    private Label invalidLoginCredentials;
    @FXML
    private Label invalidSignupCredentials;
    @FXML
    private Button cancelButton;
    @FXML
    private Button loginButton;
    @FXML
    private TextField loginUsernameTextField;
    @FXML
    private TextField loginPasswordPasswordField;
    @FXML
    private TextField signUpUsernameTextField;
    @FXML
    private TextField signUpEmailTextField;
    @FXML
    private TextField signUpPasswordPasswordField;
    @FXML
    private TextField signUpRepeatPasswordPasswordField;
    @FXML
    private CheckBox rememberMe;
    @FXML
    private Label yocerco;
    @FXML
    DatePicker signUpDateDatePicker;
    @FXML
    ToggleGroup gender;
    @FXML
    ImageView logo;



    // Creation of methods which are activated on events in the forms
    @FXML
    protected void onCancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onLoginButtonClick() throws IOException {
        if (loginUsernameTextField.getText().isBlank() || loginPasswordPasswordField.getText().isBlank()) {
            invalidLoginCredentials.setText("The Login fields are required!");
            invalidLoginCredentials.setStyle(errorMessage);
            invalidSignupCredentials.setText("");
        } else {
            String username = loginUsernameTextField.getText();
            String password = loginPasswordPasswordField.getText();
            if (UserHandler.checkUsernameAndPassword(username,password)) {
                if (rememberMe.isSelected()){
                    System.out.println("selected");
                    String stringToWrite = username + "\n" + password;
                    FileElaboration.writeOnFile(Constants.REMEMBER_LOGIN, stringToWrite);
                }
                else {
                    System.out.println("!selected");
                    FileElaboration.writeOnFile(Constants.REMEMBER_LOGIN, "");
                }
                User u = UserHandler.selectUserFromUsername(username);
                System.out.println(u);
                if (u instanceof Buyer) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
                    Parent root = loader.load();
                    Homepage homepage = loader.getController();
                    homepage.passUser(u);
                    Stage newStage = new Stage();
                    newStage.setScene(new Scene(root));
                    newStage.show();
                    newStage.setResizable(false);
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.close();
                }
                if (u instanceof Admin) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("homepageAdmin.fxml"));
                    Parent root = loader.load();
                    HomepageAdmin homepage = loader.getController();
                    homepage.passUser(u);
                    Stage newStage = new Stage();
                    newStage.setScene(new Scene(root));
                    newStage.show();
                    newStage.setResizable(false);
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.close();
                }
            }
        }
    }

    @FXML
    protected void onSignUpButtonClick() throws InterruptedException {

        if (signUpUsernameTextField.getText().isBlank() || signUpEmailTextField.getText().isBlank() || signUpPasswordPasswordField.getText().isBlank() || signUpRepeatPasswordPasswordField.getText().isBlank()) {
            invalidSignupCredentials.setText("Please fill in all fields!");
            invalidSignupCredentials.setStyle(errorMessage);
            invalidLoginCredentials.setText("");

            if (signUpUsernameTextField.getText().isBlank()) {
                signUpUsernameTextField.setStyle(errorStyle);
            } else if (signUpEmailTextField.getText().isBlank()) {
                signUpEmailTextField.setStyle(errorStyle);
            } else if (signUpPasswordPasswordField.getText().isBlank()) {
                signUpPasswordPasswordField.setStyle(errorStyle);
            } else if (signUpRepeatPasswordPasswordField.getText().isBlank()) {
                signUpRepeatPasswordPasswordField.setStyle(errorStyle);
            }
        } else if (signUpRepeatPasswordPasswordField.getText().equals(signUpPasswordPasswordField.getText())) {
            invalidSignupCredentials.setText("You are set!");
            invalidSignupCredentials.setStyle(successMessage);
            signUpUsernameTextField.setStyle(successStyle);
            signUpEmailTextField.setStyle(successStyle);
            signUpPasswordPasswordField.setStyle(successStyle);
            signUpRepeatPasswordPasswordField.setStyle(successStyle);
            invalidLoginCredentials.setText("");
            RadioButton rb = (RadioButton) gender.getSelectedToggle();
            String genderString = rb.getText();
            UserHandler.insertUser(signUpUsernameTextField.getText(), null, signUpPasswordPasswordField.getText(),
                    signUpEmailTextField.getText(), Date.valueOf(signUpDateDatePicker.getValue()), genderString, Constants.BUYER_USER);
        } else {
            invalidSignupCredentials.setText("The Passwords don't match!");
            invalidSignupCredentials.setStyle(errorMessage);
            signUpPasswordPasswordField.setStyle(errorStyle);
            signUpRepeatPasswordPasswordField.setStyle(errorStyle);
            invalidLoginCredentials.setText("");
        }
    }

    public void initialize() throws IOException {
        //logo = new ImageView(Constants.APP_LOGO);
        ArrayList<String> userdata;
        userdata = FileElaboration.readFromFile(Constants.REMEMBER_LOGIN);
        if (userdata.size() == 2){
            loginUsernameTextField.setText(userdata.get(0));
            loginPasswordPasswordField.setText(userdata.get(1));
            rememberMe.setSelected(true);
        }
    }
}