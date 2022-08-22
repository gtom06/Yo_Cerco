package model.User;

import java.sql.Date;

public class Buyer implements User {
    private final String username;
    private final String name;
    private final String surname;
    private final String password;
    private final String email;
    private final Date dateOfBirth;
    private String billingAddress;
    private final String billingStreet;
    private final String billingCity;
    private final String billingCountry;
    private final String billingZip;
    private final String phone;
    private final String gender; //1 male 2 female 3 other
    private final String profileImagepath;

    public Buyer(String username, String name, String surname, String password, String email, Date dateOfBirth, String billingStreet, String billingCity, String billingCountry, String billingZip, String phone, String gender, String profileImagepath) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.billingAddress = buildBillingAddress(billingStreet, billingCity, billingCountry, billingZip);
        this.billingStreet = billingStreet;
        this.billingCity = billingCity;
        this.billingCountry = billingCountry;
        this.billingZip = billingZip;
        this.phone = phone;
        this.gender = gender; //0:male 1:female 2:other
        this.profileImagepath = profileImagepath;
    }

    private String buildBillingAddress(String billingStreet, String billingCity, String billingCountry, String billingZip) {
        return billingStreet + " - " + billingCity + " - " + billingCountry + " - " + billingZip;
    }

    public boolean isSomeFieldBlank(){
        if (name == null || surname == null || billingStreet == null || billingCity == null || billingCountry == null || billingZip == null || phone == null){
            return true;
        }
        return false;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getSurname() {
        return surname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public String getbillingStreet() {
        return billingStreet;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public String getBillingZip() {
        return billingZip;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", billingAddress='" + billingAddress + '\'' +
                ", billingStreet='" + billingStreet + '\'' +
                ", billingCity='" + billingCity + '\'' +
                ", billingCountry='" + billingCountry + '\'' +
                ", billingZip='" + billingZip + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
