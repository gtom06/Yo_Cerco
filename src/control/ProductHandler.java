package control;

import model.Dao.ProductDao;
import model.Product.ProductShop;
import model.Product.SimpleProduct;
import model.Shop.Shop;

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
}