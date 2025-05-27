package org.mysql;

import java.util.List;
import java.util.ArrayList;

import java.time.LocalDate;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import org.mysql.DatabaseConnection;
import org.models.Task;


public interface ImportTasks { 
    public List<Task> getTasks();
}


class ImportAllTasks implements ImportTasks {
    private ResultSet taskSet;

    public List<Task> getTasks() {
        List<Task> taskList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()){
            taskSet = extractTasksFromDatabase(connection);
            return storeTasksIn(taskList); 
        } catch (SQLException e){
            e.printStackTrace();
            return taskList;
        }
    }

    private ResultSet extractTasksFromDatabase(Connection connection) throws SQLException {
        String sql = "SELECT id, task, created_at FROM tasks";
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

    private List<Task> storeTasksIn(List<Task> taskList) throws SQLException {
        while (taskSet.next()){
            String text = taskSet.getString("task");
            Date sqlDate = taskSet.getDate("created_at");
            Integer id = taskSet.getInt("id");
            if (sqlDate != null){
                LocalDate created_at = sqlDate.toLocalDate();
                taskList.add(new Task(text, created_at, id));
            } else {
                taskList.add(new Task(text, id));
            }
        }
        return taskList;
    }
}


class ImportDayTasks implements ImportTasks {
    private List<Task> taskList;

    public List<Task> getTasks() {
        return taskList;
    }
}