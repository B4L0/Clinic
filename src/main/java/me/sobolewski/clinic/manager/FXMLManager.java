package me.sobolewski.clinic.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.sobolewski.clinic.Clinic;

import java.io.IOException;
import java.util.Objects;

@UtilityClass
public class FXMLManager {
    
    @Getter
    private static Scene startScene;
    
    static {
        try {
            startScene = new Scene(FXMLLoader.load(Objects.requireNonNull(Clinic.class.getResource("/view/login.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Scene loadScene(String name){
        Scene scene = null;
        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(Clinic.class.getResource("/view/" + name + ".fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scene;
    }
    
    
}
