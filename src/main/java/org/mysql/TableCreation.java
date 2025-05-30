package org.mysql;

import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;

import org.mysql.DatabaseConnection;


public class TableCreation {
    /* Create task table in task_manager database */
    public static void createTaskTableIfNotExists(){
        try (Connection connection = DatabaseConnection.getConnection()){
            boolean databaseExists = TableCreation.doesTaskTableExists(connection);
            if (!databaseExists){
                TableCreation.createTaskTable(connection);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static boolean doesTaskTableExists(Connection connection) throws SQLException {
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

    private static void createTaskTable(Connection connection) throws SQLException {
        String use_database_sql = "USE task_manager;";
        String create_table_sql = "CREATE TABLE tasks (id INT AUTO_INCREMENT PRIMARY KEY, task VARCHAR(1000) NOT NULL, created_at DATE DEFAULT NULL);";
        Statement statement = connection.createStatement();
        statement.execute(use_database_sql);
        statement.execute(create_table_sql);
    }
}