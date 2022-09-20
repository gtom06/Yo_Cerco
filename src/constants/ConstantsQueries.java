package constants;

public final class ConstantsQueries {
    public static final String SELECT_DISTINCT_ALL = "SELECT DISTINCT * ";
    public static final String SELECT_DISTINCT_ALL_FROM_SHOP = "SELECT DISTINCT * FROM shop ";
    public static final String AND_TYPE ="AND type = ?";
    public static final String TWO_VALUES = "VALUES (?, ?)";
    public static final String WHERE_USERNAME = "WHERE username = ?";
    public static final String AND_TIME = "AND CAST(opening_time AS INT) <= ? AND CAST(closing_time AS INT) >= ? ";


    private ConstantsQueries(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }
}