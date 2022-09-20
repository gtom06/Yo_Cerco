package control;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import exceptions.AddressException;
import model.address.Address;
import constants.Constants;
import constants.ConstantsExceptions;
import model.shop.Shop;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LocationHandler {
    static final Logger logger = Logger.getLogger(LocationHandler.class.getName());
    private LocationHandler(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    public static Address calculateLatLongFromIpAddress() throws AddressException {
        //uses googleapis
        Address address = null;
        try {
            String httpResponse = HttpRequest.post(Constants.GEOLOCATION_URL);
            JsonObject jsonObject = JsonParserCustom.convertStringToJsonObject(httpResponse);
            JsonObject jsonObject1 = (JsonObject) jsonObject.get("location");
            address = new Address(jsonObject1.get("lat").getAsDouble(), jsonObject1.get("lat").getAsDouble(), null);
        }
        catch (Exception e) {
            logger.log(Level.WARNING, "error in LocationHandler");
            throw new AddressException("error while parsing response from google");
        }
        return address;
    }


    public static Address calculateLatLongFromAddress(String addressString) throws AddressException {
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
            throw new AddressException("error while parsing response from google");
        }
        return address;
    }

    public static double calculateDistancePointToPoint(Shop shop, Address address) {
        return Math.sqrt(Math.pow(shop.getLat() - address.getLat(), 2) + Math.pow(shop.getLng() - address.getLng(), 2)) * 111;
    }
}
