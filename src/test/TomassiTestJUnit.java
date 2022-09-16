package test;

import com.google.gson.Gson;
import control.*;
import exceptions.AddressException;
import exceptions.ExceptionBrowser;
import exceptions.ExceptionCart;
import model.Address;
import model.Constants;
import model.Dao.OrderDao;
import model.Dao.ShopDao;
import model.Order.Order;
import model.Order.OrderItem;
import model.Product.ProductShop;
import model.Product.SimpleProduct;
import model.Shop.Shop;
import model.User.Buyer;
import model.User.User;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static org.junit.Assert.*;

public class TomassiTestJUnit {
    @Test
    public void calculateLatLongFromAddressTest() throws AddressException {
        String addressString = "via colle alto ripi 03027";
        Address address = LocationHandler.calculateLatLongFromAddress(addressString);
        assertEquals( 41.5951003,address.getLat(), 0.5);
        assertEquals(13.4262861, address.getLng(),  0.5);
        assertEquals(addressString, address.getAddressString());
    }

    @Test
    public void calculateDistancePointToPointTest() {
        Shop shop = new Shop(null, null, null,null, null,null,
                null,0,0,null,null,0, 0,
                null,null,null,0);
        Address address = new Address(1, 1, null);
        double expected = 157;
        double result = LocationHandler.calculateDistancePointToPoint(shop, address);
        assertEquals(expected, result, 1);
    }
    @Test
    public void getTest() {
        String s = HttpRequest.get("https://apis.woptima.com/it/caps/FR/ripi");
        assertEquals("[\"03027\"]", s);

        String expected = "{\"name\":\"GPP\",\"surname\":\"TMS\",\"gender\":\"M\",\"day\":6,\"year\":1996,\"month\":11,\"birthday\":\"1996-11-06\",\"birthplace\":\"FROSINONE\",\"birthplaceProvincia\":\"FR\",\"cf\":\"TMSGPP96S06D810K\"}";
        s = HttpRequest.get("https://apis.woptima.com/cf/TMSGPP96S06D810K");
        assertEquals(expected, s);
    }

    @Test
    public void populateOrderWithOrderItemsTest() {
        ArrayList<Order> orderArrayList = OrderHandler.findOrdersInfoFromUser(new Buyer("abc", null, null, null, null, null, null, null, null, null, null, null, null));
        assertEquals(2, orderArrayList.get(0).getShopId());
        assertEquals(44, orderArrayList.get(0).getOrderId());
        assertEquals("eur", orderArrayList.get(0).getCurrency());
        assertEquals(14.0, orderArrayList.get(0).getTotalPrice(), 0);
        Order order = OrderHandler.populateOrderWithOrderItems(orderArrayList.get(0));
        assertNotNull(order.getOrderItemArrayList());
    }

    @Test
    public void addOrderItemsToCartTest() throws ExceptionCart {
        ProductShop productShop = new ProductShop(150.0,"eur",1, 1, "name1", "brand1", "description1", 1.0,"m", "null", 1, 0);
        boolean bool = CartElaboration.addOrderItemsToCart(productShop, 5);
        assertEquals(true, bool);
        ProductShop productShop2 = new ProductShop(150.0,"eur",1, 1, "name1", "brand1", "description1", 1.0,"m", "null", 1, 0);
        bool = CartElaboration.addOrderItemsToCart(productShop2, 5);
        assertEquals(true, bool);
        ProductShop productShop3 = new ProductShop(150.0,"eur",2, 1, "name1", "brand1", "description1", 1.0,"m", "null", 1, 0);
        bool = CartElaboration.addOrderItemsToCart(productShop3, 5);
        assertEquals(false, bool);
    }
}