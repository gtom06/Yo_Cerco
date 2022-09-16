package model.Dao;

import model.ConstantsExceptions;

import java.util.ArrayList;

public class DaoHelper {
    private DaoHelper(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }

    public static String buildSqlStringFromArrayOfStrings(ArrayList<String> stringArrayList){
        String output = "(";
        int lenght = stringArrayList.size();
        for (String s : stringArrayList){

        }
        return output + ")";
    }

    public static String buildSqlStringFromArrayOfIntegers(ArrayList<Integer> integerArrayList){ // (?,?,?)
        String output = "(";
        int lenght = integerArrayList.size();
        for (int i = 0; i < lenght; i++){
            if (i == lenght - 1) {
                output += integerArrayList.get(i);
                break;
            }
            output += integerArrayList.get(i) + ",";
        }
        return output + ")";
    }
}
