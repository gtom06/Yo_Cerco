package model.Dao;

import model.Constants;
import model.Db.DbHelper;
import model.User.Admin;
import model.User.Buyer;
import model.User.ShopHolder;
import model.User.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDao {
    public static boolean validateLogin(String username, String password) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        String u = null;
        try {
            conn = dbHelper.openDBConnection();

            String sql = "SELECT username, pass " +
                    "FROM userx " +
                    "WHERE username = ? AND pass = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2,password);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            u = rs.getString("username");
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return true;
        }
    }

    public static User retrieveUserFrom(String username) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        User user = null;
        try {
            conn = dbHelper.openDBConnection();

            String sql = "SELECT role, email, date_of_birth, gender " +
                    "FROM userx " +
                    "WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            rs.next();

            String role = rs.getString("role");
            String name = null;
            String pass = null;
            String email = rs.getString("email");
            Date dateOfBirth = rs.getDate("date_of_birth");
            String gender = rs.getString("gender");
            if (role.equals(Constants.SHOPHOLDER_USER)){
                user = new ShopHolder(username,null, null, email);
            }
            else if (role.equals(Constants.ADMIN_USER)){
                user = new Admin(username,null,null, email);
            }
            else if (role.equals(Constants.BUYER_USER)){
                user = new Buyer(
                        username,
                        name,
                        pass,
                        email,
                        dateOfBirth,
                        gender
                );
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return user;
        }

    }

    public static ArrayList<User> retriveUserFrom(String keyword) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        ArrayList<User> userList = null;
        try {
            conn = dbHelper.openDBConnection();

            String sql = "SELECT role, email, username " +
                    "FROM userx " +
                    "WHERE username LIKE %?% " +
                    "AND (role = 'B' OR role = 'S')";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, keyword);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String role = rs.getString("role");
                String email = rs.getString("email");
                String username = rs.getString("username");
                String gender = null;
                if (role.equals(Constants.BUYER_USER)){
                    userList.add(new Buyer(username, null, null, email, null, null));
                }
                else if (role.equals(Constants.ADMIN_USER)){
                    userList.add(new Admin(username,null, null, email));
                }
                else if (role.equals(Constants.SHOPHOLDER_USER)){
                    userList.add(new ShopHolder(username,null,null, email));
                }
            }
        } catch(SQLException se){
                se.printStackTrace();
        } finally{
            dbHelper.closeDBConnection(stmt, conn);
            return userList;
        }
    }

    public static boolean insertUser(User user){
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        try {
            String sql = "INSERT INTO userx (username, pass, email, date_of_birth, gender, role) " +
                    "VALUES (?, ?, ?, ?, ?, ?);";
            conn = dbHelper.openDBConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            if (user instanceof Buyer) {
                stmt.setDate(4, ((Buyer) user).getDateOfBirth());
                stmt.setString(5, ((Buyer) user).getGender());
                stmt.setString(6, Constants.BUYER_USER);
            }
            if (user instanceof Admin) {
                stmt.setDate(4, null);
                stmt.setString(5, null);
                stmt.setString(6, Constants.ADMIN_USER);
            }
            if (user instanceof ShopHolder) {
                stmt.setDate(4, null);
                stmt.setString(5, null);
                stmt.setString(6, Constants.SHOPHOLDER_USER);
            }
            stmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
            dbHelper.closeDBConnection(stmt, conn);
            return false;
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
            return true;
        }
    }
}
