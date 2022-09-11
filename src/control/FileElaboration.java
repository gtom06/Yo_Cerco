package control;

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
        br.close();
        return lines;
    }

    public static String fileToString(String filepath) {
        String output = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
            output = bufferedReader.readLine();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return output;
        }
    }

    public static boolean copyAndReplaceFile(File file, String pathTo) {
        try {
            File fileToDelete = new File(pathTo);
            if (fileToDelete != null) {
                file.renameTo(new File(pathTo));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            return true;
        }
    }
}
