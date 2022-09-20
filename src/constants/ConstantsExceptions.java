package constants;

public final class ConstantsExceptions {
    private ConstantsExceptions(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    public static final String UTILITY_CLASS_INFO = "Utility class";
    public static final String CART_ELABORATION_FAILURE_INFO = "Cart elaboration failure";
    public static final String CART_ELABORATION_FAILURE_CLOSING_READING_FILE = "Exception while closing file for reading from cart";
    public static final String CART_ELABORATION_FAILURE_CLOSING_WRITING_FILE = "Exception while closing file for writing into cart";
    public static final String FILE_ELABORATION_FAILURE_INFO = "File elaboration failure";
    public static final String FILE_ELABORATION_FAILURE_READING_FILE = "Exception while reading from file";
    public static final String FILE_ELABORATION_FAILURE_WRITING_FILE = "Exception while writing on file";
    public static final String HTTP_REQUEST_INFO = "error in HttpRequest";
    public static final String PRODUCT_DAO_ERROR = "error while finding product";
    public static final String USER_DAO_ERROR = "error in user dao";
    public static final String SHOP_DAO_ERROR = "error in finding shop";
    public static final String DEPARTMENT_DAO_ERROR = "error in finding department";
    public static final String ORDER_DAO_ERROR = "error in order";
    public static final String ORDER_HANDLER_ERROR = "error in order handler";
    public static final String CLOSING_STMT_ERROR = "error while closing stmt";
}
