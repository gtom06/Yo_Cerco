package control;

import model.ConstantsExceptions;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpRequest {
    static Logger logger = Logger.getLogger(HttpRequest.class.getName());
    private HttpRequest(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }
    private static HttpURLConnection conn;
    public static String get(String urlString) {

        BufferedReader reader;
        String line;
        StringBuilder bld = new StringBuilder();
        try{
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();

            // Request setup
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);// 5000 milliseconds = 5 seconds
            conn.setReadTimeout(5000);

            // Test if the response from the server is successful
            int status = conn.getResponseCode();

            if (status >= 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    bld.append(line);
                }
                reader.close();
            }
            else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    bld.append(line);
                }
                reader.close();
            }
            conn.disconnect();
        }
        catch (IOException e) {
            logger.log(Level.WARNING, ConstantsExceptions.HTTP_REQUEST_INFO);
        }
        return bld.toString();

    }
    public static String post(String urlString) {
        BufferedReader reader;
        URL url;
        String line;
        StringBuilder bld = new StringBuilder();
        try {
            url = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, StandardCharsets.UTF_8));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode >= 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    bld.append(line);
                }
                reader.close();
            }
            else {
                bld.append("Error Registering");
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, ConstantsExceptions.HTTP_REQUEST_INFO);
        }
        return bld.toString();
    }
}
