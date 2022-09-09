package view.view2;

import control.FileElaboration;
import control.UserHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Constants;
import model.User.Buyer;
import model.User.User;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

public class Login {

    protected
    String successMessage = String.format("-fx-text-fill: GREEN;");
    String errorMessage = String.format("-fx-text-fill: RED;");
    String errorStyle = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    String successStyle = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");


    @FXML
    private Label invalidLoginCredentials;
    @FXML
    private Label invalidSignupCredentials;
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
                    String stringToWrite = username + "\n" + password;
                    FileElaboration.writeOnFile(Constants.REMEMBER_LOGIN, stringToWrite);
                }
                else {
                    FileElaboration.writeOnFile(Constants.REMEMBER_LOGIN, "");
                }
                User u = UserHandler.selectUserFromUsername(username);
                System.out.println(u);
                if (u instanceof Buyer) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
                    Parent root = loader.load();
                    Homepage homepage = loader.getController();
                    System.out.println(u);
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
    protected void onSignUpButtonClick() throws IOException {

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
            } else if (billingStreetTextField.getText().isBlank()) {

            } else if (billingCityTextField.getText().isBlank()) {

            } else if (billingCountryTextField.getText().isBlank()){

            } else if (billingZipTextField.getText().isBlank()){

            } else if (phoneTextField.getText().isBlank()){

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
            UserHandler.insertUser(signUpUsernameTextField.getText(),
                    null,
                    null,
                    signUpPasswordPasswordField.getText(),
                    signUpEmailTextField.getText(),
                    Date.valueOf(signUpDateDatePicker.getValue()),
                    billingStreetTextField.getText(),
                    billingCityTextField.getText(),
                    billingCountryTextField.getText(),
                    billingZipTextField.getText(),
                    phoneTextField.getText(),
                    genderString,
                    Constants.BUYER_USER);
            loginUsernameTextField.setText(signUpUsernameTextField.getText());
            loginPasswordPasswordField.setText(signUpPasswordPasswordField.getText());
        } else {
            invalidSignupCredentials.setText("The Passwords don't match!");
            invalidSignupCredentials.setStyle(errorMessage);
            signUpPasswordPasswordField.setStyle(errorStyle);
            signUpRepeatPasswordPasswordField.setStyle(errorStyle);
            invalidLoginCredentials.setText("");
        }
    }

    public void initialize() throws IOException {
        ArrayList<String> userdata;
        userdata = FileElaboration.fileLinesToArrayList(Constants.REMEMBER_LOGIN);
        if (userdata.size() == 2){
            loginUsernameTextField.setText(userdata.get(0));
            loginPasswordPasswordField.setText(userdata.get(1));
            rememberMe.setSelected(true);
        }
    }
}