package lk.ijse.layeredarchitecture.dao;

import lk.ijse.layeredarchitecture.db.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionUtil {

    public static void settCommit(boolean isTrue) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();

        if (!isTrue) {
            connection.setAutoCommit(false);
        } else {
            connection.setAutoCommit(true);
        }
    }

    public static void rollBack() throws SQLException, ClassNotFoundException {
        Connection connection =DBConnection.getDbConnection().getConnection();

        connection.rollback();
    }
}
