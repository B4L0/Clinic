package me.sobolewski.clinic.account;

import lombok.Data;
import lombok.NonNull;
import me.sobolewski.clinic.model.Doctor;
import me.sobolewski.clinic.model.Examination;
import me.sobolewski.clinic.model.Patient;
import me.sobolewski.clinic.model.Prescription;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class CurrentVisit {
    
    @NonNull
    private Doctor doctor;
    @NonNull
    private Patient patient;
    @NonNull
    private LocalDateTime startTime;
    private Prescription prescription;
    private Set<Examination> examinations = new HashSet<>();
    
}
