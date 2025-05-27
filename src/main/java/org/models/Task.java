package org.models;

import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;


public class Task {
    public String text;
    public LocalDate date;
    public Integer id;

    public Task(String text, LocalDate date) {//TextField taskText, DatePicker taskDate){
        this.text = text; //taskText.getText();
        this.date = date; //taskDate.getValue();
        this.id = null; 
    }

    public Task(String text, LocalDate date, Integer id) {//TextField taskText, DatePicker taskDate){
        this.text = text; //taskText.getText();
        this.date = date; //taskDate.getValue();
        this.id = id; 
    }
}