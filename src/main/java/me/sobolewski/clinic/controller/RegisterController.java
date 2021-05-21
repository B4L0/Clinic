package me.sobolewski.clinic.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.sobolewski.clinic.account.AuthData;
import me.sobolewski.clinic.account.InputValidation;
import me.sobolewski.clinic.manager.FXMLManager;
import me.sobolewski.clinic.model.Address;
import me.sobolewski.clinic.model.Doctor;
import me.sobolewski.clinic.model.enums.Specialization;
import me.sobolewski.clinic.model.util.EntityUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    
    private static final ObservableList<Specialization> SPECS =
            FXCollections.observableArrayList(Specialization.values());
    
    @FXML
    public Button registerButton;
    public Button backButton;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField phoneNumberField;
    public TextField emailField;
    public TextField cityField;
    public TextField streetField;
    public TextField numberField;
    public TextField zipField;
    public ComboBox<Specialization> specCombo;
    public Label errorMsg;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        specCombo.setItems(SPECS);
        
        phoneNumberField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!InputValidation.isValidPhoneNumber(newValue)) {
                if(!phoneNumberField.getStyleClass().contains("wrong-input")){
                    phoneNumberField.getStyleClass().add("wrong-input");
                }
            } else {
                phoneNumberField.getStyleClass().removeAll("wrong-input");
            }
        });
        
        emailField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!InputValidation.isValidEmail(newValue)) {
                if(!emailField.getStyleClass().contains("wrong-input")){
                    emailField.getStyleClass().add("wrong-input");
                }
            } else {
                emailField.getStyleClass().removeAll("wrong-input");
            }
        });
        
    }
    
    public void register(ActionEvent actionEvent) {
        
        boolean exists = false;
        
        Address address = new Address();
        address.setCity(cityField.getText());
        address.setStreet(streetField.getText());
        address.setAppNumber(numberField.getText());
        address.setZip(zipField.getText());
        
        Doctor doctor = new Doctor();
        doctor.setFirstName(firstNameField.getText());
        doctor.setLastName(lastNameField.getText());
        doctor.setPhoneNumber(phoneNumberField.getText());
        doctor.setEmail(emailField.getText());
        doctor.setSpecialization(specCombo.getValue());
        doctor.setLogin(AuthData.Login.generate());
        doctor.setPassword(AuthData.Password.generate());
        
        for(Address a : EntityUtils.getList(Address.class)){
            if(address.equals(a)){
                doctor.setAddress(a);
                exists = true;
                break;
            }
        }
        
        if(!exists){
            doctor.setAddress(address);
            EntityUtils.save(address);
        }
        
        EntityUtils.save(doctor);
        
    }
    
    public void back() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("login"));
    }
}
