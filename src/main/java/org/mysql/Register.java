package org.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import org.models.Database;
import org.models.Task;

public abstract class Register {
    protected Connection connection;
    protected Database database;

    public Register(Connection connection, Database database){
        this.connection = connection;
        this.database = database;
    }
    public abstract void register(Task task) throws SQLException; 
}