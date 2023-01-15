package bean;

import model.address.Address;

public class ShopBean {
    private Integer shopId;
    private String phone;
    private String city;
    private String shopName;
    private String logoImagepath;
    private String openingTime;
    private String closingTime;
    private String gmapsLink;
    private String franchising;
    private String offersFlyerPath;
    private double distance;
    private double lat;
    private double lng;
    private String completeAddress;
    private AddressBean address;

    public ShopBean() {
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getLogoImagepath() {
        return logoImagepath;
    }

    public void setLogoImagepath(String logoImagepath) {
        this.logoImagepath = logoImagepath;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getGmapsLink() {
        return gmapsLink;
    }

    public void setGmapsLink(String gmapsLink) {
        this.gmapsLink = gmapsLink;
    }

    public String getFranchising() {
        return franchising;
    }

    public void setFranchising(String franchising) {
        this.franchising = franchising;
    }

    public String getOffersFlyerPath() {
        return offersFlyerPath;
    }

    public void setOffersFlyerPath(String offersFlyerPath) {
        this.offersFlyerPath = offersFlyerPath;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getCompleteAddress() {
        return completeAddress;
    }

    public void setCompleteAddress(String completeAddress) {
        this.completeAddress = completeAddress;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }
}
