package org.models;

import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;


public class Task {
    public String text;
    public LocalDate date;

    public Task(TextField taskText, DatePicker taskDate){
        this.text = taskText.getText();
        this.date = taskDate.getValue();
    }
}