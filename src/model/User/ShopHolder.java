package model.User;

import model.User.User;

public class ShopHolder implements User {
    private final String username;
    private final String name;
    private final String email;
    private final String password;

    public ShopHolder(String username, String password, String name, String email) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "ShopHolder{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
