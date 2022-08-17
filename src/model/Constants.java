package model;

public interface Constants {
    //for file
    String REMEMBER_LOGIN = "src/data/remember_login.txt";
    String CART_PATH = "src/data/cart.txt";
    //for images
    String SHOP_IMAGE = "src/images/shop/";
    String DEPARTMENT_IMAGE = "src/images/department/";
    //for shop/product_shop
    int NEW_OPENING = 0;
    int AVAILABLE = 1;
    int NOT_AVAILABLE = -1;
    String AVAILABLE_STRING = "Available";
    String SOON_BACK_STRING = "Soon back";
    String NOT_AVAILABLE_STRING = "Not available";

    //for product type
    String REFRIGERATED = "Freeze";

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



    String BY_ADDRESS = "by address";
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
}

