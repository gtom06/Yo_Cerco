package model.shop;

import constants.Constants;
import model.address.Address;

import java.util.List;

public class Shop {
    private Integer shopId;
    private final String phone;
    private Address address;
    private final String city;
    private final String shopName;
    private String logoImagepath;
    private String openingTime;
    private String closingTime;
    private String gmapsLink;
    private String franchising;
    private String offersFlyerPath;
    private double distance;
    private double lat;
    private double lng;
    private final String completeAddress;


    public Shop(String phone, Address address, String shopName, String logoImagepath,
                List<String> openingTimes, String franchising, String offersFlyerPath) {
        this.phone = phone;
        this.address = address;
        this.city = address.getCity();
        this.shopName = shopName;
        this.logoImagepath = logoImagepath;
        this.openingTime = openingTimes.get(0);
        this.closingTime = openingTimes.get(1);
        this.lat = address.getLat();
        this.lng = address.getLng();
        this.gmapsLink = Constants.GOOGLE_MAPS_LINK_START_STRING + address.getGmapsLink();
        this.franchising = franchising;
        this.offersFlyerPath = offersFlyerPath;
        this.completeAddress = address.getAddressString();
    }

    // start custom getter
    public String getLogoImagepath() {
        String output = Constants.LOGO_SHOPS_PATH;
        if (logoImagepath.isBlank()) {
            output += Constants.LOGO_SHOP_NA;
        } else {
            output += logoImagepath;
        }
        return output;
    }
    // end

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getCompleteAddress() {
        return completeAddress;
    }

    public String getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    @Override
    public String toString() {
        return "Shop2{" +
                "phone='" + phone + '\'' +
                ", address=" + address +
                ", city='" + city + '\'' +
                ", shopName='" + shopName + '\'' +
                ", logoImagepath='" + logoImagepath + '\'' +
                ", openingTime='" + openingTime + '\'' +
                ", closingTime='" + closingTime + '\'' +
                ", gmapsLink='" + gmapsLink + '\'' +
                ", franchising='" + franchising + '\'' +
                ", offersFlyerPath='" + offersFlyerPath + '\'' +
                ", distance=" + distance +
                ", lat=" + lat +
                ", lng=" + lng +
                ", completeAddress='" + completeAddress + '\'' +
                '}';
    }
}
