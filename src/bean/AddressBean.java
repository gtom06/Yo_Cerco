package bean;

public class AddressBean {
    private double lat;
    private double lng;
    private String addressString;
    private String gmapsLink;
    private String city;
    private String addressOnlyStreet;

    public AddressBean() {
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

    public String getAddressString() {
        return addressString;
    }

    public void setAddressString(String addressString) {
        this.addressString = addressString;
    }

    public String getGmapsLink() {
        return gmapsLink;
    }

    public void setGmapsLink(String gmapsLink) {
        this.gmapsLink = gmapsLink;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressOnlyStreet() {
        return addressOnlyStreet;
    }

    public void setAddressOnlyStreet(String addressOnlyStreet) {
        this.addressOnlyStreet = addressOnlyStreet;
    }
}
