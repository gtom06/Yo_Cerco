package control;

import java.awt.*;
import java.net.URI;

public class BrowserHandler {
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
                e.printStackTrace();
            }
        }
        return false;
    }
}
