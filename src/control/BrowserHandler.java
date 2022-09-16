package control;

import exceptions.ExceptionBrowser;
import model.ConstantsExceptions;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BrowserHandler {
    static final Logger logger = Logger.getLogger(BrowserHandler.class.getName());
    private BrowserHandler(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }
    public static boolean openWebpage(URI uri) throws ExceptionBrowser {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE) && !uri.toString().isBlank()) {
            try {
                desktop.browse(uri);
                return true;
            } catch (IOException e) {
                logger.log(Level.WARNING, "error in BrowserHandler");
                throw new ExceptionBrowser("Not able to open browser");
            }
        }
        return false;
    }
}
