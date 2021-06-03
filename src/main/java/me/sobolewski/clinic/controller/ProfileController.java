package me.sobolewski.clinic.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import me.sobolewski.clinic.Clinic;
import me.sobolewski.clinic.component.ErrorAlert;
import me.sobolewski.clinic.manager.FXMLManager;
import me.sobolewski.clinic.model.Address;
import me.sobolewski.clinic.model.Doctor;
import me.sobolewski.clinic.model.enums.Specialization;
import me.sobolewski.clinic.model.util.EntityUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    
    private static final ObservableList<Specialization> SPECS =
            FXCollections.observableArrayList(Specialization.values());
    private static final Doctor DOC = Clinic.getLoginSession().getLoggedDoctor();
    public Button backButton;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField phoneField;
    public TextField cityField;
    public TextField streetField;
    public TextField numberField;
    public TextField zipField;
    public ComboBox<Specialization> specCombo;
    public TextField loginField;
    public TextField emailField;
    public PasswordField passwordField;
    public Button editDoctorButton;
    public Button editAccountButton;
    public Button saveAccountButton;
    public Button saveDoctorButton;
    public Button historyButton;
    public Button statsButton;
    public Button logoutButton;
    public GridPane doctorInfoSection;
    public GridPane accuntInfoSection;
    private boolean editingDoctor = false;
    private boolean editingAccount = false;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        specCombo.setItems(SPECS);
        initDoctorInfo();
        initAccountInfo();
    }
    
    private void initDoctorInfo() {
        firstNameField.setText(DOC.getFirstName());
        lastNameField.setText(DOC.getLastName());
        phoneField.setText(DOC.getPhoneNumber());
        cityField.setText(DOC.getAddress().getCity());
        streetField.setText(DOC.getAddress().getStreet());
        numberField.setText(DOC.getAddress().getAppNumber());
        zipField.setText(DOC.getAddress().getZip());
        specCombo.setValue(DOC.getSpecialization());
    }
    
    private void initAccountInfo() {
        loginField.setText(DOC.getLogin());
        passwordField.setText(DOC.getPassword());
        emailField.setText(DOC.getEmail());
    }
    
    
    public void toggleEditDoctor() {
        editingDoctor = !editingDoctor;
        if (editingDoctor) {
            editDoctorButton.setText("Anuluj");
            saveDoctorButton.setDisable(false);
            doctorInfoSection.setDisable(false);
            editAccountButton.setDisable(true);
        } else {
            editDoctorButton.setText("Edytuj");
            saveDoctorButton.setDisable(true);
            doctorInfoSection.setDisable(true);
            editAccountButton.setDisable(false);
            initDoctorInfo();
        }
    }
    
    public void toggleEditAccount() {
        editingAccount = !editingAccount;
        if (editingAccount) {
            editAccountButton.setText("Anuluj");
            saveAccountButton.setDisable(false);
            accuntInfoSection.setDisable(false);
            editDoctorButton.setDisable(true);
        } else {
            editAccountButton.setText("Edytuj");
            saveAccountButton.setDisable(true);
            accuntInfoSection.setDisable(true);
            editDoctorButton.setDisable(false);
            initAccountInfo();
        }
    }
    
    public void save() {
        
        
        if (editingDoctor) {
            boolean exists = false;
            
            DOC.setFirstName(firstNameField.getText());
            DOC.setLastName(lastNameField.getText());
            DOC.setPhoneNumber(phoneField.getText());
            DOC.setSpecialization(specCombo.getValue());
            
            Address address = new Address();
            address.setCity(cityField.getText());
            address.setStreet(streetField.getText());
            address.setAppNumber(numberField.getText());
            address.setZip(zipField.getText());
            
            for (Address a : EntityUtils.getList(Address.class)) {
                if (address.equals(a)) {
                    DOC.setAddress(a);
                    exists = true;
                    break;
                }
            }
            
            if (!exists) {
                DOC.setAddress(address);
                EntityUtils.save(address);
            }
            
            EntityUtils.update(DOC);
            
            toggleEditDoctor();
        }
        
        if (editingAccount) {
            boolean unique = true;
            
            for (Doctor d : EntityUtils.getList(Doctor.class)) {
                if (loginField.getText().equals(d.getLogin())) {
                    unique = false;
                    break;
                }
            }
            if (unique) {
                DOC.setLogin(loginField.getText());
                DOC.setPassword(passwordField.getText());
                DOC.setEmail(emailField.getText());
                
                EntityUtils.update(DOC);
            } else {
                ErrorAlert alert = new ErrorAlert("Błąd", "Ten login jest już zajęty");
                alert.show();
            }
            
            toggleEditAccount();
        }
    }
    
    public void back() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("start"));
    }
    
    public void logout() {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("login"));
        Clinic.setLoginSession(null);
    }
    
    public void history() {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("history"));
    }
    
    public void stats() {
        Stage stage = (Stage) statsButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("stats"));
    }
}
