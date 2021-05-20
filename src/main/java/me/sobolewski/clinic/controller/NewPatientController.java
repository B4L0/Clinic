package me.sobolewski.clinic.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import me.sobolewski.clinic.model.Address;
import me.sobolewski.clinic.model.Patient;
import me.sobolewski.clinic.model.util.EntityUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class NewPatientController implements Initializable {
    
    
    public BorderPane root;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField PESELField;
    public TextField phoneField;
    public TextField emailField;
    public TextField cityField;
    public TextField streetField;
    public TextField numberField;
    public TextField zipField;
    public Button saveButton;
    public Button cancelButton;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        root.addEventHandler(KeyEvent.ANY, keyEvent -> saveButton.setDisable(!filled()));
    }
    
    public void save() {
        boolean exists = false;
        
        Address address = new Address();
        address.setCity(cityField.getText());
        address.setStreet(streetField.getText());
        address.setAppNumber(numberField.getText());
        address.setZip(zipField.getText());
        
        Patient patient = new Patient();
        patient.setFirstName(firstNameField.getText());
        patient.setLastName(lastNameField.getText());
        patient.setPESEL(PESELField.getText());
        patient.setPhoneNumber(phoneField.getText());
        if (!emailField.getText().equals("")) {
            patient.setEmail(emailField.getText());
        }
        
        for (Address a : EntityUtils.getList(Address.class)) {
            if (address.equals(a)) {
                patient.setAddress(a);
                exists = true;
                break;
            }
        }
        
        if (!exists) {
            patient.setAddress(address);
            EntityUtils.save(address);
        }
        
        EntityUtils.save(patient);
        
        close();
    }
    
    public void close() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
    private boolean filled() {
        return !firstNameField.getText().equals("") &&
                !lastNameField.getText().equals("") &&
                !PESELField.getText().equals("") &&
                !numberField.getText().equals("") &&
                !cityField.getText().equals("") &&
                !streetField.getText().equals("") &&
                !numberField.getText().equals("") &&
                !zipField.getText().equals("");
    }
}
