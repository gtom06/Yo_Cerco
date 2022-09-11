package model.Db;

import model.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbHelper {
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
            Class.forName(Constants.DRIVER_CLASS_NAME);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);
        } catch (ClassNotFoundException | SQLException e) {
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
