package me.sobolewski.clinic.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.SneakyThrows;
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
    
    @SneakyThrows
    public static <T> T getController(String name) {
        FXMLLoader loader = new FXMLLoader(Clinic.class.getResource("/view/" + name + ".fxml"));
        loader.load();
        return loader.getController();
    }
    
    public static void openSceneInNewWindow(String name, String windowTitle){
        try {
            Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(Clinic.class.getResource("/view/" + name + ".fxml"))));
            Stage stage = new Stage();
            stage.setTitle(windowTitle);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
    
    
}
