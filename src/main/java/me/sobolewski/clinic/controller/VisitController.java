package me.sobolewski.clinic.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Duration;
import me.sobolewski.clinic.Clinic;
import me.sobolewski.clinic.manager.FXMLManager;
import me.sobolewski.clinic.model.Examination;
import me.sobolewski.clinic.model.Patient;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class VisitController implements Initializable {
    
    private static final List<Examination> exams = new ArrayList<>();
    
    public Label durationLabel;
    public Label firstNameLabel;
    public Label lastNameLabel;
    public Label PESELLabel;
    public Label phoneNumberLabel;
    public Label emailLabel;
    public Label cityLabel;
    public Label streetLabel;
    public Label numberLabel;
    public Label zipLabel;
    public Button doExaminationButton;
    public Button writePrescriptionButton;
    public Button finishVisitButton;
    public ListView<Examination> examinationsListView;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Patient visitor = Clinic.getCurrentVisit().getPatient();
        
        firstNameLabel.setText("Imię: " + visitor.getFirstName());
        lastNameLabel.setText("Nazwisko: " + visitor.getLastName());
        PESELLabel.setText("PESEL: " + visitor.getPESEL());
        phoneNumberLabel.setText("Nr telefonu: " + visitor.getPhoneNumber());
        emailLabel.setText("Email: " + visitor.getEmail());
        cityLabel.setText("Miasto: " + visitor.getAddress().getCity());
        streetLabel.setText("Ulica: " + visitor.getAddress().getStreet());
        numberLabel.setText("Nr mieszkania: " + visitor.getAddress().getAppNumber());
        zipLabel.setText("Kod pocztowy: " + visitor.getAddress().getZip());
        
        initDuration();
        
        writePrescriptionButton.setDisable(Clinic.getCurrentVisit().getPrescription() != null);
        
        System.out.println(Clinic.getCurrentVisit().getPrescription());
    }
    
    private void initDuration() {
        Timeline duration = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    if (Clinic.getLoginSession() != null) {
                        durationLabel.setText("Czas trwania wizyty: "
                                + ChronoUnit.MINUTES.between(Clinic.getCurrentVisit().getStartTime(), LocalDateTime.now())
                                + " minut(y)");
                    }
                }),
                new KeyFrame(Duration.seconds(1))
        );
        duration.setCycleCount(Animation.INDEFINITE);
        duration.play();
    }
    
    public void doExamination() {
    
    
    }
    
    public void writePrescription() {
        Stage stage = (Stage) writePrescriptionButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("write-prescription"));
    }
    
    public void finishVisit() {
    }
}
