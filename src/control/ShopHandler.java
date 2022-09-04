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
        if (searchParam.isBlank() || searchParam.length() > 50) {
            return null;
        }
        ArrayList<Shop> output;
        if (searchBool == false) {
            if (searchMethod == Constants.NEARBY) {
                Address address = LocationHandler.calculateLatLongFromAddress(searchParam);
                System.out.println(address);
                if (address != null) {
                    output = ShopDao.findShopNearby(address.getLat(), address.getLng());
                    output = ComparableHandler.orderByDistance(output, address);
                    return output.size() != 0 ? output : null;
                }
            } else if (searchMethod == Constants.BY_CITY) {
                output = ShopDao.findShopByCity(searchParam);
                return output.size() != 0 ? output : null;
            } else if (searchMethod == Constants.BY_NAME) {
                output = ShopDao.findShopByName(searchParam);
                return output.size() != 0 ? output : null;
            }
        }
        else {
            Integer now = Integer.parseInt(LocalTime.now().toString().substring(0,2));
            if (searchMethod == Constants.NEARBY) {
                output = ShopDao.findShopByAddressAndTime(searchParam, now);
                return output.size() != 0 ? output : null;
            } else if (searchMethod == Constants.BY_CITY) {
                output = ShopDao.findShopByCityAndTime(searchParam, now);
                return output.size() != 0 ? output : null;
            } else if (searchMethod == Constants.BY_NAME) {
                output = ShopDao.findShopByNameAndTime(searchParam, now);
                return output.size() != 0 ? output : null;
            }
        }
        return null;
    }

    public static boolean isFavoriteShop(Shop shop, User user) {
        return ShopDao.isFavoriteShop(shop.getShopId(), user.getUsername());
    }

    public static void removeShopFromFavorite(Shop shop, User user) {
        ShopDao.removeFavoriteShopFromDb(shop.getShopId(), user.getUsername());
    }

    public static void insertShopIntoFavorite(Shop shop, User user){
        ShopDao.insertFavoriteShopIntoDb(Integer.valueOf(shop.getShopId()), user.getUsername());
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

    public static ArrayList<Shop> findFavouriteShopsFromUser(User user){
        return ShopDao.findShopByFavouriteUser(user.getUsername());
    }
}