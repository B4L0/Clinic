package me.sobolewski.clinic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.sobolewski.clinic.model.enums.ExaminationType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Table(name = "EXAMINATIONS")
public class Examination implements Serializable {
    
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXAM_ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private ExaminationType type;
    @Column(name = "DESCRIPTION")
    private String description;
    
    @ToString.Exclude
    @ManyToMany(mappedBy = "examinations")
    private Set<Visit> visits;
    
}
