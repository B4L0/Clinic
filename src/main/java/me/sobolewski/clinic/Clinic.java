package me.sobolewski.clinic;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import me.sobolewski.clinic.account.LoginSession;
import me.sobolewski.clinic.model.Visit;
import me.sobolewski.clinic.model.util.EntityUtils;

import java.util.Objects;

public class Clinic extends Application {
    
    @Getter
    @Setter
    private static LoginSession loginSession;
    
    public static void main(String[] args) {
        System.out.println(EntityUtils.getByID(Visit.class, 1));
        //launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        stage.setScene(FXMLManager.loadScene("login"));
        stage.setTitle("Przychodnia");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.png"))));
        stage.show();
        
    }
    
}
