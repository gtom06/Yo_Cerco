package model.db;

import model.Constants;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbHelper {
    private static DbHelper dbHelper = null;
    private Connection connection = null;
    static Logger logger = Logger.getLogger(DbHelper.class.getName());

    private DbHelper() {
        try {
            Class.forName(Constants.DRIVER_CLASS_NAME);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);
        } catch (Exception e) {
            logger.log(Level.OFF, "error in openDBConnection");
        }
    }

    public static DbHelper getInstance() {
        if (dbHelper == null)
            dbHelper = new DbHelper();
        return dbHelper;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void closeDBConnection(Statement statement, Connection conn) {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException se) {
            logger.log(Level.SEVERE, "error while closing connection");
        }
    }

    public static Connection openDBConnection() {
        return null;
    }
}
