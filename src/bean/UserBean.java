package bean;

public class UserBean {
    private String usernameBean;
    private String nameBean;
    private String surnameBean;
    private String passwordBean;
    private String emailBean;
    private String billingAddressBean;
    private String billingStreetBean;
    private String billingCityBean;
    private String billingCountryBean;
    private String billingZipBean;
    private String phoneBean;
    private String genderBean; //1 male 2 female 3 other
    private String profileImagepathBean;
    private boolean isAdminBean = false;

    public UserBean() {
        //only init
    }

    public String getUsername() {
        return usernameBean;
    }

    public void setUsername(String usernameBean) {
        this.usernameBean = usernameBean;
    }

    public String getName() {
        return nameBean;
    }

    public void setName(String nameBean) {
        this.nameBean = nameBean;
    }

    public String getSurname() {
        return surnameBean;
    }

    public void setSurname(String surnameBean) {
        this.surnameBean = surnameBean;
    }

    public String getPassword() {
        return passwordBean;
    }

    public void setPassword(String passwordBean) {
        this.passwordBean = passwordBean;
    }

    public String getEmail() {
        return emailBean;
    }

    public void setEmail(String emailBean) {
        this.emailBean = emailBean;
    }

    public String getBillingAddress() {
        return billingAddressBean;
    }

    public void setBillingAddress(String billingAddressBean) {
        this.billingAddressBean = billingAddressBean;
    }

    public String getBillingStreet() {
        return billingStreetBean;
    }

    public void setBillingStreet(String billingStreetBean) {
        this.billingStreetBean = billingStreetBean;
    }

    public String getBillingCity() {
        return billingCityBean;
    }

    public void setBillingCity(String billingCityBean) {
        this.billingCityBean = billingCityBean;
    }

    public String getBillingCountry() {
        return billingCountryBean;
    }

    public void setBillingCountry(String billingCountryBean) {
        this.billingCountryBean = billingCountryBean;
    }

    public String getBillingZip() {
        return billingZipBean;
    }

    public void setBillingZip(String billingZipBean) {
        this.billingZipBean = billingZipBean;
    }

    public String getPhone() {
        return phoneBean;
    }

    public void setPhone(String phoneBean) {
        this.phoneBean = phoneBean;
    }

    public String getGender() {
        return genderBean;
    }

    public void setGender(String genderBean) {
        this.genderBean = genderBean;
    }

    public String getProfileImagepath() {
        return profileImagepathBean;
    }

    public void setProfileImagepath(String profileImagepathBean) {
        this.profileImagepathBean = profileImagepathBean;
    }

    public boolean isAdmin() {
        return isAdminBean;
    }

    public void setIsAdmin(boolean isAdminBean) {
        this.isAdminBean = isAdminBean;
    }
}
