package org.openjfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.time.LocalDate;

import org.models.Task;
import org.models.MySQLCredentials;
import org.models.ProductionDatabase;
import org.openjfx.MenuController;
import org.openjfx.RegisterController;
import org.mysql.DatabaseConnection;
import org.mysql.DeleteTask;


public class DisplayTaskController {
    /* Handles the creation of fxml elements for displayTasks page */

    private List<Task> taskList;
    private String whichTasks;
    private TableView<Task> taskTable = new TableView<>();

    @FXML
    private VBox taskListVBox;

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public void setWhichTasks(String whichTasks) {
        this.whichTasks = whichTasks;
    }

    public void initialize(){
        createTaskTable();
        fillTable(); 
        addModificationsButtons();
    }

    private void createTaskTable() {
        taskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);// column take all horizontal place
        TableColumn<Task,String> columnDate = new TableColumn<>("Date");
        TableColumn<Task,String> columnTask = new TableColumn<>("Task");
        TableColumn<Task,String> columnId = new TableColumn<>("Id");
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));// link the column to an attribute of class Task to allow to fill the table
        columnTask.setCellValueFactory(new PropertyValueFactory<>("text"));
        taskTable.getColumns().addAll(columnId, columnDate, columnTask);
        taskListVBox.getChildren().add(taskTable);
        VBox.setVgrow(taskTable, Priority.ALWAYS); // fill vertically the window with table rows when window is widened
    }

    private void fillTable() {
        for (Task task:taskList) {
            taskTable.getItems().add(task);
        }
    }

    private void addModificationsButtons() {
        HBox buttonBox = new HBox();
        Button deleteButton = new Button("Delete task");
        Button modifyButton = new Button("Modify task");

        // Buttons are disabled if no task is selected
        ReadOnlyObjectProperty<Task> selectedItem = taskTable.getSelectionModel().selectedItemProperty();        
        deleteButton.disableProperty().bind(selectedItem.isNull());
        modifyButton.disableProperty().bind(selectedItem.isNull());

        buttonBox.getChildren().addAll(deleteButton, modifyButton);
        taskListVBox.getChildren().add(buttonBox);
        
        //Add event listeners
        deleteButton.setOnAction(event -> deleteTask(event));
        modifyButton.setOnAction(event -> modifyTask(event));
    }

    private void deleteTask(ActionEvent event) {
        try(Connection connection = DatabaseConnection.getConnection(new MySQLCredentials())){
            Task selectedTask = taskTable.getSelectionModel().getSelectedItem();        
            DeleteTask deleteTask = new DeleteTask(selectedTask.getId(), connection, new ProductionDatabase());
            deleteTask.delete();
            refreshPage(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshPage(ActionEvent event) throws Exception {
        Button clickedButton = (Button) event.getSource();
        Stage stage = (Stage) clickedButton.getScene().getWindow();
        DisplayTasksPage displayTasksPage = new DisplayTasksPage(stage, whichTasks);
        displayTasksPage.preparePageLoading();
    }

    private void modifyTask(ActionEvent event){
        try {
            Button clickedButton = (Button) event.getSource();
            Stage stage = (Stage) clickedButton.getScene().getWindow();
            Task selectedTask = taskTable.getSelectionModel().getSelectedItem();     
            ModifyTaskPage modifyTaskPage = new ModifyTaskPage(stage, selectedTask);
            modifyTaskPage.preparePageLoading();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class ModifyTaskPage {
    /*Handle the loading of register fxml page */
    private Task task;
    private Stage stage;
    private FXMLLoader loader;

    public ModifyTaskPage(Stage stage, Task task){
        this.task = task;
        this.stage = stage;
    }

    public void preparePageLoading() throws Exception {
        load("register.fxml");
    };

    private void load(String page) throws Exception {
        loader = new FXMLLoader(getClass().getResource(page));
        buildPageController();
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm()); // add prend une URL absolue sous forme de str (ce que permet toExternalForm)
        stage.setScene(scene);
    }

    private void buildPageController() {
        RegisterController controller = new RegisterController();
        controller.setMemorizedTask(task);
        loader.setController(controller);
    }
}
