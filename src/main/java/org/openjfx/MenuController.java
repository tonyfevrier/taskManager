package org.openjfx;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import java.util.List;
import java.util.ArrayList;

import java.time.LocalDate;


import org.mysql.DatabaseConnection;
import org.models.Task;


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
        Stage stage = getStage();
        stage.setScene(scene);
    }
}


class DisplayTasksPage extends MenuPage {
    private String whichTasks;

    public DisplayTasksPage(Stage stage, String whichTasks){
        super(stage);
        this.whichTasks = whichTasks;
    }

    public void preparePageLoading() throws Exception {
            load("displayTasks.fxml");
            ImportTasksFactory importTasksFactory = new ImportTasksFactory(whichTasks);
            ImportTasks importTasks = importTasksFactory.chooseImportTasks();
            List<Task> taskList = importTasks.getTasks();
            System.out.println(taskList);
    };

    private void load(String page) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(page));
        Scene scene = new Scene(root);
        Stage stage = getStage();
        stage.setScene(scene);
    }
}

/* A DEPLACER DANS LE DOSSIER MYSQL */

class ImportTasksFactory {
    private String whichTasks;
    public ImportTasksFactory(String whichTasks){
        this.whichTasks = whichTasks;
    }

    public ImportTasks chooseImportTasks(){
        if (whichTasks.equals("allTasks")){
            return new ImportAllTasks();
        } else {
            return new ImportDayTasks();
        }
    }
} 


interface ImportTasks { 
    public List<Task> getTasks();
}


class ImportAllTasks implements ImportTasks {
    private List<Task> taskList = new ArrayList<>();

    public List<Task> getTasks() {
        try (Connection connection = DatabaseConnection.getConnection()){
            String sql = "SELECT id, task, created_at FROM tasks";
            Statement statement = connection.createStatement();
            ResultSet taskSet = statement.executeQuery(sql);
            while (taskSet.next()){
                String text = taskSet.getString("task");
                Date sqlDate = taskSet.getDate("created_at");
                Integer id = taskSet.getInt("id");
                if (sqlDate != null){
                    LocalDate created_at = sqlDate.toLocalDate();
                    taskList.add(new Task(text, created_at, id));
                } else {
                    taskList.add(new Task(text, null, id));
                }
            }
            return taskList;
        } catch (SQLException e){
            e.printStackTrace();
            return taskList;
        }
    }
}

class ImportDayTasks implements ImportTasks {
    private List<Task> taskList;

    public List<Task> getTasks() {
        return taskList;
    }
}
