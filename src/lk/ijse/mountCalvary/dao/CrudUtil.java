package lk.ijse.mountCalvary.dao;

import lk.ijse.mountCalvary.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CrudUtil {
    private static PreparedStatement getPreparedStatement(String sql, Object... params) throws Exception {

        int paramsCount = questionMark(sql);
        if (paramsCount != params.length) {
            throw new RuntimeException("Parameters count is mismatched");
        }

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pStm = connection.prepareStatement(sql);

        for (int i = 0; i < params.length; i++) {
            pStm.setObject(i + 1, params[i]);
        }
        return pStm;
    }

    public static ResultSet executeQuery(String sql, Object... params) throws Exception {
        return getPreparedStatement(sql, params).executeQuery();
    }

    public static int executeUpdate(String sql, Object... params) throws Exception {
        return getPreparedStatement(sql, params).executeUpdate();
    }

    private static int questionMark(String sql) {
        int count = 0;
        for (int i = 0; i < sql.length(); i++) {
            if (sql.charAt(i) == '?')
                count++;
        }
        return count;
    }
}