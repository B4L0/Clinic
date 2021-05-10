package me.sobolewski.clinic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "PRESCRIPTIONS")
public class Prescription implements Serializable {
    
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRESC_ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "PATIENT", referencedColumnName = "PATIENT_ID")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "DOCTOR", referencedColumnName = "DOCTOR_ID")
    private Doctor doctor;
    @Column(name = "ISSUE_DATE")
    private LocalDate issueDate;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "PRESCRIPTIONS_DRUGS",
            joinColumns = @JoinColumn(name = "PRESC_ID"),
            inverseJoinColumns = @JoinColumn(name = "DRUG_ID")
    )
    private List<Drug> drugs = new ArrayList<>();

}
