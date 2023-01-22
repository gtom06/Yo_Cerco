package test;

import bean.OrderBean;
import bean.ProductShopBean;
import bean.UserBean;
import control.*;
import exceptions.AddressException;
import exceptions.ExceptionCart;
import model.address.Address;
import model.order.Order;
import model.product.ProductShop;
import model.shop.Shop;
import model.user.Buyer;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
        Address addressShop = new Address(0,0, null, null, null);
        Shop shop = new Shop(
                null,
                addressShop,
                null,
                null,
                Arrays.asList("1", "10"),
                null,
                null
        );

        Address addressUser = new Address(1, 1, null);
        double expected = 157;
        double result = LocationHandler.calculateDistancePointToPoint(shop, addressUser);
        assertEquals(expected, result, 1);

        Address addressShop2 = new Address(1,1, null, null, null);
        Shop shop2 = new Shop(
                null,
                addressShop2,
                null,
                null,
                Arrays.asList("1", "10"),
                null,
                null
        );

        double expected2 = 0;
        double result2 = LocationHandler.calculateDistancePointToPoint(shop2, addressUser);
        assertEquals(expected2, result2, 1);

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
        UserBean userBean = new UserBean();
        userBean.setUsername("gtom");
        List<OrderBean> orderArrayList = OrderHandler.findOrdersInfoFromUser(userBean);
        assertEquals(2, orderArrayList.get(0).getShopId());
        assertEquals(1, orderArrayList.get(0).getOrderId());
        assertEquals("eur", orderArrayList.get(0).getCurrency());
        assertEquals(3.5, orderArrayList.get(0).getTotalPrice(), 0);
        OrderBean order = OrderHandler.populateOrderWithOrderItems(orderArrayList.get(0));
        assertNotNull(order.getOrderItemArrayList());
    }
    @Test
    public void addOrderItemsToCartTest() throws ExceptionCart {
        ProductShopBean productShop1 = new ProductShopBean();
        productShop1.setPrice(150.00);
        productShop1.setCurrency("eur");
        productShop1.setShopId(1);
        productShop1.setSku(1);
        productShop1.setName("name1");
        productShop1.setBrand("brand1");
        productShop1.setDescription("description1");
        productShop1.setSize(1.0);
        productShop1.setUnitOfMeasure("m");
        productShop1.setLogoImagepath("null");
        productShop1.setDepartmentId(1);
        productShop1.setDiscountedPrice(0);
        boolean bool = CartElaboration.addOrderItemsToCart(productShop1, 5);
        assertEquals(true, bool);

        ProductShopBean productShop2 = new ProductShopBean();
        productShop2.setPrice(1.00);
        productShop2.setCurrency("eur");
        productShop2.setShopId(1);
        productShop2.setSku(2);
        productShop2.setName("name2");
        productShop2.setBrand("brand2");
        productShop2.setDescription("description2");
        productShop2.setSize(1.0);
        productShop2.setUnitOfMeasure("m");
        productShop2.setLogoImagepath("null");
        productShop2.setDepartmentId(1);
        productShop2.setDiscountedPrice(0);
        bool = CartElaboration.addOrderItemsToCart(productShop2, 5);
        assertEquals(true, bool);


        ProductShopBean productShop3 = new ProductShopBean();
        productShop3.setPrice(100.00);
        productShop3.setCurrency("eur");
        productShop3.setShopId(2);
        productShop3.setSku(3);
        productShop3.setName("name3");
        productShop3.setBrand("brand1");
        productShop3.setDescription("description3");
        productShop3.setSize(1.0);
        productShop3.setUnitOfMeasure("m");
        productShop3.setLogoImagepath("null");
        productShop3.setDepartmentId(1);
        productShop3.setDiscountedPrice(0);
        bool = CartElaboration.addOrderItemsToCart(productShop3, 5);
        assertEquals(false, bool);
    }
}