package model.address;

public class Address {
    private final double lat;
    private final double lng;
    private final String addressString;
    private String gmapsLink;
    private String city;
    private String addressOnlyStreet;

    public Address(double lat, double lng, String addressString) {
        this.lat = lat;
        this.lng = lng;
        this.addressString = addressString;
    }
    public Address(double lat, double lng, String gmapsLink, String city, String addressOnlyStreet){
        this.lat = lat;
        this.lng = lng;
        this.addressString = addressOnlyStreet + " - " + city;
        this.gmapsLink = gmapsLink;
        this.city = city;
        this.addressOnlyStreet = addressOnlyStreet;
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

    public String getAddress() {
        return addressOnlyStreet;
    }

    public void setAddress(String address) {
        this.addressOnlyStreet = address;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getAddressString() {
        return addressString;
    }

    @Override
    public String toString() {
        return "Address{" +
                "lat=" + lat +
                ", lng=" + lng +
                ", addressString='" + addressString + '\'' +
                ", gmapsLink='" + gmapsLink + '\'' +
                ", city='" + city + '\'' +
                ", addressOnlyStreet='" + addressOnlyStreet + '\'' +
                '}';
    }
}
