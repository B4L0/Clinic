package me.sobolewski.clinic.account;

import lombok.Data;
import lombok.NonNull;
import me.sobolewski.clinic.model.Doctor;

import java.time.LocalDateTime;

@Data
public class LoginSession {
    
    @NonNull
    private Doctor loggedDoctor;
    @NonNull
    private LocalDateTime loginTime;
    private int visitsDone = 0;
    
}
