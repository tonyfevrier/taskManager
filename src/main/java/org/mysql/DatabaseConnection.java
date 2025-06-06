package org.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.models.Credentials;


public class DatabaseConnection {

    public static Connection getConnection(Credentials credentials) throws SQLException{
        return DriverManager.getConnection(credentials.getURL(), credentials.getUsername(), credentials.getPassword());
    }

}

