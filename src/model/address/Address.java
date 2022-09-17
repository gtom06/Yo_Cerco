package model.address;

public class Address {
    private final double lat;
    private final double lng;
    private final String addressString;

    public Address(double lat, double lng, String addressString) {
        this.lat = lat;
        this.lng = lng;
        this.addressString = addressString;
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
                ", address='" + addressString + '\'' +
                '}';
    }
}
