package org.mysql;

import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;


public class TableCreation {
    /* Create task table in task_manager database */
    private Connection connection;

    public TableCreation(Connection connection) {
        this.connection = connection;
    }

    public void createTaskTableIfNotExists() throws SQLException{
        boolean databaseExists = doesTaskTableExists();
        if (!databaseExists){
            createTaskTable();
        }
    }

    private boolean doesTaskTableExists() throws SQLException {
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
        return databaseExists;
    }

    private void createTaskTable() throws SQLException {
        String use_database_sql = "USE task_manager;";
        String create_table_sql = "CREATE TABLE tasks (id INT AUTO_INCREMENT PRIMARY KEY, task VARCHAR(1000) NOT NULL, created_at DATE DEFAULT NULL);";
        Statement statement = connection.createStatement();
        statement.execute(use_database_sql);
        statement.execute(create_table_sql);
    }
}