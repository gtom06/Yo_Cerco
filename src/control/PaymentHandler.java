package control;

import constants.Constants;

public class PaymentHandler {
    public static boolean validateParams(String payment, String cardNumber, String mm, String yy, String cvv) {
        if (payment.equals(Constants.BY_CARD)){
            if (cardNumber.length() < 16 && mm.length() == 0 && yy.length() == 0 && cvv.length() < 3) {
                return true;
            }
        }
        else if (payment.equals(Constants.COD)){
            return true;
        }
        return false;
    }
}
