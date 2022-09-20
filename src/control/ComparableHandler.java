package control;

import model.address.Address;
import model.ConstantsExceptions;
import model.shop.Shop2;

import java.util.*;

public class ComparableHandler {
    private ComparableHandler(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    public static List<Shop2> orderShopsByDistance(List<Shop2> shopList, Address address) {
        for (Shop2 shop : shopList) {
            shop.setDistance(
                    Math.round(LocationHandler.calculateDistancePointToPoint(shop,address) * 100.0) / 100.0
            );
        }
        Collections.sort(shopList, (s1, s2) -> Double.compare(s1.getDistance(), s2.getDistance()));
        return new ArrayList<>(shopList);
    }


}
