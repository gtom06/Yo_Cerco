package control;

import exceptions.AddressException;
import model.address.Address;
import model.ConstantsExceptions;
import model.dao.ShopDao;
import model.order.Order;
import model.product.SimpleProduct;
import model.shop.Shop;
import model.user.User;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ShopHandler {
    private ShopHandler(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }
    public static void insertShop(Shop shop){
        throw new UnsupportedOperationException();
    }
    public static void deleteShop(Shop shop){
        throw new UnsupportedOperationException();
    }
    public static void updateShop(Shop shop){
        throw new UnsupportedOperationException();
    }

    public static Shop findShopByOrder(Order order) {
        ArrayList<Shop> shopArrayList = null;
        shopArrayList = (ArrayList<Shop>) ShopDao.findShopById(order.getShopId());
        return !shopArrayList.isEmpty() ? shopArrayList.get(0) : null;
    }


    public static List<Shop> findShopNearbyWithParams(String searchParam, boolean onlyOpenNow, String type) throws AddressException {
        List<Shop> shopArrayList;
        if (searchParam == null || searchParam.isBlank() || searchParam.length() > 50){
            Address address = LocationHandler.calculateLatLongFromIpAddress();
            if (address != null) {
                shopArrayList = ShopDao.findShopNearby(address.getLat(), address.getLng(), type);
                shopArrayList = ComparableHandler.orderShopsByDistance(shopArrayList, address);
                return !shopArrayList.isEmpty() ? shopArrayList : null;
            }
            return null;
        }
        if (!onlyOpenNow){
            Address address = LocationHandler.calculateLatLongFromAddress(searchParam);
            if (address != null) {
                shopArrayList = ShopDao.findShopNearby(address.getLat(), address.getLng(), type);
                shopArrayList = ComparableHandler.orderShopsByDistance(shopArrayList, address);
                return !shopArrayList.isEmpty() ? shopArrayList : null;
            }
        }
        else {
            Address address = LocationHandler.calculateLatLongFromAddress(searchParam);
            if (address != null) {
                shopArrayList = ShopDao.findShoNearbyAndTime(address.getLat(), address.getLng(), LocalTime.now().getHour(), type);
                shopArrayList = ComparableHandler.orderShopsByDistance(shopArrayList, address);
                return !shopArrayList.isEmpty() ? shopArrayList : null;
            }
        }
        return null;
    }

    public static ArrayList<Shop> findShopByCityWithParams(String city, boolean onlyOpenNow, String type) {
        ArrayList<Shop> shopArrayList;
        if (city.isBlank() || city.length() > 50){
            return null;
        }
        if (!onlyOpenNow) {
            shopArrayList = (ArrayList<Shop>) ShopDao.findShopByCity(city,type);
        }
        else {
            shopArrayList = (ArrayList<Shop>) ShopDao.findShopByCityAndTime(city, LocalTime.now().getHour(), type);
        }
        return !shopArrayList.isEmpty() ? shopArrayList : null;
    }

    public static ArrayList<Shop> findShopByNameWithParams(String name, boolean onlyOpenNow, String type) {
        ArrayList<Shop> shopArrayList;
        if (name.isBlank() || name.length() >= 50){
            return null;
        }
        if (!onlyOpenNow) {
            shopArrayList = (ArrayList<Shop>) ShopDao.findShopByName(name, type);
        }
        else {
            shopArrayList = (ArrayList<Shop>) ShopDao.findShopByNameAndTime(name, LocalTime.now().getHour(), type);
        }
        return !shopArrayList.isEmpty() ? shopArrayList : null;
    }

    public static ArrayList<Shop> findShopByProduct(SimpleProduct product){
        ArrayList<Shop> shopArrayList;
        shopArrayList = (ArrayList<Shop>) ShopDao.findShopsByProduct(product);
        return !shopArrayList.isEmpty() ? shopArrayList : null;
    }

    public static boolean isFavoriteShop(Shop shop, User user) {
        return ShopDao.isFavoriteShop(shop.getShopId(), user.getUsername());
    }

    public static void removeShopFromFavorite(Shop shop, User user) {
        ShopDao.removeFavoriteShopFromDb(shop.getShopId(), user.getUsername());
    }

    public static void insertShopIntoFavorite(Shop shop, User user){
		ShopDao.insertFavoriteShopIntoDb(shop.getShopId(), user.getUsername());
    }

    public static ArrayList<Shop> findShopsContainingProductBy(ArrayList<SimpleProduct> productArrayList){
        if (productArrayList.isEmpty()) {
            return null;
        }
        ArrayList<Integer> productSkuArrayList = new ArrayList<>();
        for (SimpleProduct sp : productArrayList){
            productSkuArrayList.add(sp.getSku());
        }
        return (ArrayList<Shop>) ShopDao.findShopsWithProducts(productSkuArrayList);
    }

    public static ArrayList<Shop> findFavoriteShopsFromUser(User user){
        return (ArrayList<Shop>) ShopDao.findShopByFavouriteUser(user.getUsername());
    }
}