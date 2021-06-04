package me.sobolewski.clinic.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.sobolewski.clinic.Clinic;
import me.sobolewski.clinic.account.AuthData;
import me.sobolewski.clinic.account.InputValidation;
import me.sobolewski.clinic.account.LoginSession;
import me.sobolewski.clinic.component.InformationAlert;
import me.sobolewski.clinic.manager.FXMLManager;
import me.sobolewski.clinic.model.Address;
import me.sobolewski.clinic.model.Doctor;
import me.sobolewski.clinic.model.enums.Specialization;
import me.sobolewski.clinic.model.util.EntityUtils;

import java.net.URL;
import java.time.LocalDateTime;
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
    public VBox pane;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        registerButton.setDisable(true);
        specCombo.setItems(SPECS);
        
        phoneNumberField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!InputValidation.isValidPhoneNumber(newValue)) {
                if (!phoneNumberField.getStyleClass().contains("wrong-input")) {
                    phoneNumberField.getStyleClass().add("wrong-input");
                }
            } else {
                phoneNumberField.getStyleClass().removeAll("wrong-input");
            }
        });
        
        emailField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!InputValidation.isValidEmail(newValue)) {
                if (!emailField.getStyleClass().contains("wrong-input")) {
                    emailField.getStyleClass().add("wrong-input");
                }
            } else {
                emailField.getStyleClass().removeAll("wrong-input");
            }
        });
        
        pane.addEventHandler(KeyEvent.ANY, event -> registerButton.setDisable((
                emailField.getStyleClass().contains("wrong-input") ||
                phoneNumberField.getStyleClass().contains("wrong-input"))
                || !filled())
        );
    }
    
    public void register() {
        
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
        
        for (Address a : EntityUtils.getList(Address.class)) {
            if (address.equals(a)) {
                doctor.setAddress(a);
                exists = true;
                break;
            }
        }
        
        if (!exists) {
            doctor.setAddress(address);
            EntityUtils.save(address);
        }
        
        EntityUtils.save(doctor);
    
        TextArea textArea = new TextArea("""
                Pomyślnie zarejestrowano!
        
                Wygenerowane dane do logowania:
                Login:\040""" + doctor.getLogin() + """
                
                Hasło:\040""" + doctor.getPassword() + """
                
                
                Dane można zmienić w panelu zarządzania profilem
                Zostaniesz automatycznie zalogowany
                """);
        textArea.setEditable(false);
        textArea.setWrapText(true);
    
        InformationAlert alert = new InformationAlert("Zarejestrowano", "");
        alert.getDialogPane().setContent(textArea);
        
        alert.showAndWait();
        
        Clinic.setLoginSession(new LoginSession(doctor, LocalDateTime.now()));
        
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("start"));
    }
    
    public void back() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("login"));
    }
    
    private boolean filled() {
        return !firstNameField.getText().equals("") &&
                !lastNameField.getText().equals("") &&
                !phoneNumberField.getText().equals("") &&
                !emailField.getText().equals("") &&
                !cityField.getText().equals("") &&
                !streetField.getText().equals("") &&
                !numberField.getText().equals("") &&
                !zipField.getText().equals("") &&
                !(specCombo.getValue() == null);
    }
}
