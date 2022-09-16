package control;

import model.Address;
import model.ConstantsExceptions;
import model.Shop.Shop;

import java.util.*;

public class ComparableHandler {
    private ComparableHandler(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    public static ArrayList<Shop> orderShopsByDistance(List<Shop> shopList, Address address) {
        for (Shop shop : shopList) {
            shop.setDistance(
                    Math.round(LocationHandler.calculateDistancePointToPoint(shop,address) * 100.0) / 100.0
            );
        }
        Collections.sort(shopList, new Comparator<Shop>() {
            @Override
            public int compare(Shop s1, Shop s2) {
                return Double.compare(s1.getDistance(), s2.getDistance());
            }
        });
        return new ArrayList<>(shopList);
    }


}
