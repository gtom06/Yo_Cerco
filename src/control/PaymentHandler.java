package control;

import constants.Constants;
import constants.ConstantsExceptions;

public class PaymentHandler {
    private PaymentHandler(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    public static boolean validateParams(String payment, String cardNumber, String mm, String yy, String cvv) {
        if (payment.equals(Constants.BY_CREDITCARD)){
            if (cardNumber.length() == 16 && mm.length() > 0 && yy.length() > 1 && cvv.length() > 2) {
                return true;
            }
        }
        else if (payment.equals(Constants.COD)){
            return true;
        }
        return false;
    }
}
