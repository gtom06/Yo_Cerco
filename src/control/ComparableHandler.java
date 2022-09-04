package control;

import model.Address;
import model.Shop.Shop;

import java.util.*;

public class ComparableHandler {

    public static ArrayList<Shop> orderByDistance(ArrayList<Shop> shopArrayList, Address address) {
        for (Shop shop : shopArrayList) {
            shop.setDistance(Math.round(calculateDistanceFromShopToPoint(shop,address) * 100.0) / 100.0);
        }
        Collections.sort(shopArrayList, new Comparator<Shop>() {
            @Override
            public int compare(Shop s1, Shop s2) {
                return Double.compare(s1.getDistance(), s2.getDistance());
            }
        });
        return shopArrayList;
    }

    public static double calculateDistanceFromShopToPoint(Shop shop, Address address) {
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
    }
}
