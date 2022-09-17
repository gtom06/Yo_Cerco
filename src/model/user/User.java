package model.user;

public interface User {
    String getUsername();
    String getEmail();
    String getPassword();
    String getName();
    void setName(String name);
    String getSurname();
    void setSurname(String surname);
}
