package model;

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
}
