package org.mysql;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.models.Database;

public class DeleteTask {
    /*Handles the deletion of a task given an id*/
    private Integer id;
    private Connection connection;
    private Database database;

    public DeleteTask(Integer id, Connection connection, Database database){
        this.id = id;
        this.connection = connection;
        this.database = database;
    }

    public void delete() throws SQLException {
        String sql = "DELETE FROM " + database.tableName + " WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

}