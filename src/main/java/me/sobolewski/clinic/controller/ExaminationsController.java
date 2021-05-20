package me.sobolewski.clinic.controller;

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
import me.sobolewski.clinic.model.Examination;
import me.sobolewski.clinic.model.util.EntityUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class ExaminationsController implements Initializable {
    
    private static ObservableList<Examination> examinationsList;
    
    public Button backButton;
    public TableView<Examination> table;
    public TableColumn<Examination, Long> IDColumn;
    public TableColumn<Examination, String> nameColumn;
    public TableColumn<Examination, String> typeColumn;
    public TableColumn<Examination, String> descColumn;
    public Button searchButton;
    public TextField searchField;
    public Button newButton;
    public Button editButton;
    public Button deleteButton;
    public Button refreshButton;
    
    public static void setExaminationsList(ObservableList<Examination> examinationsList) {
        ExaminationsController.examinationsList = examinationsList;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        refresh();
        
        table.getSelectionModel().select(0);
    }
    
    
    public void back() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("start"));
    }
    
    public void search() {
        FilteredList<Examination> flExams = new FilteredList<>(examinationsList, e ->
                e.getName().toLowerCase().contains(searchField.getText().toLowerCase().trim()));
        table.setItems(flExams);
    }
    
    public void onEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            search();
        }
    }
    
    public void newExam() {
        FXMLManager.openSceneInNewWindow("new-examination", "Nowe badanie");
        refresh();
    }
    
    public void editExam() {
        if(table.getSelectionModel().getSelectedItem() != null){
            EditExaminationController.setEditedExam(table.getSelectionModel().getSelectedItem());
            FXMLManager.openSceneInNewWindow("edit-examination", "Edycja badania");
            refresh();
        }
    }
    
    public void deleteExam() {
        Examination selectedExam = table.getSelectionModel().getSelectedItem();
        ConfirmationAlert alert = new ConfirmationAlert("Potwierdzenie", "Czy jesteś pewien?");
        
        alert.showAndWait().ifPresent(type -> {
            if (type.getButtonData().equals(ButtonBar.ButtonData.YES)) {
                EntityUtils.delete(selectedExam);
                refresh();
            }
        });
    }
    
    public void refresh() {
        examinationsList = FXCollections.observableArrayList(EntityUtils.getList(Examination.class));
        table.setItems(examinationsList);
    }
}
