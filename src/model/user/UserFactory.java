package model.user;

import constants.Constants;

public class UserFactory {
    public User getUser(String type) {
        if (type == null || type.isBlank()) {
            return null;
        }
        if (type.equalsIgnoreCase(Constants.ADMIN)) {
            return new Admin();
        }
        if (type.equalsIgnoreCase(Constants.BUYER)){
            return new Buyer();
        }

        if (type.equalsIgnoreCase(Constants.SUPERADMIN)){
            return new SuperAdmin();
        }

        return null;
    }
}
