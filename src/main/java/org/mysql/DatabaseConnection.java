package org.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    private static String URL = "jdbc:mysql://localhost:3306/task_manager";
    private static String username = "tony";
    private static String password = "tony";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL, username, password);
    }
}