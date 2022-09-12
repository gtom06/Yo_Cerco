package control;

import model.Dao.ProductDao;
import model.Product.ProductShop;
import model.Product.SimpleProduct;
import model.Shop.Shop;
import model.User.User;

import java.util.ArrayList;

public class ProductHandler {
    private ProductHandler(){
        throw new IllegalStateException("Utility class");
    }

    public static ArrayList<SimpleProduct> findSimpleProductBy(String productName) {
        if (productName.isBlank() || productName.length() > 100) {
            return null;
        }
        return ProductDao.findProductByName(productName);
    }
    public static ProductShop findProductShopByShopAndSimpleProduct(Shop shop, SimpleProduct simpleProduct) {
        if (shop == null || simpleProduct == null) {
            return null;
        }
        return ProductDao.findProductBySkuAndShopId(shop.getShopId(), simpleProduct.getSku()).get(0);
    }

    public static ArrayList<SimpleProduct> findFavoriteSimpleProductFromUser(User user){
        return ProductDao.findSimpleProductFromUser(user);
    }

    public static boolean isFavoriteProduct(ProductShop productShop, User user) {
        return ProductDao.isFavoriteProduct(user.getUsername(),productShop.getShopId());
    }

    public static void removeProductFromFavorites(User user, ProductShop productShop) {
        ProductDao.removeFavoriteProductFromDb(user.getUsername(), productShop.getSku());
    }

    public static void insertProductIntoFavorites(User user, ProductShop productShop){
        ProductDao.insertFavoriteProductIntoDb(user.getUsername(), productShop.getSku());
    }
}