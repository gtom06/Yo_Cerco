package control;

import exceptions.FileElaborationException;
import model.ConstantsExceptions;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileElaboration {
    static Logger logger = Logger.getLogger(FileElaboration.class.getName());
    private FileElaboration(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    public static void writeOnFile(String filepath, String stringToWrite) throws FileElaborationException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filepath));
            writer.write(stringToWrite);
        } catch (Exception e){
            logger.log(Level.WARNING, ConstantsExceptions.FILE_ELABORATION_FAILURE_INFO);
            throw new FileElaborationException(ConstantsExceptions.FILE_ELABORATION_FAILURE_WRITING_FILE);
        } finally{
            try {
                if (writer != null) {
                    writer.close();
                }
            }
            catch (Exception e) {
                logger.log(Level.WARNING, ConstantsExceptions.FILE_ELABORATION_FAILURE_INFO);
            }
        }
    }

    public static ArrayList<String> fileLinesToArrayList(String filepath) throws FileElaborationException {
        List<String> lines = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            if (br != null){
                br.close();
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, ConstantsExceptions.FILE_ELABORATION_FAILURE_INFO);
            throw new FileElaborationException(ConstantsExceptions.FILE_ELABORATION_FAILURE_WRITING_FILE);
        }
        return (ArrayList<String>) lines;
    }

    public static String fileToString(String filepath) throws FileElaborationException {
        String output;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filepath));
            output = bufferedReader.readLine();
            if (bufferedReader != null){
                bufferedReader.close();
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, ConstantsExceptions.FILE_ELABORATION_FAILURE_INFO);
            throw new FileElaborationException(ConstantsExceptions.FILE_ELABORATION_FAILURE_READING_FILE);
        }
        return output;
    }

    public static boolean copyAndReplaceFile(File file, String pathTo) throws FileElaborationException {
        try {
            if (!file.renameTo(new File(pathTo))){
                return false;
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, ConstantsExceptions.FILE_ELABORATION_FAILURE_INFO);
            throw new FileElaborationException(ConstantsExceptions.FILE_ELABORATION_FAILURE_WRITING_FILE);
        }
        return true;
    }
}
