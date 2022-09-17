package view.view2;

import control.FileElaboration;
import control.UserHandler;
import exceptions.AddressException;
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

    protected final
    String successMessage = String.format("-fx-text-fill: WHITE;");
    final String errorMessage = String.format("-fx-text-fill: RED;");
    final String errorStyle = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    final String successStyle = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");


    @FXML
    private Label invalidLoginCredentials;
    @FXML
    private Label invalidSignupCredentials;
    @FXML
    private Button loginButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
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
    private TextField billingStreetTextField;
    @FXML
    private TextField billingCityTextField;
    @FXML
    private TextField billingCountryTextField;
    @FXML
    private TextField billingZipTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private CheckBox rememberMe;
    @FXML
    DatePicker signUpDateDatePicker;
    @FXML
    ToggleGroup gender;

    @FXML
    protected void onLoginButtonClick() throws IOException, AddressException, FileElaborationException {
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
                    homepage.passParams(u);
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
                    homepage.passParams(u);
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
    protected void onSignUpButtonClick(){

        if (signUpUsernameTextField.getText().isBlank() || signUpEmailTextField.getText().isBlank() || signUpPasswordPasswordField.getText().isBlank() || signUpRepeatPasswordPasswordField.getText().isBlank() || billingStreetTextField.getText().isBlank() ||
                billingCityTextField.getText().isBlank() ||                billingCountryTextField.getText().isBlank() ||
                billingZipTextField.getText().isBlank() ||                phoneTextField.getText().isBlank() || nameTextField.getText().isBlank() || surnameTextField.getText().isBlank()) {
            invalidSignupCredentials.setText("Please fill all fields!");
            invalidSignupCredentials.setStyle(errorMessage);
            invalidLoginCredentials.setText("");
            if (nameTextField.getText().isBlank()) {
                nameTextField.setStyle(errorStyle);
            } else if (surnameTextField.getText().isBlank()) {
                surnameTextField.setStyle(errorStyle);
            } else if (signUpUsernameTextField.getText().isBlank()) {
                signUpUsernameTextField.setStyle(errorStyle);
            } else if (signUpEmailTextField.getText().isBlank()) {
                signUpEmailTextField.setStyle(errorStyle);
            } else if (signUpPasswordPasswordField.getText().isBlank()) {
                signUpPasswordPasswordField.setStyle(errorStyle);
            } else if (signUpRepeatPasswordPasswordField.getText().isBlank()) {
                signUpRepeatPasswordPasswordField.setStyle(errorStyle);
            } else if (billingStreetTextField.getText().isBlank()) {
                billingStreetTextField.setStyle(errorStyle);
            } else if (billingCityTextField.getText().isBlank()) {
                billingCityTextField.setStyle(errorStyle);
            } else if (billingCountryTextField.getText().isBlank()) {
                billingCountryTextField.setStyle(errorStyle);
            } else if (billingZipTextField.getText().isBlank()) {
                billingZipTextField.setStyle(errorStyle);
            } else if (phoneTextField.getText().isBlank()) {
                phoneTextField.setStyle(errorStyle);
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
                    nameTextField.getText(),
                    surnameTextField.getText(),
                    signUpPasswordPasswordField.getText(),
                    signUpEmailTextField.getText(),
                    Date.valueOf(signUpDateDatePicker.getValue()),
                    billingStreetTextField.getText(),
                    billingCityTextField.getText(),
                    billingCountryTextField.getText(),
                    billingZipTextField.getText(),
                    phoneTextField.getText(),
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
