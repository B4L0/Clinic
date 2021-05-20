package me.sobolewski.clinic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "DRUGS")
public class Drug implements Serializable {
    
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DRUG_ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    
    @ToString.Exclude
    @ManyToMany(mappedBy = "drugs")
    private Set<Prescription> prescriptions;
    
}
