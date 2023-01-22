package control;

import bean.ShopBean;
import bean.SimpleProductBean;
import bean.UserBean;
import exceptions.AddressException;
import model.address.Address;
import constants.ConstantsExceptions;
import dao.ShopDao;
import model.shop.Shop;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ShopHandler {
    private ShopHandler(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }
    public static void insertShop(ShopBean shop){
        throw new UnsupportedOperationException();
    }
    public static void deleteShop(ShopBean shop){
        throw new UnsupportedOperationException();
    }
    public static void updateShop(ShopBean shop){
        throw new UnsupportedOperationException();
    }

    public static List<ShopBean> useSearchByIpAddress(String type) throws AddressException {
        List<Shop> shopList = new ArrayList<>();
        Address address = LocationHandler.calculateLatLongFromIpAddress();
        if (address != null) {
            shopList = ShopDao.findShopNearby(address.getLat(), address.getLng(), type, null);
            shopList = ComparableHandler.orderShopsByDistance(shopList, address);
        }
        return convertListShopInListShopBean(shopList);
    }
    private static List<ShopBean> convertListShopInListShopBean(List<Shop> shopList){
        if (shopList != null) {
            List<ShopBean> shopBeanList = new ArrayList<>();
            for (Shop s : shopList) {
                ShopBean shopBean = new ShopBean();
                shopBean.setShopId(s.getShopId());
                shopBean.setPhone(s.getPhone());
                shopBean.setCity(s.getCity());
                shopBean.setShopName(s.getShopName());
                shopBean.setLogoImagepath(s.getLogoImagepath());
                shopBean.setOpeningTime(s.getOpeningTime());
                shopBean.setClosingTime(s.getClosingTime());
                shopBean.setGmapsLink(s.getGmapsLink());
                shopBean.setFranchising(s.getFranchising());
                shopBean.setOffersFlyerPath(s.getOffersFlyerPath());
                shopBean.setDistance(s.getDistance());
                shopBean.setLat(s.getLat());
                shopBean.setLng(s.getLng());
                shopBean.setCompleteAddress(s.getCompleteAddress());
                shopBeanList.add(shopBean);
            }
            return shopBeanList;
        }
        else {
            return null;
        }
    }

    public static List<ShopBean> findShopNearbyWithParams(String searchParam, boolean onlyOpenNow, String type) throws AddressException {
        List<ShopBean> shopList;
        if (searchParam == null || searchParam.isBlank() || searchParam.length() > 50){
            shopList = useSearchByIpAddress(type);
        }
        else {
            shopList = useSearchByLatLong(searchParam, onlyOpenNow, type);
        }
        return shopList != null ? shopList : null;
    }

    public static List<ShopBean> useSearchByLatLong(String addressString, boolean onlyOpenNow, String type) throws AddressException {
        List<Shop> shopList = new ArrayList<>();
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
        if (shopList != null) {
            return convertListShopInListShopBean(shopList);
        }
        else{
            return null;
        }
    }

    public static List<ShopBean> findShopByCityWithParams(String city, boolean onlyOpenNow, String type) {
        List<Shop> shopList;
        if (city.isBlank() || city.length() > 50){
            return null;
        }
        if (!onlyOpenNow) {
            shopList = ShopDao.findShopByCity(city, type, null);
        }
        else {
            shopList = ShopDao.findShopByCity(city, type, LocalTime.now().getHour());
        }
        return convertListShopInListShopBean(shopList);
    }

    public static List<ShopBean> findShopByNameWithParams(String name, boolean onlyOpenNow, String type) {
        List<Shop> shopList;
        if (name.isBlank() || name.length() >= 50){
            return null;
        }
        if (!onlyOpenNow) {
            shopList = ShopDao.findShopByName(name, type, null);
        }
        else {
            shopList = ShopDao.findShopByName(name, type, LocalTime.now().getHour());
        }
        return convertListShopInListShopBean(shopList);
    }

    public static List<ShopBean> findShopByProduct(SimpleProductBean product){
        List<Shop> shopList = ShopDao.findShopsByProduct(product.getSku());
        return convertListShopInListShopBean(shopList);
    }

    public static boolean isFavoriteShop(ShopBean shop, UserBean user) {
        return ShopDao.isFavoriteShop(shop.getShopId(), user.getUsername());
    }

    public static void removeShopFromFavorite(ShopBean shop, UserBean user) {
        ShopDao.removeFavoriteShopFromDb(shop.getShopId(), user.getUsername());
    }

    public static void insertShopIntoFavorite(ShopBean shop, UserBean user){
		ShopDao.insertFavoriteShopIntoDb(shop.getShopId(), user.getUsername());
    }

    public static List<ShopBean> findFavoriteShopsFromUser(UserBean user){
        List<Shop> shopList = ShopDao.findShopByFavoriteUser(user.getUsername());
        return convertListShopInListShopBean(shopList);
    }
}