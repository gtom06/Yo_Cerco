package model.db;

import constants.Constants;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbHelper {
    private static DbHelper dbHelper = null;
    private static Connection connection;
    static Logger logger = Logger.getLogger(DbHelper.class.getName());

    private DbHelper() {
        try {
            connection = DriverManager.getConnection(Constants.DB_URL, Constants.USER, Constants.PASS);
        } catch (Exception e) {
            logger.log(Level.OFF, "error in openDBConnection");
        }
    }

    public static DbHelper getInstance() {
        if (dbHelper == null){
            dbHelper = new DbHelper();
        }
        if (dbHelper.getConnection() == null){
            connection = DbHelper.getInstance().getConnection();
        }
        return dbHelper;
    }

    public Connection getConnection() {
        return connection;
    }
}
