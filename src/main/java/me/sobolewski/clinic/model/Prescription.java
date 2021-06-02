package me.sobolewski.clinic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "PRESCRIPTIONS_DRUGS",
            joinColumns = @JoinColumn(name = "PRESC_ID"),
            inverseJoinColumns = @JoinColumn(name = "DRUG_ID")
    )
    private Set<Drug> drugs = new HashSet<>();
    
    public void addDrug(Drug drug) {
        this.drugs.add(drug);
        drug.getPrescriptions().add(this);
    }
    
    public void removeDrug(Drug drug) {
        this.drugs.remove(drug);
        drug.getPrescriptions().remove(this);
    }
}
