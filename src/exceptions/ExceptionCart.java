package exceptions;

import java.io.IOException;

public class ExceptionCart extends IOException {
    private static final long serialVersionUID = 1L;
    public ExceptionCart(String message) {
        super(message);
    }
}
