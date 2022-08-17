package model.Shop;

import java.util.ArrayList;

public class Shop {
    private final String address;
    private final String city;
    private final String shopName;
    private String logoImagepath;
    private String interiorPhotosImagepath;
    private final String planimetryImagePath;
    private final int shopId;
    private int status; //shop available or closed forever
    private String openingTime;
    private String closingTime;

    public Shop(String address, String city, String shopName, String logoImagepath, String interiorPhotosImagepath, String planimetryImagePath, int shopId, int status, String openingTime, String closingTime) {
        this.address = address;
        this.city = city;
        this.shopName = shopName;
        this.logoImagepath = logoImagepath;
        this.interiorPhotosImagepath = interiorPhotosImagepath;
        this.planimetryImagePath = planimetryImagePath;
        this.shopId = shopId;
        this.status = status;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
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

    public String getLogoImagepath() {
        return logoImagepath;
    }

    public String getInteriorPhotosImagepath() {
        return interiorPhotosImagepath;
    }

    public String getPlanimetryImagePath() {
        return planimetryImagePath;
    }

    public int getShopId() {
        return shopId;
    }

    public int getStatus() {
        return status;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", shopName='" + shopName + '\'' +
                ", logoImagepath='" + logoImagepath + '\'' +
                ", interiorPhotosImagepath='" + interiorPhotosImagepath + '\'' +
                ", planimetryImagePath='" + planimetryImagePath + '\'' +
                ", shopId=" + shopId +
                ", status=" + status +
                ", openingTime='" + openingTime + '\'' +
                ", closingTime='" + closingTime + '\'' +
                '}';
    }
}


