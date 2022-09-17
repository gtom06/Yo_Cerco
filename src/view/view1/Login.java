package view.view1;

import control.FileElaboration;
import control.UserHandler;
import exceptions.FileElaborationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Constants;
import model.user.Admin;
import model.user.Buyer;
import model.user.User;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class Login {

    public CheckBox termsConditionsCheckbox;
    // Strings which hold css elements to easily re-use in the application

    protected final String successMessage = String.format("-fx-text-fill: GREEN;");
    final String errorMessage = String.format("-fx-text-fill: RED;");
    final String errorStyle = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    final String successStyle = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    // Import the application's controls
    @FXML
    protected Label invalidLoginCredentials;
    @FXML
    protected Label invalidSignupCredentials;
    @FXML
    protected Button cancelButton;
    @FXML
    protected Button loginButton;
    @FXML
    protected TextField loginUsernameTextField;
    @FXML
    protected TextField loginPasswordPasswordField;
    @FXML
    protected TextField signUpUsernameTextField;
    @FXML
    protected TextField signUpEmailTextField;
    @FXML
    protected TextField signUpPasswordPasswordField;
    @FXML
    protected TextField signUpRepeatPasswordPasswordField;
    @FXML
    protected CheckBox rememberMe;
    @FXML
    protected DatePicker signUpDateDatePicker;
    @FXML
    protected ToggleGroup gender;


    @FXML
    protected void onCancelButtonClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onLoginButtonClick() throws IOException, FileElaborationException {
        if (loginUsernameTextField.getText().isBlank() || loginPasswordPasswordField.getText().isBlank()) {
            invalidLoginCredentials.setText("The Login fields are required!");
            invalidLoginCredentials.setStyle(errorMessage);
            invalidSignupCredentials.setText("");
        } else {
            String username = loginUsernameTextField.getText();
            String password = loginPasswordPasswordField.getText();
            if (UserHandler.checkUsernameAndPassword(username,password)) {
                if (rememberMe.isSelected()){
                    String stringToWrite = username + "\n" + password;
                    FileElaboration.writeOnFile(Constants.REMEMBER_LOGIN, stringToWrite);
                }
                else {
                    FileElaboration.writeOnFile(Constants.REMEMBER_LOGIN, "");
                }
                User u = UserHandler.selectUserFromUsername(username);
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
            } else{
                invalidLoginCredentials.setText("User not found or incorrect fields!");
            }
        }
    }

    @FXML
    protected void onSignUpButtonClick() {
        if (signUpUsernameTextField.getText().isBlank() || signUpEmailTextField.getText().isBlank() || signUpPasswordPasswordField.getText().isBlank() || signUpRepeatPasswordPasswordField.getText().isBlank()) {
            invalidSignupCredentials.setText("Please fill all fields!");
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
            if (!UserHandler.insertUser(signUpUsernameTextField.getText(),
                    null,
                    null,
                    signUpPasswordPasswordField.getText(),
                    signUpEmailTextField.getText(),
                    Date.valueOf(signUpDateDatePicker.getValue()),
                    null,
                    null,
                    null,
                    null,
                    null,
                    genderString,
                    Constants.BUYER_USER)){
                invalidSignupCredentials.setText("Please use another username!");
                invalidSignupCredentials.setStyle(errorMessage);
                signUpEmailTextField.setStyle(errorStyle);
                signUpUsernameTextField.setStyle(errorStyle);
                signUpPasswordPasswordField.setStyle(errorStyle);
                signUpRepeatPasswordPasswordField.setStyle(errorStyle);
                invalidLoginCredentials.setText("");
            } else {
                invalidSignupCredentials.setText("You are set!");
                invalidSignupCredentials.setStyle(successMessage);
                loginUsernameTextField.setText(signUpUsernameTextField.getText());
                loginPasswordPasswordField.setText(signUpPasswordPasswordField.getText());
            }
        } else {
            invalidSignupCredentials.setText("The Passwords don't match!");
            invalidSignupCredentials.setStyle(errorMessage);
            signUpPasswordPasswordField.setStyle(errorStyle);
            signUpRepeatPasswordPasswordField.setStyle(errorStyle);
            invalidLoginCredentials.setText("");
        }
    }

    public void initialize() throws IOException, FileElaborationException {
        List<String> userdata = FileElaboration.fileLinesToArrayList(Constants.REMEMBER_LOGIN);
        if (userdata.size() == 2){
            loginUsernameTextField.setText(userdata.get(0));
            loginPasswordPasswordField.setText(userdata.get(1));
            rememberMe.setSelected(true);
        }
    }
}