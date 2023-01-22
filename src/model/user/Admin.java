package model.user;

public class Admin implements User {
    private String usernameA;
    private String nameA;
    private String surnameA;
    private String emailA;
    private String passwordA;

    public Admin() {
        //init
    }

    @Override
    public void setUsername(String usernameA) {
        this.usernameA = usernameA;
    }

    public void setEmail(String emailA) {
        this.emailA = emailA;
    }

    @Override
    public void setPassword(String passwordA) {
        this.passwordA = passwordA;
    }

    @Override
    public String getUsername() {
        return usernameA;
    }
    @Override
    public String getName() { return nameA;}

    @Override
    public void setName(String nameA) {
        this.nameA = nameA;
    }

    @Override
    public String getSurname() { return surnameA;}

    @Override
    public void setSurname(String surnameA) {
        this.surnameA = surnameA;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getEmail() {
        return emailA;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "username='" + usernameA + '\'' +
                ", name='" + nameA + '\'' +
                ", surname='" + surnameA + '\'' +
                ", email='" + emailA + '\'' +
                ", password='" + passwordA + '\'' +
                '}';
    }
}