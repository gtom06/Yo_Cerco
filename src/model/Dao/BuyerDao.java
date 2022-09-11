package model.Dao;

import model.Db.DbHelper;
import model.User.Buyer;

import java.sql.*;

public class BuyerDao {
    public static boolean insertBuyer(Buyer buyer){
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        try {
            conn = dbHelper.openDBConnection();
            String sql = "insert into userx (username, pass, email, date_of_birth, gender, role) " +
                    "values (?, ?, ?, ?, ?, ?);";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, buyer.getUsername());
            stmt.setString(2, buyer.getPassword());
            stmt.setString(3,buyer.getEmail());
            stmt.setDate(4, buyer.getDateOfBirth());
            stmt.setString(5,buyer.getGender());

            stmt.setString(6,null);
            stmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            System.out.println("insert ok");
        }
        return true;
    }

}
