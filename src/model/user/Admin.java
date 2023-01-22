package model.user;

public class Admin implements User {
    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;

    public Admin() {
        //init
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setPassword(String password) {
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
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}