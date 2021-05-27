package me.sobolewski.clinic.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.sobolewski.clinic.Clinic;
import me.sobolewski.clinic.account.CurrentVisit;
import me.sobolewski.clinic.component.ErrorAlert;
import me.sobolewski.clinic.manager.FXMLManager;
import me.sobolewski.clinic.model.Address;
import me.sobolewski.clinic.model.Patient;
import me.sobolewski.clinic.model.util.EntityUtils;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class NewVisitController implements Initializable {
    
    private static ObservableList<Patient> patientsList;
    
    public ToggleGroup patient;
    public RadioButton listRadioButton;
    public RadioButton inputRadioButton;
    public TableView<Patient> table;
    public TableColumn<Patient, Long> IDColumn;
    public TableColumn<Patient, String> firstNameColumn;
    public TableColumn<Patient, String> lastNameColumn;
    public TableColumn<Patient, String> PESELColumn;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField PESELField;
    public TextField phoneField;
    public TextField emailField;
    public TextField cityField;
    public TextField streetField;
    public TextField numberField;
    public TextField zipField;
    public VBox searchBox;
    public TextField searchField;
    public Button searchButton;
    public Button backButton;
    public GridPane patientInfoPane1;
    public GridPane patientInfoPane2;
    public Button startVisistButton;
    
    private Patient visitor;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        PESELColumn.setCellValueFactory(new PropertyValueFactory<>("PESEL"));
        
        patientsList = FXCollections.observableArrayList(EntityUtils.getList(Patient.class));
        table.setItems(patientsList);
        
        searchField.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) search();
        });
    }
    
    public void toggle(ActionEvent actionEvent) {
        
        if (actionEvent.getSource().equals(listRadioButton)) {
            patientInfoPane1.setDisable(true);
            patientInfoPane2.setDisable(true);
            searchBox.setDisable(false);
            table.setDisable(false);
        } else {
            patientInfoPane1.setDisable(false);
            patientInfoPane2.setDisable(false);
            searchBox.setDisable(true);
            table.setDisable(true);
        }
        
    }
    
    public void search() {
        FilteredList<Patient> flPatients = new FilteredList<>(patientsList, p ->
                p.getLastName().toLowerCase().contains(searchField.getText().toLowerCase().trim()) ||
                        p.getFirstName().toLowerCase().contains(searchField.getText().toLowerCase().trim()) ||
                        p.getPESEL().contains(searchField.getText().toLowerCase().trim()));
        table.setItems(flPatients);
    }
    
    public void back() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("start"));
    }
    
    public void startVisit() {
        if (listRadioButton.isSelected()) {
            if (table.getSelectionModel().getSelectedItem() != null)
                visitor = table.getSelectionModel().getSelectedItem();
        } else {
            if (filled()) {
                boolean exists = false;
                
                Address address = new Address();
                address.setCity(cityField.getText());
                address.setStreet(streetField.getText());
                address.setAppNumber(numberField.getText());
                address.setZip(zipField.getText());
                
                visitor = new Patient();
                visitor.setFirstName(firstNameField.getText());
                visitor.setLastName(lastNameField.getText());
                visitor.setPESEL(PESELField.getText());
                visitor.setPhoneNumber(phoneField.getText());
                if (!emailField.getText().equals("")) {
                    visitor.setEmail(emailField.getText());
                }
                
                for (Address a : EntityUtils.getList(Address.class)) {
                    if (address.equals(a)) {
                        visitor.setAddress(a);
                        exists = true;
                        break;
                    }
                }
                
                if (!exists) {
                    visitor.setAddress(address);
                    EntityUtils.save(address);
                }
                
                EntityUtils.save(visitor);
            } else {
                ErrorAlert alert = new ErrorAlert("Błąd", "Uzupełnij wszystkie dane pacjenta");
                alert.show();
            }
        }
        if(visitor != null){
            Clinic.setCurrentVisit(new CurrentVisit(Clinic.getLoginSession().getLoggedDoctor(), visitor, LocalDateTime.now()));
            
        }else{
            ErrorAlert alert = new ErrorAlert("Błąd", "Pacjent nie został poprawnie zdefiniowany");
            alert.show();
        }
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
