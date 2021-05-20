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
import lombok.Getter;
import lombok.Setter;
import me.sobolewski.clinic.manager.FXMLManager;
import me.sobolewski.clinic.model.Examination;
import me.sobolewski.clinic.model.enums.ExaminationType;
import me.sobolewski.clinic.model.util.EntityUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class EditExaminationController implements Initializable {
    
    private static final ObservableList<ExaminationType> EXAMINATION_TYPES =
            FXCollections.observableArrayList(ExaminationType.values());
    private final ExaminationsController ec = FXMLManager.getController("examinations");
    
    public BorderPane root;
    public TextField nameField;
    public ComboBox<ExaminationType> typeCombo;
    public TextArea descArea;
    public Button saveButton;
    public Button cancelButton;
    
    @Getter
    @Setter
    private static Examination editedExam;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        root.addEventHandler(KeyEvent.ANY, keyEvent -> saveButton.setDisable(!filled()));
        
        typeCombo.setItems(EXAMINATION_TYPES);
        nameField.setText(editedExam.getName());
        typeCombo.setValue(editedExam.getType());
        descArea.setText(editedExam.getDescription());
        
        System.out.println(editedExam);
    }
    
    public void save(ActionEvent actionEvent) {
        
        editedExam.setName(nameField.getText());
        editedExam.setType(typeCombo.getValue());
        editedExam.setDescription(descArea.getText());
        
        EntityUtils.update(editedExam);
        
        ec.refresh();
        
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
