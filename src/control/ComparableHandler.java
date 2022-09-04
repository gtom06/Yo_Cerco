package control;

import model.Address;
import model.Shop.Shop;

import java.util.*;

public class ComparableHandler {

    public static ArrayList<Shop> orderByDistance(ArrayList<Shop> shopArrayList, Address address) {
        for (Shop shop : shopArrayList) {
            shop.setDistance(
                    Math.round(LocationHandler.calculateDistancePointToPoint(shop,address) * 100.0) / 100.0
            );
        }
        Collections.sort(shopArrayList, new Comparator<Shop>() {
            @Override
            public int compare(Shop s1, Shop s2) {
                return Double.compare(s1.getDistance(), s2.getDistance());
            }
        });
        return shopArrayList;
    }


}
