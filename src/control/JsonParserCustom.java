package control;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Order.OrderItem;

import java.util.ArrayList;

public class JsonParserCustom {
    private JsonParserCustom(){
        throw new IllegalStateException("Utility class");
    }
    public static JsonObject convertStringToJsonObject(String jsonString) {
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        return jsonObject;
    }
}
