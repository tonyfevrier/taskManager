package org.openjfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDate;

import org.mysql.DatabaseConnection;
import org.mysql.TableCreation;
import org.mysql.SQLRegisterTask;
import org.models.*;
import org.openjfx.MenuController;

/**
 * Classe appelée dans scene.fxml pour y intégrer des éléments JavaFX. Par exemple,
 * label est appelé via son id dans scene.fxml. 
 */
public class RegisterController {
    /* Hendle behaviour of the fxml page registering tasks */

    // Les variables utilisées sous ce descripteur seront reconnues directement dans le fichier fxml 
    // qui doit avoir sa section principale associée à ce contrôleur
    // et permettent de lier ces éléments du pt de vue java (handler) et du pt de vue fxml
    @FXML
    private Label label;
    @FXML
    private Button registerButton;
    @FXML
    private DatePicker taskDate;
    @FXML 
    private TextField taskText;
    @FXML
    private Label errorRegisterTask; 
    

    /**
     * Tous les éléments qui ont un comportement spécial après injection doivent être intégrés dans cette méthode :
     * éléments dont le texte est changé dynamiquement, qui ont un comportement au clic, etc.
     */ 
    public void initialize() {
        try (Connection connection = DatabaseConnection.getConnection(new MySQLCredentials())){
            displaySoftwareInfos();
            Database database = new ProductionDatabase();
            TableCreation tableCreation = new TableCreation(connection, database);
            tableCreation.createTaskTableIfNotExists();
            RegisterTask registerTask = new RegisterTask(taskText, taskDate, database);
            registerButton.setOnAction(event -> registerTask.registerTaskIfFilled(event));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displaySoftwareInfos() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + "."); 
    }
}

class RegisterTask {
    private TextField taskText;
    private DatePicker taskDate;
    private String text;
    private LocalDate date;
    private Database database;

    public RegisterTask(TextField taskText, DatePicker taskDate, Database database){
        this.taskText = taskText;
        this.taskDate = taskDate;
        this.database = database;
    }

    public void registerTaskIfFilled(ActionEvent event){
        try (Connection connection = DatabaseConnection.getConnection(new MySQLCredentials())){
            String text = taskText.getText();
            LocalDate date = taskDate.getValue();
            Task task = new Task(text, date);
            if (task.text.isEmpty()){
                showErrorMessage();
                return;
            }
            SQLRegisterTask sqlRegister = new SQLRegisterTask(connection, database);
            sqlRegister.register(task);
            taskText.clear();
            showSuccessfulRegisteringMessage();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void showErrorMessage(){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText("Invalid task");
        alert.setContentText("You should enter a task");
        alert.showAndWait();
    }

    private void showSuccessfulRegisteringMessage(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("Your task has been successfully registered");
        alert.showAndWait();
    }
}





