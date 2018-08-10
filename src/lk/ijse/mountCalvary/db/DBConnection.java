package lk.ijse.mountCalvary.db;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class DBConnection {
    private static DBConnection dbConnection;
    private static Map<String, String> dbDetails;
    private Connection connection;

    private DBConnection() throws Exception {
        dbDetails = new HashMap<>();
        Class.forName("com.mysql.jdbc.Driver");
        File file = new File("resources/application.properties");
        FileReader fileReader = new FileReader(file);
        Properties properties = new Properties();
        properties.load(fileReader);

        String ip = properties.getProperty("ip");
        dbDetails.put("ip", ip);
        String port = properties.getProperty("port");
        dbDetails.put("port", port);
        String db = properties.getProperty("db");
        dbDetails.put("db", db);
        String user = properties.getProperty("user");
        dbDetails.put("user", user);
        String password = properties.getProperty("password");
        dbDetails.put("password", password);

        String jdbcURL = String.format("jdbc:mysql://%s:%s/%s", ip, port, db);
        connection = DriverManager.getConnection(jdbcURL, user, password);


    }

    protected static void reloadServer() throws Exception {
        dbConnection = new DBConnection();
    }

    public static DBConnection getInstance() throws Exception {
        if (dbConnection != null) {
            return dbConnection;
        } else {
            dbConnection = new DBConnection();
            return dbConnection;
        }
    }

    public static Map<String, String> getDbDetails() {
        return dbDetails;
    }

    public Connection getConnection() {
        return connection;
    }
}
