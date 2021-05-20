package me.sobolewski.clinic.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import me.sobolewski.clinic.model.Examination;
import me.sobolewski.clinic.model.enums.ExaminationType;
import me.sobolewski.clinic.model.util.EntityUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class NewExaminationController implements Initializable {
    
    private static final ObservableList<ExaminationType> EXAMINATION_TYPES =
            FXCollections.observableArrayList(ExaminationType.values());
    
    public BorderPane root;
    public TextField nameField;
    public ComboBox<ExaminationType> typeCombo;
    public TextArea descArea;
    public Button saveButton;
    public Button cancelButton;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeCombo.setItems(EXAMINATION_TYPES);
        root.addEventHandler(KeyEvent.ANY, keyEvent -> saveButton.setDisable(!filled()));
    }
    
    public void save(ActionEvent actionEvent) {
        Examination exam = new Examination();
        exam.setName(nameField.getText());
        exam.setType(typeCombo.getValue());
        exam.setDescription(descArea.getText());
        
        EntityUtils.save(exam);
        
        ExaminationsController.setExaminationsList(
                FXCollections.observableArrayList(EntityUtils.getList(Examination.class)));
        
        close(actionEvent);
    }
    
    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
    private boolean filled() {
        return !nameField.getText().equals("") && typeCombo.getValue() != null && !descArea.getText().equals("");
    }
    
    
}
