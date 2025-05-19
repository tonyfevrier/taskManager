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
import org.models.Task;
import org.openjfx.MenuController;

/**
 * Classe appelée dans scene.fxml pour y intégrer des éléments JavaFX. Par exemple,
 * label est appelé via son id dans scene.fxml. 
 */
public class RegisterController {
    

    // Les variables utilisées sous ce descripteur seront reconnues directement dans le fichier fxml 
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
    @FXML 
    private MenuItem registerATask;
    @FXML 
    private MenuItem dayTasks;
    @FXML
    private MenuItem allTasks;

    /**
     * Tous les éléments qui ont un comportement spécial après injection doivent être intégrés dans cette méthode :
     * éléments dont le texte est changé dynamiquement, qui ont un comportement au clic, etc.
     */ 
    public void initialize() {
        displaySoftwareInfos();
        TableCreation.createTaskTableIfNotExists();
        RegisterTask registerTask = new RegisterTask(taskText, taskDate);
        registerButton.setOnAction(registerTask::registerTaskIfFilled);
        MenuController menuController = new MenuController();
        registerATask.setOnAction(event -> menuController.loadTaskPage(event));
        dayTasks.setOnAction(event -> menuController.loadTaskPage(event));
        allTasks.setOnAction(event -> menuController.loadTaskPage(event));
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

    public RegisterTask(TextField taskText, DatePicker taskDate){
        this.taskText = taskText;
        this.taskDate = taskDate;
    }

    public void registerTaskIfFilled(ActionEvent event){
        Task task = new Task(this.taskText, this.taskDate);
        if (task.text.isEmpty()){
            this.showErrorMessage();
            return;
        }
        SQLRegisterTask.register(task);
        this.taskText.clear();
        this.showSuccessfulRegisteringMessage();
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





