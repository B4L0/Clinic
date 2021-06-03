package me.sobolewski.clinic;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import me.sobolewski.clinic.account.CurrentVisit;
import me.sobolewski.clinic.account.LoginSession;
import me.sobolewski.clinic.manager.FXMLManager;

import java.util.Objects;

public class Clinic extends Application {
    
    @Getter
    @Setter
    private static LoginSession loginSession;
    
    @Getter
    @Setter
    private static CurrentVisit currentVisit;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        stage.setScene(FXMLManager.getStartScene());
        stage.setTitle("Przychodnia");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.png"))));
        stage.show();
    }
    
}
