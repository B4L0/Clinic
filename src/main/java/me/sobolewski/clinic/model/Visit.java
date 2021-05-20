package me.sobolewski.clinic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Data
@Table(name = "VISITS")
public class Visit implements Serializable {
    
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VISIT_ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "DOCTOR", referencedColumnName = "DOCTOR_ID")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "PATIENT", referencedColumnName = "PATIENT_ID")
    private Patient patient;
    @Column(name = "VISIT_DATE")
    private LocalDate date;
    @Column(name = "START_TIME")
    private LocalTime startTime;
    @Column(name = "FINISH_TIME")
    private LocalTime finishTime;
    @Column(name = "INTERVIEW")
    private String interview;
    @OneToOne
    @JoinColumn(name = "PRESCRIPTION", referencedColumnName = "PRESC_ID")
    private Prescription prescription;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "VISITS_EXAMINATIONS",
            joinColumns = @JoinColumn(name = "VISIT_ID"),
            inverseJoinColumns = @JoinColumn(name = "EXAM_ID")
    )
    private Set<Examination> examinations;
    
}
