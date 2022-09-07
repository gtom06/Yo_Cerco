package control;

import model.Constants;
import model.Dao.UserDao;
import model.User.Admin;
import model.User.Buyer;
import model.User.ShopHolder;
import model.User.User;

import java.io.File;
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

    public static boolean updateLogoImagePath(User user, File file, String profileImagepath) {
        if (user == null || profileImagepath.isBlank()) {
            return false;
        }
        if (profileImagepath == ((Buyer) user).getProfileImagepath()) {
            return true;
        }
        String newFileName = user.getUsername() + ".jpg";
        updateRecord(user, user.getName(), user.getSurname(), ((Buyer) user).getBillingStreet(),
                 ((Buyer) user).getBillingCity(), ((Buyer) user).getBillingCountry(), ((Buyer) user).getBillingZip(),
                ((Buyer) user).getPhone(), newFileName);
        return true;
    }

    public static boolean updateRecord(User user, String name, String surname, String street,
                                       String city, String country, String zip, String phone, String profileImagePath) {

        if (name == user.getName() && surname == user.getSurname() && street == ((Buyer) user).getBillingStreet() &&
                city == ((Buyer) user).getBillingCity() && country == ((Buyer) user).getBillingCountry() && zip == ((Buyer) user).getBillingZip()
                && phone == ((Buyer) user).getPhone() && profileImagePath == ((Buyer) user).getProfileImagepath()) {
            System.out.println("nothing to update in user");
            return true;
        }

        if (!name.isBlank() && !surname.isBlank() && !street.isBlank() &&
                !city.isBlank() && !country.isBlank() &&
                !zip.isBlank() && !phone.isBlank() && !profileImagePath.isBlank()) {

            user.setName(name);
            user.setSurname(surname);
            ((Buyer) user).setBillingStreet(street);
            ((Buyer) user).setBillingCity(city);
            ((Buyer) user).setBillingCountry(country);
            ((Buyer) user).setBillingZip(zip);
            ((Buyer) user).setPhone(phone);
            ((Buyer) user).setBillingAddress();
            ((Buyer) user).setProfileImagepath(profileImagePath);
            return UserDao.updateBuyerRecord(user);
        }
        return false;
    }
}
