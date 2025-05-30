package org.openjfx;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.sql.SQLException;
import java.sql.Connection;

import java.util.List;
import java.util.ArrayList;

import org.mysql.ExtractSQLDataFactory;
import org.mysql.ExtractSQLData;
import org.mysql.DatabaseConnection;
import org.models.Task;
import org.openjfx.DisplayTaskController;


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
            return new DisplayTasksPage(stage, "dayTasks");
        } else {
            return new DisplayTasksPage(stage, "allTasks");
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
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm()); // add prend une URL absolue sous forme de str (ce que permet toExternalForm)
        Stage stage = getStage();
        stage.setScene(scene);
    }
}


class DisplayTasksPage extends MenuPage {
    private String whichTasks;
    private FXMLLoader loader;

    public DisplayTasksPage(Stage stage, String whichTasks){
        super(stage);
        this.whichTasks = whichTasks;
    }

    public void preparePageLoading() throws Exception {
            load("displayTasks.fxml", getTaskList());
    };

    private List<Task> getTaskList(){
        try (Connection connection = DatabaseConnection.getConnection()){
            ExtractSQLDataFactory extractSQLDataFactory = new ExtractSQLDataFactory(whichTasks, connection);
            ExtractSQLData importTasks = extractSQLDataFactory.chooseSQLData();
            return importTasks.getData();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    private void load(String page, List<Task> taskList) throws Exception {
        // The page necessitates a task list to display on the page
        loader = new FXMLLoader(getClass().getResource(page));
        buildPageController(taskList);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = getStage();
        stage.setScene(scene);
    }

    private void buildPageController(List<Task> taskList) {
        /* Pass the task list to the fxml controller of the page */
        DisplayTaskController controller = new DisplayTaskController();
        controller.setTaskList(taskList);
        loader.setController(controller);
    }
}



