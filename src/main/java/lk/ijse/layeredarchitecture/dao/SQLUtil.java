package lk.ijse.layeredarchitecture.dao;

import lk.ijse.layeredarchitecture.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUtil {

    public static <T>T setQue(String sql, Object... values) throws SQLException, ClassNotFoundException {

        PreparedStatement pstm = DBConnection.getDbConnection().getConnection().prepareStatement(sql);

        for (int i = 0; i < values.length; i++) {
            pstm.setObject((i+1), values[i]);
        }

        if (sql.startsWith("SELECT")) {
            return (T) pstm.executeQuery();
        } else {
            return (T)(Boolean)(pstm.executeUpdate() > 0);
        }
    }
}
