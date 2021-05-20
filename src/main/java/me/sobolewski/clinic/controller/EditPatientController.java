package me.sobolewski.clinic.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import me.sobolewski.clinic.manager.FXMLManager;
import me.sobolewski.clinic.model.Address;
import me.sobolewski.clinic.model.Patient;
import me.sobolewski.clinic.model.util.EntityUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class EditPatientController implements Initializable {
    
    @Getter
    @Setter
    private static Patient editedPatient;
    private final PatientsController pc = FXMLManager.getController("patients");
    
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
        
        firstNameField.setText(editedPatient.getFirstName());
        lastNameField.setText(editedPatient.getLastName());
        PESELField.setText(editedPatient.getPESEL());
        phoneField.setText(editedPatient.getPhoneNumber());
        emailField.setText(editedPatient.getEmail());
        if (editedPatient.getAddress().getCity() != null)
            cityField.setText(editedPatient.getAddress().getCity());
        streetField.setText(editedPatient.getAddress().getStreet());
        numberField.setText(editedPatient.getAddress().getAppNumber());
        zipField.setText(editedPatient.getAddress().getZip());
    }
    
    
    public void save() {
        boolean exists = false;
        
        Address oldAddress = editedPatient.getAddress();
        Address address = new Address();
        address.setCity(cityField.getText());
        address.setStreet(streetField.getText());
        address.setAppNumber(numberField.getText());
        address.setZip(zipField.getText());
        
        editedPatient.setFirstName(firstNameField.getText());
        editedPatient.setLastName(lastNameField.getText());
        editedPatient.setPESEL(PESELField.getText());
        editedPatient.setPhoneNumber(phoneField.getText());
        if (!emailField.getText().equals("")) {
            editedPatient.setEmail(emailField.getText());
        }
        
        if (!oldAddress.equals(address)) {
            for (Address a : EntityUtils.getList(Address.class)) {
                if (address.equals(a)) {
                    editedPatient.setAddress(a);
                    exists = true;
                    break;
                }
            }
            
            if (!exists) {
                editedPatient.setAddress(address);
                EntityUtils.save(address);
            }
        }
        
        EntityUtils.update(editedPatient);
        
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
