package me.sobolewski.clinic.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import me.sobolewski.clinic.manager.FXMLManager;
import me.sobolewski.clinic.model.Examination;
import me.sobolewski.clinic.model.util.EntityUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class ExaminationsController implements Initializable {
    
    
    public Button backButton;
    public TableView<Examination> table;
    public TableColumn<Examination, Long> IDColumn;
    public TableColumn<Examination, String> nameColumn;
    public TableColumn<Examination, String> typeColumn;
    public TableColumn<Examination, String> descColumn;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        table.getItems().setAll(EntityUtils.getList(Examination.class));
        
    }
    
    
    public void back(ActionEvent actionEvent) {
        
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("start"));
        
    }
}
