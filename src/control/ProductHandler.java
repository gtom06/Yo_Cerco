package control;

import model.Dao.ProductDao;
import model.Dao.ShopDao;
import model.Product.Product;
import model.Product.ProductShop;
import model.Product.SimpleProduct;
import model.Shop.Shop;
import model.User.User;

import java.util.ArrayList;

public class ProductHandler {
    public static ArrayList<ProductShop> findProductBy(String productName){
        if (productName.isBlank() || productName.length() > 100) {
            return null;
        }
        return ProductDao.findProductShopByName(productName);
    }

    public static ArrayList<SimpleProduct> findSimpleProductBy(String productName) {
        if (productName.isBlank() || productName.length() > 100) {
            return null;
        }
        return ProductDao.findProductByName(productName);
    }

    public static ArrayList<ProductShop> findProductShopBy(Shop shop) {
        if (shop == null) {
            return null;
        }
        return ProductDao.findProductInShop(shop.getShopId());
    }

    public static ArrayList<SimpleProduct> findFavoriteShopsFromUser(User user) {
        return ProductDao.findFavoriteShopsFromUser(user.getUsername());
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