package org.models;

public class TestCredentials extends Credentials {
    
    public TestCredentials() {
        this.URL = "jdbc:sqlite::memory:";
        this.username = "";
        this.password = "";
    }
}