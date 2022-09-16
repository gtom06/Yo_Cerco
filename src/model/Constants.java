package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public final class Constants {
    private Constants(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }
    //for google apis
    public static final String GEOCODE_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    public static final String GOOGLE_AND_KEY = "&key=AIzaSyC_HhyhuMjokSEFzXZ4K3jQNkXJlgdBNFQ";
    public static final String GEOLOCATION_URL = "https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyC_HhyhuMjokSEFzXZ4K3jQNkXJlgdBNFQ";
    public static final String GOOGLE_MAPS_LINK_START_STRING = "https://www.google.com/maps/place";

    //for postgres
    public static final String USER = "postgres";
    public static final String PASS = "postgres";
    public static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/yocerco";
    public static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";

    //some strings
    public static final String NO_RESULT = "No result";
    public static final String SHOP = "Shop";
    public static final String PRODUCT = "Product";
    public static final String ADDRESS = "Address";
    public static final String CITY = "City";
    public static final String SHOP_NAME = "Shop name";
    public static final String OPENING = "Opening";
    public static final String CLOSING = "Closing";
    public static final String DISTANCE = "Distance";
    public static final String ADD_TO_FAVORITE_SHOP_CAPSLOCK = "ADD TO FAVORITES";
    public static final String REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK = "REMOVE FROM FAVORITES";

    //use that ArrayList to populate filters
    public static final ArrayList<String> SHOP_TYPE = new ArrayList<>(Arrays.asList("All shops","Supermarket","Clothing"));
    //for shop/product_shop
    public static final String ALL_TYPES = Constants.SHOP_TYPE.get(0);

    //for files
    public static final String REMEMBER_LOGIN = "src/data/remember_login.txt";
    public static final String CART_PATH = "src/data/cart.json";
    public static final String CART_PATH2 = "src/data/cart2.json";
    //for images
    public static final String LOGO_PRODUCTS_PATH = "src/images/product/";
    public static final String LOGO_SHOPS_PATH = "src/images/shop_images/";
    public static final String LOGO_SHOP_NA = "NotAvailablePhoto.png";
    public static final String PROFILE_IMAGES_PATH = "src/images/user_profile_images/";
    public static final String PROFILE_IMAGE_BLANK = "src/images/user_profile_images/icons8-customer-100.png";
    public static final String SHOP_IMAGE = "src/images/shop/";
    public static final String LOGO_DEPARTMENT_PATH = "src/images/department/";

    //values
    public static final int NEW_OPENING = 0;
    public static final int AVAILABLE = 1;
    public static final int NOT_AVAILABLE = -1;

    //for orders
    public static final String TOTAL_ORDERS_STRING = "Total orders: ";
    //order status
    public static final String CREATED_STATUS = "Created";
    public static final String IN_PROGRESS_STATUS = "In progress";
    public static final String READY_TO_PICKUP = "Ready to pickup";
    public static final String COMPLETED = "Completed";
    //payment name
    public static final String CREDITCARD_PAYMENT = "Creditcard";
    public static final String CASH_ON_DELIVERY_PAYMENT = "Cash on delivery";
    //for search
    public static final String NEARBY = "nearby";
    public static final String BY_CITY = "by city";
    public static final String BY_NAME = "by name";
    //for users
    public static final String ADMIN_USER = "A";
    public static final String BUYER_USER = "B";
    public static final String SHOPHOLDER_USER = "S";
    public static final String ADMIN = "Admin";
    public static final String BUYER = "Buyer";
    public static final String OWNER = "Owner";
}