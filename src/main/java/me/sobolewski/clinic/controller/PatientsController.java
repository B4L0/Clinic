package me.sobolewski.clinic.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import me.sobolewski.clinic.component.ConfirmationAlert;
import me.sobolewski.clinic.manager.FXMLManager;
import me.sobolewski.clinic.model.Address;
import me.sobolewski.clinic.model.Patient;
import me.sobolewski.clinic.model.util.EntityUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class PatientsController implements Initializable {
    
    private static ObservableList<Patient> patientsList;
    
    public Button refreshButton;
    public Button backButton;
    public TextField searchField;
    public Button searchButton;
    public Button newButton;
    public Button editButton;
    public Button deleteButton;
    public TableView<Patient> table;
    public TableColumn<Patient, Long> IDColumn;
    public TableColumn<Patient, String> firstNameColumn;
    public TableColumn<Patient, String> lastNameColumn;
    public TableColumn<Patient, String> PESELColumn;
    public TableColumn<Patient, String> phoneColumn;
    public TableColumn<Patient, String> emailColumn;
    public TableColumn<Patient, Address> addressColumn;
    public TableColumn<Patient, String> cityColumn;
    public TableColumn<Patient, String> streetColumn;
    public TableColumn<Patient, String> numberColumn;
    public TableColumn<Patient, String> zipColumn;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        PESELColumn.setCellValueFactory(new PropertyValueFactory<>("PESEL"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityColumn.setCellValueFactory(cellData -> {
            String city = cellData.getValue().getAddress().getCity();
            return new SimpleStringProperty(city);
        });
        streetColumn.setCellValueFactory(addressStringCellDataFeatures -> {
            String street = addressStringCellDataFeatures.getValue().getAddress().getStreet();
            return new SimpleStringProperty(street);
        });
        numberColumn.setCellValueFactory(addressStringCellDataFeatures -> {
            String number = addressStringCellDataFeatures.getValue().getAddress().getAppNumber();
            return new SimpleStringProperty(number);
        });
        zipColumn.setCellValueFactory(addressStringCellDataFeatures -> {
            String zip = addressStringCellDataFeatures.getValue().getAddress().getZip();
            return new SimpleStringProperty(zip);
        });
        
        refresh();
    }
    
    public void onEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            search();
        }
    }
    
    public void back() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("start"));
    }
    
    public void refresh() {
        patientsList = FXCollections.observableArrayList(EntityUtils.getList(Patient.class));
        table.setItems(patientsList);
    }
    
    public void search() {
        FilteredList<Patient> flPatients = new FilteredList<>(patientsList, p ->
                p.getLastName().toLowerCase().contains(searchField.getText().toLowerCase().trim()) ||
                        p.getFirstName().toLowerCase().contains(searchField.getText().toLowerCase().trim()) ||
                        p.getPESEL().contains(searchField.getText().toLowerCase().trim()));
        table.setItems(flPatients);
    }
    
    public void newPatient() {
    }
    
    public void editPatient() {
    }
    
    public void deletePatient() {
        Patient selectedPatient = table.getSelectionModel().getSelectedItem();
        ConfirmationAlert alert = new ConfirmationAlert("Potwierdzenie", "Czy jesteś pewien?");
    
        alert.showAndWait().ifPresent(type -> {
            if (type.getButtonData().equals(ButtonBar.ButtonData.YES)) {
                EntityUtils.delete(selectedPatient);
                refresh();
            }
        });
    }
}
