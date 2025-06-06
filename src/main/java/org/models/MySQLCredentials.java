package org.models;

public class MySQLCredentials extends Credentials {
    
    public MySQLCredentials() {
        this.URL = "jdbc:mysql://localhost:3306/task_manager";
        this.username = "tony";
        this.password = "tony";
    }
}