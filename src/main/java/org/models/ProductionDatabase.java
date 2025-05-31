package org.models; 

public class ProductionDatabase extends Database {
    public ProductionDatabase() {
        this.databaseName = "task_manager";
        this.tableName = "tasks";
    }
}
