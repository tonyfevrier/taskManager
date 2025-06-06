package org.openjfx;

import javafx.fxml.FXML;
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
import javafx.geometry.Pos;
import javafx.beans.property.ReadOnlyObjectProperty;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.time.LocalDate;

import org.models.Task;
import org.openjfx.MenuController;


public class DisplayTaskController {
    /* Handles the creation of fxml elements for displayTasks page */

    private List<Task> taskList;
    private TableView<Task> taskTable = new TableView<>();

    @FXML
    private VBox taskListVBox;

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
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
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));// link the column to an attribute of class Task to allow to fill the table
        columnTask.setCellValueFactory(new PropertyValueFactory<>("text"));
        taskTable.getColumns().addAll(columnDate, columnTask);
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
    }
}
