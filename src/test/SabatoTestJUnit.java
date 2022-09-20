package test;

import control.BrowserHandler;
import control.ShopHandler;
import exceptions.AddressException;
import exceptions.ExceptionBrowser;
import model.Constants;
import model.product.SimpleProduct;
import model.shop.Shop;
import org.junit.Test;

import java.net.URI;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SabatoTestJUnit {
    @Test
    public void findShopByProductTest() {
        List<Shop> shopArrayList = ShopHandler.findShopByProduct(
                new SimpleProduct(1, null, null, null, 0, null, null));

        assertNotNull(shopArrayList);
        assertEquals("TODIS FR", shopArrayList.get(0).getShopName());
        assertEquals("+393401234567", shopArrayList.get(0).getPhone());
    }
    @Test
    public void findShopNearbyWithParamsTest() throws AddressException {
        List<Shop> shopArrayList = ShopHandler.findShopNearbyWithParams("via maria frosinone", false, Constants.SHOP_TYPE.get(0));
        assertNotNull(shopArrayList);
        assertEquals("LIDL FR", shopArrayList.get(0).getShopName());
        assertEquals("+393881234567", shopArrayList.get(0).getPhone());
    }
    @Test
    public void openWebpageTest() throws ExceptionBrowser {
        assertEquals(true, BrowserHandler.openWebpage(URI.create("https://google.com")));
    }
}
