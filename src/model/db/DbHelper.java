package model.db;

import model.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbHelper {
    private static DbHelper dbHelper = null;
    private static Connection connection = null;
    static final Logger logger = Logger.getLogger(DbHelper.class.getName());

    private DbHelper() {}

    public static DbHelper getInstance() {
        if (dbHelper == null)
            dbHelper = new DbHelper();
        return dbHelper;
    }

    public Connection openDBConnection() {
        try {
            //Class.forName(Constants.DRIVER_CLASS_NAME);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);
        } catch (SQLException e) {
            logger.log(Level.OFF, "error in openDBConnection");
        }
        return connection;
    }

    public void closeDBConnection(PreparedStatement statement, Connection connection) {
        try {
            if (statement != null)
                statement.close();
        } catch (SQLException se2) {
            logger.log(Level.SEVERE, "error while closing statement");
        }
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException se) {
            logger.log(Level.SEVERE, "error while closing connection");
        }
    }
}
