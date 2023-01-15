package view.view2;

import bean.UserBean;
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
import constants.Constants;
import model.user.Admin;
import model.user.Buyer;
import model.user.User;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class Login {
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

    protected void changePage() throws FileElaborationException, IOException, AddressException {
        if (rememberMe.isSelected()){
            String stringToWrite = loginUsernameTextField.getText() + "\n" + loginPasswordPasswordField.getText();
            FileElaboration.writeOnFile(Constants.REMEMBER_LOGIN, stringToWrite);
        }
        else {
            FileElaboration.writeOnFile(Constants.REMEMBER_LOGIN, "");
        }

        UserBean user1 = UserHandler.selectUserFromUsername(loginUsernameTextField.getText());
        if (!user1.isAdmin()) {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("homepage.fxml"));
            Parent root1 = loader1.load();
            view.view1.Homepage homepage1 = loader1.getController();
            homepage1.passUser(user1);
            Stage newStage1 = new Stage();
            newStage1.setScene(new Scene(root1));
            newStage1.show();
            newStage1.setResizable(false);
            Stage stage1 = (Stage) loginButton.getScene().getWindow();
            stage1.close();
        }
        else {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("homepageAdmin.fxml"));
            Parent root1 = loader1.load();
            view.view1.HomepageAdmin homepage1 = loader1.getController();
            homepage1.passUser(user1);
            Stage newStage1 = new Stage();
            newStage1.setScene(new Scene(root1));
            newStage1.show();
            newStage1.setResizable(false);
            Stage stage1 = (Stage) loginButton.getScene().getWindow();
            stage1.close();
        }
    }

    @FXML
    protected void onLoginButtonClick() throws IOException, FileElaborationException, AddressException {
        if (loginUsernameTextField.getText().isBlank() || loginPasswordPasswordField.getText().isBlank()) {
            invalidLoginCredentials.setText("The Login fields are required!");
            invalidSignupCredentials.setText("");
        } else {
            String username = loginUsernameTextField.getText();
            String password = loginPasswordPasswordField.getText();
            if (UserHandler.checkUsernameAndPassword(username,password)) {
                changePage();
            } else{
                invalidLoginCredentials.setText("User not found or incorrect fields!");
            }
        }
    }

    @FXML
    protected void onSignUpButtonClick(){

        if (signUpUsernameTextField.getText().isBlank() || signUpEmailTextField.getText().isBlank() || signUpPasswordPasswordField.getText().isBlank() || signUpRepeatPasswordPasswordField.getText().isBlank() || billingStreetTextField.getText().isBlank() ||
                billingCityTextField.getText().isBlank() || billingCountryTextField.getText().isBlank() ||
                billingZipTextField.getText().isBlank() || phoneTextField.getText().isBlank() || nameTextField.getText().isBlank() || surnameTextField.getText().isBlank()) {
            invalidSignupCredentials.setText("Please fill all fields!");
            invalidLoginCredentials.setText("");
        } else if (signUpRepeatPasswordPasswordField.getText().equals(signUpPasswordPasswordField.getText())) {
            invalidSignupCredentials.setText("You are set!");
            invalidLoginCredentials.setText("");
            RadioButton rb = (RadioButton) gender.getSelectedToggle();
            String genderString = rb.getText();
            List<? extends Serializable> data = Arrays.asList(
                    signUpUsernameTextField.getText(),
                    null,
                    null,
                    signUpPasswordPasswordField.getText(),
                    signUpEmailTextField.getText(),
                    signUpDateDatePicker.getValue().toString(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    genderString,
                    Constants.BUYER_USER);
            if (!UserHandler.insertUser2((List<String>) data)){
                invalidSignupCredentials.setText("Please use another username!");
                invalidLoginCredentials.setText("");
            }
            else {
                invalidSignupCredentials.setText("You are set!");
                loginUsernameTextField.setText(signUpUsernameTextField.getText());
                loginPasswordPasswordField.setText(signUpPasswordPasswordField.getText());
            }
        } else {
            invalidSignupCredentials.setText("The Passwords don't match!");
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
