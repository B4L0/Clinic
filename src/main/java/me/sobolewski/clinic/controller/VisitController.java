package me.sobolewski.clinic.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import me.sobolewski.clinic.Clinic;
import me.sobolewski.clinic.component.ConfirmationAlert;
import me.sobolewski.clinic.manager.FXMLManager;
import me.sobolewski.clinic.model.*;
import me.sobolewski.clinic.model.util.EntityUtils;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class VisitController implements Initializable {
    
    private static final Visit visit = new Visit();
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
    
    private static ListCell<Examination> examNameCall(ListView<Examination> examinationsListView) {
        return new ListCell<>() {
            @Override
            protected void updateItem(Examination exam, boolean empty) {
                super.updateItem(exam, empty);
                setText(exam == null ? null : exam.getName());
            }
        };
    }
    
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
        
        examinationsListView.setCellFactory(VisitController::examNameCall);
        initDuration();
        
        if (Clinic.getCurrentVisit().getExaminations() != null) {
            if (!Clinic.getCurrentVisit().getExaminations().isEmpty()) {
                examinationsListView.setItems(FXCollections.observableArrayList(Clinic.getCurrentVisit().getExaminations()));
            }
        }
        
        writePrescriptionButton.setDisable(Clinic.getCurrentVisit().getPrescription() != null);
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
        Stage stage = (Stage) doExaminationButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("do-examination"));
    }
    
    public void writePrescription() {
        Stage stage = (Stage) writePrescriptionButton.getScene().getWindow();
        stage.setScene(FXMLManager.loadScene("write-prescription"));
    }
    
    public void finishVisit() {
        Prescription prescription = new Prescription();
        prescription.setDoctor(Clinic.getCurrentVisit().getDoctor());
        prescription.setPatient(Clinic.getCurrentVisit().getPatient());
        prescription.setIssueDate(LocalDate.now());
        
        for (Drug d : Clinic.getCurrentVisit().getPrescription().getDrugs()) {
            prescription.addDrug(d);
        }
        
        visit.setDoctor(Clinic.getCurrentVisit().getDoctor());
        visit.setPatient(Clinic.getCurrentVisit().getPatient());
        visit.setDate(Clinic.getCurrentVisit().getStartTime().toLocalDate());
        visit.setStartTime(Clinic.getCurrentVisit().getStartTime().toLocalTime());
        visit.setPrescription(prescription);
        visit.setFinishTime(LocalDateTime.now().toLocalTime());
        
        for (Examination e : Clinic.getCurrentVisit().getExaminations()) {
            visit.addExamination(e);
        }
        
        ConfirmationAlert alert = new ConfirmationAlert("Potwierdzenie", "Czy jesteś pewny?");
        alert.showAndWait().ifPresent(type -> {
            if (type.getButtonData().equals(ButtonBar.ButtonData.YES)) {
                EntityUtils.save(visit);
                Clinic.getLoginSession().setVisitsDone(Clinic.getLoginSession().getVisitsDone() + 1);
                Stage stage = (Stage) finishVisitButton.getScene().getWindow();
                stage.setScene(FXMLManager.loadScene("start"));
            }
        });
    }
}
