package me.sobolewski.clinic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

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
}
