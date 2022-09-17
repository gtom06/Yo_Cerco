package model.user;

public class Admin implements User {
    private final String username;
    private String name;
    private String surname;
    private final String email;
    private final String password;

    public Admin(String username, String name, String surname, String email, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public String getName() { return name;}

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSurname() { return surname;}

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
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
        return "Admin{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}