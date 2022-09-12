package control;

import exceptions.AddressException;
import model.Address;
import model.Dao.ShopDao;
import model.Product.SimpleProduct;
import model.Shop.Shop;
import model.User.User;

import java.time.LocalTime;
import java.util.ArrayList;

public class ShopHandler {
    private ShopHandler(){
        throw new IllegalStateException("Utility class");
    }
    public static void insertShop(Shop shop){
        ShopDao.insertShopDb(shop);
    }
    public static void deleteShop(Shop shop){
        ShopDao.deleteShopDb(shop);
    }
    public static void updateShop(Shop shop){}

    public static ArrayList<Shop> findShopNearbyWithParams(String searchParam, boolean onlyOpenNow, String type) throws AddressException {
        ArrayList<Shop> shopArrayList;
        if (searchParam == null || searchParam.length() == 0 || searchParam.length() > 50){
            Address address = LocationHandler.calculateLatLongFromIpAddress();
            if (address != null) {
                shopArrayList = ShopDao.findShopNearby(address.getLat(), address.getLng(), type);
                shopArrayList = ComparableHandler.orderShopsByDistance(shopArrayList, address);
                return shopArrayList.size() != 0 ? shopArrayList : null;
            }
            return null;
        }
        if (!onlyOpenNow){
            Address address = LocationHandler.calculateLatLongFromAddress(searchParam);
            if (address != null) {
                shopArrayList = ShopDao.findShopNearby(address.getLat(), address.getLng(), type);
                shopArrayList = ComparableHandler.orderShopsByDistance(shopArrayList, address);
                return shopArrayList.size() != 0 ? shopArrayList : null;
            }
        }
        else {
            Address address = LocationHandler.calculateLatLongFromAddress(searchParam);
            if (address != null) {
                shopArrayList = ShopDao.findShoNearbyAndTime(address.getLat(), address.getLng(), LocalTime.now().getHour(), type);
                shopArrayList = ComparableHandler.orderShopsByDistance(shopArrayList, address);
                return shopArrayList.size() != 0 ? shopArrayList : null;
            }
        }
        return null;
    }

    public static ArrayList<Shop> findShopByCityWithParams(String city, boolean onlyOpenNow, String type) {
        ArrayList<Shop> shopArrayList;
        if (city == null || city.length() == 0 || city.length() > 50){
            return null;
        }
        if (!onlyOpenNow) {
            shopArrayList = ShopDao.findShopByCity(city,type);
        }
        else {
            shopArrayList = ShopDao.findShopByCityAndTime(city, LocalTime.now().getHour(), type);
        }
        return shopArrayList.size() != 0 ? shopArrayList : null;
    }

    public static ArrayList<Shop> findShopByNameWithParams(String name, boolean onlyOpenNow, String type) {
        ArrayList<Shop> shopArrayList;
        if (name == null || name.length() == 0 || name.length() >= 50){
            return null;
        }
        if (!onlyOpenNow) {
            shopArrayList = ShopDao.findShopByName(name, type);
        }
        else {
            shopArrayList = ShopDao.findShopByNameAndTime(name, LocalTime.now().getHour(), type);
        }
        return shopArrayList.size() != 0 ? shopArrayList : null;
    }

    public static ArrayList<Shop> findShopByProduct(SimpleProduct product){
        ArrayList<Shop> shopArrayList;
        shopArrayList = ShopDao.findShopsByProduct(product);
        return shopArrayList.size() != 0 ? shopArrayList : null;
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
        if (productArrayList.size() == 0) {
            return null;
        }
        ArrayList<Integer> productSkuArrayList = new ArrayList<>();
        for (SimpleProduct sp : productArrayList){
            productSkuArrayList.add(sp.getSku());
        }
        return ShopDao.findShopsWithProducts(productSkuArrayList);
    }

    public static ArrayList<Shop> findFavoriteShopsFromUser(User user){
        return ShopDao.findShopByFavouriteUser(user.getUsername());
    }

    public static ArrayList<String> findTypeShop(){
        ArrayList<String> arrayTypeShop;
        arrayTypeShop = ShopDao.findTypeShop();
        arrayTypeShop.add(0, "All types");
        return arrayTypeShop;
    }
}