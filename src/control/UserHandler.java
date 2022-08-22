package control;

import model.*;
import model.User.Admin;
import model.User.Buyer;
import model.User.ShopHolder;
import model.User.User;
import model.Dao.UserDao;

import java.sql.Date;
import java.util.ArrayList;

public class UserHandler {
    public static boolean checkUsernameAndPassword(String username, String password){
        return UserDao.validateLogin(username, password);
    }
    public static User selectUserFromUsername(String username){
        return UserDao.retrieveUserFrom(username);
    }
    public static ArrayList<User> selectUsernamesFromKeyword(String keyword) {
        return UserDao.retriveUserFrom(keyword);
    }
    public static boolean insertUser(String username, String name, String surname, String password, String email, Date dateOfBirth, String billingStreet, String billingCity, String billingCountry, String billingZip, String phone, String gender, String roleString) {
        if (roleString.equals(Constants.BUYER) || roleString.equals(Constants.BUYER_USER)) {
            //todo: insert new params
            Buyer buyer = new Buyer(username, name, surname, password, email, dateOfBirth, billingStreet, null, null, null, phone, gender, null);
            return UserDao.insertUser(buyer);
        } else if (roleString.equals(Constants.ADMIN) || roleString.equals(Constants.ADMIN_USER)) {
            Admin admin = new Admin(username, name, surname, email, password);
            return UserDao.insertUser(admin);
        } else if (roleString.equals(Constants.OWNER) || roleString.equals(Constants.SHOPHOLDER_USER)) {
            ShopHolder shopHolder = new ShopHolder(username, password, name, surname, email);
            return UserDao.insertUser(shopHolder);
        }
        System.out.println("insert failed");
        return false;
    }
}
