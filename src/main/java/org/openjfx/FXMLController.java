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
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDate;

import org.mysql.DatabaseConnection;
import org.models.Task;

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
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + "."); 
        this.createTaskTableIfNotExists();
        registerButton.setOnAction(this::registerTaskIfFilled);
    }

    private void registerTaskIfFilled(ActionEvent event){
        Task task = new Task(taskText, taskDate);
        if (task.text.isEmpty()){
            this.showErrorMessage();
            return;
        }
        this.register(task);
        this.showSuccessfulRegisteringMessage();
    }

    private void showErrorMessage(){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText("Invalid task");
        alert.setContentText("You should enter a task");
        alert.showAndWait();
    }

    private void register(Task task) {
        /* MySQL registering of a given task */
        try (Connection connection = DatabaseConnection.getConnection()){
            String sql = "INSERT INTO tasks (task, created_at) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, task.text);
            if (task.date != null){
                statement.setString(2, task.date.toString());
            } else {
                statement.setNull(2, java.sql.Types.TIMESTAMP);
            }
            statement.executeUpdate();
            taskText.clear();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void createTaskTableIfNotExists(){
        try (Connection connection = DatabaseConnection.getConnection()){
            boolean databaseExists = this.doesTaskTableExists(connection);
            if (!databaseExists){
                this.createTaskTable(connection);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private boolean doesTaskTableExists(Connection connection){
        try {
            String sql = "SHOW TABLES";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            boolean databaseExists = false;
            while(resultSet.next()){
                String databaseName = resultSet.getString(1);
                if (databaseName.equals("tasks")){
                    databaseExists = true;
                }
            }
            System.out.println(databaseExists);
            return databaseExists;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    private void createTaskTable(Connection connection){
        try {
            String use_database_sql = "USE task_manager;";
            String create_table_sql = "CREATE TABLE tasks (id INT AUTO_INCREMENT PRIMARY KEY, task VARCHAR(255) NOT NULL, created_at TIMESTAMP DEFAULT NULL);";
            Statement statement = connection.createStatement();
            statement.execute(use_database_sql);
            statement.execute(create_table_sql);
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



