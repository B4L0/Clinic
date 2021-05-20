package me.sobolewski.clinic.component;

import javafx.scene.control.Alert;

public class ErrorAlert extends Alert {
    
    public ErrorAlert(String title, String message){
        super(AlertType.ERROR);
        this.setHeaderText(null);
        this.setTitle(title);
        this.setContentText(message);
    }
    
}
