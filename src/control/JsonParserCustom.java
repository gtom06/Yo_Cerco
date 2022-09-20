package control;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import constants.ConstantsExceptions;

public class JsonParserCustom {
    private JsonParserCustom(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }
    public static JsonObject convertStringToJsonObject(String jsonString) {
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }
}
