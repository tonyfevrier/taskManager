package org.mysql;

import org.models.*;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;


public class SQLModifyTask extends Register {

    public SQLModifyTask(Connection connection, Database database){
        super(connection, database);
    }

    public void register(Task task) throws SQLException {
        /* MySQL updating of a given task */
        String sql = "UPDATE " + database.tableName + " SET task=?, created_at=? WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, task.getText());
        statement.setInt(3, task.getId());
        if (task.getDate() != null){
            statement.setDate(2, Date.valueOf(task.getDate()));
        } else {
            statement.setNull(2, java.sql.Types.DATE);
        }
        statement.executeUpdate();
    }
}