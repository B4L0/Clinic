package me.sobolewski.clinic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.sobolewski.clinic.model.enums.Specialization;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "DOCTORS")
public class Doctor implements Serializable {
    
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DOCTOR_ID")
    private Long id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "SPECIALIZATION")
    @Enumerated(EnumType.STRING)
    private Specialization specialization;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "EMAIL")
    private String email;
    @ManyToOne
    @JoinColumn(name = "ADDRESS", referencedColumnName="ADDRESS_ID")
    private Address address;
    @Column(name = "LOGIN")
    private String login;
    @Column(name = "PASSWORD")
    private String password;
    
}
