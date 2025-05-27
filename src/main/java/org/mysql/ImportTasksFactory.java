package org.mysql;


public class ImportTasksFactory {
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


