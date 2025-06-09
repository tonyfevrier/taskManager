package org.models;

public class TestCredentials extends Credentials {
    
    public TestCredentials() {
        this.URL = "jdbc:mysql://localhost:3306/test";
        this.username = "tony";
        this.password = "tony";
        /*this.URL = "jdbc:sqlite::memory:";
        this.username = "";
        this.password = "";*/
    }
}