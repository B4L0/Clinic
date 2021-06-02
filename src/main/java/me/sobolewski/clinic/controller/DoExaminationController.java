package me.sobolewski.clinic.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import me.sobolewski.clinic.Clinic;
import me.sobolewski.clinic.manager.FXMLManager;
import me.sobolewski.clinic.model.Examination;
import me.sobolewski.clinic.model.util.EntityUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class DoExaminationController implements Initializable {
    
    private static ObservableList<Examination> examinationsList;
    
    public Button backButton;
    public TableView<Examination> table;
    public TableColumn<Examination, Long> IDColumn;
    public TableColumn<Examination, String> nameColumn;
    public TableColumn<Examination, String> typeColumn;
    public TableColumn<Examination, String> descColumn;
    public Button doExamButton;
    public TextField searchField;
    public Button searchButton;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    
        examinationsList = FXCollections.observableArrayList(EntityUtils.getList(Examination.class));
        table.setItems(examinationsList);
    }
    
    public void back() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("visit"));
    }
    
    public void doExam() {
        
        if(table.getSelectionModel().getSelectedItem() != null){
            Clinic.getCurrentVisit().getExaminations().add(table.getSelectionModel().getSelectedItem());
        }
    
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("visit"));
        
    }
    
    public void onEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            search();
        }
    }
    
    public void search() {
        FilteredList<Examination> flExams = new FilteredList<>(examinationsList, e ->
                e.getName().toLowerCase().contains(searchField.getText().toLowerCase().trim()));
        table.setItems(flExams);
    }
}
