package control;

import model.Address;
import model.Shop.Shop;

import java.util.*;

public class ComparableHandler {

    public static ArrayList<Shop> orderByDistance(ArrayList<Shop> shopArrayList, Address address) {
        HashMap<Shop, Double> map = new HashMap<>();
        for (Shop shop : shopArrayList) {
            map.put(shop, calculateDistanceFromShopToPoint(shop, address));
        }

        LinkedHashMap<Shop, Double> sortedMap = new LinkedHashMap<>();
        ArrayList<Double> list = new ArrayList<>();
        for (Map.Entry<Shop, Double> entry : map.entrySet()) {
            list.add(entry.getValue());
        }
        Collections.sort(list);
        for (Double num : list) {
            for (Map.Entry<Shop, Double> entry : map.entrySet()) {
                if (entry.getValue().equals(num)) {
                    sortedMap.put(entry.getKey(), num);
                }
            }
        }
        System.out.println(sortedMap);
        ArrayList<Shop> shopArrayListSorted = new ArrayList<>(sortedMap.keySet());
        return shopArrayListSorted;
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
        return R * c * 1000; //in meters
    }
}
