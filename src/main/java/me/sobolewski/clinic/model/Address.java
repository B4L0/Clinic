package me.sobolewski.clinic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Table(name = "ADDRESSES")
public class Address implements Serializable {
    
    
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESS_ID")
    private Long id;
    @Column(name = "CITY")
    private String city;
    @Column(name = "STREET")
    private String street;
    @Column(name = "APP_NUMBER")
    private String appNumber;
    @Column(name = "ZIP")
    private String zip;
    
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "address", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Set<Doctor> doctors;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "address", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Set<Patient> patients;
}
