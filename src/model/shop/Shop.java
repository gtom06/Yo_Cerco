package model.shop;

import model.Constants;

public class Shop {
    private final String phone;
    private final String address;
    private final String city;
    private final String shopName;
    private String logoImagepath;
    private final int shopId;
    private String openingTime;
    private String closingTime;
    private double lat;
    private double lng;
    private String gmapsLink;
    private String franchising;
    private String offersFlyerPath;
    private double distance;


    public Shop(String phone, String address, String city, String shopName, String logoImagepath,
                int shopId, String openingTime,
                String closingTime, double lat, double lng, String gmapsLink, String franchising, String offersFlyerPath) {
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.shopName = shopName;
        this.logoImagepath = logoImagepath;
        this.shopId = shopId;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.lat = lat;
        this.lng = lng;
        this.gmapsLink = Constants.GOOGLE_MAPS_LINK_START_STRING + gmapsLink;
        this.franchising = franchising;
        this.offersFlyerPath = offersFlyerPath;
    }
    // start custom getter
    public String getCompleteAddress(){
        return address + " - " + city;
    }

    public String getLogoImagepath() {
        String output = Constants.LOGO_SHOPS_PATH;
        if (logoImagepath.isBlank()) {
            output += Constants.LOGO_SHOP_NA;
        } else {
            output += logoImagepath;
        }
        return output;
    }

    //end custom getter
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getGmapsLink() {
        return gmapsLink;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getShopName() {
        return shopName;
    }

    public void setLogoImagepath(String logoImagepath) {
        this.logoImagepath = logoImagepath;
    }

    public int getShopId() {
        return shopId;
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

    @Override
    public String toString() {
        return "Shop{" +
                "phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", shopName='" + shopName + '\'' +
                ", logoImagepath='" + logoImagepath + '\'' +
                ", shopId=" + shopId +
                ", openingTime='" + openingTime + '\'' +
                ", closingTime='" + closingTime + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", gmapsLink='" + gmapsLink + '\'' +
                ", franchising='" + franchising + '\'' +
                '}';
    }
}


