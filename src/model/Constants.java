package model;

public interface Constants {
    //for google apis
    String GEOCODE_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    String GOOGLE_KEY = "&key=AIzaSyC_HhyhuMjokSEFzXZ4K3jQNkXJlgdBNFQ";
    String SAVE = "Save";
    String SAVE_CAPSLOCK = "SAVE";

    //for file
    String REMEMBER_LOGIN = "src/data/remember_login.txt";
    String CART_PATH = "src/data/cart.json";
    //for images
    String SHOP_IMAGE = "src/images/shop/";

    String LOGO_DEPARTMENT_PATH = "src/images/department/";


    //for shop/product_shop
    //values
    int NEW_OPENING = 0;
    int AVAILABLE = 1;
    int NOT_AVAILABLE = -1;
    String LOGO_SHOPS_PATH = "src/images/shop_images/";
    String LOGO_SHOP_NA = "NotAvailablePhoto.png";
    //String
    String AVAILABLE_STRING = "Available";
    String SOON_BACK_STRING = "Soon back";
    String NOT_AVAILABLE_STRING = "Not available";
    String MY_FAVORITE_SHOPS = "My favorite shops";
    String MY_FAVORITE_SHOPS_CAPSLOCK = "MY FAVORITE SHOPS";
    String ADD_TO_FAVORITE_SHOP = "Add to favorite";
    String ADD_TO_FAVORITE_SHOP_CAPSLOCK = "ADD TO FAVORITE";
    String REMOVE_FROM_FAVORITE_SHOP = "Remove from favorite";
    String REMOVE_FROM_FAVORITE_SHOP_CAPSLOCK = "REMOVE FROM FAVORITE";


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

    //for product type
    String REFRIGERATED = "Freeze";

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

    //app logo
    String APP_LOGO = "../images/shop/icons8-shop-local-96.png";

    //Day of the week
    String MONDAY = "Monday";
    String TUESDAY = "Tuesday";
    String WEDNESDAY = "Wednesday";
    String THURSDAY = "Thursday";
    String FRIDAY = "Friday";
    String SATURDAY = "Saturday";
    String SUNDAY = "Sunday";



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
    String STANDARD_PASSWORD = "12345";


    String GOOGLE_MAPS_LINK_START_STRING = "https://www.google.com/maps/place";
}