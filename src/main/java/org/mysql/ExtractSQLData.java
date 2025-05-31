package org.mysql;

import java.util.List;
import java.util.ArrayList;

import java.time.LocalDate;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import org.models.Task;


public abstract class ExtractSQLData {
    public abstract List getData() throws SQLException;
}


class ImportTasks extends ExtractSQLData {
    /* Handles the extraction of specific tasks depending on the sql string. */
    private ResultSet taskSet;
    private String sql;
    private Connection connection;

    public ImportTasks(String sql, Connection connection){
        this.sql = sql;
        this.connection = connection;
    }

    public List<Task> getData() throws SQLException {
        List<Task> taskList = new ArrayList<>();
        taskSet = extractTasksFromDatabase(connection);
        return storeTasksIn(taskList); 
    }

    private ResultSet extractTasksFromDatabase(Connection connection) throws SQLException {
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
