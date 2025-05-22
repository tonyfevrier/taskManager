package org.openjfx;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class MenuController {
    /* Handle the loading of the pages associated with the menu */
    private Stage stage;

    @FXML 
    private MenuItem registerATask;
    @FXML 
    private MenuItem dayTasks;
    @FXML
    private MenuItem allTasks;


    public void initialize() {
        registerATask.setOnAction(event -> loadTaskPage(event));
        dayTasks.setOnAction(event -> loadTaskPage(event));
        allTasks.setOnAction(event -> loadTaskPage(event));
    }

    public void loadTaskPage(ActionEvent event) {
        try {
            MenuItem clickedItem = (MenuItem) event.getSource();
            loadPageCorrespondingTo(clickedItem);
        } catch (Exception e){
            e.printStackTrace();
        } 
    }

    private void loadPageCorrespondingTo(MenuItem clickedItem) throws Exception {
        stage = (Stage) clickedItem.getParentPopup().getOwnerWindow();

        if (clickedItem.getId().equals("registerATask")) {
            load("register.fxml");
        } else if (clickedItem.getId().equals("dayTasks")) {
            load("displayTasks.fxml");
        } else if (clickedItem.getId().equals("allTasks")) {
            load("displayTasks.fxml");
        }
    }

    private void load(String page) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(page));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}