package model.user;

import java.sql.Date;

public class Buyer implements User {
    private final String username;
    private String name;
    private String surname;
    private String password;
    private final String email;
    private final Date dateOfBirth;
    private String billingAddress;
    private String billingStreet;
    private String billingCity;
    private String billingCountry;
    private String billingZip;
    private String phone;
    private final String gender; //1 male 2 female 3 other
    private String profileImagepath;

    public Buyer(String username, String name, String surname, String password, String email, Date dateOfBirth,
                 String billingStreet, String billingCity, String billingCountry, String billingZip, String phone, String gender, String profileImagepath) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.billingStreet = billingStreet;
        this.billingCity = billingCity;
        this.billingCountry = billingCountry;
        this.billingZip = billingZip;
        this.phone = phone;
        this.gender = gender; //0:male 1:female 2:other
        this.profileImagepath = profileImagepath;
        setBillingAddress();
    }


    public String getProfileImagepath() {
        return profileImagepath;
    }

    public boolean isSomeFieldBlank(){
        return name.isBlank() || surname.isBlank() || billingStreet.isBlank() || billingCity.isBlank() || billingCountry.isBlank() || billingZip.isBlank() || phone.isBlank();
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

    @Override
    public String getSurname() {
        return surname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public String getBillingStreet() {
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
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBillingAddress() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.billingStreet).append(" - ").append(this.billingCity).append(" - ")
                .append(this.billingCountry).append(" - ").append(this.billingZip);
        this.billingAddress = sb.toString();
    }

    public void setBillingStreet(String billingStreet) {
        this.billingStreet = billingStreet;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    public void setBillingZip(String billingZip) {
        this.billingZip = billingZip;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setProfileImagepath(String profileImagepath) {
        this.profileImagepath = profileImagepath;
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
                ", profileImagepath='" + profileImagepath + '\'' +
                '}';
    }
}
