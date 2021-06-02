package me.sobolewski.clinic;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import me.sobolewski.clinic.account.CurrentVisit;
import me.sobolewski.clinic.account.LoginSession;
import me.sobolewski.clinic.manager.FXMLManager;

import java.util.Objects;

public class Clinic extends Application {
    
    @Getter
    @Setter
    private static LoginSession loginSession;
    
    @Getter
    @Setter
    private static CurrentVisit currentVisit;
    
    public static void main(String[] args) {
        launch(args);
    
//        Doctor doctor = EntityUtils.getByID(Doctor.class, 1);
//        Patient patient = EntityUtils.getByID(Patient.class, 1);
//
//        Prescription prescription = new Prescription();
//        prescription.setDoctor(doctor);
//        prescription.setPatient(patient);
//        prescription.setIssueDate(LocalDate.now());
//        prescription.addDrug(EntityUtils.getByID(Drug.class, 1));
//        prescription.addDrug(EntityUtils.getByID(Drug.class, 2));
//
//        Visit visit = new Visit();
//        visit.setDoctor(doctor);
//        visit.setPatient(patient);
//        visit.setStartTime(LocalTime.now());
//        visit.setFinishTime(LocalTime.now());
//        visit.setDate(LocalDate.now());
//        visit.setPrescription(prescription);
//        visit.addExamination(EntityUtils.getByID(Examination.class, 1));
//        visit.addExamination(EntityUtils.getByID(Examination.class, 21));
//
//
//        EntityUtils.save(visit);
        
    }
    
    @Override
    public void start(Stage stage) {
        
        stage.setScene(FXMLManager.loadScene("login"));
        stage.setTitle("Przychodnia");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.png"))));
        stage.show();
        
    }
    
}
