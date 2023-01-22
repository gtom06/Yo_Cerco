package control;

import bean.UserBean;
import constants.Constants;
import constants.ConstantsExceptions;
import dao.UserDao;
import model.user.Admin;
import model.user.Buyer;
import model.user.User;
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

    public static UserBean selectUserFromUsername(String username){
        UserBean user = new UserBean();
        User userQ = UserDao.retrieveUserFrom(username);
        if (userQ instanceof Buyer) {
            user.setUsername(username);
            user.setName(userQ.getName());
            user.setSurname(userQ.getSurname());
            user.setBillingAddress(((Buyer)userQ).getBillingAddress());
            user.setBillingCountry(((Buyer)userQ).getBillingCountry());
            user.setBillingCity(((Buyer)userQ).getBillingCity());
            user.setBillingStreet(((Buyer)userQ).getBillingStreet());
            user.setBillingZip(((Buyer)userQ).getBillingZip());
            user.setEmail(userQ.getEmail());
            user.setGender(((Buyer)userQ).getGender());
            user.setPassword("");
            user.setPhone(((Buyer)userQ).getPhone());
            user.setProfileImagepath(((Buyer)userQ).getProfileImagepath());
        }
        if (userQ instanceof Admin) {
            user.setIsAdmin(true);
            return null;
        }
        return user;
    }

    public static boolean insertUser2(List<String> data){
        if (data.get(12).equals(Constants.BUYER) || data.get(12).equals(Constants.BUYER_USER)) {
            return UserDao.insertBuyer(data);
        } else if (data.get(12).equals(Constants.ADMIN) || data.get(12).equals(Constants.ADMIN_USER)) {
            return UserDao.insertAdmin(data.get(0), data.get(3), data.get(4));
        }
        logger.log(Level.WARNING, "no insert");
        return false;
    }

    public static boolean updateLogoImagePath(UserBean user, String profileImagepath) {
        if (user == null || profileImagepath.isBlank()) {
            return false;
        }
        if (profileImagepath.equals(user.getProfileImagepath())) {
            return true;
        }
        String newFileName = user.getUsername() + ".jpg";
        updateRecord2(user, Arrays.asList(user.getName(), user.getSurname(), user.getBillingStreet(),
                 user.getBillingCity(), user.getBillingCountry(), user.getBillingZip(),
                user.getPhone(), newFileName));
        return true;
    }

    public static boolean updateRecord2(UserBean user, List<String> data){
        String name = data.get(0);
        String surname = data.get(1);
        String street = data.get(2);
        String city = data.get(3);
        String country = data.get(4);
        String zip = data.get(5);
        String phone = data.get(6);
        String profileImagePath = data.get(7);
        if (name.equals(user.getName()) && surname.equals(user.getSurname()) && street.equals(user.getBillingStreet()) &&
                city.equals(user.getBillingCity()) && country.equals(user.getBillingCountry()) && zip.equals(user.getBillingZip())
                && phone.equals(user.getPhone()) && profileImagePath.equals(user.getProfileImagepath())) {
            logger.log(Level.INFO, "nothing to update in user");
            return true;
        }

        if (!name.isBlank() && !surname.isBlank() && !street.isBlank() &&
                !city.isBlank() && !country.isBlank() &&
                !zip.isBlank() && !phone.isBlank() && !profileImagePath.isBlank()) {

            user.setName(name);
            user.setSurname(surname);
            user.setBillingStreet(street);
            user.setBillingCity(city);
            user.setBillingCountry(country);
            user.setBillingZip(zip);
            user.setPhone(phone);
            StringBuilder sb = new StringBuilder();
            sb.append(user.getBillingStreet()).append(" - ").append(user.getBillingCity()).append(" - ")
                    .append(user.getBillingCountry()).append(" - ").append(user.getBillingZip());
            user.setBillingAddress(sb.toString());
            user.setProfileImagepath(profileImagePath);
            return UserDao.updateBuyerRecord(user);
        }
        return false;
    }
}
