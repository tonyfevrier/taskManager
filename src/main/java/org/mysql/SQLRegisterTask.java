package org.mysql;

import org.mysql.DatabaseConnection;
import org.models.Task;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;


public class SQLRegisterTask {

    public static void register(Task task) {
        /* MySQL registering of a given task */
        try (Connection connection = DatabaseConnection.getConnection()){
            String sql = "INSERT INTO tasks (task, created_at) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, task.text);
            if (task.date != null){
                statement.setDate(2, Date.valueOf(task.date));
            } else {
                statement.setNull(2, java.sql.Types.DATE);
            }
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}