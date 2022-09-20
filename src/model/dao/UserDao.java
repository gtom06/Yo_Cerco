package model.dao;

import model.Constants;

import model.ConstantsExceptions;
import model.db.DbHelper;
import model.user.Admin;
import model.user.Buyer;
import model.user.User;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDao {
    static Logger logger = Logger.getLogger(UserDao.class.getName());
    private UserDao(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }
    public static boolean validateLogin(String username, String password) {
        boolean output = false;
        try {
            ResultSet rs = Queries.validateLoginQuery(username, password);
            if (rs.next()) {
                output = true;
            }
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error while finding user");
        }
        return output;
    }

    public static User retrieveUserFrom(String username) {
        User user = null;
        ResultSet rs = null;
        try {
            rs = Queries.retrieveUserFromQuery(username);
            user = convertRSInUser(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error while finding user");
        } finally{
            try {
            rs.getStatement().close();
            } catch (Exception e ){

            }
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
            stmt.executeUpdate();
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in insert user");
            return false;
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
        }
        return true;
    }

    private static User convertRSInUser(ResultSet rs) throws SQLException {
        User user = null;
        rs.next();
        String role = rs.getString("role");
        String name = rs.getString("name");
        String username = rs.getString("username");
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
        if (role.equals(Constants.ADMIN_USER)){
            user = new Admin(username, name, surname, email, null);
        }
        else if (role.equals(Constants.BUYER_USER)) {
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
        return user;
    }
}
