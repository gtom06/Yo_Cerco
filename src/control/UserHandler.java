package control;

import constants.Constants;
import constants.ConstantsExceptions;
import dao.UserDao;
import model.user.Admin;
import model.user.Buyer;
import model.user.User;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserHandler {
    static final Logger logger = Logger.getLogger(UserHandler.class.getName());
    private UserHandler(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }
    public static boolean checkUsernameAndPassword(String username, String password){
        return UserDao.validateLogin(username, password);
    }

    public static User selectUserFromUsername(String username){
        return UserDao.retrieveUserFrom(username);
    }

    public static boolean insertUser(String username, String name, String surname, String password, String email,
                                     Date dateOfBirth, String billingStreet, String billingCity,
                                     String billingCountry, String billingZip, String phone, String gender,
                                     String roleString) {
        if (roleString.equals(Constants.BUYER) || roleString.equals(Constants.BUYER_USER)) {
            Buyer buyer = new Buyer(
                    username,
                    name,
                    surname,
                    password,
					email,
                    dateOfBirth,
                    billingStreet,
                    billingCity,
                    billingCountry,
                    billingZip,
                    phone,
                    gender,
                    null
            );
            return UserDao.insertUser(buyer);
        } else if (roleString.equals(Constants.ADMIN) || roleString.equals(Constants.ADMIN_USER)) {
            Admin admin = new Admin(username, name, surname, email, null);
            return UserDao.insertUser(admin);
        }
        logger.log(Level.WARNING, "insert failed");
        return false;
    }

    public static boolean updateLogoImagePath(User user, String profileImagepath) {
        if (user == null || profileImagepath.isBlank()) {
            return false;
        }
        if (profileImagepath.equals(((Buyer) user).getProfileImagepath())) {
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

        if (name.equals(user.getName()) && surname.equals(user.getSurname()) && street.equals(((Buyer) user).getBillingStreet()) &&
                city.equals(((Buyer) user).getBillingCity()) && country.equals(((Buyer) user).getBillingCountry()) && zip.equals(((Buyer) user).getBillingZip())
                && phone.equals(((Buyer) user).getPhone()) && profileImagePath.equals(((Buyer) user).getProfileImagepath())) {
            logger.log(Level.INFO, "nothing to update in user");
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
