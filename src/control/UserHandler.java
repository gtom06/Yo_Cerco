package control;

import constants.Constants;
import constants.ConstantsExceptions;
import dao.UserDao;
import model.user.Admin;
import model.user.Buyer;
import model.user.User;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
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

    public static boolean insertUser2(List<String> data){
        if (data.get(12).equals(Constants.BUYER) || data.get(12).equals(Constants.BUYER_USER)) {
            Buyer buyer = new Buyer(
                    data.get(0),
                    data.get(1),
                    data.get(2),
                    data.get(3),
                    data.get(4),
                    Date.valueOf(data.get(5)),
                    data.get(6),
                    data.get(7),
                    data.get(8),
                    data.get(9),
                    data.get(10),
                    data.get(11),
                    null
            );
            return UserDao.insertUser(buyer);
        } else if (data.get(0).equals(Constants.ADMIN) || data.get(0).equals(Constants.ADMIN_USER)) {
            Admin admin = new Admin(data.get(1), data.get(2), data.get(3), data.get(4), null);
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
        updateRecord2(user, Arrays.asList(user.getName(), user.getSurname(), ((Buyer) user).getBillingStreet(),
                 ((Buyer) user).getBillingCity(), ((Buyer) user).getBillingCountry(), ((Buyer) user).getBillingZip(),
                ((Buyer) user).getPhone(), newFileName));
        return true;
    }

    public static boolean updateRecord2(User user, List<String> data){
        String name = data.get(0);
        String surname = data.get(1);
        String street = data.get(2);
        String city = data.get(3);
        String country = data.get(4);
        String zip = data.get(5);
        String phone = data.get(6);
        String profileImagePath = data.get(7);
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
