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
        registerATask.setOnAction(event -> loadPage(event));
        dayTasks.setOnAction(event -> loadPage(event));
        allTasks.setOnAction(event -> loadPage(event));
    }

    public void loadPage(ActionEvent event) {
        try {
            MenuItem clickedItem = (MenuItem) event.getSource();
            PageFactory pageFactory = new PageFactoryImpl();
            MenuPage menuPage = pageFactory.makeMenuPage(clickedItem);
            menuPage.preparePageLoading();
        } catch (Exception e){
            e.printStackTrace();
        } 
    }
}


interface PageFactory {
    /* permet de regrouper les pages créées sous le même type PageFactory */
    public MenuPage makeMenuPage(MenuItem clickedItem);
}

class PageFactoryImpl implements PageFactory {

    public MenuPage makeMenuPage (MenuItem clickedItem){
        Stage stage = (Stage) clickedItem.getParentPopup().getOwnerWindow();

        if (clickedItem.getId().equals("registerATask")){
            return new RegisterPage(stage);
        } else if (clickedItem.getId().equals("dayTasks")){
            return new DisplayTasksPage(stage);
        } else {
            return new DisplayTasksPage(stage);
        }
    }
}


abstract class MenuPage {
    /* abstract class to choose the page in the menu when clicking on a menu item */
    private Stage stage;

    public MenuPage(Stage stage){
        this.stage = stage;
    };

    public Stage getStage() {
        return stage;
    };

    public abstract void preparePageLoading() throws Exception;
}


class RegisterPage extends MenuPage {
    public RegisterPage(Stage stage){
        super(stage);
    }

    public void preparePageLoading() throws Exception {
        load("register.fxml");
    };

    private void load(String page) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(page));
        Scene scene = new Scene(root);
        Stage stage = getStage();
        stage.setScene(scene);
    }
}


class DisplayTasksPage extends MenuPage {
    public DisplayTasksPage(Stage stage){
        super(stage);
    }

    public void preparePageLoading() throws Exception {
            load("displayTasks.fxml");
    };

    private void load(String page) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(page));
        Scene scene = new Scene(root);
        Stage stage = getStage();
        stage.setScene(scene);
    }
}
