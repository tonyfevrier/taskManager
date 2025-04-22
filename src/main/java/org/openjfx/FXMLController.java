package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Classe appelée dans scene.fxml pour y intégrer des éléments JavaFX. Par exemple,
 * label est appelé via son id dans scene.fxml. 
 */
public class FXMLController {
    

    // Les variables utilisées sous ce descripteur seront reconnues directement dans le fichier fxml 
    @FXML
    private Label label;

    /**
     * Tous les éléments qui ont un comportement spécial après injection doivent être intégrés dans cette méthode :
     * éléments dont le texte est changé dynamiquement, qui ont un comportement au clic, etc.
     */ 
    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");
        
    }
}


