package me.sobolewski.clinic.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import me.sobolewski.clinic.Clinic;
import me.sobolewski.clinic.manager.FXMLManager;
import me.sobolewski.clinic.model.Drug;
import me.sobolewski.clinic.model.Prescription;
import me.sobolewski.clinic.model.util.EntityUtils;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Set;

public class WritePrescriptionController implements Initializable {
    
    private static ObservableList<Drug> drugList;
    
    public ListView<Drug> drugListView;
    public TextField searchField;
    public Button searchButton;
    public Button addButton;
    public ListView<Drug> prescDrugListView;
    public Button removeButton;
    public Button writeButton;
    
    private static ListCell<Drug> drugNameCall(ListView<Drug> drugListView) {
        return new ListCell<>() {
            @Override
            protected void updateItem(Drug drug, boolean empty) {
                super.updateItem(drug, empty);
                setText(drug == null ? null : drug.getName());
            }
        };
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drugList = FXCollections.observableArrayList(EntityUtils.getList(Drug.class));
        drugListView.setItems(drugList);
        
        drugListView.setCellFactory(WritePrescriptionController::drugNameCall);
        prescDrugListView.setCellFactory(WritePrescriptionController::drugNameCall);
    }
    
    public void onEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            search();
        }
    }
    
    public void search() {
        FilteredList<Drug> flDrugs = new FilteredList<>(drugList, d ->
                d.getName().toLowerCase().contains(searchField.getText().toLowerCase().trim()));
        drugListView.setItems(flDrugs);
    }
    
    public void write() {
        Prescription presc = new Prescription();
        presc.setDoctor(Clinic.getCurrentVisit().getDoctor());
        presc.setPatient(Clinic.getCurrentVisit().getPatient());
        presc.setDrugs(Set.copyOf(prescDrugListView.getItems()));
        presc.setIssueDate(LocalDate.now());
        
        Clinic.getCurrentVisit().setPrescription(presc);
        
        Stage stage = (Stage) writeButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("visit"));
    }
    
    public void add() {
        if (drugListView.getSelectionModel().getSelectedItem() != null) {
            if (!prescDrugListView.getItems().contains(drugListView.getSelectionModel().getSelectedItem())) {
                prescDrugListView.getItems().add(drugListView.getSelectionModel().getSelectedItem());
            }
        }
    }
    
    public void remove() {
        if (prescDrugListView.getSelectionModel().getSelectedItem() != null) {
            prescDrugListView.getItems().remove(prescDrugListView.getSelectionModel().getSelectedItem());
        }
    }
}
