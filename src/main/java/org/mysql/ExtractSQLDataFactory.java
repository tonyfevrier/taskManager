package org.mysql;

import java.time.LocalDate;


public class ExtractSQLDataFactory {
    // Allows to choose which data to extract. label is a string : in the case of tasks to import, it corresponds to allTasks or dayTasks.
    private String label;
    public ExtractSQLDataFactory(String label){
        this.label = label;
    }

    public ExtractSQLData chooseSQLData(){
        String sql;
        if (label.equals("allTasks")){
            sql = "SELECT id, task, created_at FROM tasks";
        } else {
            sql = String.format("SELECT id, task, created_at FROM tasks WHERE created_at='%s'", LocalDate.now());
        }
        return new ImportTasks(sql);
    }
} 


