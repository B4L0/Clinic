package me.sobolewski.clinic.component;

import javafx.scene.control.Alert;

public class InformationAlert extends Alert {
    
    public InformationAlert(String title, String message){
        super(AlertType.INFORMATION);
        this.setHeaderText(null);
        this.setTitle(title);
        this.setContentText(message);
    }
}
