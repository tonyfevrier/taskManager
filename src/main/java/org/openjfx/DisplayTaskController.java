package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

import org.openjfx.MenuController;


public class DisplayTaskController {
    @FXML
    private MenuItem registerATask;    
    @FXML
    private MenuItem dayTasks;    
    @FXML
    private MenuItem allTasks;


    public void initialize(){
        MenuController menuController = new MenuController();
        registerATask.setOnAction(event -> menuController.loadTaskPage(event));
        dayTasks.setOnAction(event -> menuController.loadTaskPage(event));
        allTasks.setOnAction(event -> menuController.loadTaskPage(event));
    }
}