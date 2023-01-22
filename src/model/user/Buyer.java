package model.user;

import java.sql.Date;

public class Buyer implements User {
    private String usernameB;
    private String nameB;
    private String surnameB;
    private String passwordB;
    private String emailB;
    private Date dateOfBirthB;
    private String billingAddressB;
    private String billingStreetB;
    private String billingCityB;
    private String billingCountryB;
    private String billingZipB;
    private String phoneB;
    private String genderB; //1 male 2 female 3 other
    private String profileImagepathB;

    public Buyer(){
        //init
    }

    public String getProfileImagepath() {
        return profileImagepathB;
    }

    public boolean isSomeFieldBlank(){
        return nameB.isBlank() || surnameB.isBlank() || billingStreetB.isBlank() || billingCityB.isBlank() || billingCountryB.isBlank() || billingZipB.isBlank() || phoneB.isBlank();
    }

    @Override
    public void setUsername(String usernameB) {
        this.usernameB = usernameB;
    }

    @Override
    public void setEmail(String emailB) {
        this.emailB = emailB;
    }

    public void setDateOfBirth(Date dateOfBirthB) {
        this.dateOfBirthB = dateOfBirthB;
    }

    public void setBillingAddress(String billingAddressB) {
        this.billingAddressB = billingAddressB;
    }

    public void setGender(String genderB) {
        this.genderB = genderB;
    }

    @Override
    public String getUsername() {
        return usernameB;
    }

    @Override
    public String getEmail() {
        return emailB;
    }

    @Override
    public String getName() {
        return nameB;
    }

    @Override
    public String getPassword() {
        return passwordB;
    }

    @Override
    public String getSurname() {
        return surnameB;
    }

    public Date getDateOfBirth() {
        return dateOfBirthB;
    }

    public String getBillingAddress() {
        return billingAddressB;
    }

    public String getBillingStreet() {
        return billingStreetB;
    }

    public String getBillingCity() {
        return billingCityB;
    }

    public String getBillingCountry() {
        return billingCountryB;
    }

    public String getBillingZip() {
        return billingZipB;
    }

    public String getPhone() {
        return phoneB;
    }

    public String getGender() {
        return genderB;
    }

    @Override
    public void setName(String nameB) {
        this.nameB = nameB;
    }

    @Override
    public void setSurname(String surnameB) {
        this.surnameB = surnameB;
    }

    public void setPassword(String passwordB) {
        this.passwordB = passwordB;
    }

    public void setBillingAddress() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.billingStreetB).append(" - ").append(this.billingCityB).append(" - ")
                .append(this.billingCountryB).append(" - ").append(this.billingZipB);
        this.billingAddressB = sb.toString();
    }

    public void setBillingStreet(String billingStreetB) {
        this.billingStreetB = billingStreetB;
    }

    public void setBillingCity(String billingCityB) {
        this.billingCityB = billingCityB;
    }

    public void setBillingCountry(String billingCountryB) {
        this.billingCountryB = billingCountryB;
    }

    public void setBillingZip(String billingZipB) {
        this.billingZipB = billingZipB;
    }

    public void setPhone(String phoneB) {
        this.phoneB = phoneB;
    }

    public void setProfileImagepath(String profileImagepathB) {
        this.profileImagepathB = profileImagepathB;
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "username='" + usernameB + '\'' +
                ", name='" + nameB + '\'' +
                ", surname='" + surnameB + '\'' +
                ", password='" + passwordB + '\'' +
                ", email='" + emailB + '\'' +
                ", dateOfBirth=" + dateOfBirthB +
                ", billingAddress='" + billingAddressB + '\'' +
                ", billingStreet='" + billingStreetB + '\'' +
                ", billingCity='" + billingCityB + '\'' +
                ", billingCountry='" + billingCountryB + '\'' +
                ", billingZip='" + billingZipB + '\'' +
                ", phone='" + phoneB + '\'' +
                ", gender='" + genderB + '\'' +
                ", profileImagepath='" + profileImagepathB + '\'' +
                '}';
    }
}
