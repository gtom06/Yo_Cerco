package control;

import constants.ConstantsExceptions;
import dao.ProductDao;
import model.product.ProductShop;
import model.product.SimpleProduct;
import model.shop.Shop;
import model.user.User;

import java.util.Collections;
import java.util.List;

public class ProductHandler {
    private ProductHandler(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    public static List<SimpleProduct> findSimpleProductBy(String productName) {
        if (productName.isBlank() || productName.length() > 100) {
            return Collections.emptyList();
        }
        return ProductDao.findProductByName(productName);
    }
    public static ProductShop findProductShopByShopAndSimpleProduct(Shop shop, SimpleProduct simpleProduct) {
        if (shop == null || simpleProduct == null) {
            return (ProductShop) Collections.emptyList();
        }
        return ProductDao.findProductBySkuAndShopId(shop.getShopId(), simpleProduct.getSku()).get(0);
    }

    public static List<SimpleProduct> findFavoriteSimpleProductFromUser(User user){
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