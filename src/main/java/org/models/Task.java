package org.models;

import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;


public class Task {
    public String text;
    public LocalDate date;
    public Integer id;

    public Task(String text, LocalDate date) {
        this.text = text; 
        this.date = date; 
        this.id = null; 
    }

    public Task(String text, LocalDate date, Integer id) {
        this.text = text;
        this.date = date; 
        this.id = id; 
    }

    public Task(String text,  Integer id) {
        this.text = text;
        this.date = null; 
        this.id = id; 
    }
}