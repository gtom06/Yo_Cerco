package control;

import java.io.*;
import java.util.ArrayList;

public class FileElaboration {
    private FileElaboration(){
        throw new IllegalStateException("Utility class");
    }

    public static void writeOnFile(String filepath, String stringToWrite) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filepath));
            writer.write(stringToWrite);
        } catch (Exception e){
            e.printStackTrace();
        } finally{
            try {
                if (writer != null) {
                    writer.close();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<String> fileLinesToArrayList(String filepath) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null){
                    br.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return lines;
    }

    public static String fileToString(String filepath) {
        String output = "";
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filepath));
            output = bufferedReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (bufferedReader != null){
                    bufferedReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    public static boolean copyAndReplaceFile(File file, String pathTo) {
        try {
            File fileToDelete = new File(pathTo);
            if (fileToDelete != null) {
                if (!file.renameTo(new File(pathTo))){
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
