package control;

import bean.AddressBean;
import bean.ShopBean;
import model.address.Address;
import constants.ConstantsExceptions;
import model.shop.Shop;

import java.util.*;

public class ComparableHandler {
    private ComparableHandler(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    public static List<Shop> orderShopsByDistance(List<Shop> shopList, Address address) {
        for (Shop shop : shopList) {
            shop.setDistance(
                    Math.round(LocationHandler.calculateDistancePointToPoint(shop,address) * 100.0) / 100.0
            );
        }
        Collections.sort(shopList, (s1, s2) -> Double.compare(s1.getDistance(), s2.getDistance()));
        return new ArrayList<>(shopList);
    }


}
