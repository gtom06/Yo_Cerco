package dao;

import bean.UserBean;
import constants.Constants;

import constants.ConstantsExceptions;
import model.db.DbHelper;
import model.user.Admin;
import model.user.Buyer;
import model.user.User;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDao {
    private static final Connection conn = DbHelper.getInstance().getConnection();
    static Logger logger = Logger.getLogger(UserDao.class.getName());
    private UserDao(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    public static boolean validateLogin(String username, String password) {
        PreparedStatement stmt = null;
        boolean output = false;
        try {
            String sql = "SELECT username, pass FROM userx WHERE username = ? AND pass = ?";
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
            DbHelper.closeStatement(stmt);
        }
        return output;
    }

    public static User retrieveUserFrom(String username) {
        PreparedStatement stmt = null;
        User user = null;
        try {
            String sql = "SELECT DISTINCT * FROM userx WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            user = convertRSInUser(rs);
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error while finding user");
        } finally {
            DbHelper.closeStatement(stmt);
        }
        return user;

    }

    public static boolean insertBuyer(String username, String password, String email, Date dateOfBirth,
                                      String gender, String billingStreet, String billingCity, String billingCountry,
                                      String billingZip, String phone, String name, String surname) {
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO userx (username, pass, email, date_of_birth, gender, role, billing_street, billing_city, billing_country, billing_zip, phone, name, surname) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setDate(4, dateOfBirth);
            stmt.setString(5, gender);
            stmt.setString(6, Constants.BUYER_USER);
            stmt.setString(7, billingStreet);
            stmt.setString(8, billingCity);
            stmt.setString(9, billingCountry);
            stmt.setString(10, billingZip);
            stmt.setString(11, phone);
            stmt.setString(12, name);
            stmt.setString(13, surname);
            stmt.executeUpdate();
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in insert buyer");
            return false;
        }  finally {
            DbHelper.closeStatement(stmt);
        }
        return true;
    }

    public static boolean insertAdmin(String username, String password, String email) {
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO userx (username, pass, email, role) " +
                    "VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setString(4, Constants.ADMIN_USER);
            stmt.executeUpdate();
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error in insert admin");
            return false;
        }  finally {
            DbHelper.closeStatement(stmt);
        }
        return true;

    }

    public static boolean insertUser(User user){
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO userx (username, pass, email, date_of_birth, gender, role, billing_street, billing_city, billing_country, billing_zip, phone, name, surname) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
        }  finally {
            DbHelper.closeStatement(stmt);
        }
        return true;
    }

    public static boolean updateBuyerRecord(UserBean user) {
        PreparedStatement stmt = null;
        try {
            String sql = "UPDATE userx SET name=?,surname=?,billing_street=?,billing_city=?,billing_country=?,billing_zip=?,phone=?,profile_imagepath=? WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getSurname());
            stmt.setString(3, user.getBillingStreet());
            stmt.setString(4, user.getBillingCity());
            stmt.setString(5, user.getBillingCountry());
            stmt.setString(6, user.getBillingZip());
            stmt.setString(7, user.getPhone());
            stmt.setString(8, user.getProfileImagepath());
            stmt.setString(9, user.getUsername());

            stmt.executeUpdate();
        } catch (SQLException se) {
            logger.log(Level.WARNING, "error while updating user");
            return false;
        }  finally {
            DbHelper.closeStatement(stmt);
        }
        return true;
    }

    private static User convertRSInUser(ResultSet rs) throws SQLException {
        User user = null;
        rs.next();
        String role = rs.getString("role");
        String name = rs.getString("name");
        String username = rs.getString("username");
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
                    null,
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
