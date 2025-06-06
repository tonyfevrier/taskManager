package org.models;

public abstract class Credentials {
    protected String URL;
    protected String username;
    protected String password ;

    public String getURL(){
        return URL;
    }

    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }
}

