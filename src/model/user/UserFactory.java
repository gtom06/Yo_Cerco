package model.user;

public class UserFactory {
    public User getUser(String type) {
        if (type == null || type.isBlank()) {
            return null;
        }
        if (type.equalsIgnoreCase("ADMIN")) {
            return new Admin();
        }
        if (type.equalsIgnoreCase("BUYER")){
            return new Buyer();
        }
        return null;
    }
}
