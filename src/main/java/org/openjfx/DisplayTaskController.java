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

import org.openjfx.MenuController;
import org.mysql.DatabaseConnection;


public class DisplayTaskController {
    @FXML
    private MenuItem registerATask;    
    @FXML
    private MenuItem dayTasks;    
    @FXML
    private MenuItem allTasks;
    @FXML
    private VBox taskList;


    public void initialize(){
        
    }

}

/*
abstract class ImportTasks {
    public void importTasks();
}


class ImportAllTasks extends ImportTasks {

    private ResultSet taskSet;

    public void importTasks() {
        try (Connection connection = DatabaseConnection.getConnection()){
            getAllTasks();
            while (taskSet.next()){
                HBox taskLine = new HBox();
                taskLine.setId("taskLine_" + taskSet.getString(1));
                Text taskText = new Text(taskSet.getString(2));
                Text taskDate = new Text(taskSet.getString(3));
                taskLine.getChildren().add(taskDate);
                taskLine.getChildren().add(taskText);
                taskList.getChildren().add(taskLine);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private ResultSet getAllTasks() {
        String sql = "SELECT id, task, created_at FROM tasks";
        Statement statement = connection.createStatement();
        taskSet = statement.executeQuery(sql);
    }


} */