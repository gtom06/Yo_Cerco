package control;

import java.awt.*;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BrowserHandler {
    static Logger logger = Logger.getLogger(BrowserHandler.class.getName());
    private BrowserHandler(){
        throw new IllegalStateException("Utility class");
    }
    public static boolean openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE) && !uri.toString().isBlank()) {
            try {
                desktop.browse(uri);
                return true;
            } catch (Exception e) {
                logger.log(Level.WARNING, "error in BrowserHandler");
            }
        }
        return false;
    }
}
