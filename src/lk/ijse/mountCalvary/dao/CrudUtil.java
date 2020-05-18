package lk.ijse.mountCalvary.dao;

import com.mysql.jdbc.CommunicationsException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException;
import lk.ijse.mountCalvary.db.DBConnection;
import lk.ijse.mountCalvary.db.DBReloader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CrudUtil {

    protected static PreparedStatement getPreparedStatement(Connection conn, String sql, Object... params) throws Exception {
        int paramsCount = questionMark(sql);
        if (paramsCount != params.length) {
            throw new RuntimeException("Parameters count is mismatched");
        }
        PreparedStatement pStm = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            pStm.setObject(i + 1, params[i]);
        }
        return pStm;
    }

    public static ResultSet executeQuery(String sql, Object... params) throws Exception {
        try {
            return getPreparedStatement(DBConnection.getInstance().getConnection(), sql, params).executeQuery();
        } catch (CommunicationsException | MySQLNonTransientConnectionException e) {
            DBReloader.reloadServer();
            return getPreparedStatement(DBConnection.getInstance().getConnection(), sql, params).executeQuery();
        }
    }

    public static int executeUpdate(String sql, Object... params) throws Exception {
        try {
            return getPreparedStatement(DBConnection.getInstance().getConnection(), sql, params).executeUpdate();
        } catch (CommunicationsException | MySQLNonTransientConnectionException e) {
            DBReloader.reloadServer();
            return getPreparedStatement(DBConnection.getInstance().getConnection(), sql, params).executeUpdate();
        }
    }

    private static int questionMark(String sql) {
        byte count = 0;
        for (int i = 0; i < sql.length(); i++) {
            if (sql.charAt(i) == '?')
                count++;
        }
        return count;
    }
}