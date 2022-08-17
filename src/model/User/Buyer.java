package model.User;

import java.sql.Date;

public class Buyer implements User {
    private final String username;
    private final String name;
    private final String password;
    private final String email;
    private final Date dateOfBirth;
    private final String gender; //1 male 2 female 3 other

    public Buyer(String username, String name, String password, String email, Date dateOfBirth, String gender) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender; //0:male 1:female 2:other
    }

    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender='" + gender + '\'' +
                '}';
    }
}
