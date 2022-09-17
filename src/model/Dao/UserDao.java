package model.dao;

import model.Constants;
import model.ConstantsExceptions;
import model.db.DbHelper;
import model.user.Admin;
import model.user.Buyer;
import model.user.ShopHolder;
import model.user.User;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDao {
    private UserDao(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    static final Logger logger = Logger.getLogger(UserDao.class.getName());

    public static boolean validateLogin(String username, String password) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        boolean output = false;
        try {
            conn = dbHelper.openDBConnection();

            String sql = "SELECT username, pass " +
                    "FROM userx " +
                    "WHERE username = ? AND pass = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2,password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                output = true;
            }
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.USER_DAO_ERROR);
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return output;
    }

    public static User retrieveUserFrom(String username) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        User user = null;
        try {
            conn = dbHelper.openDBConnection();

            String sql = "SELECT * " +
                    "FROM userx " +
                    "WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            rs.next();

            String role = rs.getString("role");
            String name = rs.getString("name");
            String pass = null;
            String email = rs.getString("email");
            Date dateOfBirth = rs.getDate("date_of_birth");
            String gender = rs.getString("gender");
            String surname = rs.getString("surname");
            String billingStreet = rs.getString("billing_street");
            String billingCity = rs.getString("billing_city");
            String billingCountry = rs.getString("billing_country");
            String billingZip = rs.getString("billing_zip");
            String phone = rs.getString("phone");
            String profileImagepath = rs.getString("profile_imagepath");
            if (role.equals(Constants.SHOPHOLDER_USER)){
                user = new ShopHolder(username,null, name, surname, email);
            }
            else if (role.equals(Constants.ADMIN_USER)){
                user = new Admin(username, name, surname, email, null);
            }
            else if (role.equals(Constants.BUYER_USER)){
                user = new Buyer(
                        username,
                        name,
                        surname,
                        pass,
                        email,
                        dateOfBirth,
                        billingStreet,
                        billingCity,
                        billingCountry,
                        billingZip,
                        phone,
                        gender,
                        profileImagepath
                );
            }
        } catch (SQLException se) {
            logger.log(Level.WARNING, ConstantsExceptions.USER_DAO_ERROR);
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return user;

    }

    public static boolean insertUser(User user){
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        try {
            String sql = "INSERT INTO userx (username, pass, email, date_of_birth, gender, role, billing_street, billing_city, billing_country, billing_zip, phone, name, surname) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            conn = dbHelper.openDBConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            if (user instanceof Buyer) {
                stmt.setDate(4, ((Buyer) user).getDateOfBirth());
                stmt.setString(5, ((Buyer) user).getGender());
                stmt.setString(6, Constants.BUYER_USER);
                stmt.setString(7, ((Buyer) user).getBillingStreet());
                stmt.setString(8, ((Buyer) user).getBillingCity());
                stmt.setString(9, ((Buyer) user).getBillingCountry());
                stmt.setString(10, ((Buyer) user).getBillingZip());
                stmt.setString(11, ((Buyer) user).getPhone());
                stmt.setString(12, user.getName());
                stmt.setString(13, user.getSurname());
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
            logger.log(Level.WARNING, "error in insert user");
            return false;
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return true;
    }

    public static boolean updateBuyerRecord(User user) {
        PreparedStatement stmt = null;
        Connection conn = null;
        DbHelper dbHelper = DbHelper.getInstance();
        try {
            String sql = "UPDATE userx " +
                    "SET name = ?, surname = ?, billing_street = ?, billing_city = ?, " +
                    "billing_country = ?, billing_zip = ?, phone = ?, profile_imagepath = ? " +
                    "WHERE username = ?";
            conn = dbHelper.openDBConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getSurname());
            stmt.setString(3, ((Buyer) user).getBillingStreet());
            stmt.setString(4, ((Buyer) user).getBillingCity());
            stmt.setString(5, ((Buyer) user).getBillingCountry());
            stmt.setString(6, ((Buyer) user).getBillingZip());
            stmt.setString(7, ((Buyer) user).getPhone());
            stmt.setString(8, ((Buyer) user).getProfileImagepath());
            stmt.setString(9, user.getUsername());

            stmt.executeUpdate();
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error while updating user");
            return false;
        } finally {
            dbHelper.closeDBConnection(stmt, conn);
        }
        return true;
    }
}
