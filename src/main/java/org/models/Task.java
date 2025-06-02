package org.models;

import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;


public class Task {
    private String text;
    private LocalDate date;
    private Integer id;

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

    public Integer getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getText() {
        return text;
    }
}