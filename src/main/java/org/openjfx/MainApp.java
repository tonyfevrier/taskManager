package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader; 
import javafx.scene.Parent;
import javafx.scene.Scene; // Gérer le style (css) et l'interactivité de l'application (comme JS)
import javafx.stage.Stage; // Créer la fenêtre qui a été stylisée et gérée dans stage
import javafx.scene.control.*;

import org.openjfx.RegisterController;

public class MainApp extends Application {

    @Override 
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
        RegisterController controller = new RegisterController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm()); // add prend une URL absolue sous forme de str (ce que permet toExternalForm)  
        stage.setTitle("TaskManager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args); // Method coming from Application class
    }

}
