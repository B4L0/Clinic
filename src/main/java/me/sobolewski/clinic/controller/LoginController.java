package me.sobolewski.clinic.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.sobolewski.clinic.Clinic;
import me.sobolewski.clinic.account.LoginSession;
import me.sobolewski.clinic.component.ErrorAlert;
import me.sobolewski.clinic.manager.FXMLManager;
import me.sobolewski.clinic.model.Doctor;
import me.sobolewski.clinic.model.util.EntityUtils;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    
    @FXML
    public VBox container;
    public TextField loginField;
    public PasswordField passwordField;
    public Button loginButton;
    public Button registerButton;
    public Label errorMsg;
    
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    
    
    public void login() {
        
        String login = loginField.getText();
        String password = passwordField.getText();
        
        boolean success = false;
        
        for (Doctor d : EntityUtils.getList(Doctor.class)) {
            if (d.getLogin().equals(login)) {
                if (d.getPassword().equals(password)) {
                    Clinic.setLoginSession(new LoginSession(d, LocalDateTime.now()));
                    success = true;
                    break;
                }
            }
        }
        if (success) {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(FXMLManager.loadScene("start"));
        } else {
            ErrorAlert alert = new ErrorAlert("Błąd", "Nieprawidłowy login lub hasło!");
            alert.show();
            passwordField.setText("");
        }
    }
    
    public void register() {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("register"));
    }
    
    
    public void onEnter(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            login();
        }
    }
}
