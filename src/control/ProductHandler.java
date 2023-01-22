package control;

import bean.*;
import constants.ConstantsExceptions;
import dao.ProductDao;
import model.product.ProductShop;
import model.product.SimpleProduct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductHandler {
    private ProductHandler(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    public static List<SimpleProductBean> findSimpleProductBy(String productName) {
        if (productName.isBlank() || productName.length() > 100) {
            return Collections.emptyList();
        }
        return convertSimpleProductInSimpleProductBean(ProductDao.findProductByName(productName));
    }
    public static ProductShopBean findProductShopByShopAndSimpleProduct(ShopBean shop, SimpleProductBean simpleProduct) {
        if (shop == null || simpleProduct == null) {
            return (ProductShopBean) Collections.emptyList();
        }
        return convertProductShopInProductShopBean(ProductDao.findProductBySkuAndShopId(shop.getShopId(), simpleProduct.getSku()).get(0));
    }

    private static ProductShopBean convertProductShopInProductShopBean(ProductShop ps){
        ProductShopBean productShopBean = new ProductShopBean();
        productShopBean.setSku(ps.getSku());
        productShopBean.setName(ps.getName());
        productShopBean.setShopId(ps.getShopId());
        productShopBean.setDepartmentId(ps.getDepartmentId());
        productShopBean.setPrice(ps.getPrice());
        productShopBean.setCurrency(ps.getCurrency());
        productShopBean.setBrand(ps.getBrand());
        productShopBean.setDescription(ps.getDescription());
        productShopBean.setSize(ps.getSize());
        productShopBean.setUnitOfMeasure(ps.getUnitOfMeasure());
        productShopBean.setLogoImagepath(ps.getLogoImagepath());
        productShopBean.setLogoImagepath(ps.getLogoImagepath());
        productShopBean.setDiscountedPrice(ps.getDiscountedPrice());
        return productShopBean;
    }

    public static List<ProductShopBean> findProductByDepartmentAndShop(ShopBean shop, DepartmentBean department){
        ArrayList<ProductShop> output = (ArrayList<ProductShop>) ProductDao.findProductByDepartmentAndShop(shop.getShopId(), department.getDepartmentId());
        ArrayList<ProductShopBean> outputBean = new ArrayList<>();
        if (output.size() > 14) {
            output = new ArrayList<>(output.subList(0, 14));
        }
        for (ProductShop ps : output){
            outputBean.add(convertProductShopInProductShopBean(ps));
        }
        return !outputBean.isEmpty() ? outputBean : null;
    }

    private static List<SimpleProductBean> convertSimpleProductInSimpleProductBean(List<SimpleProduct> simpleProductList){
        List<SimpleProductBean> simpleProductBeanList = new ArrayList<>();
        for (SimpleProduct sp : simpleProductList){
            SimpleProductBean spb = new SimpleProductBean();
            spb.setSku(sp.getSku());
            spb.setName(sp.getName());
            spb.setBrand(sp.getBrand());
            spb.setDescription(sp.getDescription());
            spb.setLogoImagepath(sp.getLogoImagepath());
            spb.setSize(sp.getSize());
            spb.setUnitOfMeasure(sp.getUnitOfMeasure());
            simpleProductBeanList.add(spb);
        }
        return simpleProductBeanList;
    }

    public static List<SimpleProductBean> findFavoriteSimpleProductFromUser(UserBean user){
        return convertSimpleProductInSimpleProductBean(ProductDao.findSimpleProductFromUser(user.getUsername()));
    }

    public static boolean isFavoriteProduct(ProductShopBean productShop, UserBean user) {
        return ProductDao.isFavoriteProduct(user.getUsername(),productShop.getShopId());
    }

    public static void removeProductFromFavorites(UserBean user, ProductShopBean productShop) {
        ProductDao.removeFavoriteProductFromDb(user.getUsername(), productShop.getSku());
    }

    public static void insertProductIntoFavorites(UserBean user, ProductShopBean productShop){
        ProductDao.insertFavoriteProductIntoDb(user.getUsername(), productShop.getSku());
    }
}