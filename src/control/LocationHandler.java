package control;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.Address;
import model.Constants;
import model.Shop.Shop;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LocationHandler {
    static Logger logger = Logger.getLogger(LocationHandler.class.getName());
    private LocationHandler(){
        throw new IllegalStateException("Utility class");
    }

    public static Address calculateLatLongFromIpAddress(){
        //uses googleapis
        Address address = null;
        try {
            String httpResponse = HttpRequest.post(Constants.GEOLOCATION_URL, null);
            JsonObject jsonObject = JsonParserCustom.convertStringToJsonObject(httpResponse);
            JsonObject jsonObject1 = (JsonObject) jsonObject.get("location");
            address = new Address(jsonObject1.get("lat").getAsDouble(), jsonObject1.get("lat").getAsDouble(), null);
        }
        catch (Exception e) {
            logger.log(Level.WARNING, "error in LocationHandler");
        }
        return address;
    }


    public static Address calculateLatLongFromAddress(String addressString) {
        //uses googleapis
        Address address = null;
        try {
            String httpResponse = HttpRequest.get(Constants.GEOCODE_URL + addressString.replace(" ", "+") + Constants.GOOGLE_AND_KEY);
            JsonObject jsonObject = JsonParserCustom.convertStringToJsonObject(httpResponse);
            JsonArray jsonObject1 = (JsonArray) jsonObject.get("results");
            JsonObject jsonObject2 = (JsonObject) jsonObject1.get(0);
            JsonObject jsonObject3 = (JsonObject) jsonObject2.get("geometry");
            JsonObject location = (JsonObject) jsonObject3.get("location");
            address = new Address(location.get("lat").getAsDouble(), location.get("lng").getAsDouble(), addressString);
        }
        catch (Exception e) {
            logger.log(Level.WARNING, "error in LocationHandler");
        }
        return address;
    }

    public static double calculateDistancePointToPoint(Shop shop, Address address) {
        /*
        final int R = 6371; // Radius of the earth
        double lat1 = shop.getLat();
        double lng1 = shop.getLng();
        double lat2 = address.getLat();
        double lng2 = address.getLng();
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lng2 - lng1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; //in km
        */
        return Math.sqrt(Math.pow(shop.getLat() - address.getLat(), 2) + Math.pow(shop.getLng() - address.getLng(), 2)) * 111;
    }
}
