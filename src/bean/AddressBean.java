package bean;

public class AddressBean {
    private double latBean;
    private double lngBean;
    private String addressStringBean;
    private String gmapsLinkBean;
    private String cityBean;
    private String addressOnlyStreetBean;

    public AddressBean() {
        //only init
    }

    public double getLat() {
        return latBean;
    }

    public void setLat(double latBean) {
        this.latBean = latBean;
    }

    public double getLngBean() {
        return lngBean;
    }

    public void setLng(double lngBean) {
        this.lngBean = lngBean;
    }

    public String getAddressString() {
        return addressStringBean;
    }

    public void setAddressString(String addressStringBean) {
        this.addressStringBean = addressStringBean;
    }

    public String getGmapsLink() {
        return gmapsLinkBean;
    }

    public void setGmapsLink(String gmapsLinkBean) {
        this.gmapsLinkBean = gmapsLinkBean;
    }

    public String getCity() {
        return cityBean;
    }

    public void setCity(String cityBean) {
        this.cityBean = cityBean;
    }

    public String getAddressOnlyStreet() {
        return addressOnlyStreetBean;
    }

    public void setAddressOnlyStreet(String addressOnlyStreetBean) {
        this.addressOnlyStreetBean = addressOnlyStreetBean;
    }
}
