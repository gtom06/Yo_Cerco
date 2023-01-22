package model.user;

public class SuperAdmin implements User {

    private String usernameSA;
    private String nameSA;
    private String surnameSA;
    private String emailSA;
    private String passwordSA;

    public SuperAdmin() {
        //init
    }

    public void setUsername(String usernameSA) {
        this.usernameSA = usernameSA;
    }

    public void setEmail(String emailSA) {
        this.emailSA = emailSA;
    }

    @Override
    public void setPassword(String passwordSA) {
        this.passwordSA = passwordSA;
    }

    @Override
    public String getUsername() {
        return usernameSA;
    }
    @Override
    public String getName() { return nameSA;}

    @Override
    public void setName(String nameSA) {
        this.nameSA = nameSA;
    }

    @Override
    public String getSurname() { return surnameSA;}

    @Override
    public void setSurname(String surnameSA) {
        this.surnameSA = surnameSA;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getEmail() {
        return emailSA;
    }

    @Override
    public String toString() {
        return "SuperAdmin{" +
                "username='" + usernameSA + '\'' +
                ", name='" + nameSA + '\'' +
                ", surname='" + surnameSA + '\'' +
                ", email='" + emailSA + '\'' +
                ", password='" + passwordSA + '\'' +
                '}';
    }
}
