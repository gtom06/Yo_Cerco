package control;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Order.OrderItem;
import model.Product.ProductShop;

import java.util.ArrayList;

public class JsonParserCustom {

    public static ArrayList<OrderItem> convertJsonIntoOrderItem(String json) {
/*
example of json:

{
  "orderId": 111,
  "orderItems": [
    {
      "sku": 12134,
      "amount": 10,
      "currency": "eur",
      "quantityOrdered": 1
    }
  ]
}
*/
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

    public static ArrayList<String> convertJsonIntoPhonePrefix(String json) {
        ArrayList<String> phonePrefixArrayList = new ArrayList<>();
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        String prefix;
        for (JsonElement e : jsonObject.getAsJsonArray()){
            prefix = ((JsonObject) e).get("dial_code").getAsString();
            phonePrefixArrayList.add(prefix);
        }
        return phonePrefixArrayList;
    }
}
