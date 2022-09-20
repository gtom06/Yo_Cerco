package constants;

public final class Queries {
    private Queries(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }
    public static final String UPDATE_BUYER_RECORD = "UPDATE userx " +
            "SET name = ?, surname = ?, billing_street = ?, billing_city = ?, " +
            "billing_country = ?, billing_zip = ?, phone = ?, profile_imagepath = ? " +
            "WHERE username = ?";

    public static final String VALIDATE_LOGIN = "SELECT username, pass " +
            "FROM userx " +
            "WHERE username = ? AND pass = ?";

}
