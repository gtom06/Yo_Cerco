package model.db;

import constants.Constants;
import constants.ConstantsExceptions;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbHelper {
    private static DbHelper dbHelper = null;
    private Connection connection;
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
        return dbHelper;
    }

    public static void closeStatement(PreparedStatement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e){
            logger.log(Level.OFF, ConstantsExceptions.CLOSING_STMT_ERROR);
        }
    }
    public Connection getConnection() {
        return connection;
    }
}
