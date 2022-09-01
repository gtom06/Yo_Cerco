package control;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.Address;
import model.Constants;
import model.Dao.ShopDao;
import model.Shop.Shop;

import java.util.ArrayList;

public class LocationHandler {

    public static ArrayList<Shop> findShopByDistanceFromAddress(double lat, double lon, int distanceKm) {
        if (distanceKm == 0) {
            return null;
        }
        return ShopDao.returnAllShopsInCircle(lat, lon, distanceKm);
    }

    public static Address calculateLatLongFromAddress(String addressString) {
        //uses googleapis
        Address address = null;
        try {
            String httpResponse = HttpRequest.get(Constants.GEOCODE_URL + addressString.replace(" ", "+") + Constants.GOOGLE_KEY);
            JsonObject jsonObject = JsonParserCustom.convertStringToJsonObject(String.valueOf(httpResponse));
            JsonArray jsonObject1 = (JsonArray) jsonObject.get("results");
            JsonObject jsonObject2 = (JsonObject) jsonObject1.get(0);
            JsonObject jsonObject3 = (JsonObject) jsonObject2.get("geometry");
            JsonObject location = (JsonObject) jsonObject3.get("location");
            address = new Address(location.get("lat").getAsDouble(), location.get("lng").getAsDouble(), addressString);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return address;
    }
}
