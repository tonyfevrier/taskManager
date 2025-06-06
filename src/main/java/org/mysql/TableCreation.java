package org.mysql;

import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;

import org.models.Database;

public class TableCreation {
    /* Create task table in task_manager database */
    private Connection connection;
    private Database database; 

    public TableCreation(Connection connection, Database database) {
        this.connection = connection;
        this.database = database;
    }

    public void createTaskTableIfNotExists() throws SQLException{
        boolean tableExists = doesTaskTableExists();
        if (!tableExists){
            chooseDatabase();
            createTaskTable();
        }
    }

    private boolean doesTaskTableExists() throws SQLException {
        String sql = "SHOW TABLES";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        boolean tableExists = false;
        while(resultSet.next()){
            String tableName = resultSet.getString(1);
            if (tableName.equals(database.tableName)){
                tableExists = true;
            }
        }
        return tableExists;
    }

    private void chooseDatabase() throws SQLException {
        String use_database_sql = "USE " + database.databaseName + ";";
        Statement statement = connection.createStatement();
        statement.execute(use_database_sql);
    }

    public void createTaskTable() throws SQLException {
        String create_table_sql = "CREATE TABLE " + database.tableName + " (id INT AUTO_INCREMENT PRIMARY KEY, task VARCHAR(1000) NOT NULL, created_at DATE DEFAULT NULL);";
        Statement statement = connection.createStatement();
        statement.execute(create_table_sql);
    }
}