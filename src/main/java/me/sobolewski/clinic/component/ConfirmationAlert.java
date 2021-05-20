package me.sobolewski.clinic.component;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class ConfirmationAlert extends Alert {
    
    public ConfirmationAlert(String title, String message){
        super(AlertType.CONFIRMATION);
        this.setHeaderText(null);
        this.setTitle(title);
        this.setContentText(message);
        ButtonType yesButton = new ButtonType("Tak", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("Nie", ButtonBar.ButtonData.NO);
        this.getButtonTypes().setAll(yesButton, noButton);
    }
}
