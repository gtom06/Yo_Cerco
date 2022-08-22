package model.User;

public class Admin implements User {
    private final String username;
    private final String name;
    private final String surname;
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
    public String getSurname() { return surname;}
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
