package control;

import java.io.*;
import java.util.ArrayList;

public class FileElaboration {
    public static void writeOnFile(String filepath, String stringToWrite) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
        writer.write(stringToWrite);
        writer.close();
    }

    public static ArrayList<String> readFromFile(String filepath) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }
}
