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
import constants.Constants;
import model.user.Admin;
import model.user.Buyer;
import model.user.User;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Login {

    @FXML
    public CheckBox termsConditionsCheckbox1;
    @FXML
    protected Label invalidLoginCredentials1;
    @FXML
    protected Label invalidSignupCredentials1;
    @FXML
    protected Button cancelButton1;
    @FXML
    protected Button loginButton1;
    @FXML
    protected TextField loginUsernameTextField1;
    @FXML
    protected TextField loginPasswordPasswordField1;
    @FXML
    protected TextField signUpUsernameTextField1;
    @FXML
    protected TextField signUpEmailTextField1;
    @FXML
    protected TextField signUpPasswordPasswordField1;
    @FXML
    protected TextField signUpRepeatPasswordPasswordField1;
    @FXML
    protected CheckBox rememberMe1;
    @FXML
    protected DatePicker signUpDateDatePicker1;
    @FXML
    protected ToggleGroup gender1;


    @FXML
    protected void onCancelButtonClick() {
        Stage stage1 = (Stage) cancelButton1.getScene().getWindow();
        stage1.close();
    }
    protected void changePage() throws FileElaborationException, IOException {
        if (rememberMe1.isSelected()){
            String stringToWrite = loginUsernameTextField1.getText() + "\n" + loginPasswordPasswordField1.getText();
            FileElaboration.writeOnFile(Constants.REMEMBER_LOGIN, stringToWrite);
        }
        else {
            FileElaboration.writeOnFile(Constants.REMEMBER_LOGIN, "");
        }
        User user1 = UserHandler.selectUserFromUsername(loginUsernameTextField1.getText());
        if (user1 instanceof Buyer) {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("homepage.fxml"));
            Parent root1 = loader1.load();
            Homepage homepage1 = loader1.getController();
            homepage1.passUser(user1);
            Stage newStage1 = new Stage();
            newStage1.setScene(new Scene(root1));
            newStage1.show();
            newStage1.setResizable(false);
            Stage stage1 = (Stage) loginButton1.getScene().getWindow();
            stage1.close();
        }
        if (user1 instanceof Admin) {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("homepageAdmin.fxml"));
            Parent root1 = loader1.load();
            HomepageAdmin homepage1 = loader1.getController();
            homepage1.passUser(user1);
            Stage newStage1 = new Stage();
            newStage1.setScene(new Scene(root1));
            newStage1.show();
            newStage1.setResizable(false);
            Stage stage1 = (Stage) loginButton1.getScene().getWindow();
            stage1.close();
        }
    }

    @FXML
    protected void onLoginButtonClick() throws IOException, FileElaborationException {
        if (loginUsernameTextField1.getText().isBlank() || loginPasswordPasswordField1.getText().isBlank()) {
            invalidLoginCredentials1.setText("The Login fields are required!");
            invalidSignupCredentials1.setText("");
        } else {
            String username1 = loginUsernameTextField1.getText();
            String password1 = loginPasswordPasswordField1.getText();
            if (UserHandler.checkUsernameAndPassword(username1,password1)) {
                changePage();
            } else{
                invalidLoginCredentials1.setText("User not found or incorrect fields!");
            }
        }
    }

    @FXML
    protected void onSignUpButtonClick() {
        if (signUpUsernameTextField1.getText().isBlank() || signUpEmailTextField1.getText().isBlank()
                || signUpPasswordPasswordField1.getText().isBlank()
                || signUpRepeatPasswordPasswordField1.getText().isBlank()) {
            invalidSignupCredentials1.setText("Please fill all fields!");
            invalidLoginCredentials1.setText("");
        } else if (signUpRepeatPasswordPasswordField1.getText().equals(signUpPasswordPasswordField1.getText())) {
            invalidSignupCredentials1.setText("You are set!");
            invalidLoginCredentials1.setText("");
            RadioButton rb1 = (RadioButton) gender1.getSelectedToggle();
            String genderString1 = rb1.getText();

            List<? extends Serializable> data1 = Arrays.asList(
                    signUpUsernameTextField1.getText(),
                    null,
                    null,
                    signUpPasswordPasswordField1.getText(),
                    signUpEmailTextField1.getText(),
                    signUpDateDatePicker1.getValue().toString(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    genderString1,
                    Constants.BUYER_USER);
            if (!UserHandler.insertUser2((List<String>) data1)){
                invalidSignupCredentials1.setText("Please use another username!");
                invalidLoginCredentials1.setText("");
            }
            else {
                invalidSignupCredentials1.setText("You are set!");
                loginUsernameTextField1.setText(signUpUsernameTextField1.getText());
                loginPasswordPasswordField1.setText(signUpPasswordPasswordField1.getText());
            }
        } else {
            invalidSignupCredentials1.setText("The Passwords don't match!");
            invalidLoginCredentials1.setText("");
        }
    }

    public void initialize() throws IOException, FileElaborationException {
        List<String> userdata1 = FileElaboration.fileLinesToArrayList(Constants.REMEMBER_LOGIN);
        if (userdata1.size() == 2){
            loginUsernameTextField1.setText(userdata1.get(0));
            loginPasswordPasswordField1.setText(userdata1.get(1));
            rememberMe1.setSelected(true);
        }
    }
}