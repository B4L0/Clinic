package me.sobolewski.clinic.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import me.sobolewski.clinic.Clinic;
import me.sobolewski.clinic.manager.FXMLManager;
import me.sobolewski.clinic.model.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {
    
    public Button backButton;
    public TableColumn<Visit, LocalDate> dateColumn;
    public TableColumn<Visit, Patient> patientColumn;
    public TableColumn<Visit, String> firstNameColumn;
    public TableColumn<Visit, String> lastNameColumn;
    public TableColumn<Visit, String> PESELColumn;
    public TableColumn<Visit, LocalTime> startTimeColumn;
    public TableColumn<Visit, LocalTime> finishTimeColumn;
    public TableColumn<Visit, String> examinationsColumn;
    public TableColumn<Visit, String> drugsColumn;
    public TableView<Visit> table;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Doctor doctor = Clinic.getLoginSession().getLoggedDoctor();
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        patientColumn.setCellValueFactory(new PropertyValueFactory<>("patient"));
        firstNameColumn.setCellValueFactory(cellData -> {
            String firstName = cellData.getValue().getPatient().getFirstName();
            return new SimpleStringProperty(firstName);
        });
        lastNameColumn.setCellValueFactory(cellData -> {
            String lastName = cellData.getValue().getPatient().getLastName();
            return new SimpleStringProperty(lastName);
        });
        PESELColumn.setCellValueFactory(cellData -> {
            String PESEL = cellData.getValue().getPatient().getPESEL();
            return new SimpleStringProperty(PESEL);
        });
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        finishTimeColumn.setCellValueFactory(new PropertyValueFactory<>("finishTime"));
        examinationsColumn.setCellValueFactory(cellData ->{
            StringBuilder sb = new StringBuilder();
            for(Examination e : cellData.getValue().getExaminations()){
                sb.append(e.getName()).append(", ");
            }
            return new SimpleStringProperty(sb.substring(0, sb.toString().length() - 2));
        });
        drugsColumn.setCellValueFactory(cellData ->{
            StringBuilder sb = new StringBuilder();
            for(Drug d : cellData.getValue().getPrescription().getDrugs()){
                sb.append(d.getName()).append(", ");
            }
            return new SimpleStringProperty(sb.substring(0, sb.toString().length() - 2));
        });
        
        table.setItems(FXCollections.observableArrayList(doctor.getVisits()));
        
    }
    
    public void back() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("start"));
    }
}
