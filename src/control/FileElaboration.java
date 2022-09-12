package control;

import exceptions.FileElaborationException;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileElaboration {
    static Logger logger = Logger.getLogger(FileElaboration.class.getName());
    private FileElaboration(){
        throw new IllegalStateException("Utility class");
    }

    public static void writeOnFile(String filepath, String stringToWrite) throws FileElaborationException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filepath));
            writer.write(stringToWrite);
        } catch (Exception e){
            logger.log(Level.WARNING, "error in FileElaboration");
            throw new FileElaborationException("Exception while writing on file");
        } finally{
            try {
                if (writer != null) {
                    writer.close();
                }
            }
            catch (Exception e) {
                logger.log(Level.WARNING, "error in FileElaboration");
            }
        }
    }

    public static ArrayList<String> fileLinesToArrayList(String filepath) throws IOException, FileElaborationException {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "error in FileElaboration");
            throw new FileElaborationException("Exception while writing on file");
        } finally {
            try {
                if (br != null){
                    br.close();
                }
            } catch (Exception e){
                logger.log(Level.WARNING, "error in FileElaboration");
            }
        }
        return lines;
    }

    public static String fileToString(String filepath) throws FileElaborationException {
        String output = "";
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filepath));
            output = bufferedReader.readLine();
        } catch (Exception e) {
            logger.log(Level.WARNING, "error in FileElaboration");
            throw new FileElaborationException("Exception while reading from file");
        }
        finally {
            try {
                if (bufferedReader != null){
                    bufferedReader.close();
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, "error in FileElaboration");
            }
        }
        return output;
    }

    public static boolean copyAndReplaceFile(File file, String pathTo) throws FileElaborationException {
        try {
            File fileToDelete = new File(pathTo);
            if (fileToDelete != null) {
                if (!file.renameTo(new File(pathTo))){
                    return false;
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "error in FileElaboration");
            throw new FileElaborationException("Exception while writing on file");
        }
        return true;
    }
}
