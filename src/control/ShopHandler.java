package control;

import exceptions.AddressException;
import model.address.Address;
import model.ConstantsExceptions;
import model.dao.ShopDao;
import model.product.SimpleProduct;
import model.shop.Shop;
import model.shop.Shop2;
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

    public static List<Shop2> useSearchByIpAddress(String type) throws AddressException {
        List<Shop2> shopList = new ArrayList<>();
        Address address = LocationHandler.calculateLatLongFromIpAddress();
        if (address != null) {
            shopList = ShopDao.findShopNearby(address.getLat(), address.getLng(), type, null);
            shopList = ComparableHandler.orderShopsByDistance(shopList, address);
        }
        return shopList != null ? shopList : null;
    }

    public static List<Shop2> findShopNearbyWithParams(String searchParam, boolean onlyOpenNow, String type) throws AddressException {
        List<Shop2> shopList;
        if (searchParam == null || searchParam.isBlank() || searchParam.length() > 50){
            shopList = useSearchByIpAddress(type);
        }
        else {
            shopList = useSearchByLatLong(searchParam, onlyOpenNow, type);
        }
        return shopList != null ? shopList : null;
    }

    public static List<Shop2> useSearchByLatLong(String addressString, boolean onlyOpenNow, String type) throws AddressException {
        List<Shop2> shopList = new ArrayList<>();
        Address address = LocationHandler.calculateLatLongFromAddress(addressString);
        if (address != null && onlyOpenNow){
            shopList = ComparableHandler.orderShopsByDistance(
                    ShopDao.findShopNearby(
                            address.getLat(),
                            address.getLng(),
                            type,
                            LocalTime.now().getHour()),
                    address);
        }
        else if (address != null){
            shopList = ComparableHandler.orderShopsByDistance(
                    ShopDao.findShopNearby(
                            address.getLat(),
                            address.getLng(),
                            type,
                            null),
                    address);
        }
        return shopList != null ? shopList : null;
    }

    public static List<Shop2> findShopByCityWithParams(String city, boolean onlyOpenNow, String type) {
        List<Shop2> shopArrayList;
        if (city.isBlank() || city.length() > 50){
            return null;
        }
        if (!onlyOpenNow) {
            shopArrayList = ShopDao.findShopByCity(city, type, null);
        }
        else {
            shopArrayList = ShopDao.findShopByCity(city, type, LocalTime.now().getHour());
        }
        return !shopArrayList.isEmpty() ? shopArrayList : null;
    }

    public static List<Shop2> findShopByNameWithParams(String name, boolean onlyOpenNow, String type) {
        List<Shop2> shopArrayList;
        if (name.isBlank() || name.length() >= 50){
            return null;
        }
        if (!onlyOpenNow) {
            shopArrayList = ShopDao.findShopByName(name, type, null);
        }
        else {
            shopArrayList = ShopDao.findShopByName(name, type, LocalTime.now().getHour());
        }
        return !shopArrayList.isEmpty() ? shopArrayList : null;
    }

    public static List<Shop2> findShopByProduct(SimpleProduct product){
        List<Shop2> shopArrayList = ShopDao.findShopsByProduct(product);
        return !shopArrayList.isEmpty() ? shopArrayList : null;
    }

    public static boolean isFavoriteShop(Shop2 shop, User user) {
        return ShopDao.isFavoriteShop(shop.getShopId(), user.getUsername());
    }

    public static void removeShopFromFavorite(Shop2 shop, User user) {
        ShopDao.removeFavoriteShopFromDb(shop.getShopId(), user.getUsername());
    }

    public static void insertShopIntoFavorite(Shop2 shop, User user){
		ShopDao.insertFavoriteShopIntoDb(shop.getShopId(), user.getUsername());
    }

    public static List<Shop2> findShopsContainingProductBy(List<SimpleProduct> productArrayList){
        if (productArrayList.isEmpty()) {
            return null;
        }
        List<Integer> productSkuArrayList = new ArrayList<>();
        for (SimpleProduct sp : productArrayList){
            productSkuArrayList.add(sp.getSku());
        }
        return ShopDao.findShopsWithProducts(productSkuArrayList);
    }

    public static List<Shop2> findFavoriteShopsFromUser(User user){
        List<Shop2> shopArrayList = ShopDao.findShopByFavoriteUser(user.getUsername());
        return !shopArrayList.isEmpty() ? shopArrayList : null;
    }
}