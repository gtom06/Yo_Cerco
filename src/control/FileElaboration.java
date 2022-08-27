package control;

import model.Constants;

import java.io.*;
import java.util.ArrayList;

public class FileElaboration {
    public static void writeOnFile(String filepath, String stringToWrite) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
        writer.write(stringToWrite);
        writer.close();
    }

    public static ArrayList<String> fileLinesToArrayList(String filepath) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }

    public static String fileToString(String filepath) {
        String output = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
            output = bufferedReader.readLine();
        } catch (Exception e) {
            System.out.println(e);
        }
        finally {
            return output;
        }
    }
}
