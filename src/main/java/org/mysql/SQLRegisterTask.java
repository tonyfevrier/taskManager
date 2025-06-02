package org.mysql;

import org.models.*;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;


public class SQLRegisterTask {
    private Connection connection;
    private Database database;

    public SQLRegisterTask(Connection connection, Database database){
        this.connection = connection;
        this.database = database;
    }

    public void register(Task task) throws SQLException {
        /* MySQL registering of a given task */
        String sql = "INSERT INTO " + database.tableName + " (task, created_at) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, task.getText());
        if (task.getDate() != null){
            statement.setDate(2, Date.valueOf(task.getDate()));
        } else {
            statement.setNull(2, java.sql.Types.DATE);
        }
        statement.executeUpdate();
    }
}