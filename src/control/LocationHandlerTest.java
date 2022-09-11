package control;

import model.Address;
import model.Shop.Shop;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocationHandlerTest {
    @Test
    public void calculateLatLongFromAddressTest() {
        String addressString = "via colle alto ripi 03027";
        Address address = LocationHandler.calculateLatLongFromAddress(addressString);
        assertEquals( 41.5951003,address.getLat(), 0.5);
        assertEquals(13.4262861, address.getLng(),  0.5);
        assertEquals(addressString, address.getAddress());
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
}