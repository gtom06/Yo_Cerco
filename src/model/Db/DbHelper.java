package model.Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbHelper {
    private static final String USER = "postgres";
    private static final String PASS = "postgres";
    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/yocerco";
    private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";

    private static DbHelper dbHelper = null;
    private static Connection connection = null;

    private DbHelper() {}

    public static DbHelper getInstance() {
        if (dbHelper == null)
            dbHelper = new DbHelper();
        return dbHelper;
    }

    public Connection openDBConnection() {
        try {
            Class.forName(DRIVER_CLASS_NAME);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connection ok");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
        return connection;
    }

    public void closeDBConnection(PreparedStatement statement, Connection connection) {
        try {
            if (statement != null)
                statement.close();
        } catch (SQLException se2) {
            se2.printStackTrace();
        }
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
