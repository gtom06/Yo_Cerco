package control;

import model.Address;
import model.Constants;
import model.Dao.ShopDao;
import model.Product.SimpleProduct;
import model.Shop.Shop;
import model.User.User;

import java.time.LocalTime;
import java.util.ArrayList;

public class ShopHandler {
    public static void insertShop(Shop shop){
        ShopDao.insertShopDb(shop);
    }
    public static void deleteShop(Shop shop){
        ShopDao.deleteShopDb(shop);
    }
    public static void updateShop(Shop shop){}

    public static ArrayList<Shop> findShopBy(String searchParam, String searchMethod, boolean searchBool){
        ArrayList<Shop> shopArrayList;
        if (searchParam.length() > 50) {
            return null;
        }
        if (searchBool == false) {
            if (searchParam.isBlank() && searchMethod == Constants.NEARBY){
                Address address = LocationHandler.calculateLatLongFromIpAddress();
                if (address != null) {
                    shopArrayList = ShopDao.findShopNearby(address.getLat(), address.getLng());
                    shopArrayList = ComparableHandler.orderShopsByDistance(shopArrayList, address);
                    return shopArrayList.size() != 0 ? shopArrayList : null;
                }
            }
            if (searchMethod == Constants.NEARBY) {
                Address address = LocationHandler.calculateLatLongFromAddress(searchParam);
                if (address != null) {
                    shopArrayList = ShopDao.findShopNearby(address.getLat(), address.getLng());
                    shopArrayList = ComparableHandler.orderShopsByDistance(shopArrayList, address);
                    return shopArrayList.size() != 0 ? shopArrayList : null;
                }
            } else if (searchMethod == Constants.BY_CITY) {
                shopArrayList = ShopDao.findShopByCity(searchParam);
                return shopArrayList.size() != 0 ? shopArrayList : null;
            } else if (searchMethod == Constants.BY_NAME) {
                shopArrayList = ShopDao.findShopByName(searchParam);
                return shopArrayList.size() != 0 ? shopArrayList : null;
            }
        }
        else {
            Integer now = Integer.parseInt(LocalTime.now().toString().substring(0,2));
            if (searchParam.isBlank() && searchMethod == Constants.NEARBY){
                Address address = LocationHandler.calculateLatLongFromIpAddress();
                if (address != null) {
                    shopArrayList = ShopDao.findShoNearbyAndTime(address.getLat(), address.getLng(), LocalTime.now().getHour());
                    shopArrayList = ComparableHandler.orderShopsByDistance(shopArrayList, address);
                    return shopArrayList.size() != 0 ? shopArrayList : null;
                }
            }
            if (searchMethod == Constants.NEARBY) {
                Address address = LocationHandler.calculateLatLongFromAddress(searchParam);
                if (address != null) {
                    shopArrayList = ShopDao.findShoNearbyAndTime(address.getLat(), address.getLng(), LocalTime.now().getHour());
                    shopArrayList = ComparableHandler.orderShopsByDistance(shopArrayList, address);
                    return shopArrayList.size() != 0 ? shopArrayList : null;
                }
            } else if (searchMethod == Constants.BY_CITY) {
                shopArrayList = ShopDao.findShopByCityAndTime(searchParam, now);
                return shopArrayList.size() != 0 ? shopArrayList : null;
            } else if (searchMethod == Constants.BY_NAME) {
                shopArrayList = ShopDao.findShopByNameAndTime(searchParam, now);
                return shopArrayList.size() != 0 ? shopArrayList : null;
            }
        }
        return null;
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
}