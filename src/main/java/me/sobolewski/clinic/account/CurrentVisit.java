package me.sobolewski.clinic.account;

import lombok.Data;
import lombok.NonNull;
import me.sobolewski.clinic.model.Doctor;
import me.sobolewski.clinic.model.Patient;
import me.sobolewski.clinic.model.Prescription;

import java.time.LocalDateTime;

@Data
public class CurrentVisit {
    
    @NonNull
    private Doctor doctor;
    @NonNull
    private Patient patient;
    @NonNull
    private LocalDateTime startTime;
    private Prescription prescription;
    
}
