package org.openjfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.time.LocalDate;

import org.mysql.DatabaseConnection;

/**
 * Classe appelée dans scene.fxml pour y intégrer des éléments JavaFX. Par exemple,
 * label est appelé via son id dans scene.fxml. 
 */
public class FXMLController {
    

    // Les variables utilisées sous ce descripteur seront reconnues directement dans le fichier fxml 
    // et permettent de lier ces éléments du pt de vue java (handler) et du pt de vue fxml
    @FXML
    private Label label;
    @FXML
    private Button register;
    @FXML
    private DatePicker taskDate;
    @FXML 
    private TextField task;
    @FXML
    private Label errorRegisterTask;

 

    /**
     * Tous les éléments qui ont un comportement spécial après injection doivent être intégrés dans cette méthode :
     * éléments dont le texte est changé dynamiquement, qui ont un comportement au clic, etc.
     */ 
    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");
        register.setOnAction(this::registerTaskIfFilled);
    }

    private void registerTaskIfFilled(ActionEvent event){
        String text = task.getText();
        LocalDate date = taskDate.getValue();
        this.showErrorMessageIfTaskIsEmpty(text);
        this.registerTask(text, date);
        this.showSuccessfulRegisteringMessage();
    }


    private void showErrorMessageIfTaskIsEmpty(String text){
        if (text.isEmpty()){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Invalid task");
            alert.setContentText("You should enter a task");
            alert.showAndWait();
            return;
        }
    }

    private void registerTask(String text, LocalDate date){
        try (Connection connection = DatabaseConnection.getConnection()){
            String sql = "INSERT INTO tasks (task, created_at) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, text);
            if (date != null){
                statement.setString(2, date.toString());
            } else {
                statement.setNull(2, java.sql.Types.TIMESTAMP);
            }
            statement.executeUpdate();
            task.clear();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void showSuccessfulRegisteringMessage(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("Your task has been successfully registered");
        alert.showAndWait();
    }
}

/*FAUDRA SUREMENT METTRE CET OBJET DANS UN MODEL */
private class Task {
    private String text = task.getText();
    private LocalDate date = taskDate.getValue;
}

