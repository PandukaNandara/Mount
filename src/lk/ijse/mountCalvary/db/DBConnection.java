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
    private String ip;
    private String port;
    private String db;
    private String user;
    private String password;

    private DBConnection() throws Exception {

        dbDetails = new HashMap<>();
        Class.forName("com.mysql.jdbc.Driver");
        readProperties();

        String jdbcURL = String.format("jdbc:mysql://%s:%s/%s?createDatabaseIfNotExist=true", ip, port, db);
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

    private void readProperties() throws Exception {
        File file = new File("resources/application.properties");
        FileReader fileReader = new FileReader(file);
        Properties properties = new Properties();
        properties.load(fileReader);
        ip = properties.getProperty("ip");
        dbDetails.put("ip", ip);
        port = properties.getProperty("port");
        dbDetails.put("port", port);
        db = properties.getProperty("db");
        dbDetails.put("db", db);
        user = properties.getProperty("user");
        dbDetails.put("user", user);
        password = properties.getProperty("password");
        dbDetails.put("password", password);
    }

    public Map<String, String> getDbDetails() {
        return dbDetails;
    }

    public Connection getConnection() {
        return connection;
    }
}
