package control;
import model.Constants;
import model.Product.SimpleProduct;
import model.Shop.Shop;
import model.Dao.ShopDao;
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
    public static void updateShop(Shop shop){ }

    //retrieve details of a single shop.
    //to be changed with return Shop;
    public static ArrayList<String> findShopForViewShop(int shopId){
        Shop shop = ShopDao.findShopById(shopId);
        ArrayList<String> out = new ArrayList<>();
        out.add(shop.getLogoImagepath());
        out.add(shop.getCity());
        out.add(shop.getAddress());
        out.add(shop.getShopName());
        out.add(String.valueOf(shop.getShopId()));
        return out;
    }


    public static ArrayList<Shop> findShopBy(String searchParam, String searchMethod, boolean searchBool){
        if (searchParam.isBlank() || searchParam.length() > 50) {
            return null;
        }
        ArrayList<Shop> output;
        if (searchBool == false) {
            if (searchMethod == Constants.BY_ADDRESS) {
                output = ShopDao.findShopByAddress(searchParam);
                return output.size() != 0 ? output : null;
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
            System.out.println(now);
            if (searchMethod == Constants.BY_ADDRESS) {
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

    // todo: check the usage and remove it
    public String getPhotoByShopId(int shopId){
        return ShopDao.findShopById(shopId).getLogoImagepath();
    }

    // todo: remove that class and use another way
    public static int getShopFromString(String s){
        int tab = s.indexOf(" ");
        String subString = "";
        if (tab != -1)
        {
            subString= s.substring(0 , tab); //this will give abc
        }
        return Integer.valueOf(subString);
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