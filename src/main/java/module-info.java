

module taskManager {
    requires javafx.controls; // Gérer les éléments de base de JavaFX (boutons, labels, etc.)
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires java.sql;
    
    opens org.openjfx to javafx.fxml; // org.openjfx est le package que je crée via ce projet. opens rend toutes les classes (public et private) de ce package accessibles à javafx.fxml (pour le fxml et le css).
    exports org.openjfx; // Rend les classes publiques du module visible à d'autres modules
    exports org.mysql;
}
