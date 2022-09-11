package model;

public interface Constants {
    //for google apis
    String GEOCODE_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    String GOOGLE_AND_KEY = "&key=AIzaSyC_HhyhuMjokSEFzXZ4K3jQNkXJlgdBNFQ";
    String GEOLOCATION_URL = "https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyC_HhyhuMjokSEFzXZ4K3jQNkXJlgdBNFQ";
    String GOOGLE_MAPS_LINK_START_STRING = "https://www.google.com/maps/place";

    //for postgres
    String USER = "postgres";
    String PASS = "postgres";
    String DB_URL = "jdbc:postgresql://127.0.0.1:5432/yocerco";
    String DRIVER_CLASS_NAME = "org.postgresql.Driver";

    //some strings
    String NO_RESULT = "no result";
    String SHOP = "Shop";
    String PRODUCT = "Product";
    String ADDRESS = "Address";
    String CITY = "City";
    String SHOP_NAME = "Shop name";
    String OPENING = "Opening";
    String CLOSING = "Closing";
    String DISTANCE = "Distance";
    String SAVE = "Save";
    String SAVE_CAPSLOCK = "SAVE";
    String AVAILABLE_STRING = "Available";
    String SOON_BACK_STRING = "Soon back";
    String NOT_AVAILABLE_STRING = "Not available";
    String MY_FAVORITE_SHOPS = "My favorite shops";
    String MY_FAVORITE_SHOPS_CAPSLOCK = "MY FAVORITE SHOPS";
    String ADD_TO_FAVORITE_SHOP = "Add to favorite";
    String ADD_TO_FAVORITE_SHOP_CAPSLOCK = "ADD TO FAVORITES";
    String REMOVE_FROM_FAVORITE_SHOP = "Remove from favorites";
    String REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK = "REMOVE FROM FAVORITES";

    //for files
    String REMEMBER_LOGIN = "src/data/remember_login.txt";
    String CART_PATH = "src/data/cart.json";
    String CART_PATH2 = "src/data/cart2.json";
    //for images
    String LOGO_PRODUCTS_PATH = "src/images/product/";
    String LOGO_SHOPS_PATH = "src/images/shop_images/";
    String LOGO_SHOP_NA = "NotAvailablePhoto.png";
    String PROFILE_IMAGES_PATH = "src/images/user_profile_images/";
    String PROFILE_IMAGE_BLANK = "src/images/user_profile_images/icons8-customer-100.png";
    String SHOP_IMAGE = "src/images/shop/";
    String LOGO_DEPARTMENT_PATH = "src/images/department/";
    //for shop/product_shop
    String ALL_TYPES = "All types";

    //values
    int NEW_OPENING = 0;
    int AVAILABLE = 1;
    int NOT_AVAILABLE = -1;
    //String
    /*
    //departments
    String FRUIT_DEPARTMENT = "Fruit";
    String VEGETABLES_DEPARTMENT = "Vegetables";
    String CURED_MEAT_DEPARTMENT = "Cured meat"; //salumi
    String CHEESE_DEPARTMENT = "Cheese";
    String BEVERAGE_DEPARTMENT = "Beverage";
    String MEAT_DEPARTMENT = "Meat";
    String FOODSTUFF_DEPARTMENT = "Foodstuff";
    String MILK_BUTTER_EGGS_DEPARTMENT = "Milk, butter & eggs";
    String BREAKFAST_SWEETS_CAKES_DEPARTMENT = "Breakfast, sweets & cakes";
    String ICECREAM_FROZEN_DEPARTMENT = "Icecream & frozen";
    String COSMETICS_DEPARTMENT = "Cosmetics";
    String HOUSEWARE_DEPARTMENT = "Houseware";
    String PETS_DEPARTMENT = "Pets";
    */
    //Profile
    String COMPLETE_YOUR_PROFILE = "Complete your profile";
    String COMPLETE_YOUR_PROFILE_CAPSLOCK = "COMPLETE YOUR PROFILE";
    String MODIFY_YOUR_PROFILE_STRING = "Modify your profile";
    String MODIFY_YOUR_PROFILE_STRING_CAPSLOCK = "MODIFY YOUR PROFILE";
    //for orders
    String TOTAL_ORDERS_STRING_CAPSLOCK = "TOTAL ORDERS: ";
    String TOTAL_ORDERS_STRING = "Total orders: ";
    //order status
    String CREATED_STATUS = "Created";
    String IN_PROGRESS_STATUS = "In progress";
    String READY_TO_PICKUP = "Ready to pickup";
    String COMPLETED = "Completed";
    //payment name
    String CREDITCARD_PAYMENT = "Creditcard";
    String CASH_ON_DELIVERY_PAYMENT = "Cash on delivery";
    //for search
    String NEARBY = "nearby";
    String BY_CITY = "by city";
    String BY_NAME = "by name";
    //for currencies
    String EUR = "EUR"; //euro
    String EUR_SYMBOL = "€";
    String USD = "USD"; //us dollar
    String USD_SYMBOL = "$";
    String GBP = "GBP"; //sterling
    String GBP_SYMBOL = "£";
    String BTC = "BTC"; //bitcoin
    String BTC_SYMBOL = "₿";
    //for users
    String ADMIN_USER = "A";
    String BUYER_USER = "B";
    String SHOPHOLDER_USER = "S";
    String ADMIN = "Admin";
    String BUYER = "Buyer";
    String OWNER = "Owner";
    String STANDARD_TO_USE = "12345";
}