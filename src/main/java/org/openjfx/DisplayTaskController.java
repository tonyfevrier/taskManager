package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.time.LocalDate;

import org.models.Task;
import org.openjfx.MenuController;
import org.mysql.DatabaseConnection;


public class DisplayTaskController {
    /* Handles the creation of fxml elements for displayTasks page */

    private List<Task> taskList;

    @FXML
    private VBox taskListVBox;

    public void setTaskList(List<Task> taskList) {
            this.taskList = taskList;
    }

    public void initialize(){
        /* on crée une HBox par élt de ask List qu'on ajoute à Vbox */
        for (Task task : taskList){
            addTaskToVBox(task);
        }
    }

    private void addTaskToVBox(Task task) {
        HBox taskLine = new HBox();
        taskLine = fillTaskLine(task, taskLine);
        taskListVBox.getChildren().add(taskLine);
    }

    private HBox fillTaskLine(Task task, HBox taskLine) {
        Text date = new Text();
        if (task.date != null){
            date.setText(task.date.toString());
        }
        taskLine.getChildren().add(date);
        Text text = new Text(task.text);
        taskLine.getChildren().add(text);
        return taskLine;
    }
}
