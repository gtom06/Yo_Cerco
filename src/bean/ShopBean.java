package bean;

public class ShopBean {
    private Integer shopIdBean;
    private String phoneBean;
    private String cityBean;
    private String shopNameBean;
    private String logoImagepathBean;
    private String openingTimeBean;
    private String closingTimeBean;
    private String gmapsLinkBean;
    private String franchisingBean;
    private String offersFlyerPathBean;
    private double distanceBean;
    private double latBean;
    private double lngBean;
    private String completeAddressBean;
    private AddressBean addressBean;

    public ShopBean() {
        //only init
    }

    public Integer getShopId() {
        return shopIdBean;
    }

    public void setShopId(Integer shopIdBean) {
        this.shopIdBean = shopIdBean;
    }

    public String getPhone() {
        return phoneBean;
    }

    public void setPhone(String phoneBean) {
        this.phoneBean = phoneBean;
    }

    public String getCity() {
        return cityBean;
    }

    public void setCity(String cityBean) {
        this.cityBean = cityBean;
    }

    public String getShopName() {
        return shopNameBean;
    }

    public void setShopName(String shopNameBean) {
        this.shopNameBean = shopNameBean;
    }

    public String getLogoImagepath() {
        return logoImagepathBean;
    }

    public void setLogoImagepath(String logoImagepathBean) {
        this.logoImagepathBean = logoImagepathBean;
    }

    public String getOpeningTime() {
        return openingTimeBean;
    }

    public void setOpeningTime(String openingTimeBean) {
        this.openingTimeBean = openingTimeBean;
    }

    public String getClosingTime() {
        return closingTimeBean;
    }

    public void setClosingTime(String closingTimeBean) {
        this.closingTimeBean = closingTimeBean;
    }

    public String getGmapsLink() {
        return gmapsLinkBean;
    }

    public void setGmapsLink(String gmapsLinkBean) {
        this.gmapsLinkBean = gmapsLinkBean;
    }

    public String getFranchising() {
        return franchisingBean;
    }

    public void setFranchising(String franchisingBean) {
        this.franchisingBean = franchisingBean;
    }

    public String getOffersFlyerPath() {
        return offersFlyerPathBean;
    }

    public void setOffersFlyerPath(String offersFlyerPathBean) {
        this.offersFlyerPathBean = offersFlyerPathBean;
    }

    public double getDistance() {
        return distanceBean;
    }

    public void setDistance(double distanceBean) {
        this.distanceBean = distanceBean;
    }

    public double getLat() {
        return latBean;
    }

    public void setLat(double latBean) {
        this.latBean = latBean;
    }

    public double getLng() {
        return lngBean;
    }

    public void setLng(double lngBean) {
        this.lngBean = lngBean;
    }

    public String getCompleteAddress() {
        return completeAddressBean;
    }

    public void setCompleteAddress(String completeAddressBean) {
        this.completeAddressBean = completeAddressBean;
    }

    public AddressBean getAddress() {
        return addressBean;
    }

    public void setAddress(AddressBean addressBean) {
        this.addressBean = addressBean;
    }
}
