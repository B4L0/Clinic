package me.sobolewski.clinic.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import me.sobolewski.clinic.Clinic;
import me.sobolewski.clinic.manager.FXMLManager;
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
    public MenuItem logoutMenuItem;
    public Button patientsButton;
    public Button examinationsButton;
    public SplitMenuButton profileButton;
    public MenuItem historyMenuItem;
    public MenuItem statsMenuItem;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameLabel.setText(loggedDoctor.getFirstName() + " " + loggedDoctor.getLastName());
        specLabel.setText(specLabel.getText() + loggedDoctor.getSpecialization());
        phoneLabel.setText(phoneLabel.getText() + loggedDoctor.getPhoneNumber());
        initClock();
        visitsLabel.setText("Wizyty w sesji: " + Clinic.getLoginSession().getVisitsDone());
    }
    
    public void startVisit() {
        Stage stage = (Stage) startVisitButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("new-visit"));
    }
    
    private void initClock() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(clockLabel.getText());
        Timeline clock = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    if (Clinic.getLoginSession() != null) {
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
    
    public void logout() {
        Stage stage = (Stage) profileButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("login"));
        Clinic.setLoginSession(null);
    }
    
    public void examinations() {
        Stage stage = (Stage) examinationsButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("examinations"));
    }
    
    public void patients() {
        Stage stage = (Stage) patientsButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("patients"));
    }
    
    public void profile() {
        Stage stage = (Stage) profileButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("profile"));
    }
    
    public void history() {
        Stage stage = (Stage) profileButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("history"));
    }
    
    public void stats() {
    }
}
