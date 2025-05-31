package org.mysql;

import org.models.Task;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;


public class SQLRegisterTask {
    private Connection connection;

    public SQLRegisterTask(Connection connection){
        this.connection = connection;
    }

    public void register(Task task) throws SQLException {
        /* MySQL registering of a given task */
        String sql = "INSERT INTO tasks (task, created_at) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, task.text);
        if (task.date != null){
            statement.setDate(2, Date.valueOf(task.date));
        } else {
            statement.setNull(2, java.sql.Types.DATE);
        }
        statement.executeUpdate();
    }
}