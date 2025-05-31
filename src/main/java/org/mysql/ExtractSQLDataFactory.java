package org.mysql;

import java.time.LocalDate;
import java.sql.Connection;

import org.models.Database;


public class ExtractSQLDataFactory {
    // Allows to choose which data to extract. label is a string : in the case of tasks to import, it corresponds to allTasks or dayTasks.
    private String label;
    private Connection connection;
    private Database database;

    public ExtractSQLDataFactory(String label, Connection connection, Database database){
        this.label = label;
        this.connection = connection;
        this.database = database;
    }

    public ExtractSQLData chooseSQLData(){
        String sql;
        if (label.equals("allTasks")){
            sql = "SELECT id, task, created_at FROM " + database.tableName;
        } else {
            sql = String.format("SELECT id, task, created_at FROM " + database.tableName + " WHERE created_at='%s'", LocalDate.now());
        }
        return new ImportTasks(sql, connection);
    }
} 


