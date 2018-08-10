package lk.ijse.mountCalvary.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by IntelliJ IDEA.
 *
 * @author pandu
 * Date: 8/4/2018
 * Time: 7:34 PM
 */
public class MSDBConnection {
    private static MSDBConnection ourInstance;
    private Statement statement = null;
    // variables
    private ResultSet rst;
    private Connection conn;

    private MSDBConnection() throws Exception {
        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
    }

    public static MSDBConnection getInstance() throws Exception {
        return ourInstance != null ?
                ourInstance :
                (ourInstance = new MSDBConnection());
    }

    public Connection getConnection(String absolutePath) throws Exception {
        if (conn != null) conn.close();

        // Opening database connection
        String dbURL = "jdbc:ucanaccess://" + absolutePath;
        System.out.println(dbURL);
        // Create and get connection using DriverManager class
        conn = DriverManager.getConnection(dbURL);

        return conn;
    }
}

// Step 2.B: Creating JDBC Statement
//        statement = conn.createStatement();
//
//                rst = statement.executeQuery("select * from tMchsAdmissions order by 1");
//
//                // Step 2.C: Executing SQL &amp; retrieve data into ResultSet
//
//                // processing returned data and printing into console
//                while (rst.next()) {
//                System.out.println(
//                rst.getString(1) + " " +
//                rst.getString(9));
//                }

