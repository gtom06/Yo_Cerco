package control;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Order.OrderItem;

import java.util.ArrayList;

public class JsonParserCustom {
    //todo: modificare dopo la verifica degli insert
    public static ArrayList<OrderItem> convertJsonIntoOrderItem(String json) {

        /*
        ArrayList<OrderItem> orderItemArrayList = new ArrayList<>();
        OrderItem orderItem;
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        int orderId = jsonObject.get("orderId").getAsInt();
        for (JsonElement e : jsonObject.getAsJsonArray()){
            orderItem = new OrderItem(orderId, new ProductShop(((JsonObject) e).get("sku").getAsInt(),((JsonObject) e).get("amount").getAsDouble(), ((JsonObject) e).get("discountedAmount").getAsDouble(), ((JsonObject) e).get("currency").getAsString(), ((JsonObject) e).get("type").getAsString()), ((JsonObject) e).get("quantityOrdered").getAsInt());
            orderItemArrayList.add(orderItem);
        }
        return orderItemArrayList;*/
        return null;
    }

    public static JsonObject convertStringToJsonObject(String jsonString) {
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        return jsonObject;
    }
}
