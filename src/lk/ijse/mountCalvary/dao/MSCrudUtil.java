package lk.ijse.mountCalvary.dao;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 8/4/2018
 * Time: 8:40 PM
 */
public class MSCrudUtil {
    public static ResultSet executeQuery(Connection conn, String sql, Object... params) throws Exception {
        return conn.createStatement().executeQuery(sql);
    }

}
