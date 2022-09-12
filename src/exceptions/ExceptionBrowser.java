package exceptions;

import java.io.IOException;

public class ExceptionBrowser extends IOException {
    private static final long serialVersionUID = 1L;
    public ExceptionBrowser(String message) {
        super(message);
    }
}
