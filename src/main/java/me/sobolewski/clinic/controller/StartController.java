package me.sobolewski.clinic.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import me.sobolewski.clinic.Clinic;
import me.sobolewski.clinic.FXMLManager;
import me.sobolewski.clinic.model.Doctor;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class StartController implements Initializable {
    
    
    private final Doctor loggedDoctor = Clinic.getLoginSession().getLoggedDoctor();
    public ToolBar toolBar;
    public Label nameLabel;
    public Label clockLabel;
    public Label specLabel;
    public Label phoneLabel;
    public Label visitsLabel;
    public Label loggedLabel;
    public Button startVisitButton;
    public MenuButton profileButton;
    public MenuItem logoutMenuItem;
    public Button patientsButton;
    public Button prescriptionsButton;
    public Button examinationsButton;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameLabel.setText(loggedDoctor.getFirstName() + " " + loggedDoctor.getLastName());
        specLabel.setText(specLabel.getText() + loggedDoctor.getSpecialization());
        phoneLabel.setText(phoneLabel.getText() + loggedDoctor.getPhoneNumber());
        initClock();
        
        
    }
    
    
    public void startVisit() {
    
    }
    
    private void initClock() {
        
        //TODO ilość wizyt w sesji
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(clockLabel.getText());
        Timeline clock = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    if(Clinic.getLoginSession() != null){
                        clockLabel.setText(LocalDateTime.now().format(formatter));
                        loggedLabel.setText("Zalogowano: "
                                + ChronoUnit.MINUTES.between(Clinic.getLoginSession().getLoginTime(), LocalDateTime.now())
                                + " minut(y) temu");
                    }
                }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    
    public void logout(ActionEvent actionEvent) {
        
        Stage stage = (Stage) profileButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("login"));
        Clinic.setLoginSession(null);
        
    }
    
    public void prescriptions(ActionEvent actionEvent) {
    
    }
    
    public void examinations(ActionEvent actionEvent) {
        Stage stage = (Stage) examinationsButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("examinations"));
    }
    
    public void patients(ActionEvent actionEvent) {
    
    }
}
